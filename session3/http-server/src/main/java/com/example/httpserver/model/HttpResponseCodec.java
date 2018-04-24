package com.example.httpserver.model;

import com.example.tcpserver.codec.Codec;

import java.util.List;
import java.util.Map;

public class HttpResponseCodec implements Codec<String, HttpResponse> {
    private static final String CRLF = "\r\n";
    private static final String LENGTH_FIELD = "content-length";

    @Override
    public List<HttpResponse> decode(String s) {
        return null;
    }

    @Override
    public String encode(HttpResponse httpResponse) {
        // this is pretty straight forward, just implement the protocol
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(httpResponse.getStatusLine()); //status-line
        responseBuilder.append(httpResponse.getHeader()); //header
        responseBuilder.append(httpResponse.getBody()); //body

        return responseBuilder.toString();
    }


}
