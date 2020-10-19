package com.alibelkadhi.http.server.executor;

import com.alibelkadhi.http.HttpHeaders;
import com.alibelkadhi.http.HttpResponse;
import com.alibelkadhi.http.HttpResponseStatus;
import com.alibelkadhi.http.HttpVersion;

import java.io.Writer;

public class HttpExecutorResponseImpl implements HttpExecutorResponse {
    private HttpResponse response;
    private StringBuilder builder;
    private HttpResponseStatus status;
    private String contentType;

    public HttpExecutorResponseImpl(HttpResponse response, StringBuilder builder,String contentType) {
        this.response = response;
        this.builder = builder;
        this.status = response.getResponseStatus();
        this.contentType = contentType;
    }

    @Override
    public StringBuilder getBuilder() {
        return this.builder;
    }

    @Override
    public void setResponseStatus(HttpResponseStatus status) {
        this.status = status;
    }

    @Override
    public HttpVersion getHttpVersion() {
        return response.getHttpVersion();
    }

    @Override
    public HttpResponseStatus getResponseStatus() {
        return status;
    }

    @Override
    public HttpHeaders getHeaders() {
        return response.getHeaders();
    }

    @Override
    public String getBody() {
        return response.getBody();
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
