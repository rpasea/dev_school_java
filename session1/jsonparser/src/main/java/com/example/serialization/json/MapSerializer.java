package com.example.serialization.json;


import com.example.serialization.SerializationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

class MapSerializer {
    public StringBuilder serialize(Map<String, Object> jsonObject) throws SerializationException {
        // TODO
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int i = 0;
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()){
            i++;
            if (i > 1 ){
                sb.append(",");
            }
            sb.append("\"").append(entry.getKey()).append("\"").append(":");
            sb.append(serialize(entry.getValue()));
        }
        sb.append("}");
        return sb;
    }

    public StringBuilder serialize(Collection<Object> collection) throws SerializationException {
        // TODO
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int dimensiune = collection.size();
        if (dimensiune > 1){
            sb.append("[");
        }
        for (Object obj : collection){
            i++;
            if (i > 1){
                sb.append(",");
            }
            sb.append(serialize(obj));
        }
        if (dimensiune > 1){
            sb.append("]");
        }
        return sb;
    }

    public StringBuilder serialize(String string) {
        // TODO
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
