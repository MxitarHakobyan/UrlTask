package com.mino.urltask5.ui.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.domain.UrlRemoteUseCase;
import com.mino.urltask5.domain.UrlUseCase;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.mino.urltask5.utils.Constants.URL_LOADING;

public class UrlViewModel extends ViewModel {

    public MutableLiveData<String> insertUrl = new MutableLiveData<>();

    @Inject
    UrlUseCase urlUseCase;

    @Inject
    UrlRemoteUseCase remoteUseCase;

    @Inject
    CompositeDisposable compositeDisposable;


    @Inject
    public UrlViewModel() {
    }

    public void insert() {
        urlUseCase.insert(new UrlEntity(Objects.requireNonNull(insertUrl.getValue()), URL_LOADING, 0));
        checkUrl();
    }

    private void checkUrl() {
        compositeDisposable.add(remoteUseCase.checkUrl(insertUrl.getValue())
                .subscribe(this::update));
    }

    public void update(final UrlEntity url) {
        urlUseCase.update(url);
    }

    public void delete(final UrlEntity url) {
        urlUseCase.delete(url);
    }

    public LiveData<List<UrlModel>> getUrlsOrderByUrl() {
        return urlUseCase.getUrlsOrderByUrl();
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
