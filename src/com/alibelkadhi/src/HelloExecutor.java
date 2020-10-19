package com.alibelkadhi.src;

import com.alibelkadhi.http.HttpRequestMethod;
import com.alibelkadhi.http.server.executor.HttpExecutor;
import com.alibelkadhi.http.server.executor.HttpExecutorRequest;
import com.alibelkadhi.http.server.executor.HttpExecutorResponse;

import java.io.IOException;

public class HelloExecutor implements HttpExecutor {


    @Override
    public HttpRequestMethod getMethod() {
        return HttpRequestMethod.GET; // TODO multi methods
    }

    @Override
    public String getRoute() {
        return "/hello"; // TODO multi methods
    }

    @Override
    public void execute(HttpExecutorRequest request, HttpExecutorResponse response) throws IOException {



        if(request.getAttribute("user").isPresent()) {
            response.getBuilder().append("Hello, "+request.getAttribute("user").get());

        } else {

            String form = "<form><input name='user'><input type='submit'></form>";
            response.getBuilder().append(form);

        }

    }
}
