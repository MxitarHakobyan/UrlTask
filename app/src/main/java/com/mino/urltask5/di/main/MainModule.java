package com.mino.urltask5.di.main;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.mino.urltask5.di.app.ViewModelKey;
import com.mino.urltask5.ui.main.viewmodel.UrlViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class MainModule {

    @Provides
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Binds
    @IntoMap
    @ViewModelKey(UrlViewModel.class)
    public abstract ViewModel bindUrlViewModel(UrlViewModel urlViewModel);
}
