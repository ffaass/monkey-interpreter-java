package org.example.interpreter.ast;

import lombok.Getter;
import org.example.interpreter.token.Token;

@Getter
public class IntegerLiteral implements Expression {
    private Token token;
    private int value;

    public IntegerLiteral(Token token, int value) {
        this.token = token;
        this.value = value;
    }

    @Override
    public String toString() {
        return token.getLiteral();
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }
}
