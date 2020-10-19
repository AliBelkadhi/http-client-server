package com.alibelkadhi.http.client;

import com.alibelkadhi.http.HttpRequest;
import com.alibelkadhi.http.HttpResponse;
import com.alibelkadhi.http.exception.ConnectionException;
import com.alibelkadhi.http.exception.InvalidHttpHeaderException;
import com.alibelkadhi.http.exception.InvalidHttpRequestException;
import com.alibelkadhi.http.exception.InvalidHttpResponseException;

import java.io.IOException;

public interface HttpClient {

    public static String NEW_HTTP_LINE = "\r\n";

    public HttpResponse execute(HttpRequest request) throws InvalidHttpResponseException, InvalidHttpHeaderException, InvalidHttpRequestException, ConnectionException;

    public static HttpClient newClient() {
        return new HttpClientImpl();
    }

}
