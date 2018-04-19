package com.example.serialization.json;


import com.example.serialization.SerializationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

class MapSerializer {
    public StringBuilder serialize(Map<String, Object> jsonObject) throws SerializationException {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int i=0;
        for (Map.Entry<String, Object> entry: jsonObject.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":");
            sb.append(serialize(entry.getValue()));
            if (entry.getValue() instanceof Map || entry.getValue() instanceof Collection) {
                i++;
            }
        }
        if(i==0){
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("}");

        return sb;
    }

    public StringBuilder serialize(Collection<Object> collection) throws SerializationException {
        StringBuilder sb = new StringBuilder();


        sb.append("[");
        int i =1;
        for (Object entry: collection) {
            if (i<collection.size()) {
                sb.append(serialize(entry)).append(",");
                i++;
            }
            else{
                sb.append(serialize(entry));
            }
        }
        sb.append("]");
        return sb;

    }

    public StringBuilder serialize(String string) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(string).append("\",");
        return sb;
    }

    public StringBuilder serialize(Number number) {
        StringBuilder sb = new StringBuilder();
        sb.append(number).append(",");
        return sb;
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
