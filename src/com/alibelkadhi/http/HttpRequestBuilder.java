package com.alibelkadhi.http;

import com.alibelkadhi.http.exception.InvalidHttpRequestException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class HttpRequestBuilder {

    private HttpRequestMethod method = HttpRequestMethod.GET;

    private HttpVersion version = HttpVersion.HTTP_1_1;

    private URI uri;

    private URL url;

    private String host;

    private int port = 80;

    private String path;

    private String query;

    private HttpHeadersBuilder httpHeadersBuilder = new HttpHeadersBuilder();

    private String body;

    public static HttpRequestBuilder newBuilder() {
        return new HttpRequestBuilder();
    }

    public HttpRequestBuilder version(HttpVersion version) {
        this.version = version;
        return this;
    }

    public HttpRequestBuilder method(HttpRequestMethod method) {
        this.method = method;
        return this;
    }

    public HttpRequestBuilder uri(URI uri) {
        this.uri = uri;

        this.host = uri.getHost() != null ? uri.getHost() : host;
        this.port = uri.getPort() == -1 ? port : uri.getPort();
        this.path = uri.getPath();
        this.query = uri.getQuery();

        return this;
    }


    public HttpRequestBuilder uri(String uri) throws URISyntaxException {
        this.uri(new URI(uri));

        return this;
    }

    public HttpRequestBuilder url(URL url) {
        this.url = url;

        this.host = url.getHost();
        this.port = url.getPort() == -1 ? port : url.getPort();
        this.path = url.getPath();
        this.query = url.getQuery();

        return this;
    }

    public HttpRequestBuilder url(String url) throws MalformedURLException {
        this.url(new URL(url));
        return this;
    }

    public HttpRequestBuilder host(String host) {
        this.host = host;
        return this;
    }

    public HttpRequestBuilder port(int port) {
        this.port = port;
        return this;
    }

    public HttpRequestBuilder path(String path) {
        this.path = path;
        return this;
    }

    public HttpRequestBuilder query(String query) {
        this.query = query;
        return this;
    }

    public HttpRequestBuilder header(String key,String value) {
        httpHeadersBuilder.addHeader(key,value);
        return this;
    }

    public HttpRequestBuilder header(String key, List<String> values) {
        for (String value: values) {
            this.header(key,value);
        }
        return this;
    }

    public HttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public HttpRequest build() throws InvalidHttpRequestException {
        if (version != null && method != null && port > -1 && path != null) {
            return new HttpRequestImpl(version,method,host,port,path, Optional.ofNullable(query),httpHeadersBuilder.build(),Optional.ofNullable(body));
        } else {
            throw new InvalidHttpRequestException();
        }
    }


}
