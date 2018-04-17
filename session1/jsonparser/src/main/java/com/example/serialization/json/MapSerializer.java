package com.example.serialization.json;


import com.example.serialization.SerializationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

class MapSerializer {
    public StringBuilder serialize(Map<String, Object> jsonObject) throws SerializationException {
        StringBuilder sb = new StringBuilder();
        int leng = jsonObject.entrySet().toArray().length;
        int cnt = 0;
        sb.append("{");
        for(Map.Entry<String, Object> entry: jsonObject.entrySet()){
            sb.append("\"").append(entry.getKey()).append("\":");
            sb.append(serialize(entry.getValue()));
            if (cnt <  leng - 1) {
                sb.append(",");
                cnt++;
            }
        }
        sb.append("}");

        /*
            "key" : "value",
            "key" : "value",

         */
        return sb;
    }

    public StringBuilder serialize(Collection<Object> collection) throws SerializationException {
        // TODO
        /*
           [ jsonObj, jsonObj, ...]
         */

        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        int leng = collection.size();
        sb.append("[");
        for(Object ob :collection){
            sb.append(serialize(ob));
            if(cnt < leng - 1){
                sb.append(",");
                cnt ++;
            }
        }
        sb.append("]");
        return sb;
    }

    public StringBuilder serialize(String string) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(string);
        sb.append("\"");
        return sb;
    }

    public StringBuilder serialize(Number number) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s", number.toString()));
        return sb;
    }

    public StringBuilder serialize(Boolean bool) {
        // true
        StringBuilder sb = new StringBuilder();
        sb.append(bool ? "\"true\"" : "\"false\"");
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
