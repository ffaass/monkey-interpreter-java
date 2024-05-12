package org.example.interpreter.ast;

import lombok.Getter;
import org.example.interpreter.token.Token;

@Getter
public class ExpressionStatement implements Statement {
    private Token token;
    private Expression expression;

    public ExpressionStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

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
