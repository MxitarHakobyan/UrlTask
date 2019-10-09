package com.mino.urltask5.utils;

import android.util.Log;

import com.mino.urltask5.data.remote.entity.RequestInfo;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.mino.urltask5.utils.Constants.URL_AVAILABLE;
import static com.mino.urltask5.utils.Constants.URL_UNAVAILABLE;

public abstract class ResponseUtil {

    private static final String TAG = "ResponseUtil";

    private static final int FIRST_CHAR = 0;

    public static RequestInfo Response2RequestInfo(final Response<ResponseBody> response) {
        return new RequestInfo(response.raw().sentRequestAtMillis(), response.code());
    }

    public static int isResponseValid(final int code) {
        if (String.valueOf(code).charAt(FIRST_CHAR) == '2') {
            Log.d(TAG, "isResponseValid: code=  " + code);
            return URL_AVAILABLE;
        }else {
            return URL_UNAVAILABLE;
        }
    }
}
