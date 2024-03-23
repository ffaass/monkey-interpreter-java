package org.example.interpreter.parser;

import lombok.Getter;
import org.example.interpreter.ast.Expression;
import org.example.interpreter.ast.Identifier;
import org.example.interpreter.ast.LetStatement;
import org.example.interpreter.ast.Program;
import org.example.interpreter.ast.ReturnStatement;
import org.example.interpreter.ast.Statement;
import org.example.interpreter.lexer.Lexer;
import org.example.interpreter.token.Token;
import org.example.interpreter.token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Parser {
    private Lexer lexer;

    @Getter
    private List<String> errors;

    private Token curToken;
    private Token peekToken;

    private Map<TokenType, Supplier<Expression>> prefixParseFns;
    private Map<TokenType, Function<Expression, Expression>> infixParseFns;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.errors = new ArrayList<>();
        this.prefixParseFns = new HashMap<>();
        this.infixParseFns = new HashMap<>();

        // 토큰을 두 개 읽어서 curToken / peekToken 세팅
        this.nextToken();
        this.nextToken();
    }

    public Program parseProgram() {
        Program program = new Program();

        while (!this.curTokenIs(TokenType.EOF)) {
            Statement stmt = this.parseStatement();
            if (stmt != null) {
                program.addStatement(stmt);
            }
            this.nextToken();
        }
        return program;
    }

    private Statement parseStatement() {
        return switch (this.curToken.getType()) {
            case LET -> this.parseLetStatement();
            case RETURN -> this.parseReturnStatement();
            default -> this.parseExpressionStatement();
        };
    }

    private Statement parseLetStatement() {
        // 현재 토큰 (LET) 을 저장
        Token token = this.curToken;

        // 다음 토큰이 Identifier가 아니면 에러
        if (!this.expectPeek(TokenType.IDENT)) {
            return null;
        }

        // 현재 토큰(IDENT(변수명))으로 Identifier 생성
        Identifier name = new Identifier(this.curToken, this.curToken.getLiteral());

        // 다음 토큰이 ASSIGN(=) 이 아니면 에러
        if (!this.expectPeek(TokenType.ASSIGN)) {
            return null;
        }

        // TODO Value는 나중에 추가
        while (!curTokenIs(TokenType.SEMICOLON)) {
            this.nextToken();
        }

        return new LetStatement(token, name, null);
    }

    private Statement parseReturnStatement() {
        Token token = this.curToken;
        this.nextToken();

        // TODO Value는 나중에 추가
        while (!curTokenIs(TokenType.SEMICOLON)) {
            this.nextToken();
        }
        return new ReturnStatement(token, null);
    }

    private Statement parseExpressionStatement() {
        return null;
    }

    private void nextToken() {
        this.curToken = this.peekToken;
        this.peekToken = this.lexer.nextToken();
    }

    private boolean curTokenIs(TokenType tokenType) {
        return this.curToken.getType() == tokenType;
    }

    private boolean peekTokenIs(TokenType tokenType) {
        return this.peekToken.getType() == tokenType;
    }

    private boolean expectPeek(TokenType tokenType) {
        if (this.peekTokenIs(tokenType)) {
            this.nextToken();
            return true;
        }

        this.peekError(tokenType);
        return false;
    }

    private void peekError(TokenType tokenType) {
        String message = String.format("expected next token to be %s, got %s instead",
                tokenType.name(), this.peekToken.getType().name());
        this.errors.add(message);
    }
}