package com.example.serialization.json;


import com.example.serialization.SerializationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static com.example.serialization.json.model.Token.*;

class MapSerializer {
    public StringBuilder serialize(Map<String, Object> jsonObject) throws SerializationException {
        StringBuilder sb = new StringBuilder();
        sb.append(LBRACE);
        jsonObject.forEach((key, value) -> {
            try {
                sb.append(serialize(key)).append(COLON).append(serialize(value)).append(COMMA);

            } catch (SerializationException e) {

            }
        });

        sb.setLength(sb.length() - 1);
        sb.append(RBRACE);
        return sb;
    }

    public StringBuilder serialize(Collection<Object> collection) throws SerializationException {
        StringBuilder sb = new StringBuilder();
        sb.append(LBRACKET);
        collection.forEach((item) -> {
            try {
                sb.append(this.serialize(item)).append(COMMA);

            } catch (SerializationException e) {

            }
        });
        sb.setLength(sb.length() - 1);
        sb.append(RBRACKET);
        return sb;
    }

    public StringBuilder serialize(String string) {
        StringBuilder sb = new StringBuilder();
        sb.append(QUOTE).append(string).append(QUOTE);

        return sb;
    }

    public StringBuilder serialize(Number number) {
        StringBuilder sb = new StringBuilder();
        sb.append(number);

        return sb;
    }

    public StringBuilder serialize(Boolean bool) {
        StringBuilder sb = new StringBuilder();
        sb.append(bool);

        return sb;
    }

    public StringBuilder serialize(Object object) throws SerializationException {
        if (object instanceof Map) {
            return serialize((Map<String, Object>) object);
        }

        if (object instanceof Collection) {
            return serialize((Collection<Object>) object);
        }

        if (object instanceof String) {
            return serialize((String) object);
        }

        if (object instanceof Number) {
            return serialize((Number) object);
        }

        if (object instanceof Boolean) {
            return serialize((Boolean) object);
        }

        throw new SerializationException("Unsupported object: " + object.toString());
    }
}
