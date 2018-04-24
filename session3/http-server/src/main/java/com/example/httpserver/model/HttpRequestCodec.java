package com.example.httpserver.model;

import com.example.tcpserver.codec.Codec;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CRL;
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
                    try {
                        if (parseRequestLine()) {
                            phase = Phase.HEADERS;
                        } else {
                            canContinue = false;
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
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
    private boolean parseRequestLine() throws URISyntaxException {

        // the request line has the format:
        // METHOD URI HTTP/1.1 CRLF

        int position = buffer.indexOf(CRLF);
        if (-1 == position) {
            return false;
        }

        String requestLine = buffer.substring(0, position);

        String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            return false;
        }

        URI uri = new URI(parts[1]);
        request.setMetthod(parts[0]);
        request.setUri(uri);
        request.setVersion(parts[2]);

        // search the buffer for CRLF to see if you can process the line, otherwise return false
        // to stop the parser from trying the next phase


        // parse the line and set the fields on this.request

        // don't forget to consume the line from the parser
        buffer.delete(0, position + 2);

        return true;
    }

    /*
        returns if all the headers are done
     */
    private boolean parseHeaders() {

        int position = buffer.indexOf(CRLF);
        while (position != -1) {
            String headerLine = buffer.substring(0, position);
            buffer.delete(0, position + 2);

            if (headerLine.length() == 0) {
                return true;
            }

            String[] parts = headerLine.split(":");
            String key = parts[0].trim().toLowerCase();
            String value = parts[1].trim();

            request.getHeaders().put(key, value);

            position = buffer.indexOf(CRLF);
        }


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
        if (request.getHeaders().get(LENGTH_FIELD) == null) {
            return true;
        }

        int bodyLength = Integer.parseInt(request.getHeaders().get(LENGTH_FIELD));
        // body length is controlled by the LENGTH_HEADER header

        // if you don't have enough bytes in the buffer, return false
        if (buffer.length() < bodyLength) {
            return false;
        }

        request.setBody(buffer.substring(0, bodyLength));


        buffer.delete(0, bodyLength);


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
