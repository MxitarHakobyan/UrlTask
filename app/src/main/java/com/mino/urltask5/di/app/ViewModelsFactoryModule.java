package com.mino.urltask5.di.app;

import androidx.lifecycle.ViewModelProvider;

import com.mino.urltask5.ui.common.viewmodels_factory.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelsFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);
}
