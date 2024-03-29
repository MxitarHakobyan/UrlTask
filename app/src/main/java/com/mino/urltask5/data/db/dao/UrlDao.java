package com.mino.urltask5.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mino.urltask5.data.db.entity.UrlEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UrlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(final UrlEntity urlEntity);

    @Update
    void update(final UrlEntity urlEntity);

    @Query("DELETE FROM urlsTable WHERE url = :url")
    void delete(final String url);

    @Query("SELECT * FROM urlsTable ORDER BY url ASC")
    Flowable<List<UrlEntity>> getUrlsOrderByUrl();

    @Query("SELECT * FROM urlsTable ORDER BY availability ASC")
    Flowable<List<UrlEntity>> getUrlsOrderByAvailability();

    @Query("SELECT * FROM urlsTable ORDER BY loadingTime ASC")
    Flowable<List<UrlEntity>> getUrlsOrderByLoadingTime();
}
