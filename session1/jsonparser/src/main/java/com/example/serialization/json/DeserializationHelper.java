package com.example.serialization.json;

import com.example.serialization.json.model.Pair;
import com.example.serialization.json.model.Token;

class DeserializationHelper {
    public static Pair<Token, Integer> findNextToken(String json) {
        for (int i = 0; i < json.length(); ++i) {
            Token token = Token.forChar(json.charAt(i));
            if (token != null) {
                return new Pair<Token, Integer>(token, i);
            }
        }

        return new Pair<Token, Integer>(null, -1);
    }
}
