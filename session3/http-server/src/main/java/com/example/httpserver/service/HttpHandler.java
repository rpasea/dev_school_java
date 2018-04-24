package com.example.httpserver.service;

import com.example.httpserver.model.HttpRequest;
import com.example.httpserver.model.HttpResponse;
import com.example.tcpserver.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class HttpHandler implements MessageHandler<HttpRequest, HttpResponse> {
    private static final Logger LOG = LoggerFactory.getLogger(HttpHandler.class);

    private Environment environment;

    public HttpHandler(Environment environment) {
        this.environment = environment;
    }

    @Override
    public List<HttpResponse> handle(HttpRequest httpRequest) {
        LOG.info("Got request: {}", httpRequest);

        switch (httpRequest.getMetthod()) {
            case "GET":
                return Collections.singletonList(handleGet(httpRequest));
            case "PUT":
            case "DELETE":
            default:
                HttpResponse response = new HttpResponse();
                // TODO
                return Collections.singletonList(response);
        }
    }

    private HttpResponse handleGet(HttpRequest httpRequest) {
        Path path = null;

        HttpResponse response = new HttpResponse();
        // get the path from the request, if not found return a not found exception

        if (Files.isDirectory(path)) {
            StringBuilder builder = new StringBuilder();

            // get the list of files in the directory
            List<File> files = null;
            files.forEach(f -> {
                builder.append
                        ("<a href=\"/" + /* what here? */ "" + "\">" + /* how about here? */ "" + "</a>\n").append
                        ("<br>");
            });
            // set the body to the response
        } else {
           // read the file contents and set the body to the response
        }

        return response;
    }

    private HttpResponse createError(int status, String reason) {
        HttpResponse response = new HttpResponse();
        response.setStatus(status);
        response.setReason(reason);

        return response;
    }
}
