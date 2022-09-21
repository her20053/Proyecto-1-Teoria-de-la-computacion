import java.util.ArrayList;

public class Transicion {

    public int estadoInicial;
    public int estadoFinal;
    public char valor;
    public int valorTransaccion;

    public ArrayList<Integer> estadosInicial;
    public ArrayList<Integer> estadosFinal;

    public ArrayList<Node> ein;
    public ArrayList<Node> efn;

    public Transicion(int estado1, char c, int estado2) {
        this.estadoInicial = estado1;
        this.estadoFinal = estado2;
        this.valor = c;
    }

    public Transicion(ArrayList<Integer> estadosIncial, char c, ArrayList<Integer> estadosFinal) {
        this.estadosInicial = estadosIncial;
        this.estadosFinal = estadosFinal;
        this.valor = c;
    }

    public Transicion(ArrayList<Node> estadosIncial, char c, ArrayList<Node> estadosFinal, ArrayList<Node> queImporta) {
        this.ein = estadosIncial;
        this.efn = estadosFinal;
        this.valor = c;
    }

    public void mostrarTransicion() {
        System.out.println("[" + estadoInicial + "," + valor + "," + estadoFinal + "]");
    }

    public void mostrarTransicion2() {
        System.out.println("[" + estadosInicial + "," + valor + "," + estadosFinal + "]");
    }

    public String toString() {
        return "[" + estadosInicial + "," + valor + "," + estadosFinal + "]";
    }

}
