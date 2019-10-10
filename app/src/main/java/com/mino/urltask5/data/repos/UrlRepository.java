package com.mino.urltask5.data.repos;

import com.mino.urltask5.data.db.dao.UrlDao;
import com.mino.urltask5.data.db.entity.UrlEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class UrlRepository {

    private UrlDao urlDao;

    @Inject
    public UrlRepository(UrlDao urlDao) {
        this.urlDao = urlDao;
    }

    public void insert(final UrlEntity urlEntity) {
        urlDao.insert(urlEntity);
    }

    public void update(final UrlEntity urlEntity) {
        urlDao.update(urlEntity);
    }

    public void delete(final String url) {
        urlDao.delete(url);
    }

    public Flowable<List<UrlEntity>> getUrlsOrderByUrl() {
        return urlDao.getUrlsOrderByUrl();
    }

    public Flowable<List<UrlEntity>> getUrlsOrderByAvailability() {
        return urlDao.getUrlsOrderByAvailability();
    }

    public Flowable<List<UrlEntity>> getUrlsOrderByLoadingTime() {
        return urlDao.getUrlsOrderByLoadingTime();
    }
}
