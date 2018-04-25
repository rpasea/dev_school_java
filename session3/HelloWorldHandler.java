/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.httpserver.service;

import com.example.httpserver.model.HttpRequest;
import com.example.httpserver.model.HttpResponse;
import com.example.tcpserver.MessageHandler;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kratos
 */
@Component("helloWorldHandler")
/**
 *
 * @author Kratos
 */
public class HelloWorldHandler implements MessageHandler<HttpRequest, HttpResponse>
{
   @Override
        public List<HttpResponse> handle (HttpRequest request){

            HttpResponse response;
            response = new HttpResponse();
            response.setStatus(200);
            response.setResponse("ok");

            response.setBody("hello world");
            return Collections.singletonList(response);
        }

}