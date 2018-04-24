package com.example.serialization.json.model;

import com.example.serialization.DeserializationException;

public class ObjectElement implements Element{
    @Override
    public void parseNextText(String text) throws DeserializationException {

    }

    @Override
    public void feed(Element element) throws DeserializationException {

    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object convert() {
        return null;
    }

    @Override
    public boolean consumeToken(Token token) {
        return false;
    }
}
