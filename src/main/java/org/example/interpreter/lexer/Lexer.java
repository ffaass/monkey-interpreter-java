package org.example.interpreter.lexer;

import org.example.interpreter.token.Token;
import org.example.interpreter.token.TokenType;

public class Lexer {
    private String input;
    private int position;
    private int readPosition;
    char ch;

    public Lexer(String input) {
        this.input = input;
        this.readChar();
    }

    public Token nextToken() {
        Token token;

        skipWhitespace();
        switch (this.ch) {
            case '=' -> {
                if (this.peekChar() == '=') {
                    char ch = this.ch;
                    this.readChar();
                    String literal = ch + Character.toString(this.ch);
                    token = new Token(TokenType.EQ, literal);
                } else {
                    token = new Token(TokenType.ASSIGN, Character.toString(this.ch));
                }
            }
            case '-' -> token = new Token(TokenType.MINUS, Character.toString(this.ch));
            case '*' -> token = new Token(TokenType.ASTERISK, Character.toString(this.ch));
            case '/' -> token = new Token(TokenType.SLASH, Character.toString(this.ch));
            case '!' -> {
                if (this.peekChar() == '=') {
                    char ch = this.ch;
                    this.readChar();
                    String literal = ch + Character.toString(this.ch);
                    token = new Token(TokenType.NOT_EQ, literal);
                } else {
                    token = new Token(TokenType.BANG, Character.toString(this.ch));
                }
            }
            case '<' -> token = new Token(TokenType.LT, Character.toString(this.ch));
            case '>' -> token = new Token(TokenType.GT, Character.toString(this.ch));
            case ';' -> token = new Token(TokenType.SEMICOLON, Character.toString(this.ch));
            case '(' -> token = new Token(TokenType.LPAREN, Character.toString(this.ch));
            case ')' -> token = new Token(TokenType.RPAREN, Character.toString(this.ch));
            case ',' -> token = new Token(TokenType.COMMA, Character.toString(this.ch));
            case '+' -> token = new Token(TokenType.PLUS, Character.toString(this.ch));
            case '{' -> token = new Token(TokenType.LBRACE, Character.toString(this.ch));
            case '}' -> token = new Token(TokenType.RBRACE, Character.toString(this.ch));
            case 0 -> token = new Token(TokenType.EOF, "");
            default -> {
                if (isLetter(this.ch)) {
                    String literal = this.readIdentifier();
                    TokenType type = TokenType.lookupIdent(literal);
                    return new Token(type, literal);
                } else if (isDigit(this.ch)) {
                    return new Token(TokenType.INT, this.readNumber());
                } else {
                    token = new Token(TokenType.ILLEGAL, Character.toString(this.ch));
                }
            }
        }
        this.readChar();
        return token;
    }

    private void readChar() {
        if (this.readPosition >= input.length()) {
            this.ch = 0;
        } else {
            this.ch = input.charAt(this.readPosition);
        }
        this.position = this.readPosition;
        this.readPosition++;
    }

    private boolean isLetter(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || c == '_';
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private String readIdentifier() {
        int startPosition = this.position;
        while (isLetter(this.ch)) {
            this.readChar();
        }
        return this.input.substring(startPosition, this.position);
    }

    private String readNumber() {
        int startPosition = this.position;
        while (isDigit(this.ch)) {
            this.readChar();
        }
        return this.input.substring(startPosition, this.position);
    }

    private char peekChar() {
        if (this.readPosition >= this.input.length()) {
            return 0;
        }

        return this.input.charAt(this.readPosition);
    }

    private void skipWhitespace() {
        while (this.ch == ' ' || this.ch == '\t' || this.ch == '\n' || this.ch == '\r') {
            this.readChar();
        }
    }
}
