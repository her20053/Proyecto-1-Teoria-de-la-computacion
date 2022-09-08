
class Main {

    public static void main(String[] args) {

        String expresionRegular = "(b|b)*abb(a|b)*";

        // Definimos el objeto Thompson y le asignamos su expresion regular
        Thompson thompson = new Thompson(expresionRegular);

        // thompson.reglaSimbolo('a');
        AFN afn1 = thompson.reglaSimbolo('a');
        AFN afn2 = thompson.reglaSimbolo('b');

        AFN afn3 = thompson.reglaOR(afn1, afn2);

        AFN afn4 = thompson.reglaKleen(afn3);
        afn4.mostrarAFN();

        // afn3.mostrarAFN();

        // Construimos el Automata finito no dirigido:
        // thompson.construirAFN();

        // String expresionPostfix =
        // Postfix.infixToPostfix("((a|b)*.a.(a|b)*.a.(a|b)*)*.b");

    }

}
