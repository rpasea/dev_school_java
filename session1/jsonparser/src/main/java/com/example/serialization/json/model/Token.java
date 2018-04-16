package com.example.serialization.json.model;

import java.util.HashMap;
import java.util.Map;

public enum Token {
    LBRACE('{'), RBRACE('}'), LBRACKET('['), RBRACKET(']'), COMMA(','), QUOTE('\"'), COLON(':'), NOOP('\0');

    private static Map<Character, Token> charToToken = new HashMap<>();

    static {
        for (Token token : Token.values()) {
            charToToken.put(token.getValue(), token);
        }
    }

    private final char value;

    Token(char c) {
        value = c;
    }

    public char getValue() {
        return value;
    }

    public static Token forChar(char c) {
        return charToToken.get(c);
    }
}
