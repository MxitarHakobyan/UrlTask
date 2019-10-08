package com.mino.urltask5.di.app;

import android.app.Application;

import com.mino.urltask5.BaseApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@PerApplication
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuildersModule.class,
        ViewModelsFactoryModule.class,
        AppModule.class,
})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(final Application application);

        AppComponent build();
    }
}
