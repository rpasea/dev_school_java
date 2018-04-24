package com.example.httpserver.model;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String version;
    private Integer status;
    private String reason;
    private Map<String, String> headers;
    private String body;

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
    }
}
