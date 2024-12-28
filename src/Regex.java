public final class Regex {
    static String IDENTIFIER = "[a-zA-Z_][a-zA-Z0-9_]*";
    static String KEYWORD = "(if|else|while|var|Println)";
    static String LITERAL = "(\\d+|\"[^\"]*\")";
    static String BOOLEAN = "(true|false)";
    static String OPERATOR = "(\\+|-|\\*|/|%|=)";
    static String COMPARATOR = "(>=|<=|==|>|<)";
    static String LOGICAL_OPERATOR = "(&&|\\|\\||!)";
    static String BRACE = "(\\{|\\})";
    static String PAREN = "(\\(|\\))";
    static String WHITESPACE = "(\\s+)";
    static String ERROR = ".";
}
