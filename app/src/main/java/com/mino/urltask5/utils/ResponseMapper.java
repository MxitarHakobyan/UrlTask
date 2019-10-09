package com.mino.urltask5.utils;

import com.mino.urltask5.data.remote.RequestInfo;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.mino.urltask5.utils.Constants.URL_AVAILABLE;
import static com.mino.urltask5.utils.Constants.URL_UNAVAILABLE;

public abstract class ResponseMapper {

    private static final int FIRST_CHAR = 0;

    public static RequestInfo Response2RequestInfo(final Response<ResponseBody> response) {
        return new RequestInfo(0, response.code());
    }

    public static int isRequestValid(final int code) {
        if (String.valueOf(code).charAt(FIRST_CHAR) == '2') {
            return URL_AVAILABLE;
        }else {
            return URL_UNAVAILABLE;
        }
    }
}
