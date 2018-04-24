package com.example.httpserver.model;

import com.example.tcpserver.codec.Codec;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HttpRequestCodec implements Codec<String, HttpRequest> {
    private static final String CRLF = "\r\n";
    private static final String LENGTH_FIELD = "content-length";

    private StringBuffer buffer;
    private Phase phase;
    private HttpRequest request;

    public HttpRequestCodec() {
        buffer = new StringBuffer();
        phase = Phase.REQUEST_LINE;
        initRequest();
    }

    @Override
    public List<HttpRequest> decode(String s) {
        buffer.append(s);

        List<HttpRequest> requests = new LinkedList<>();
        boolean canContinue = true;

        /*
         * Go through the each phase in order
         */
        while (canContinue) {
            switch (phase) {
                case REQUEST_LINE:
                    if (parseRequestLine()) {
                        phase = Phase.HEADERS;
                    } else {
                        canContinue = false;
                    }
                    break;
                case HEADERS:
                   if (parseHeaders()) {
                       phase = Phase.BODY;
                   } else {
                       canContinue = false;
                   }
                    break;
                case BODY:
                    if (parseBody()) {
                        phase = Phase.REQUEST_LINE;
                        requests.add(request);
                        initRequest();
                    } else {
                        canContinue = false;
                    }
                    break;
            }
        }

        return requests;
    }

    /*
        returns if the request line is done
     */
    private boolean parseRequestLine() {

        // the request line has the format:
        // METHOD URI HTTP/1.1 CRLF

        // search the buffer for CRLF to see if you can process the line, otherwise return false
        // to stop the parser from trying the next phase


        // parse the line and set the fields on this.request

        // don't forget to consume the line from the parser

        return true;
    }

    /*
        returns if all the headers are done
     */
    private boolean parseHeaders() {

        // headers have format:
        // key: value CRLF

        // the list ends with an empty CRLF

        // so you can process headers as long as you have CRLFs

        // put each header in this.request.getHeaders()

        // return true if you encounter a CRLF on an empty line

        // don't forget to consume the used lines from the buffer

        return false;
    }

    private boolean parseBody() {
        // body length is controlled by the LENGTH_HEADER header

        // if you don't have enough bytes in the buffer, return false

        // otherwise consume them
        return true;
    }

    private void initRequest() {
        request = new HttpRequest();
        request.setHeaders(new HashMap<>());
    }

    @Override
    public String encode(HttpRequest httpRequest) {
        return null;
    }

    private enum Phase {
        REQUEST_LINE, HEADERS, BODY
    }
}
