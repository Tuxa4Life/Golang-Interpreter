public enum Type {
    IDENTIFIER,       // Variable names
    KEYWORD,          // if, else, while, var, Println

    LITERAL,          // int or string
    BOOLEAN,          // true, false

    OPERATOR,         // +, -, *, /, %, =
    COMPARATOR,       // >, <, ==, <=, >=
    LOGICAL_OPERATOR, // &&, ||, !

    BRACE,       // {, }
    PAREN,      // (, )

    WHITESPACE,       // Spaces, tabs, and newlines
    ERROR             // Unrecognized
}