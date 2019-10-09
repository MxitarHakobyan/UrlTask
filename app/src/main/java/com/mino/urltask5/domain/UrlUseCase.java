package com.mino.urltask5.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.data.repos.OrderType;
import com.mino.urltask5.data.repos.UrlRepository;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;
import com.mino.urltask5.utils.UrlModelMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class  UrlUseCase implements BaseUseCaseBehaivor {

    @Inject
    UrlRepository urlRepository;

    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    public UrlUseCase() {
    }

    public void insert(final UrlEntity urlEntity) {
        compositeDisposable.add(urlRepository.insert(urlEntity));
    }

    public void update(final UrlEntity urlEntity) {
        compositeDisposable.add(urlRepository.update(urlEntity));
    }

    public void delete(final UrlEntity urlEntity) {
        compositeDisposable.add(urlRepository.delete(urlEntity));
    }

    public LiveData<List<UrlModel>> getUrlsOrderBy(final OrderType orderType) {
        return LiveDataReactiveStreams.fromPublisher(urlRepository.getUrlsOrderBy(orderType)
                .map(UrlModelMapper::convert2UrlModel)
        );
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }
}
