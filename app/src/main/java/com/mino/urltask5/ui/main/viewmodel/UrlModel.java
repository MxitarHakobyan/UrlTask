package com.mino.urltask5.ui.main.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.Objects;

import static com.mino.urltask5.utils.Constants.URL_LOADING;
import static com.mino.urltask5.utils.Constants.URL_UNAVAILABLE;

public class UrlModel {

    private ObservableField<String> url;
    private ObservableInt availability;

    public UrlModel(final @NonNull String url,
                    final int availability) {
        this.url = new ObservableField<>(url);
        this.availability = new ObservableInt(availability);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlModel urlModel = (UrlModel) o;
        return availability.get() == urlModel.availability.get() &&
                Objects.equals(url.get(), urlModel.url.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, availability);
    }

    public UrlModel resetState(final UrlModel urlModel) {
        urlModel.availability.set(URL_LOADING);
        return urlModel;
    }

    public boolean isLoading() {
        return availability.get() == URL_LOADING;
    }

    public ObservableField<String> getUrl() {
        return url;
    }

    public ObservableInt getAvailability() {
        return availability;
    }
}
