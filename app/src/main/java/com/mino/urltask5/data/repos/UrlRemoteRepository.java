package com.mino.urltask5.data.repos;

import com.mino.urltask5.data.remote.UrlApi;

import javax.inject.Inject;

import io.reactivex.Maybe;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class UrlRemoteRepository {

    private UrlApi api;

    @Inject
    UrlRemoteRepository(final UrlApi api) {
        this.api = api;
    }

    public Maybe<Response<ResponseBody>> getResponseObservable(final String url) {
        return api.getResponse(url);
    }
}
