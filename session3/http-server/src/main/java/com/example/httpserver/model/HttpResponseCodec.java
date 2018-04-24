package com.example.httpserver.model;

import com.example.tcpserver.codec.Codec;

import java.security.cert.CRL;
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

        String stringResponse = "";
        stringResponse += httpResponse.getVersion();
        stringResponse += " ";
        stringResponse += httpResponse.getStatus();
        stringResponse += " ";
        stringResponse += httpResponse.getReason();
        stringResponse += CRLF;
        stringResponse += LENGTH_FIELD + ":" + httpResponse.getBody().length() + CRLF;

        for (Map.Entry<String, String> entry : httpResponse.getHeaders().entrySet()) {
            stringResponse += entry.getKey() + ":" + entry.getValue() + CRLF;
        }
        stringResponse += CRLF;

        stringResponse += httpResponse.getBody().toString();

        return stringResponse;
    }
}
