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
        StringBuilder sb = new StringBuilder();
        httpResponse.getHeaders().forEach((key, value) -> {
            sb.append(value);
        });
        sb.append(httpResponse.getVersion());
        sb.append(httpResponse.getStatus());
        sb.append(httpResponse.getBody());

        return sb.toString();
    }
}
