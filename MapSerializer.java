package com.example.serialization.json;


import com.example.serialization.SerializationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class MapSerializer {
    public StringBuilder serialize(Map<String, Object> jsonObject) throws SerializationException {
        // TODO
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, Object> entry: jsonObject.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":");
            sb.append(serialize(entry.getValue())).append(",");
        }
        sb.setLength(sb.length() - 1);

        sb.append("}");
        return sb;
    }

    public StringBuilder serialize(Collection<Object> collection) throws SerializationException {
        // TODO
        //System.out.println(collection);
        StringBuilder sb = new StringBuilder();
        Iterator<Object> it = null;
        it = collection.iterator();
        sb.append("[");
        while (it.hasNext()){
            Object elem = it.next();
            sb.append(serialize(elem)).append(",");


        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb;
    }

    public StringBuilder serialize(String string) {
        // TODO
       // System.out.println(string);
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(string).append("\"");
        return sb;
    }

    public StringBuilder serialize(Number number) {
        // TODO
        StringBuilder sb = new StringBuilder();
        sb.append(number);
        return sb;
    }

    public StringBuilder serialize(Boolean bool) {
        // TODO
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(bool).append("\"");
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
