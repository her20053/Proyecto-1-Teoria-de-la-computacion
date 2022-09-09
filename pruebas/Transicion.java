public class Transicion {

    public int estadoInicial;
    public int estadoFinal;
    public char valor;
    public int valorTransaccion;

    public Transicion(int estado1, char c, int estado2) {
        this.estadoInicial = estado1;
        this.estadoFinal = estado2;
        this.valor = c;
    }

}
