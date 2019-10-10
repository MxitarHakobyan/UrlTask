package com.mino.urltask5.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mino.urltask5.R;
import com.mino.urltask5.databinding.ActivityMainBinding;
import com.mino.urltask5.ui.common.binding.ClickHandler;
import com.mino.urltask5.ui.common.binding.SwipeHandler;
import com.mino.urltask5.ui.common.viewmodels_factory.ViewModelProviderFactory;
import com.mino.urltask5.ui.main.adaptors.UrlAdapter;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;
import com.mino.urltask5.ui.main.viewmodel.UrlViewModel;
import com.mino.urltask5.utils.OrderType;

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
    private RecyclerView rvUrls;
    private List<UrlModel> models = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        findViews();
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        initSearchView();

        viewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(UrlViewModel.class);

        binding.setViewmodel(viewModel);
        binding.setClickHandler(clickHandler);
        binding.setLifecycleOwner(this);
        binding.setSwipeHandler(this);

        refreshLayout.setOnRefreshListener(() -> {
            viewModel.reCheck(models);
            refreshLayout.setRefreshing(false);
        });

        initRecyclerView();
        getByUrls(OrderType.URL);

        viewModel.error.observe(this, s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show());
    }

    private void findViews() {
        refreshLayout = findViewById(R.id.swipRefresh);
        toolbar = findViewById(R.id.toolbar);
        rvUrls = findViewById(R.id.rvUrls);
    }

    private void initSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
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
    }

    private void initRecyclerView() {
        rvUrls.setLayoutManager(new LinearLayoutManager(this));
        rvUrls.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
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
