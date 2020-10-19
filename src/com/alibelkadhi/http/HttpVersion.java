package com.alibelkadhi.http;

import java.util.HashMap;
import java.util.Map;

public enum HttpVersion {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0"),
    HTTP_3_0("HTTP/3.0");

    private String value;
    private static Map<String,HttpVersion> map = new HashMap<String, HttpVersion>();

    static {
        for (HttpVersion version : HttpVersion.values()) {
            map.put(version.value,version);
        }
    }

    public static HttpVersion getByValue(String value) {
        return map.get(value);
    }

    HttpVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
