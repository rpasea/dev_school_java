package com.example.serialization.json;


import com.example.serialization.SerializationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

class MapSerializer {
    public StringBuilder serialize(Map<String, Object> jsonObject) throws SerializationException {
        // TODO
        return null;
    }

    public StringBuilder serialize(Collection<Object> collection) throws SerializationException {
        // TODO
        return null;
    }

    public StringBuilder serialize(String string) {
        // TODO
        return null;
    }

    public StringBuilder serialize(Number number) {
        // TODO
        return null;
    }

    public StringBuilder serialize(Boolean bool) {
        // TODO
        return null;
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
