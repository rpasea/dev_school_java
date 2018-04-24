package com.example.httpserver.model;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String version;
    private Integer status;
    private String reason;
    private Map<String, String> headers;
    private String body;

    private String contentLength = "content-length";
    private String CRLF = "\r\n";

    public HttpResponse() {
        version = "HTTP/1.1";
        headers = new HashMap<>();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;

        if(headers.containsKey(contentLength)){
            headers.put(contentLength, Integer.toString(body.length()));
        }
    }

    public String getStatusLine(){
        //Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
        return String.format("%s %s %s %s", version, status, reason, CRLF);
    }

    public  String getHeader(){
        StringBuilder headerResponse = new StringBuilder();

        headers.forEach((key, value) -> {
            headerResponse.append(String.format("%s: %s%s", key, value, CRLF));
        });

        headerResponse.append(CRLF);

        return  headerResponse.toString();
    }
}
