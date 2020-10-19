package com.alibelkadhi.http;

public interface HttpResponse {

    public HttpVersion getHttpVersion();
    public HttpResponseStatus getResponseStatus();
    public HttpHeaders getHeaders();
    public String getBody();

}
