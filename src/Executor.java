import java.util.LinkedList;
import java.util.Objects;

public class Executor {
    public LinkedList<Token> getBody (int index, LinkedList<Token> tokens) {
        LinkedList<Token> output = new LinkedList<>();

        int i = index;
        while (i < tokens.size()) {
            if (tokens.get(i).getType() == Type.BRACE) break;
            output.add(tokens.get(i));

            i ++;
        }

        return output;
    }

    public void execute (LinkedList<Token> tokens) {
        int i = 0;
        while (i < tokens.size()) {
            Token token = tokens.get(i);
            if (token.getType() == Type.KEYWORD && Objects.equals(token.getValue(), "var")) {
                i += 4;
                continue;
            }
            if (token.getType() == Type.IDENTIFIER) {
                i += 2;
                continue;
            }
            if (token.getType() == Type.KEYWORD && Objects.equals(token.getValue(), "Println")) {
                System.out.println(tokens.get(i + 1).getValue());
                i += 2;
                continue;
            }

            if (token.getType() == Type.KEYWORD && Objects.equals(token.getValue(), "if")) {
                LinkedList<Token> ifBody = getBody(i + 2, tokens);
                LinkedList<Token> elseBody = Objects.equals(tokens.get(i + ifBody.size() + 3).getValue(), "else") ? getBody(i + ifBody.size() + 4, tokens) : null;

                if (Objects.equals(tokens.get(i + 1).getValue(), "true")) {
                    execute(ifBody);
                } else {
                    if (elseBody != null) execute(elseBody);
                }

                i += ifBody.size() + 5 + (elseBody != null ? elseBody.size() : 0);
            }

            if (token.getType() == Type.KEYWORD && Objects.equals(token.getValue(), "while")) {
                LinkedList<Token> whileBody = getBody(i + 2, tokens);
            }

            i ++;
        }
    }
}
