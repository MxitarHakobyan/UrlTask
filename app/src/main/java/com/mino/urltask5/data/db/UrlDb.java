package com.mino.urltask5.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mino.urltask5.data.db.entity.UrlEntity;

@Database(entities = {UrlEntity.class}, version = 1, exportSchema = false)
public abstract class UrlDb extends RoomDatabase {

    public abstract UrlDao urlDao();
}
