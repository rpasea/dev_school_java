package com.example.httpserver.model;

import com.example.tcpserver.codec.Codec;
import sun.jvm.hotspot.opto.Phase;

import java.net.URI;
import java.net.URISyntaxException;
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
        int position = buffer.indexOf(CRLF);
        if(position == -1){
            return false;
        }

        String rqLine = buffer.substring(0, position);

        // don't forget to consume the line from the parser
        buffer.delete(0, (position + CRLF.length()));

        String[] parts = rqLine.trim().split(" ");
        if(parts.length != 3){
            return false;
        }

        // parse the line and set the fields on this.request
        request.setMetthod(parts[0]);
        try {
            request.setUri(new URI(parts[1]));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
        request.setVersion(parts[2]);

        return true;
    }

    /*
        returns if all the headers are done
     */
    private boolean parseHeaders() {

        // headers have format:
        // key: value CRLF

        int position = buffer.indexOf(CRLF);
        while(position != -1){
            // Stop parsing headers if we encounter a CRLF as the first character of the headerLine
            if(position == 0){
                // return true if you encounter a CRLF on an empty line
                return true;
            }

            String headerLine = buffer.substring(0, position).trim();
            buffer.delete(0, (position + CRLF.length()));

            String[] headerParts = headerLine.split(":");
            // put each header in this.request.getHeaders()
            request.getHeaders().put(headerParts[0].toLowerCase().trim(), headerParts[1].trim());

            // don't forget to consume the used lines from the buffer
            position = buffer.indexOf(CRLF);
        }
        return false;

    }

    private boolean parseBody() {
        // body length is controlled by the LENGTH_HEADER header
        int bodyLength;
        try{
            // maybe we have no length field (this happens when GET for example)
            bodyLength = Integer.parseInt(request.getHeaders().get(LENGTH_FIELD));
        }
        catch (Exception e){
            return true;
        }

        // if you don't have enough bytes in the buffer, return false
        if(buffer.length() < bodyLength){
            return false;
        }

        request.setBody(buffer.substring(0, bodyLength));

        // otherwise consume them
        buffer.delete(0, bodyLength);
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
