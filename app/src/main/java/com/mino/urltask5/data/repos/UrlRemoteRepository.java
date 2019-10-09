package com.mino.urltask5.data.repos;

import com.mino.urltask5.data.remote.UrlApi;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class UrlRemoteRepository {

    private UrlApi api;

    @Inject
    public UrlRemoteRepository(final UrlApi api) {
        this.api = api;
    }

    public Single<Response<ResponseBody>> getHeaderObservable(final String url) {
        return api.getResponse(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
