import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private static int intValueOf (String x, HashMap<String, String> memory) {
        if (x.matches(Regex.LITERAL)) return Integer.parseInt(x);

        if (memory.containsKey(x)) return Integer.parseInt(memory.get(x));
        throw new RuntimeException("Variable `"+ x +"` needs to be declared");
    }

    private static String[] getOperators (String str) {
        LinkedList<String> operators = new LinkedList<>();
        for (char ch : str.toCharArray()) {
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                operators.add(ch + "");
            }
        }

        return operators.toArray(new String[0]);
    }
    private static int[] getLiterals (String str, HashMap<String, String> memory) {
        LinkedList<Integer> literals = new LinkedList<>();
        for (String l : str.split(Regex.OPERATOR)) {
            literals.add(intValueOf(l, memory));
        }

        int[] array = new int[literals.size()];
        int index = 0;
        for (Integer value : literals) {
            array[index++] = value;
        }

        return array;
    }
    static String parseArithmeticExpression(String str, HashMap<String, String> memory) {
        int[] literals = getLiterals(str, memory);
        String[] operators = getOperators(str);

        if (literals.length != operators.length + 1) {
            throw new IllegalArgumentException("Syntax error: Incorrect amount of operators.");
        }

        LinkedList<Integer> literalList = new LinkedList<>();
        for (int literal : literals) literalList.add(literal);
        LinkedList<String> operatorList = new LinkedList<>(Arrays.asList(operators));

        int i = 0;
        while (i < operatorList.size()) {
            String op = operatorList.get(i);
            if (Objects.equals(op, "*") || Objects.equals(op, "/")) {
                int left = literalList.get(i);
                int right = literalList.get(i + 1);
                int intermediateResult = (op.equals("*")) ? left * right : left / right;

                literalList.set(i, intermediateResult);
                literalList.remove(i + 1);
                operatorList.remove(i);
            } else {
                i++;
            }
        }

        int result = literalList.getFirst();
        for (int j = 0; j < operatorList.size(); j++) {
            String op = operatorList.get(j);
            int nextLiteral = literalList.get(j + 1);
            if (Objects.equals(op, "+")) {
                result += nextLiteral;
            } else if (Objects.equals(op, "-")) {
                result -= nextLiteral;
            } else {
                throw new RuntimeException("Runtime error: Incorrect operation (" + op + ").");
            }
        }

        return result + "";
    }

    public static String getBooleanOperator(String input) {
        Pattern pattern = Pattern.compile(Regex.COMPARATOR);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group();
        }

        throw new RuntimeException("Syntax error: no comparator found.");
    }
    private static int[] getBooleanLiterals (String str, HashMap<String, String> memory) {
        LinkedList<Integer> literals = new LinkedList<>();
        for (String l : str.split(Regex.COMPARATOR)) {
            literals.add(intValueOf(l, memory));
        }

        int[] array = new int[literals.size()];
        int index = 0;
        for (Integer value : literals) {
            array[index++] = value;
        }

        return array;
    }
    static String parseBooleanExpression(String str, HashMap<String, String> memory) {
        if (Objects.equals(str, "true")) return "true";
        if (Objects.equals(str, "false")) return "false";

        int first = getBooleanLiterals(str, memory)[0];
        int second = getBooleanLiterals(str, memory)[1];

        return switch (getBooleanOperator(str)) {
            case ">" -> first > second ? "true" : "false";
            case "<" -> first < second ? "true" : "false";
            case "==" -> first == second ? "true" : "false";
            case "<=" -> first <= second ? "true" : "false";
            case ">=" -> first >= second ? "true" : "false";
            default -> throw new RuntimeException("Syntax error: Cannot solve condition.");
        };
    }
}
