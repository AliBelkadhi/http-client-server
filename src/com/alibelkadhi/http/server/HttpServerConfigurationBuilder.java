package com.alibelkadhi.http.server;

import java.util.*;

public class HttpServerConfigurationBuilder {

    private int port = 80;
    private int maxThreads = 5;
    private Optional<String> publicStaticDirectory;
    private Set<String> defaultIndexFiles = new LinkedHashSet<>();

    public static HttpServerConfigurationBuilder newBuilder() {
        return new HttpServerConfigurationBuilder();
    }


    public HttpServerConfigurationBuilder port(int port) {
        this.port = port;
        return this;
    }

    public HttpServerConfigurationBuilder maxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
        return this;
    }

    public HttpServerConfigurationBuilder publicStaticDirectory(String publicStaticDirectory) {
        this.publicStaticDirectory = Optional.ofNullable(publicStaticDirectory);
        return this;
    }

    public HttpServerConfigurationBuilder defaultIndexFiles(String fileName) {
        this.defaultIndexFiles.add(fileName);
        return this;
    }

    public HttpServerConfigurationBuilder defaultIndexFiles(String ... fileNames) {
        for (String fileName : fileNames) {
            this.defaultIndexFiles.add(fileName);
        }
        return this;
    }

    public HttpServerConfiguration build() throws Exception {
        if (port < 0 || maxThreads < 1) {
            throw new Exception();
        } else {
            return new HttpServerConfigurationImpl(port, maxThreads, publicStaticDirectory, defaultIndexFiles);
        }
    }

}
