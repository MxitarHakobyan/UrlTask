package com.mino.urltask5.ui.main;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mino.urltask5.R;
import com.mino.urltask5.databinding.ActivityMainBinding;
import com.mino.urltask5.ui.common.binding.ClickHandler;
import com.mino.urltask5.ui.common.viewmodels_factory.ViewModelProviderFactory;
import com.mino.urltask5.ui.main.adaptors.UrlAdapter;
import com.mino.urltask5.ui.main.viewmodel.UrlViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    UrlAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    ClickHandler clickHandler;

    private UrlViewModel viewModel;
    private ActivityMainBinding binding;
    private RecyclerView rvUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(UrlViewModel.class);

        binding.setViewmodel(viewModel);
        binding.setHandler(clickHandler);
        binding.setLifecycleOwner(this);

        rvUrls = findViewById(R.id.rvUrls);
        rvUrls.setLayoutManager(new LinearLayoutManager(this));
        rvUrls.setAdapter(adapter);

        viewModel.getUrlsOrderByUrl().observe(this, urlModels -> {
            adapter.updateUrlsList(urlModels);
        });

    }
}
