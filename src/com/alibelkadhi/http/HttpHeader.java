package com.alibelkadhi.http;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface HttpHeader {

    public String getKey();
    public Collection getValues();

    public class Builder {

        public String key;
        public List<String> values = new LinkedList<String>();

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(String value) {
            this.values.add(value);
            return this;
        }

        public Builder values(String ... values) {
            for (String value : values) {
                this.values.add(value);
            }
            return this;
        }

        public HttpHeader build() {
            return new HttpHeaderImpl(this.key,this.values);
        }

    }

}
