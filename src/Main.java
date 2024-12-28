public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("src/script.txt");
        lexer.tokenize();
        Executor executor = new Executor();
        executor.execute(lexer.getTokens());

        System.out.println(lexer.getTokens());
    }
}