package com.mino.urltask5.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void delete(final UrlEntity urlEntity);

    @Query("SELECT * FROM urlsTable ORDER BY url ASC")
    Flowable<List<UrlEntity>> getUrlsOrderByUrl();
}
