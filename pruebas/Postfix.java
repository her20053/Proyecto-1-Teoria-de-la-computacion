import java.util.ArrayDeque;
import java.util.Deque;

public class Postfix {

    // A utility function to return
    // precedence of a given operator
    // Higher returned value means
    // higher precedence
    static int Prec(char ch) {
        switch (ch) {
            case '|':
                return 1;

            case '.':
                // case '|':
                return 2;

            case '*':
                return 3;
        }
        return -1;
    }

    // The main method that converts
    // given infix expression
    // to postfix expression.
    static String infixToPostfix(String exp) {
        // initializing empty String for result
        String result = new String("");

        // initializing empty stack
        Deque<Character> stack = new ArrayDeque<Character>();

        // System.out.println(exp);

        for (int i = 0; i < exp.length(); ++i) {

            // System.out.println(result);
            // System.out.print("Operandos en stack: ");
            // System.out.println();

            char c = exp.charAt(i);

            // If the scanned character is an
            // operand, add it to output.
            if (Character.isLetterOrDigit(c))
                result += c;

            // If the scanned character is an '(',
            // push it to the stack.
            else if (c == '(')
                stack.push(c);

            // If the scanned character is an ')',
            // pop and output from the stack
            // until an '(' is encountered.
            else if (c == ')') {
                while (!stack.isEmpty()
                        && stack.peek() != '(') {
                    result += stack.peek();
                    stack.pop();
                }

                stack.pop();
            } else // an operator is encountered
            {
                while (!stack.isEmpty()
                        && Prec(c) <= Prec(stack.peek())) {

                    result += stack.peek();
                    stack.pop();
                }
                stack.push(c);
            }
        }

        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                return "Invalid Expression";
            result += stack.peek();
            stack.pop();
        }

        return result;
    }

    // Driver's code
    // public static void main(String[] args) {
    // String exp = "a.(a+b)*.b";
    // String exp1 = "(a|b)*.a.b.b";
    // String exp2 = "((a|b)*.a.(a|b)*.a.(a|b)*)*.b";

    // // Function call
    // System.out.println(infixToPostfix(exp));
    // System.out.println(infixToPostfix(exp1));
    // System.out.println(infixToPostfix(exp2));
    // }
}