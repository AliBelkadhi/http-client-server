package com.alibelkadhi.http;

import java.util.Optional;

public class HttpRequestImpl implements HttpRequest{

    private HttpVersion version;

    private HttpRequestMethod method;

    private String host;

    private int port;

    private String path;

    private Optional<String> query;

    private HttpHeaders headers;

    private Optional<String> body;

    public HttpRequestImpl(HttpVersion version,HttpRequestMethod method,String host,int port,String path, Optional<String> query, HttpHeaders headers,  Optional<String> body) {
        this.version = version;
        this.method = method;
        this.host = host;
        this.port = port;
        this.path = path;
        this.query = query;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public HttpVersion getVersion() {
        return this.version;
    }

    @Override
    public HttpRequestMethod getMethod() {
        return this.method;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public Optional<String> getQuery() {
        return this.query;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public Optional<String> getBody() {
        return this.body;
    }


}
