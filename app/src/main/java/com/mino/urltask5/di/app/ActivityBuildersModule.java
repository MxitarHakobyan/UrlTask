package com.mino.urltask5.di.app;

import com.mino.urltask5.di.main.MainModule;
import com.mino.urltask5.di.main.PerMainActivity;
import com.mino.urltask5.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @PerMainActivity
    @ContributesAndroidInjector(modules = {
            MainModule.class,
    })
    abstract MainActivity contributeMainActivity();
}
