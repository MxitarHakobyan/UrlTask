package com.mino.urltask5.data.remote;

import io.reactivex.Maybe;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface UrlApi {

    @GET()
    Maybe<Response<ResponseBody>> getResponse(@Url final String url);
}
