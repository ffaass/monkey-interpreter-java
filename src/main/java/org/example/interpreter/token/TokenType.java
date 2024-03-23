package org.example.interpreter.token;

import java.util.Map;

public enum TokenType {


    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    IDENT("IDENT"),
    INT("INT"),
    ASSIGN("="),
    PLUS("+"),
    MINUS("-"),
    BANG("!"),
    ASTERISK("*"),
    SLASH("/"),
    LT("<"),
    GT(">"),
    COMMA(","),
    SEMICOLON(";"),
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    FUNCTION("FUNCTION"),
    LET("LET"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    IF("IF"),
    ELSE("ELSE"),
    RETURN("RETURN"),
    EQ("=="),
    NOT_EQ("!=");


    private static final Map<String, TokenType> KEYWORDS = Map.of(
            "fn", FUNCTION,
            "let", LET,
            "true", TRUE,
            "false", FALSE,
            "if", IF,
            "else", ELSE,
            "return", RETURN
    );

    private String literal;

    TokenType(String literal) {
        this.literal = literal;
    }
    public static TokenType lookupIdent(String ident) {
        if (KEYWORDS.containsKey(ident)) {
            return KEYWORDS.get(ident);
        }
        return IDENT;
    }
}
