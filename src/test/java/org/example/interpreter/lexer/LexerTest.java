package org.example.interpreter.lexer;


import org.example.interpreter.token.Token;
import org.example.interpreter.token.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LexerTest {
    @Test
    void testNextToken() {
        // given
        String input = """
               let five = 5;
               let ten = 10;
               let add = fn(x, y) {
                   x + y;
               };
               let result = add(five, ten);
               !-/*5;
               5 < 10 > 5;

                if (5 < 10) {
                 return true;
                } else {
                 return false;
                }

               10 == 10;
               10 != 9;
               """;

        List<ExpectedToken> expectedTokens = new ArrayList<>();
        expectedTokens.add(new ExpectedToken(TokenType.LET, "let"));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "five"));
        expectedTokens.add(new ExpectedToken(TokenType.ASSIGN, "="));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "5"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));

        expectedTokens.add(new ExpectedToken(TokenType.LET, "let"));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "ten"));
        expectedTokens.add(new ExpectedToken(TokenType.ASSIGN, "="));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "10"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));

        expectedTokens.add(new ExpectedToken(TokenType.LET, "let"));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "add"));
        expectedTokens.add(new ExpectedToken(TokenType.ASSIGN, "="));
        expectedTokens.add(new ExpectedToken(TokenType.FUNCTION, "fn"));
        expectedTokens.add(new ExpectedToken(TokenType.LPAREN, "("));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "x"));
        expectedTokens.add(new ExpectedToken(TokenType.COMMA, ","));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "y"));
        expectedTokens.add(new ExpectedToken(TokenType.RPAREN, ")"));
        expectedTokens.add(new ExpectedToken(TokenType.LBRACE, "{"));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "x"));
        expectedTokens.add(new ExpectedToken(TokenType.PLUS, "+"));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "y"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));
        expectedTokens.add(new ExpectedToken(TokenType.RBRACE, "}"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));

        expectedTokens.add(new ExpectedToken(TokenType.LET, "let"));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "result"));
        expectedTokens.add(new ExpectedToken(TokenType.ASSIGN, "="));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "add"));
        expectedTokens.add(new ExpectedToken(TokenType.LPAREN, "("));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "five"));
        expectedTokens.add(new ExpectedToken(TokenType.COMMA, ","));
        expectedTokens.add(new ExpectedToken(TokenType.IDENT, "ten"));
        expectedTokens.add(new ExpectedToken(TokenType.RPAREN, ")"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));

        expectedTokens.add(new ExpectedToken(TokenType.BANG, "!"));
        expectedTokens.add(new ExpectedToken(TokenType.MINUS, "-"));
        expectedTokens.add(new ExpectedToken(TokenType.SLASH, "/"));
        expectedTokens.add(new ExpectedToken(TokenType.ASTERISK, "*"));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "5"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));

        expectedTokens.add(new ExpectedToken(TokenType.INT, "5"));
        expectedTokens.add(new ExpectedToken(TokenType.LT, "<"));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "10"));
        expectedTokens.add(new ExpectedToken(TokenType.GT, ">"));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "5"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));

        expectedTokens.add(new ExpectedToken(TokenType.IF, "if"));
        expectedTokens.add(new ExpectedToken(TokenType.LPAREN, "("));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "5"));
        expectedTokens.add(new ExpectedToken(TokenType.LT, "<"));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "10"));
        expectedTokens.add(new ExpectedToken(TokenType.RPAREN, ")"));

        expectedTokens.add(new ExpectedToken(TokenType.LBRACE, "{"));
        expectedTokens.add(new ExpectedToken(TokenType.RETURN, "return"));
        expectedTokens.add(new ExpectedToken(TokenType.TRUE, "true"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));
        expectedTokens.add(new ExpectedToken(TokenType.RBRACE, "}"));

        expectedTokens.add(new ExpectedToken(TokenType.ELSE, "else"));
        expectedTokens.add(new ExpectedToken(TokenType.LBRACE, "{"));
        expectedTokens.add(new ExpectedToken(TokenType.RETURN, "return"));
        expectedTokens.add(new ExpectedToken(TokenType.FALSE, "false"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));
        expectedTokens.add(new ExpectedToken(TokenType.RBRACE, "}"));

        expectedTokens.add(new ExpectedToken(TokenType.INT, "10"));
        expectedTokens.add(new ExpectedToken(TokenType.EQ, "=="));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "10"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));

        expectedTokens.add(new ExpectedToken(TokenType.INT, "10"));
        expectedTokens.add(new ExpectedToken(TokenType.NOT_EQ, "!="));
        expectedTokens.add(new ExpectedToken(TokenType.INT, "9"));
        expectedTokens.add(new ExpectedToken(TokenType.SEMICOLON, ";"));

        expectedTokens.add(new ExpectedToken(TokenType.EOF, ""));


        // when
        Lexer lexer = new Lexer(input);

        // then
        for (ExpectedToken expectedToken : expectedTokens) {
            Token token = lexer.nextToken();
            Assertions.assertEquals(expectedToken.expectedType, token.getType());
            Assertions.assertEquals(expectedToken.expectedLiteral, token.getLiteral());
        }
    }

    private static class ExpectedToken {
        TokenType expectedType;
        String expectedLiteral;

        public ExpectedToken(TokenType expectedType, String expectedLiteral) {
            this.expectedType = expectedType;
            this.expectedLiteral = expectedLiteral;
        }
    }
}