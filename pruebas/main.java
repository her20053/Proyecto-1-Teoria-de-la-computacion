
class Main {

    public static void main(String[] args) {

        // String expresionRegular = "(b|b)*abb(a|b)*";
        // String expresionRegular = "a.(a|b)*.b";
        // String expresionRegular = "a.a*|b.b*";
        // String expresionRegular = "a.a*.b*.(a|b)(a|b.a*.b*.(a|b))*";
        // String expresionRegular = "((a|b)*.a.(a|b)*.a.(a|b)*)*b";
        String expresionRegular = "(a|b)*.a.b.b";
        // String expresionRegular = "(a*|b*)*";

        // ------------------------------------------------------------------

        // Postfix

        String postfix = Postfix.infixToPostfix(expresionRegular);

        // System.out.println("\nExpresion postfix: " + postfix + "\n");

        // ------------------------------------------------------------------

        // Thompson

        Thompson thompson = new Thompson(postfix);

        AFN afnFinal = thompson.analizarPostfix();

        // System.out.println("AFN generado por Thompson: \n");

        // afnFinal.mostrarAFN();

        // ------------------------------------------------------------------

        // Subconjuntos

        // Subconjuntos subconjunto = new Subconjuntos(afnFinal);

        // System.out.println("\nSubconjuntos");
        // subconjunto.obtenerEclosures();

        AFN afnReorganizado = Eclosure.reOrganizarAFN(afnFinal);

        // System.out.println(afnReorganizado);

        AFD afd = Eclosure.convertirAFN(afnReorganizado);

        // ------------------------------------------------------------------

    }

}
