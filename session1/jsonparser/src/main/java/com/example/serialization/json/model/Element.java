package com.example.serialization.json.model;

import com.example.serialization.DeserializationException;

public interface Element {
    // parse the text between 2 tokens. think about string elements
    void parseNextText(String text) throws DeserializationException;

    // feed a child element
    void feed(Element element) throws DeserializationException;

    // are we done with this element?
    boolean isDone();

    // convert to Java Object
    Object convert();

    /*
     * check if the current token has to be consumed by this element
     * Tokens can be consumed only by container elements ( for example, the right bracket of an Object element.
     */

    boolean consumeToken(Token token);
}
