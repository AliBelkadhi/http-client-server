package com.alibelkadhi.src;

import com.alibelkadhi.http.*;
import com.alibelkadhi.http.client.HttpClient;
import com.alibelkadhi.http.client.HttpClientImpl;
import com.alibelkadhi.http.server.HttpServer;
import com.alibelkadhi.http.server.HttpServerConfiguration;
import com.alibelkadhi.http.server.HttpServerConfigurationBuilder;
import com.alibelkadhi.http.server.HttpServerImpl;
import com.alibelkadhi.http.server.executor.HttpExecutor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServerImpl();
        HttpServerConfiguration configuration = HttpServerConfigurationBuilder.newBuilder()
                                                    .defaultIndexFiles("index.html","main.html")
                                                    .publicStaticDirectory("/Users/alibelkadhi/Desktop/server")
                                                    .build();


        List<HttpExecutor> executors = new LinkedList<>();

        executors.add(new HelloExecutor());

        server.run(configuration,executors);

    }



    /*
    public static void main(String[] args) throws Exception {

        HttpRequest request = HttpRequestBuilder.newBuilder().method(HttpRequestMethod.GET)
                                .host("postman-echo.com")
                                .path("/get")
                                .query("foo1=bar1&foo2=bar2")
                                .port(80)
                                .header("X1","VAL1")
                                .header("X1","VAL2")
                                .header("X2","VAL")
                                .build();


        HttpClient client = HttpClient.newClient();

        HttpResponse response = client.execute(request);


        System.out.println("Http Version: "+response.getHttpVersion());
        System.out.println("Response Status: "+response.getResponseStatus());
        System.out.println("Headers: \n"+response.getHeaders().getAllHeaders());
        System.out.println("Body: \n"+response.getBody());


    }*/


    /*
    public static void main(String[] args) throws Exception {

        HttpRequest request = HttpRequestBuilder.newBuilder().method(HttpRequestMethod.POST)
                .url("http://postman-echo.com/post")
                .header("Content-Type"," x-www-form-urlencoded")
                .header("X1","VAL1")
                .header("X1","VAL2")
                .header("X2","VAL")
                .body("foo1=bar1&foo2=bar2")
                .build();


        HttpClient client = new HttpClientImpl();

        HttpResponse response = client.execute(request);


        System.out.println("Http Version: "+response.getHttpVersion());
        System.out.println("Response Status: "+response.getResponseStatus());
        System.out.println("Headers: \n"+response.getHeaders().getAllHeaders());
        System.out.println("Body: \n"+response.getBody());


    }*/
}
