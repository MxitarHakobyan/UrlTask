package com.mino.urltask5.domain;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.data.repos.UrlRepository;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;
import com.mino.urltask5.utils.OrderType;
import com.mino.urltask5.utils.UrlModelMapper;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.mino.urltask5.utils.Constants.URL_UNAVAILABLE;

public class UrlUseCase extends BaseUseCase implements BaseUseCaseBehaviour {

    private UrlRepository urlRepository;
    private CompositeDisposable compositeDisposable;
    private UrlRemoteUseCase remoteUseCase;

    @Inject
    UrlUseCase(final UrlRepository urlRepository,
               final UrlRemoteUseCase remoteUseCase,
               final CompositeDisposable compositeDisposable) {

        this.urlRepository = urlRepository;
        this.remoteUseCase = remoteUseCase;
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
                        remoteUseCase.checkUrl(urlEntity.getUrl())
                                .subscribe(getMaybeObserver(urlEntity.getUrl()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },
                () -> urlRepository.insert(urlEntity));
    }

    public void reCheck(final List<UrlModel> models) {
        compositeDisposable.add(Observable.fromIterable(models)
                .map(urlModel -> urlModel.resetState(urlModel))
                .map(urlModel -> urlModel.getUrl().get())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(url -> remoteUseCase.checkUrl(url).subscribe(getMaybeObserver(url)))
        );
    }

    private MaybeObserver<UrlEntity> getMaybeObserver(final String url) {
        return new MaybeObserver<UrlEntity>() {
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
                update(new UrlEntity(Objects.requireNonNull(url), URL_UNAVAILABLE, 0));
            }

            @Override
            public void onComplete() {

            }
        };
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
