package com.mino.urltask5.domain;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.data.repos.UrlRepository;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;
import com.mino.urltask5.utils.OrderType;
import com.mino.urltask5.utils.UrlModelMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UrlUseCase extends BaseUseCase implements BaseUseCaseBehaviour {

    private UrlRepository urlRepository;
    private CompositeDisposable compositeDisposable;

    @Inject
    UrlUseCase(final UrlRepository urlRepository,
               final CompositeDisposable compositeDisposable) {

        this.urlRepository = urlRepository;
        this.compositeDisposable = compositeDisposable;
    }

    public void insert(final UrlEntity urlEntity) {
        execute(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },
                () -> urlRepository.insert(urlEntity));
    }

    public void update(final UrlEntity urlEntity) {
        execute(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },
                () -> urlRepository.update(urlEntity));

    }

    public void delete(final String url) {
        execute(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },
                () -> urlRepository.delete(url));
    }

    public Flowable<List<UrlModel>> getUrlsOrderBy(final OrderType orderType) {

        if (orderType == OrderType.URL) {
            return urlRepository.getUrlsOrderByUrl()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(UrlModelMapper::convert2UrlModel);
        } else if (orderType == OrderType.AVAILABILITY) {
            return urlRepository.getUrlsOrderByAvailability()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(UrlModelMapper::convert2UrlModel);
        } else if (orderType == OrderType.TIME) {
            return urlRepository.getUrlsOrderByLoadingTime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(UrlModelMapper::convert2UrlModel);
        } else {
            return urlRepository.getUrlsOrderByUrl()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(UrlModelMapper::convert2UrlModel);
        }
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }
}
