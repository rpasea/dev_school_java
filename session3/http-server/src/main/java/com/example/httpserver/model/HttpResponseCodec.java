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
        //request.getHeaders.has?
        StringBuilder res = new StringBuilder();

        res.append(httpResponse.getVersion())
                .append(" ")
                .append(httpResponse.getStatus())
                .append(httpResponse.getReason())
                .append(CRLF)
                .append(LENGTH_FIELD)
                .append(":")
                .append(httpResponse.getBody().length())
                .append(CRLF);
        for(Map.Entry<String, String> elem : httpResponse.getHeaders().entrySet()){
            res.append(elem.getKey())
                    .append(":")
                    .append(elem.getValue())
                    .append(CRLF);
        }

        res.append(CRLF)
                .append(httpResponse.getBody().toString());


        return res.toString();
    }
}
