package com.mino.urltask5.ui.main.viewmodel;

import androidx.annotation.NonNull;

import java.util.Objects;

public class UrlModel {

    private String url;
    private int availability;

    public UrlModel(final @NonNull String url,
                    final int availability) {
        this.url = url;
        this.availability = availability;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlModel urlModel = (UrlModel) o;
        return availability == urlModel.availability &&
                Objects.equals(url, urlModel.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, availability);
    }

    public String getUrl() {
        return url;
    }

    public int getAvailability() {
        return availability;
    }
}
