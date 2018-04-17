package com.example.serialization.json;

import com.example.serialization.SerializationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

class MapSerializer {
    public StringBuilder serialize(Map<String, Object> jsonObject) throws SerializationException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");

        int counter = 0;
        int length = jsonObject.entrySet().toArray().length;

        for (Map.Entry<String, Object> entry: jsonObject.entrySet()) {

            stringBuilder.append("\"").append(entry.getKey()).append("\":").append(serialize(entry.getValue()));

            if (counter <  length - 1) {
                stringBuilder.append(",");
            }

            counter++;
        }

        stringBuilder.append("}");

        return stringBuilder;
    }

    public StringBuilder serialize(Collection<Object> collection) throws SerializationException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            stringBuilder.append(serialize((iterator.next())));
            if (iterator.hasNext()) {
                stringBuilder.append(",");
            }
        }

        stringBuilder.append("]");

        return stringBuilder;
    }

    public StringBuilder serialize(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"" + string + "\"");

        return stringBuilder;
    }

    public StringBuilder serialize(Number number) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(number);

        return stringBuilder;
    }

    public StringBuilder serialize(Boolean bool) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"" + bool + "\"");

        return stringBuilder;
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
