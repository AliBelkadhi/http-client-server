package com.alibelkadhi.http;


import java.util.Optional;

public interface HttpRequest {

    public HttpVersion getVersion();
    public HttpRequestMethod getMethod();
    public String getHost();
    public int getPort();
    public String getPath();
    public Optional<String> getQuery();
    public HttpHeaders getHeaders();
    public Optional<String> getBody();

}


