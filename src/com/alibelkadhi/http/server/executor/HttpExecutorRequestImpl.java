package com.alibelkadhi.http.server.executor;

import com.alibelkadhi.http.HttpHeaders;
import com.alibelkadhi.http.HttpRequest;
import com.alibelkadhi.http.HttpRequestMethod;
import com.alibelkadhi.http.HttpVersion;

import java.util.*;

public class HttpExecutorRequestImpl implements HttpExecutorRequest {

    private HttpRequest httpRequest;
    private Map<String,String> attributes = new HashMap<String, String>();

    public HttpExecutorRequestImpl(HttpRequest request) {
        this.httpRequest = request;

        if(request.getQuery().isPresent()) {
            String [] params = request.getQuery().get().split("&");
            for (String param : params) {
                String [] attribute = param.split("=",2);
                if (attribute.length < 2) {
                    attributes.put(attribute[0],"");
                } else {
                    attributes.put(attribute[0],attribute[1]);
                }
            }
        }

        if (request.getBody().isPresent() && request.getHeaders().firstValue("Content-Typer").isPresent() && request.getHeaders().firstValue("Content-Typer").get().trim().equals(" application/x-www-form-urlencoded")) {
            String [] params = request.getBody().get().split("&");
            for (String param : params) {
                String [] attribute = param.split("=",2);
                if (attribute.length < 2) {
                    attributes.put(attribute[0],"");
                } else {
                    attributes.put(attribute[0],attribute[1]);
                }
            }
        }
    }


    @Override
    public Optional<String> getAttribute(String key) {
        return Optional.ofNullable(attributes.get(key));
    }

    @Override
    public void setAttribute(String key,String value) {
        attributes.put(key,value);
    }

    @Override
    public HttpVersion getVersion() {
        return this.httpRequest.getVersion();
    }

    @Override
    public HttpRequestMethod getMethod() {
        return this.httpRequest.getMethod();
    }

    @Override
    public String getHost() {
        return this.httpRequest.getHost();
    }

    @Override
    public int getPort() {
        return this.httpRequest.getPort();
    }

    @Override
    public String getPath() {
        return this.httpRequest.getPath();
    }

    @Override
    public Optional<String> getQuery() {
        return this.httpRequest.getQuery();
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.httpRequest.getHeaders();
    }

    @Override
    public Optional<String> getBody() {
        return this.httpRequest.getBody();
    }
}
