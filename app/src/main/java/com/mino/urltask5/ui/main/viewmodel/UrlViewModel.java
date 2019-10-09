package com.mino.urltask5.ui.main.viewmodel;

import android.util.Log;
import android.webkit.URLUtil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.data.repos.OrderType;
import com.mino.urltask5.domain.UrlRemoteUseCase;
import com.mino.urltask5.domain.UrlUseCase;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.mino.urltask5.utils.Constants.URL_AVAILABLE;
import static com.mino.urltask5.utils.Constants.URL_LOADING;
import static com.mino.urltask5.utils.Constants.URL_UNAVAILABLE;

public class UrlViewModel extends ViewModel {

    public MutableLiveData<String> insertUrl = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

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
        if (URLUtil.isValidUrl(insertUrl.getValue())) {
            urlUseCase.insert(new UrlEntity(Objects.requireNonNull(insertUrl.getValue()), URL_LOADING, 0));
            checkUrl();
        }else {
            error.postValue("Not Valid Url");
        }
    }

    private void checkUrl() {
        remoteUseCase.checkUrl(insertUrl.getValue())
                .subscribe(new MaybeObserver<UrlEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(UrlEntity entity) {
                        update(entity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        error.postValue(e.getMessage());
                        update(new UrlEntity(insertUrl.getValue(), URL_UNAVAILABLE, 0));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void update(final UrlEntity url) {
        urlUseCase.update(url);
    }

    public void delete(final UrlEntity url) {
        urlUseCase.delete(url);
    }

    public LiveData<List<UrlModel>> getUrlsOrderBy(final OrderType order) {
        return urlUseCase.getUrlsOrderBy(order);
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
