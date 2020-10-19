package com.alibelkadhi.http;

import java.util.HashMap;
import java.util.Map;

public enum HttpResponseStatus {
    OK(200),
    CREATED(201),
    MOVED_PERMANENTLY(301),
    FOUND(302),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    INTERNAL_SERVER_ERROR(500);

    private int value;
    private static Map<Integer,HttpResponseStatus> map = new HashMap<Integer, HttpResponseStatus>();

    static {

        for (HttpResponseStatus status : HttpResponseStatus.values()) {
            map.put(status.value,status);
        }


    }

    public static HttpResponseStatus valueOf(int value) {
        return map.get(value);
    }

    HttpResponseStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
