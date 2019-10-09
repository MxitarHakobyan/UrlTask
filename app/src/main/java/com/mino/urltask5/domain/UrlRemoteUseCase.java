package com.mino.urltask5.domain;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.data.repos.UrlRemoteRepository;
import com.mino.urltask5.utils.ResponseMapper;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class UrlRemoteUseCase implements BaseUseCaseBehaivor {

    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    UrlRemoteRepository repository;

    @Inject
    public UrlRemoteUseCase() {
    }

    public Single<UrlEntity> checkUrl(final String url) {
        return repository.getHeaderObservable(url)
                .map(ResponseMapper::Response2RequestInfo)
                .map(requestInfo -> new UrlEntity(url,
                        ResponseMapper.isRequestValid(requestInfo.getCode()),
                        requestInfo.getTime())
                );
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
