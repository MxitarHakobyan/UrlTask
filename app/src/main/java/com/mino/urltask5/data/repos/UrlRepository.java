package com.mino.urltask5.data.repos;

import com.mino.urltask5.data.db.UrlDao;
import com.mino.urltask5.data.db.entity.UrlEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UrlRepository {

    private UrlDao urlDao;

    @Inject
    public UrlRepository(UrlDao urlDao) {
        this.urlDao = urlDao;
    }

    public Disposable insert(final UrlEntity urlEntity) {
        return Observable.just(urlEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(urlEntity1 -> urlDao.insert(urlEntity1));
    }

    public Disposable update(final UrlEntity urlEntity) {
        return Observable.just(urlEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(urlEntity1 -> urlDao.update(urlEntity1));
    }

    public Disposable delete(final UrlEntity urlEntity) {
        return Observable.just(urlEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(urlEntity1 -> urlDao.delete(urlEntity1));
    }

    public Flowable<List<UrlEntity>> getUrlsOrderBy(final OrderType orderType) {

        if (orderType == OrderType.URL) {
            return urlDao.getUrlsOrderByUrl()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        } else if (orderType == OrderType.AVAILABILITY) {
            return urlDao.getUrlsOrderByAvailability()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        } else if (orderType == OrderType.TIME) {
            return urlDao.getUrlsOrderByLoadingTime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        } else {
            return urlDao.getUrlsOrderByUrl()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }
    }

}
