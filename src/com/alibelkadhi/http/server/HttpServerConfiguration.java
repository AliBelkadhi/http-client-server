package com.alibelkadhi.http.server;

import java.util.Collection;
import java.util.Optional;

public interface HttpServerConfiguration {

    public int getPort();
    public int getMaxThreads();
    public Optional<String> getPublicStaticDirectory();
    public Collection<String> getDefaultIndexFiles();

}
