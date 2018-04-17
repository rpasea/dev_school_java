package com.example.serialization.json;


import com.example.serialization.SerializationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

class MapSerializer {
    public StringBuilder serialize(Map<String, Object> jsonObject) throws SerializationException {
        StringBuilder sb = new StringBuilder();

        sb.append('{');

        jsonObject.forEach((key, value) -> {
            StringBuilder serializedValue = null;

            try {
                serializedValue = serialize(value);
            }catch (SerializationException se){

            }

            sb.append(String.format("\"%s\":%s,", key, serializedValue == null ? "" : serializedValue));
        });

        sb.deleteCharAt(sb.length() - 1);

        sb.append('}');

        return sb;
    }

    public StringBuilder serialize(Collection<Object> collection) throws SerializationException {

        StringBuilder collectionSb = new StringBuilder();

        collectionSb.append('[');

        collection.forEach(currentObject -> {

            StringBuilder serializedValue = null;

            try{
                serializedValue = serialize(currentObject);
            }catch (SerializationException se){

            }

            collectionSb.append(serializedValue == null ? "" : serializedValue);
            collectionSb.append(",");
        });

        collectionSb.deleteCharAt(collectionSb.length() - 1);
        collectionSb.append(']');

        return null;
    }

    public StringBuilder serialize(String string) {

        return new StringBuilder().append(String.format("\"%s\"", string));
    }

    public StringBuilder serialize(Number number) {

        return new StringBuilder().append(String.format("%s", number.toString()));
    }

    public StringBuilder serialize(Boolean bool) {

        return new StringBuilder().append(bool ? "\"true\"" : "\"false\"");
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
