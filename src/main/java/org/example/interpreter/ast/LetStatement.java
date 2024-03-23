package org.example.interpreter.ast;

import org.example.interpreter.token.Token;

public class LetStatement implements Statement {
    private Token token;
    private Identifier name;
    private Expression value;

    public LetStatement(Token token, Identifier name, Expression value) {
        this.token = token;
        this.name = name;
        this.value = value;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    public String toString() {
        String result = getTokenLiteral() + " ";
        result += name + " = ";
        if (value != null) {
            result += value.toString();
        }
        result += ";";
        return result;
    }
}
