package org.example.interpreter.ast;

import org.example.interpreter.token.Token;

public class ReturnStatement implements Statement {
    private Token token;
    private Expression returnValue;

    public ReturnStatement(Token token, Expression expression) {
        this.token = token;
        this.returnValue = expression;
    }


    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    public String toString() {
        String result = getTokenLiteral() + " ";
        if (returnValue != null) {
            result += returnValue.toString();
        }
        result += ";";
        return result;
    }
}
