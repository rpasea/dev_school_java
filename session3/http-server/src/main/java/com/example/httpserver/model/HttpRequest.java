package com.example.httpserver.model;

import java.net.URI;
import java.util.Map;

public class HttpRequest {
    private String metthod;
    private URI uri;
    private String version;
    private Map<String, String> headers;
    private String body;

    public HttpRequest() {
    }

    public String getMetthod() {
        return metthod;
    }

    public void setMetthod(String metthod) {
        this.metthod = metthod;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    @Override
    public String toString() {
        return "HttpRequest{" +
                "metthod='" + metthod + '\'' +
                ", uri=" + uri +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
