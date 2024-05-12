package org.example.interpreter.parser;

public enum OperatorPrecedence {
    LOWEST,
    EQUALS, // ==
    LESSGREATER, // > or <
    SUM, // +
    PRODUCT, // *
    PREFIX, // -X or !X
    CALL // myFunction(X)
}
