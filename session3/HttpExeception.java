package com.example.httpserver.model;

public class HttpExeception extends RuntimeException {
    public HttpExeception() {
    }

    public HttpExeception(String message) {
        super(message);
    }

    public HttpExeception(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpExeception(Throwable cause) {
        super(cause);
    }

    public HttpExeception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
