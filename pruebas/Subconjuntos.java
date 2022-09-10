import java.util.ArrayList;
import java.util.Collections;

public class Subconjuntos {

    public AFN afn;
    ArrayList<ArrayList<Integer>> estados = new ArrayList<ArrayList<Integer>>();

    public Subconjuntos(AFN afn) {
        this.afn = afn;
    }

    public void toAfd(AFN afn) {

    }

    // metodo para obtener los eclosures de los estados
    // aca podriamos pasarle el estadoinicial del eclore como parametro mejor para
    // que pudieramos utilizarlo para todos los estados
    public void obtenerEclosures() {

        int estadoInicialE = afn.transiciones.get(0).estadoInicial;

        ArrayList<Character> simbolos = new ArrayList<Character>();

        for (Transicion t : afn.transiciones) {
            if (t.valor != 'ε') {
                simbolos.add(t.valor);
            }
        }

        ArrayList<Integer> eclosure = new ArrayList<Integer>();
        ArrayList<Integer> estado = new ArrayList<Integer>();

        estado.add(estadoInicialE);

        for (Transicion t : afn.transiciones) {
            if (t.estadoInicial == estadoInicialE) {
                if (t.valor == 'ε') {
                    eclosure.add(t.estadoFinal);
                }
            }
        }

        System.out.println(eclosure);
        for (int i = 0; i < eclosure.size(); i++) {
            System.out.println("i" + eclosure.get(i));
            estado.add(eclosure.get(i));
            for (Transicion t : afn.transiciones) {
                if (t.estadoInicial == eclosure.get(i)) {
                    if (t.valor == 'ε') {
                        estado.add(t.estadoFinal);
                    }
                }
            }
            eclosure.remove(i);
        }

        System.out.println(eclosure);
        System.out.println(estado);

        Collections.sort(estado);
        boolean existe = estados.contains(estado);
        if (existe == false) {
            estados.add(estado);
        }

        obtenerMoves(estado);
    }

    // metodo para obtener los moves de cada estado
    // se necesita la lista del estado obtenido anteriormente como parametro
    public void obtenerMoves(ArrayList<Integer> estado) {

        ArrayList<Integer> nuevoEstado = new ArrayList<Integer>();

        for (Integer i : estado) {
            for (Transicion t : afn.transiciones) {
                if (t.estadoInicial == i) {
                    if (t.valor != 'ε') {
                        // No esta sirviendo porque agrega a estados unas listas vacias en vez de las
                        // listas que contienen al 1 y al 3
                        // Esto tampoco serviria con todos los casos porque hay casos en que a y b se
                        // mueven hacia varios estados no solo uno (cambiar a futuro)
                        System.out.println(t.valor + " " + t.estadoFinal);
                        nuevoEstado.add(t.estadoFinal);
                        Collections.sort(nuevoEstado);
                        System.out.println(nuevoEstado);
                        boolean existe = estados.contains(nuevoEstado);
                        System.out.println(existe);

                        estados.add(nuevoEstado);

                        nuevoEstado.clear();
                    }
                }
            }
        }

        System.out.println(estados);
    }

}
