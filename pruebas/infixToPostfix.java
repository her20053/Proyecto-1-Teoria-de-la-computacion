import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class infixToPostfix {

    private static boolean isOperator(char operator) {
        return List.of('*', '|', '+', '.').contains(operator);
    }

    private static int precedence(Character operator) {
        return switch (operator) {
            case '.' -> 1;
            case '|', '+' -> 2;
            case '*' -> 3;
            default -> 4;
        };
    }

    private static boolean hasLowerPrecendence(Character c1, Character c2) {
        return precedence(c1) < precedence(c2);
    }

    public static String generatePostfix(String expresion) {

        Stack<Character> operadores = new Stack<Character>();

        ArrayList<Character> postfix = new ArrayList<Character>();

        for (int i = 0; i < operadores.size(); i++) {

            char cActual = expresion.charAt(i);

            if (isOperator(cActual)) {

                while (!operadores.empty() && hasLowerPrecendence(cActual, operadores.peek())) {
                    postfix.add(operadores.pop());
                }

                operadores.push(cActual);

            } else {

                postfix.add(cActual);

            }

        }

        return "";
    }

    public static void main(String[] args) {

        String infix = "a(a|b)*b";
        System.out.println("Postfix: " + generatePostfix(infix));
    }

}
