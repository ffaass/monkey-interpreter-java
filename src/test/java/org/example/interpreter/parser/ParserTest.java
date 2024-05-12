package org.example.interpreter.parser;


import org.example.interpreter.ast.Expression;
import org.example.interpreter.ast.ExpressionStatement;
import org.example.interpreter.ast.Identifier;
import org.example.interpreter.ast.IntegerLiteral;
import org.example.interpreter.ast.Program;
import org.example.interpreter.ast.Statement;
import org.example.interpreter.lexer.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class ParserTest {

    @Test
    void testIdentifierExpression() {
        String input = "foobar;";
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);

        Program program = parser.parseProgram();

        checkParserErrors(parser);
        if (program.getStatements().size() != 1) {
            Assertions.fail(String.format("program has not enough statements. got=%d%n", program.getStatements().size()));
        }

        Statement statement = program.getStatements().get(0);
        if (!(statement instanceof ExpressionStatement)) {
            Assertions.fail(String.format("program.Statements[0] is not ExpressionStatement. got=%s%n", statement.getClass().getName()));
        }

        Expression exp = ((ExpressionStatement) statement).getExpression();
        if (!(exp instanceof Identifier)) {
            Assertions.fail(String.format("exp not Identifier. got=%s", exp.getClass().getName()));
        }

        Identifier identifier = (Identifier) exp;

        if (!identifier.getValue().equals("foobar")) {
            Assertions.fail(String.format("ident.Value not %s. got=%s", "foobar", identifier.getValue()));
        }

        if (!identifier.getTokenLiteral().equals("foobar")) {
            Assertions.fail(String.format("ident.TokenLiteral not %s. got=%s", "foobar", identifier.getTokenLiteral()));
        }
    }

    @Test
    void testIntegerLiteralExpression() {
        String input = "5;";
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);

        Program program = parser.parseProgram();

        checkParserErrors(parser);

        if (program.getStatements().size() != 1) {
            Assertions.fail(String.format("program has not enough statements. got=%d%n", program.getStatements().size()));
        }

        Statement statement = program.getStatements().get(0);
        if (!(statement instanceof ExpressionStatement)) {
            Assertions.fail(String.format("program.Statements[0] is not ExpressionStatement. got=%s%n", statement.getClass().getName()));
        }

        Expression exp = ((ExpressionStatement) statement).getExpression();
        if (!(exp instanceof IntegerLiteral)) {
            Assertions.fail(String.format("exp not IntegerLiteral. got=%s", exp.getClass().getName()));
        }

        IntegerLiteral literal = (IntegerLiteral) exp;

        if (literal.getValue() != 5) {
            Assertions.fail(String.format("ident.Value not %d. got=%d", 5, literal.getValue()));
        }

        if (!literal.getTokenLiteral().equals("5")) {
            Assertions.fail(String.format("ident.TokenLiteral not %s. got=%s", "5", literal.getTokenLiteral()));
        }
    }

    private void checkParserErrors(Parser p) {
        List<String> errors = p.getErrors();
        if (errors.isEmpty()) {
            return;
        }

        System.err.printf("Parser has %d errors%n", errors.size());
        for (String error : errors) {
            System.err.printf("parser error: %s%n", error);
        }
        Assertions.fail();
    }

}