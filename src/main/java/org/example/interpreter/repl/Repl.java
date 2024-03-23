package org.example.interpreter.repl;

import org.example.interpreter.lexer.Lexer;
import org.example.interpreter.token.Token;
import org.example.interpreter.token.TokenType;

import java.util.Scanner;

public class Repl {
    private static final String PROMPT = ">> ";
    public static void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(PROMPT);
            String input = scanner.nextLine();
            if (input == null) {
                return;
            }
            Lexer lexer = new Lexer(input);

            for (Token token = lexer.nextToken(); token.getType() != TokenType.EOF; token = lexer.nextToken()) {
                System.out.println(String.format("Type: %s, Literal: %s", token.getType().name(), token.getLiteral()));
            }
        }
    }
}
