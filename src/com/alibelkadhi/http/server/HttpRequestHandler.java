package com.alibelkadhi.http.server;


import com.alibelkadhi.http.*;
import com.alibelkadhi.http.client.HttpClient;
import com.alibelkadhi.http.exception.InvalidHttpHeaderException;
import com.alibelkadhi.http.exception.InvalidHttpRequestException;
import com.alibelkadhi.http.exception.InvalidHttpResponseException;
import com.alibelkadhi.http.server.executor.*;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class HttpRequestHandler implements Runnable {

    private Socket socket;
    private HttpServer server;

    public HttpRequestHandler(Socket socket, HttpServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            HttpRequest request = readRequest();

            // Handle Static file
            HttpResponse response = handleStaticFile(request);

            // Handle executor
            if(response == null) {
                response = handleExecutor(request);
            }

            // 404 not found
            if(response == null) {
                response = HttpResponseBuilder.notFoundResponse();
            }

            writeResponse(response);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private HttpRequest readRequest() throws IOException, InvalidHttpHeaderException, URISyntaxException, InvalidHttpRequestException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = bufferedReader.readLine();
        String [] elements = line.split(" ");

        HttpRequestBuilder httpRequestBuilder = HttpRequestBuilder.newBuilder().method(HttpRequestMethod.valueOf(elements[0]))
                .uri(elements[1])
                .version(HttpVersion.getByValue(elements[2]));

        long contentLength = 0;

        while (!(line = bufferedReader.readLine()).isEmpty()) {
            elements = line.split(":",2);
            if(elements.length != 2) {
                throw new InvalidHttpHeaderException();
            }
            httpRequestBuilder.header(elements[0],elements[1]);

            if (elements[0].equals("Content-Length")) {
                contentLength = Long.parseLong(elements[1].trim());
            }
        }

        StringBuilder body = new StringBuilder();
        int i = 0;
        int c;
        while (i < contentLength && (c = bufferedReader.read()) != -1) {
            body.append((char)c);
            i++;
        }

        httpRequestBuilder.body(body.length() > 0 ? body.toString() : null);

        return httpRequestBuilder.build();
    }

    private HttpResponse handleStaticFile(HttpRequest request) throws Exception {

        if (this.server.getPublicStaticDirectory().isPresent()) {


            HttpResponseBuilder responseBuilder = HttpResponseBuilder.newBuilder().version(HttpVersion.HTTP_1_1);


            File staticDirectory = new File(this.server.getPublicStaticDirectory().get());

            if(!staticDirectory.isDirectory())
                throw new Exception();

            Path staticFilePath = Paths.get(request.getPath());

            if (staticFilePath.toString().contains(".."))
                throw new Exception();

            if (staticFilePath.startsWith("/"))
                staticFilePath = Paths.get(staticFilePath.toString().substring(1));


            if(request.getPath().contains("../"))
                throw new Exception();

            staticFilePath = Paths.get(staticDirectory.getPath()).resolve(staticFilePath);

            try {
                File file = staticFilePath.toRealPath().toFile();

                if(file.isDirectory()) {

                    File[] dir_files = file.listFiles();

                    for (File f : dir_files) {
                        if(server.getDefaultIndexFiles().contains(f.getName())) {
                            file = f;
                            continue;
                        }
                    }

                }

                if(file.isFile()) {


                    responseBuilder.status(HttpResponseStatus.OK);

                    String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());

                    if (mimeType != null)
                        responseBuilder.header("Content-Type", mimeType);

                    StringBuilder responseBody = new StringBuilder();

                    BufferedReader fileBufferedReader = new BufferedReader(new FileReader(file));

                    String currentLine;
                    while ((currentLine = fileBufferedReader.readLine()) != null) {
                        responseBody.append(currentLine).append("\n");
                    }

                    responseBuilder.header("Content-Length", String.valueOf(responseBody.length()));

                    responseBuilder.body(responseBody.toString());

                    return responseBuilder.build();

                }
            } catch (Exception e) {
                return  null;
            }
        }
        return null;
    };

    private HttpResponse handleExecutor(HttpRequest request) throws IOException, InvalidHttpResponseException {

        HttpResponse response = null;
        HttpExecutor executor = null;
        for (HttpExecutor executor2 : server.getExecutors()) {

            if(executor2.getRoute().equals(request.getPath())) {
                if(executor2.getMethod().equals(request.getMethod())) {
                    executor = executor2;
                } else {
                    response = HttpResponseBuilder.methodNotAllowed();
                }

            }
        }

        if(executor != null) {

            HttpResponseBuilder responseBuilder = HttpResponseBuilder.newBuilder().version(HttpVersion.HTTP_1_1).status(HttpResponseStatus.OK);

            HttpExecutorRequest httpExecutorRequest = new HttpExecutorRequestImpl(request);

            StringBuilder bodyBuilder = new StringBuilder();

            HttpExecutorResponse httpExecutorResponse = new HttpExecutorResponseImpl(responseBuilder.build(),bodyBuilder,"text/html");

            executor.execute(httpExecutorRequest,httpExecutorResponse);

            responseBuilder.status(httpExecutorResponse.getResponseStatus());

            responseBuilder.body(httpExecutorResponse.getBuilder().toString());

            responseBuilder.header("Content-Type",String.valueOf(httpExecutorResponse.getContentType()));

            return responseBuilder.build();

        } else if(response != null) {
            return response;
        }

        return null;
    }

    private void writeResponse(HttpResponse response) throws IOException {



        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        bufferedWriter.write(response.getHttpVersion().getValue() +" "+response.getResponseStatus().getValue()+ HttpClient.NEW_HTTP_LINE);

        for (Map.Entry<String, List<String>> entry : response.getHeaders().getAllHeaders().entrySet()) {
            for (String value : entry.getValue()) {
                bufferedWriter.write(entry.getKey()+":"+value+HttpClient.NEW_HTTP_LINE);
            }
        }

        if (response.getBody() != null && !response.getBody().isEmpty()) {
            bufferedWriter.write("Content-Type:"+response.getBody().length()+HttpClient.NEW_HTTP_LINE);
        }


        bufferedWriter.write(HttpClient.NEW_HTTP_LINE);

        if (response.getBody() != null && !response.getBody().isEmpty()) {
            bufferedWriter.write(response.getBody());
        }

        bufferedWriter.flush();

    }

}