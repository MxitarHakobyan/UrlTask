package com.mino.urltask5.ui.main.viewmodel;

import android.webkit.URLUtil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.domain.UrlRemoteUseCase;
import com.mino.urltask5.domain.UrlUseCase;
import com.mino.urltask5.utils.OrderType;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.mino.urltask5.utils.Constants.URL_LOADING;
import static com.mino.urltask5.utils.Constants.URL_UNAVAILABLE;

public class UrlViewModel extends ViewModel {

    public MutableLiveData<String> insertUrl = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();


    private UrlUseCase urlUseCase;
    private UrlRemoteUseCase remoteUseCase;


    @Inject
    UrlViewModel(final UrlUseCase urlUseCase,
                 final UrlRemoteUseCase remoteUseCase) {

        this.urlUseCase = urlUseCase;
        this.remoteUseCase = remoteUseCase;
    }

    public void insert() {
        if (URLUtil.isValidUrl(insertUrl.getValue())) {
            urlUseCase.insert(new UrlEntity(Objects.requireNonNull(insertUrl.getValue()), URL_LOADING, 0));
        } else {
            error.postValue("Not Valid Url");
        }
    }


    public void reCheck(final List<UrlModel> models) {
        urlUseCase.reCheck(models);
    }

    public void delete(final String url) {
        urlUseCase.delete(url);
    }

    public LiveData<List<UrlModel>> getUrlsOrderBy(final OrderType order) {
        return LiveDataReactiveStreams.fromPublisher(urlUseCase.getUrlsOrderBy(order));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        remoteUseCase.unsubscribe();
        urlUseCase.unsubscribe();
    }
}
