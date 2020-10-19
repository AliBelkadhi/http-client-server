package com.alibelkadhi.http.server;

import java.util.Collection;
import java.util.Optional;

public class HttpServerConfigurationImpl implements HttpServerConfiguration {

    private int port;
    private int maxThreads;
    private Optional<String> publicStaticDirectory;
    private Collection<String> defaultIndexFiles;

    public HttpServerConfigurationImpl(int port, int maxThreads, Optional<String> publicStaticDirectory,Collection<String> defaultIndexFiles) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.publicStaticDirectory = publicStaticDirectory;
        this.defaultIndexFiles = defaultIndexFiles;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public int getMaxThreads() {
        return this.maxThreads;
    }

    @Override
    public Optional<String> getPublicStaticDirectory() {
        return this.publicStaticDirectory;
    }

    @Override
    public Collection<String> getDefaultIndexFiles() {
        return this.defaultIndexFiles;
    }
}
