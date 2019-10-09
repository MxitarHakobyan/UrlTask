package com.mino.urltask5.data.remote;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface UrlApi {

    @GET()
    Single<Response<ResponseBody>> getResponse(@Url String url);
}
