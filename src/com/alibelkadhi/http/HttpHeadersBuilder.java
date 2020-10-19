package com.alibelkadhi.http;

import java.util.*;

public class HttpHeadersBuilder {

    private HttpHeaders headers;

    public static HttpHeadersBuilder newBuilder() {
        return new HttpHeadersBuilder();
    }

    public HttpHeadersBuilder() {
        this.headers = new HttpHeadersImpl();
    }

    public HttpHeadersBuilder addHeader(String key,String value) {
        List<String> list = this.headers.getAllHeaders().get(key);
        if (list == null) {
            list = new LinkedList<String>();
            this.headers.getAllHeaders().put(key,list);
        }
        list.add(value);
        return this;
    }

    public HttpHeadersBuilder setHeader(String key,String value) {
        List<String> l = new LinkedList<String>();
        l.add(value);
        this.headers.getAllHeaders().put(key,l);
        return this;
    }


    public HttpHeadersBuilder setHeader(String key,List<String> values) {
        this.headers.getAllHeaders().put(key,values);
        return this;
    }

    public HttpHeadersBuilder copyOf(HttpHeaders headers) {

        HttpHeadersBuilder builder = new HttpHeadersBuilder();
        for (Map.Entry<String,List<String>> set : headers.getAllHeaders().entrySet()) {
            builder.setHeader(set.getKey(),set.getValue());
        }

        return builder;

    }

    public HttpHeaders build() {
        return this.headers;
    }
}
