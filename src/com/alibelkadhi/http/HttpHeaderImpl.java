package com.alibelkadhi.http;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class HttpHeaderImpl implements HttpHeader  {

    public String key;
    public List<String> values = new LinkedList<String>();

    public HttpHeaderImpl(String key, List<String> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return this.key;
    };
    public Collection getValues() {
        return this.values;
    };

}
