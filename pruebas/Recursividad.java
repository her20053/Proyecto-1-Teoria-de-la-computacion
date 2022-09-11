import java.util.ArrayList;

public class Recursividad {

    static AFN afn = new AFN();

    static ArrayList<Integer> listaTransiciones = new ArrayList<Integer>();

    public static void main(String[] args) {

        formarAFN();
        obtenerUltimo(0);
        System.out.println(listaTransiciones);

    }

    public static void formarAFN() {

        afn.transiciones.add(new Transicion(0, 'ε', 1));
        afn.transiciones.add(new Transicion(0, 'a', 1));
        afn.transiciones.add(new Transicion(1, 'ε', 2));
        afn.transiciones.add(new Transicion(2, 'ε', 3));
        afn.transiciones.add(new Transicion(0, 'b', 1));
        afn.transiciones.add(new Transicion(3, 'ε', 4));

    }

    public static void obtenerUltimo(Integer inicial) {

        listaTransiciones.add(inicial);

        for (Transicion t : afn.transiciones) {
            if (t.estadoInicial == inicial) {
                if (t.valor == 'ε') {
                    obtenerUltimo(t.estadoFinal);
                }
            }
        }
    }

}
