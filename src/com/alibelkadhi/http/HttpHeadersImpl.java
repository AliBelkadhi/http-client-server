package com.alibelkadhi.http;

import java.util.*;

public class HttpHeadersImpl implements HttpHeaders {

    private Map<String,List<String>> headers = new HashMap<String, List<String>>();
    private List<HttpHeader> headers2 = new LinkedList<>();

    @Override
    public Optional<String> firstValue(String key) {

        List<String> l = headers.get(key);
        return  Optional.ofNullable(l == null ? null : l.get(0));

    }

    @Override
    public Optional<List<String>> allValues(String key) {

        return Optional.of(headers.get(key));
    }

    @Override
    public Map<String, List<String>> getAllHeaders() {
        return headers;
    }



}
