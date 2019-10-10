package com.mino.urltask5.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mino.urltask5.R;
import com.mino.urltask5.utils.OrderType;
import com.mino.urltask5.databinding.ActivityMainBinding;
import com.mino.urltask5.ui.common.binding.ClickHandler;
import com.mino.urltask5.ui.common.viewmodels_factory.ViewModelProviderFactory;
import com.mino.urltask5.ui.common.binding.SwipeHandler;
import com.mino.urltask5.ui.main.adaptors.UrlAdapter;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;
import com.mino.urltask5.ui.main.viewmodel.UrlViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements SwipeHandler {

    @Inject
    UrlAdapter adapter;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    ClickHandler clickHandler;

    private UrlViewModel viewModel;
    private ActivityMainBinding binding;
    private RecyclerView rvUrls;
    private List<UrlModel> models = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(UrlViewModel.class);

        binding.setViewmodel(viewModel);
        binding.setClickHandler(clickHandler);
        binding.setLifecycleOwner(this);
        binding.setSwipeHandler(this);

        refreshLayout = findViewById(R.id.swipRefresh);

        refreshLayout.setOnRefreshListener(() -> {
            viewModel.reCheck(models);
            refreshLayout.setRefreshing(false);
        });


        rvUrls = findViewById(R.id.rvUrls);
        rvUrls.setLayoutManager(new LinearLayoutManager(this));
        rvUrls.setAdapter(adapter);

        getByUrls(OrderType.URL);

        viewModel.error.observe(this, s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.url : {
                getByUrls(OrderType.URL);
                break;
            }
            case R.id.availability : {
                getByUrls(OrderType.AVAILABILITY);
                break;
            }
            case R.id.loadingTime : {
                getByUrls(OrderType.TIME);
                break;
            }
        }
        return true;
    }

    private void getByUrls(final OrderType orderType) {
        viewModel.getUrlsOrderBy(orderType).observe(this, urlModels -> {
            adapter.setFullUrlModels(urlModels);
            models = urlModels;
        });
    }

    @Override
    public void onItemSwipedLeft(final int position) {
        deleteUrl(position);
    }

    @Override
    public void onItemSwipedRight(final int position) {
        deleteUrl(position);
    }

    private void deleteUrl(final int position) {
        UrlModel model = models.get(position);
        if (model.isLoading()) {
            showAlertDialog(position);
        }else {
            viewModel.delete(models.get(position).getUrl().get());
        }
    }

    private void showAlertDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("The url is loading!")
                .setMessage("Do you want to delete?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
            viewModel.delete(models.get(position).getUrl().get());
        });
    }
}
