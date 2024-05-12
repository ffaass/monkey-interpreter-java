package org.example.interpreter.ast;

import lombok.Getter;
import org.example.interpreter.token.Token;

@Getter
public class Identifier implements Expression {
    private Token token;
    private String value;

    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getTokenLiteral() {
        return token.getLiteral();
    }
}
