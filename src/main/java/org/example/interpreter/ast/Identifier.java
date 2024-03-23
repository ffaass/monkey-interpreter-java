package org.example.interpreter.ast;

import org.example.interpreter.token.Token;

public class Identifier {
    private Token token;
    private String value;

    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }
}
