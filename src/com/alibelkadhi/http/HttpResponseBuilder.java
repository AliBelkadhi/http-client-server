package com.alibelkadhi.http;

import com.alibelkadhi.http.exception.InvalidHttpResponseException;

import java.util.List;

public class HttpResponseBuilder {

    private HttpVersion version;

    private HttpResponseStatus status;

    private HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder();

    private String body;

    public static HttpResponseBuilder newBuilder() {
        return new HttpResponseBuilder();
    }

    public HttpResponseBuilder version(HttpVersion version) {
        this.version = version;
        return this;
    }

    public HttpResponseBuilder status(HttpResponseStatus status) {
        this.status = status;
        return this;
    }

    public HttpResponseBuilder header(String key, String value) {
        httpHeadersBuilder.addHeader(key,value);
        return this;
    }

    public HttpResponseBuilder header(String key, List<String> values) {
        for (String value: values) {
            this.header(key,value);
        }
        return this;
    }

    public HttpResponseBuilder body(String body) {
        this.body = body;
        return this;
    }

    public HttpResponse build() {//} throws InvalidHttpResponseException {
       // if (version != null && status != null) {
            return new HttpResponseImpl(version,status,httpHeadersBuilder.build(),body);
        //} else {
          //  throw new InvalidHttpResponseException();
        //}
    }



    public static HttpResponse notFoundResponse() throws InvalidHttpResponseException {
        return newBuilder().version(HttpVersion.HTTP_1_1).status(HttpResponseStatus.NOT_FOUND)
                    .header("Content-Type","text/html").body("<h1>404 Not Found</h1>").build();
    }
    public static HttpResponse badRequestResponse() throws InvalidHttpResponseException {
        return newBuilder().version(HttpVersion.HTTP_1_1).status(HttpResponseStatus.BAD_REQUEST)
                    .header("Content-Type","text/html").body("<h1>400 Bad Request</h1>").build();
    }

    public static HttpResponse methodNotAllowed() throws InvalidHttpResponseException {
        return newBuilder().version(HttpVersion.HTTP_1_1).status(HttpResponseStatus.METHOD_NOT_ALLOWED)
                    .header("Content-Type","text/html").body("<h1>405 Method Not Allowed</h1>").build();
    }

    public static HttpResponse internalServerError(){//} throws InvalidHttpResponseException {
        return newBuilder().version(HttpVersion.HTTP_1_1).status(HttpResponseStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type","text/html").body("<h1>500 Internal Server Error</h1>").build();
    }


}
