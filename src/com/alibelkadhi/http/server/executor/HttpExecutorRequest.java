package com.alibelkadhi.http.server.executor;

import com.alibelkadhi.http.HttpRequest;

import java.util.Optional;

public interface HttpExecutorRequest extends HttpRequest {



    public Optional<String> getAttribute(String key);

    public void setAttribute(String key,String value);

    //TODO MULTIPLE Attributes

    //TODO COOKIES

}
