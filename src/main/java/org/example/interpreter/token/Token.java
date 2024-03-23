package org.example.interpreter.token;

import lombok.Getter;

import java.util.Map;

@Getter
public class Token {
    private TokenType type;
    private String literal;

    public Token(TokenType type, String literal) {
        this.type = type;
        this.literal = literal;
    }
}
