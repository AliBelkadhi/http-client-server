package com.alibelkadhi.http.server;

import com.alibelkadhi.http.*;
import com.alibelkadhi.http.client.HttpClient;
import com.alibelkadhi.http.exception.InvalidHttpHeaderException;
import com.alibelkadhi.http.server.executor.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executor;

public class HttpServerImpl implements HttpServer {


    private ServerSocket serverSocket;
    private int maxThreads;
    private int numberOfThreads;
    private Optional<String>  publicStaticDirectory;
    private Collection<String> defaultIndexFiles;
    private Iterable<HttpExecutor> executors;

    @Override
    public Optional<String> getPublicStaticDirectory() {
        return this.publicStaticDirectory;
    }

    @Override
    public Collection<String> getDefaultIndexFiles() {
        return this.defaultIndexFiles;
    }

    @Override
    public Iterable<HttpExecutor> getExecutors() {
        return this.executors;
    }

    @Override
    public void run(HttpServerConfiguration configuration, Iterable<HttpExecutor> executors) throws IOException {

        this.serverSocket = new ServerSocket(configuration.getPort());
        this.maxThreads = configuration.getMaxThreads();
        this.numberOfThreads = 0;
        this.publicStaticDirectory = configuration.getPublicStaticDirectory();
        this.executors = executors;
        this.defaultIndexFiles = configuration.getDefaultIndexFiles();

        HttpServer server = this;

        while (true) {
            Socket socket = serverSocket.accept();
            while(numberOfThreads >= maxThreads);
            new Thread(new HttpRequestHandler(socket,server)).start();
            incrementMaxThreads();
        }
    }

    private synchronized void incrementMaxThreads() {
        this.maxThreads += 1;
    }

    private synchronized void derementMaxThreads() {
        this.maxThreads -= 1;
    }



}
