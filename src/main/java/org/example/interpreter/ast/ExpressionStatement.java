package org.example.interpreter.ast;

import org.example.interpreter.token.Token;

public class ExpressionStatement implements Statement {
    private Token token;
    private Expression expression;

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    public String toString() {
        if (this.expression != null) {
            return expression.toString();
        }
        return "";
    }
}
