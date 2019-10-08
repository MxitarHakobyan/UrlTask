package com.mino.urltask5.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "urlsTable")
public class UrlEntity {

    @NonNull
    @PrimaryKey
    private String url;

    private int availability;

    private int loadingTime;

    public UrlEntity(@NonNull final String url,
                     final int availability,
                     final int loadingTime) {
        this.url = url;
        this.availability = availability;
        this.loadingTime = loadingTime;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public int getAvailability() {
        return availability;
    }

    public int getLoadingTime() {
        return loadingTime;
    }
}
