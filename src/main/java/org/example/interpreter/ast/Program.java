package org.example.interpreter.ast;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Program {
    private List<Statement> statements;

    public Program() {
        this.statements = new ArrayList<>();
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }
    public String getTokenLiteral() {
        if (this.statements.size() > 0) {
            return this.statements.get(0).getTokenLiteral();
        }
        return "";
    }

    public String toString() {
        return statements.stream()
                .map(Statement::toString)
                .collect(Collectors.joining());
    }
}
