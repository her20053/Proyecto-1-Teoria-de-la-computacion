import java.util.Stack;

class Main {

    public static void main(String[] args) {

        String expresionRegular = "(b|b)*abb(a|b)*";

        // Definimos el objeto Thompson y le asignamos su expresion regular
        Thompson thompson = new Thompson(expresionRegular);

        // Construimos el Automata finito no dirigido:
        thompson.construirAFN();

    }

}

class Thompson {

    private String er;

    private Stack<String> stack;

    public Thompson(String expresionRegular) {
        this.er = expresionRegular;
    }

    public void construirAFN() {

    }

    public String getEr() {
        return er;
    }

}