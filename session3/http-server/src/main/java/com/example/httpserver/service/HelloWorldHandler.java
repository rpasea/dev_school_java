package com.example.httpserver.service;

import com.example.httpserver.model.HttpRequest;
import com.example.httpserver.model.HttpResponse;
import com.example.httpserver.model.HttpResponseCodec;
import com.example.tcpserver.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("HelloWorldHandler")
public class HelloWorldHandler implements MessageHandler<HttpRequest, HttpResponse> {
    @Override
    public List<HttpResponse> handle(HttpRequest httpRequest) {
        HttpResponse response = new HttpResponse();

        response.setStatus(200);
        response.setReason("OK");
        response.setBody("my response body");
        return Collections.singletonList(response);
    }
}