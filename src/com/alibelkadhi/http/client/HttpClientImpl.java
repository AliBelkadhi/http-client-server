package com.alibelkadhi.http.client;

import com.alibelkadhi.http.*;
import com.alibelkadhi.http.exception.ConnectionException;
import com.alibelkadhi.http.exception.InvalidHttpHeaderException;
import com.alibelkadhi.http.exception.InvalidHttpRequestException;
import com.alibelkadhi.http.exception.InvalidHttpResponseException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class HttpClientImpl implements HttpClient {



    @Override
    public HttpResponse execute(HttpRequest request) throws InvalidHttpResponseException, InvalidHttpHeaderException, InvalidHttpRequestException, ConnectionException {
        if(request.getVersion() == null || request.getMethod() == null || request.getHost() == null || request.getPath() == null) {
            throw new InvalidHttpRequestException();
        }

        try {

            String request2 = prepareRequest(request);

            InetAddress address = InetAddress.getByName(request.getHost());
            Socket socket = new Socket(address,request.getPort());

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            bufferedWriter.write(request2);
            bufferedWriter.flush();


            String line = bufferedReader.readLine();
            String [] elements = line.split(" ");

            HttpResponseBuilder httpResponseBuilder = HttpResponseBuilder.newBuilder().version(HttpVersion.getByValue(elements[0]))
                    .status(HttpResponseStatus.valueOf(Integer.parseInt(elements[1])));

            long contentLength = 0;
            while (!(line = bufferedReader.readLine()).isEmpty()) {
                elements = line.split(":",2);

                if(elements.length != 2) {
                    throw new InvalidHttpHeaderException();
                }

                httpResponseBuilder.header(elements[0],elements[1]);

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

            socket.close();

            return httpResponseBuilder.body(body.length() > 0 ? body.toString() : null).build();

        } catch (IOException e) {
            throw new ConnectionException();
        }


    }

    private String prepareRequest(HttpRequest request) {

        StringBuilder request2 = new StringBuilder();

        request2.append(request.getMethod() + " " + request.getPath() +   (request.getQuery().isPresent() ? "?"+request.getQuery().get() : "") + " "+request.getVersion().getValue()+HttpClient.NEW_HTTP_LINE);

        if (!request.getVersion().equals(HttpVersion.HTTP_1_0)) {
            request2.append("Host:"+request.getHost()+NEW_HTTP_LINE);
        }


        for (Map.Entry<String,List<String>> entry :request.getHeaders().getAllHeaders().entrySet()) {
            for (String value : entry.getValue()) {
                request2.append(entry.getKey()+":"+value+NEW_HTTP_LINE);
            }
        }

        if (request.getBody().isPresent() && request.getBody().isPresent() && !request.getBody().get().isEmpty() && !request.getHeaders().firstValue("Content-Length").isPresent()) {

            request2.append("Content-Length:"+request.getBody().get().length()+NEW_HTTP_LINE);

        }

        request2.append(NEW_HTTP_LINE);

        if (request.getBody().isPresent() && !request.getBody().get().isEmpty())
            request2.append(request.getBody().get());


        return request2.toString();

    }

}
