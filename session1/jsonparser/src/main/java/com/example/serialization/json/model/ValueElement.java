package com.example.serialization.json.model;

import com.example.serialization.DeserializationException;

public class ValueElement implements Element {
    private Object value;
    private boolean done = false;

    public ValueElement() {
    }

    public ValueElement(Object value) {
        this.value = value;
        this.done = true;
    }

    @Override
    public void parseNextText(String text) throws DeserializationException {
        if (!done) {
            value = text;
            done = true;
        }
    }

    @Override
    public void feed(Element element) throws DeserializationException {

    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public Object convert() {
        return value;
    }

    @Override
    public boolean consumeToken(Token token) {
        return false;
    }

    // this is for not string values
    public static ValueElement fromString(String text) {
        try {
            Integer i = Integer.parseInt(text);
            return new ValueElement(i);
        } catch (NumberFormatException e) {
        }

        try {
            Float f = Float.parseFloat(text);
            return new ValueElement(f);
        } catch (NumberFormatException e) {
        }

        Boolean b = Boolean.parseBoolean(text);
        return new ValueElement(b);
    }
}
