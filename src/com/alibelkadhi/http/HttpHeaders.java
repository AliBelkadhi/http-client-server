package com.alibelkadhi.http;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HttpHeaders {

    public Optional<String> firstValue(String key);
    public Optional<List<String>> allValues(String key);
    public Map<String,List<String>> getAllHeaders();

}
