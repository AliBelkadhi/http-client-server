package com.alibelkadhi.http.server.executor;

import com.alibelkadhi.http.HttpResponse;
import com.alibelkadhi.http.HttpResponseStatus;

import java.io.Writer;

public interface HttpExecutorResponse extends HttpResponse {


    public StringBuilder getBuilder();
    public void setResponseStatus(HttpResponseStatus status);
    public String getContentType();
    public void setContentType(String contentType);

    //TODO Cookies

    //TODO
}
