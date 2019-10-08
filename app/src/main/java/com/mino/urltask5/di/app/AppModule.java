package com.mino.urltask5.di.app;

import android.app.Application;

import androidx.room.Room;

import com.mino.urltask5.data.db.UrlDao;
import com.mino.urltask5.data.db.UrlDb;
import com.mino.urltask5.data.repos.UrlRepository;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {


    @PerApplication
    @Provides
    static UrlDb provideUrlDb(final Application application) {
        return Room.databaseBuilder(application,
                        UrlDb.class,
                        "url.db")
                .fallbackToDestructiveMigration()
                .build();

    }

    @PerApplication
    @Provides
    static UrlDao provideUrlDao(final UrlDb urlDb) {
        return urlDb.urlDao();
    }

    @PerApplication
    @Provides
    static UrlRepository provideRepository(final UrlDao dao) {
        return new UrlRepository(dao);
    }
}
