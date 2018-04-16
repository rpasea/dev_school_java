package com.example.serialization;

import java.util.Map;

public interface SerializerDeserializer {
    Map<String, Object> deserialize(String json) throws DeserializationException;

    String serialize(Map<String, Object> jsonObject) throws SerializationException;

    String serialize(Object jsonObject);
}
