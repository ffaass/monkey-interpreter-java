package org.example.interpreter.parser;


import org.example.interpreter.ast.Expression;
import org.example.interpreter.ast.ExpressionStatement;
import org.example.interpreter.ast.Identifier;
import org.example.interpreter.ast.IntegerLiteral;
import org.example.interpreter.ast.PrefixExpression;
import org.example.interpreter.ast.Program;
import org.example.interpreter.ast.Statement;
import org.example.interpreter.lexer.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;


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


    @ParameterizedTest
    @MethodSource("parsingPrefixExpressionsSource")
    void testParsingPrefixExpressions(String input, String operator, int integerValue) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);

        Program program = parser.parseProgram();

        checkParserErrors(parser);

        if (program.getStatements().size() != 1) {
            Assertions.fail(String.format("program.Statements does not contain %d statements. got=%d%n", 1, program.getStatements().size()));
        }

        Statement statement = program.getStatements().get(0);
        if (!(statement instanceof ExpressionStatement)) {
            Assertions.fail(String.format("program.Statements[0] is not ExpressionStatement. got=%s%n", statement.getClass().getName()));
        }

        Expression exp = ((ExpressionStatement) statement).getExpression();
        if (!(exp instanceof PrefixExpression)) {
            Assertions.fail(String.format("exp not PrefixExpression. got=%s", exp.getClass().getName()));
        }

        PrefixExpression prefixExp = (PrefixExpression) exp;

        if (!prefixExp.getOperator().equals(operator)) {
            Assertions.fail(String.format("exp.Operator not %s. got=%s", operator, prefixExp.getOperator()));
        }

        if (!testIntegerLiteral(prefixExp.getRight(), integerValue)) {
            Assertions.fail();
        }
    }

    private static Stream<Arguments> parsingPrefixExpressionsSource() {
        return Stream.of(
                Arguments.of("!5;", "!", 5),
                Arguments.of("-15;", "-", 15)
        );
    }

    private boolean testIntegerLiteral(Expression right, int integerValue) {
        if (!(right instanceof IntegerLiteral)) {
            System.err.printf("right not IntegerLiteral. got=%s%n", right.getClass().getName());
            return false;
        }
        IntegerLiteral integerLiteral = (IntegerLiteral) right;
        if (integerLiteral.getValue() != integerValue) {
            System.err.printf("integ.Value not %d. got=%d%n", integerValue, integerLiteral.getValue());
            return false;
        }

        if (!integerLiteral.getTokenLiteral().equals(String.valueOf(integerValue))) {
            System.err.printf("integ.TokenLiteral not %d. got=%s%n", integerValue, integerLiteral.getTokenLiteral());
            return false;
        }

        return true;
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