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
        StringBuilder response = new StringBuilder();
        response.append(httpResponse.getStatus())
                .append(" ").append(httpResponse.getVersion())
                .append(" ").append(httpResponse.getReason())
                .append(CRLF)
                .append(LENGTH_FIELD).append(":").append(httpResponse.getBody().length())
                .append(CRLF);
        httpResponse.getHeaders().forEach((key, value) -> {
            response.append(key).append(":").append(value).append(CRLF);
        });
        response.append(CRLF);
        response.append(httpResponse.getBody().toString());
        return response.toString();
    }
}
