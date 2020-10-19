package com.alibelkadhi.http;

public class HttpResponseImpl implements HttpResponse {

    private HttpVersion version;
    private HttpResponseStatus responseStatus;

    private HttpHeaders httpHeaders;

    private String body;

    public HttpResponseImpl(HttpVersion version, HttpResponseStatus responseStatus, HttpHeaders httpHeaders, String body) {
        this.version = version;
        this.responseStatus = responseStatus;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    @Override
    public HttpResponseStatus getResponseStatus() {
        return this.responseStatus;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public HttpVersion getHttpVersion() {
        return version;
    }
}
