package com.example.httpserver.model;

import com.example.tcpserver.codec.Codec;

import java.util.List;

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
        StringBuilder resp = new StringBuilder();
        resp.append(httpResponse.getStatus());
        resp.append(" ").append(httpResponse.getVersion());
        resp.append(" ").append(httpResponse.getReason());
        resp.append(CRLF).append(LENGTH_FIELD).append(":");
        resp.append(httpResponse.getBody().length()).append(CRLF);
        httpResponse.getHeaders().forEach((key,value) ->{
            resp.append(key).append(":").append(CRLF);
        });

        resp.append(httpResponse.getBody());

        return resp.toString();
    }
}
