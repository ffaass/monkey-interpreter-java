package org.example.interpreter.ast;

import lombok.Getter;
import lombok.Setter;
import org.example.interpreter.token.Token;

@Getter
public class PrefixExpression implements Expression {
    private Token token;
    private String operator;
    @Setter
    private Expression right;

    public PrefixExpression(Token token, String operator) {
        this.token = token;
        this.operator = operator;
    }

    @Override
    public String toString() {
        String result = "(";
        result += operator;
        result += right.toString();
        result += ")";
        return result;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }
}
