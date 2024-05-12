package org.example.interpreter.ast;

import lombok.Getter;
import lombok.Setter;
import org.example.interpreter.token.Token;

@Getter
public class InfixExpression implements Expression {
    private Token token;
    private String operator;
    private Expression left;
    @Setter
    private Expression right;

    public InfixExpression(Token token, String operator, Expression left) {
        this.token = token;
        this.operator = operator;
        this.left = left;
    }

    @Override
    public String toString() {
        String result = "(";
        result += left.toString();
        result += " " + operator + " ";
        result += right.toString();
        result += ")";
        return result;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }
}
