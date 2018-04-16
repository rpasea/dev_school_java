package com.example.serialization.json;

import com.example.serialization.DeserializationException;
import com.example.serialization.SerializationException;
import com.example.serialization.SerializerDeserializer;

import java.util.Map;

public class JsonSerdes implements SerializerDeserializer {
    private MapSerializer mapSerializer;
    private MapDeserializer mapDeserializer;

    public JsonSerdes() {
        mapSerializer = new MapSerializer();
        mapDeserializer = new MapDeserializer();
    }

    public Map<String, Object> deserialize(String json) throws DeserializationException {
        return mapDeserializer.deserialize(json);
    }

    public String serialize(Map<String, Object> jsonObject) throws SerializationException {
        return mapSerializer.serialize(jsonObject).toString();
    }

    public String serialize(Object jsonObject) {
        return "";
    }
}
