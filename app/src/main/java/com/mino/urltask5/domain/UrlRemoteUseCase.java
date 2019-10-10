package com.mino.urltask5.domain;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.data.repos.UrlRemoteRepository;
import com.mino.urltask5.utils.ResponseUtil;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UrlRemoteUseCase implements BaseUseCaseBehaviour {

    private UrlRemoteRepository repository;
    private CompositeDisposable compositeDisposable;

    @Inject
    public UrlRemoteUseCase(final CompositeDisposable compositeDisposable,
                            final UrlRemoteRepository repository) {
        this.compositeDisposable = compositeDisposable;
        this.repository = repository;
    }

    public Maybe<UrlEntity> checkUrl (final String url) {
        return repository.getResponseObservable(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(ResponseUtil::Response2RequestInfo)
                .map(requestInfo -> new UrlEntity(url,
                        ResponseUtil.isResponseValid(requestInfo.getCode()),
                        requestInfo.getTime())
                );
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }
}
