package com.example.serialization.json;

import com.example.serialization.DeserializationException;
import com.example.serialization.json.model.*;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Map;

public class MapDeserializer {
    private LinkedList<Element> stack;
    private Element root;

    public MapDeserializer() {
        stack = new LinkedList<>();
    }

    public Map<String, Object> deserialize(String json) throws DeserializationException {
        System.out.println(json);
        json = json.trim();

        while (!json.isEmpty()) {
            json = json.trim();
            Pair<Token, Integer> next = DeseializationHelper.findNextToken(json);

            if (next.getValue() == -1) {
                throw new DeserializationException("Invalid json");
            }

            String before = json.substring(0, next.getValue()).trim();
           // System.out.println(before);
            if (!stack.isEmpty() && !before.isEmpty()) {
                stack.peekLast().parseNextText(before);
            }

            switch (next.getKey()) {
                case LBRACE:
                    //openNewElement(ObjectElement.class);
                    //System.out.println(next);
                    break;
                case LBRACKET:
                    //openNewElement(ArrayElement.class);
                    break;
                case QUOTE:
                    if (stack.isEmpty() || !(stack.peekLast() instanceof ValueElement)) {
                        openNewElement(ValueElement.class);
                    }
                    break;
                case RBRACE:
                case RBRACKET:
                case COMMA:
                    boolean changed = true;
                    Token t = next.getKey();

                    while (!stack.isEmpty() && changed) {
                        changed = false;
                        Element el = stack.peekLast();
                        t = el.consumeToken(t) ? Token.NOOP : t;
                        if (el.isDone()) {
                            changed = true;
                            stack.removeLast();
                            if (!stack.isEmpty()) {
                                stack.peekLast().feed(el);
                            }
                        }
                    }
                    break;
                case COLON:
                    if (stack.peekLast() instanceof ValueElement) {
                        ValueElement value = (ValueElement) stack.removeLast();
                       // KeyValuePair valuePair = new KeyValuePair();
                       // valuePair.setKey((String) value.convert());
                       // stack.add(valuePair);
                    } else {
                        throw new DeserializationException("Invalid json");
                    }
                    break;
                default:
                    break;

            }
            if (json.length() > 1) {
                json = json.substring(next.getValue() + 1).trim();
            } else {
                break;
            }
        }

        //ObjectElement obj = (ObjectElement) root;
        return (Map<String, Object>) root.convert();
    }

    private void openNewElement(Class<? extends Element> klass) throws DeserializationException {
        Element object;
        try {
            object = klass.newInstance();
        } catch (Exception e) {
            throw new DeserializationException("Unknown", e);
        }
        stack.add(object);
        if (root == null) {
            root = object;
        }
    }
}
