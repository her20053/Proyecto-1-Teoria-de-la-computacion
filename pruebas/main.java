
class Main {

    public static void main(String[] args) {

        // String expresionRegular = "(b|b)*abb(a|b)*";
        String expresionRegular = "a.(a|b)*.b";
        // String expresionRegular = "a.a*|b.b*";
        // String expresionRegular = "a.a*.b*.(a|b)(a|b.a*.b*.(a|b))*";
        // String expresionRegular = "((a|b)*.a.(a|b)*.a.(a|b)*)*b";

        String postfix = Postfix.infixToPostfix(expresionRegular);

        // System.out.println(postfix);

        Thompson thompson = new Thompson(postfix);

        AFN afnFinal = thompson.analizarPostfix();

        afnFinal.mostrarAFN();

        // thompson.reglaSimbolo('a');
        // AFN afn1 = thompson.reglaSimbolo('a');
        // AFN afn2 = thompson.reglaSimbolo('b');

        // AFN afn3 = thompson.reglaOR(afn1, afn2);

        // AFN afn4 = thompson.reglaKleen(afn3);
        // // afn4.mostrarAFN();

        // AFN afn6 = thompson.reglaSimbolo('c');

        // AFN afn5 = thompson.reglaConcat(afn4, afn6);
        // afn5.mostrarAFN();

        // afn3.mostrarAFN();

        // Construimos el Automata finito no dirigido:
        // thompson.construirAFN();

        // String expresionPostfix =
        // Postfix.infixToPostfix("((a|b)*.a.(a|b)*.a.(a|b)*)*.b");

    }

}
