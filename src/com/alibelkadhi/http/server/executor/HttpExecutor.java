package com.alibelkadhi.http.server.executor;

import com.alibelkadhi.http.HttpRequestMethod;

import java.io.IOException;

public interface HttpExecutor {


    public HttpRequestMethod getMethod();
    public String getRoute();
    public void execute(HttpExecutorRequest request,HttpExecutorResponse response) throws IOException;


    //TODO forward to another executor


}
