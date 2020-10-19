package com.alibelkadhi.http.server;

import com.alibelkadhi.http.server.executor.HttpExecutor;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;

public interface HttpServer {

    public Optional<String> getPublicStaticDirectory();
    public Collection getDefaultIndexFiles();
    public Iterable<HttpExecutor> getExecutors();
    public void run(HttpServerConfiguration configuration,Iterable<HttpExecutor> executors) throws IOException;

}
