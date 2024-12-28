import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class Lexer {
    private String[] lines;
    private final LinkedList<Token> tokens = new LinkedList<>();
    public HashMap<String, String> memory = new HashMap<>();

    public Lexer(String file) {
        try {
            Path path = Paths.get(file);
            List<String> linesList = Files.readAllLines(path);
            lines = linesList.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println("Incorrect path.");
        }
    }

    private void uploadToMemory(String name, String value) {
        if (memory.containsKey(name)) memory.replace(name, value);
        else memory.put(name, value);
    }

    public void tokenize () {
        for (String line : lines) {
            tokenizeLine(line);
//            System.out.println(getTokens());
        }
    }



    public void tokenizeLine(String line) {
        String[] words = removeLeadingWhitespaces(line).trim().split(" ");
        String[] forPrint = removeLeadingWhitespaces(line).split("\\(");

        if (Arrays.asList(words).contains("var")) {
            String name = words[1];
            String value = Expression.parseArithmeticExpression(line.split("=")[1].replaceAll(" ", ""), memory);


            uploadToMemory(name, value);
            tokens.add(new Token(Type.KEYWORD, "var"));
            tokens.add(new Token(Type.IDENTIFIER, name));
            tokens.add(new Token(Type.OPERATOR, "="));
            tokens.add(new Token(Type.LITERAL, value));
        }

        if (Arrays.asList(forPrint).contains("Println")) {
            String output = Expression.parseArithmeticExpression(forPrint[1].split("\\)")[0].replaceAll(" ", ""), memory);

            tokens.add(new Token(Type.KEYWORD, "Println"));
            tokens.add(new Token(Type.LITERAL, output));
        }

        if (words[0].matches(Regex.IDENTIFIER) && words[1].matches("=")) {
            String value = Expression.parseArithmeticExpression(line.split("=")[1].replaceAll(" ", ""), memory);

            uploadToMemory(words[0], value);
            tokens.add(new Token(Type.IDENTIFIER, words[0]));
            tokens.add(new Token(Type.LITERAL, value));
        }

        if (words[0].matches("if")) {
            String condition = forPrint[1].split("\\)")[0].replaceAll(" ", "");
            String outcome = Expression.parseBooleanExpression(condition, memory);

            tokens.add(new Token(Type.KEYWORD, "if"));
            tokens.add(new Token(Type.BOOLEAN, outcome));
        }

        if (Objects.equals(words[0], "}")) {
            tokens.add(new Token(Type.BRACE, "}"));

            if (words.length > 1 && Objects.equals(words[1], "else")) {
                tokens.add(new Token(Type.KEYWORD, "else"));
            }
        }

        if (words[0].matches("while")) {
            String condition = forPrint[1].split("\\)")[0].replaceAll(" ", "");
            String outcome = Expression.parseBooleanExpression(condition, memory);

            tokens.add(new Token(Type.KEYWORD, "while"));
            tokens.add(new Token(Type.BOOLEAN, outcome));
        }
    }


    public String removeLeadingWhitespaces(String str) {
        return str.replaceAll("^\\s+", "");
    }
    public LinkedList<Token> getTokens() {
        return tokens;
    }
}
