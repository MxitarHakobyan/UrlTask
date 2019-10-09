package com.mino.urltask5.data.remote;

public class RequestInfo {

    private long time;
    private int code;

    public RequestInfo(final long time,
                       final int code) {
        this.time = time;
        this.code = code;
    }

    public long getTime() {
        return time;
    }

    public int getCode() {
        return code;
    }
}
