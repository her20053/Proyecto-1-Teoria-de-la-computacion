import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Se necesita agregar recurrsividad al primer eclosure:

public class Subconjuntos {

    public AFN afn;

    ArrayList<ArrayList<Integer>> estados = new ArrayList<ArrayList<Integer>>();

    ArrayList<Integer> listaTransiciones = new ArrayList<Integer>();

    AFD afd = new AFD();

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

        // System.out.println(eclosure);
        // System.out.println(eclosure.size());
        for (int i = 0; i < eclosure.size(); i++) {
            // System.out.println("i" + eclosure.get(i));
            estado.add(eclosure.get(i));
            for (Transicion t : afn.transiciones) {
                if (t.estadoInicial == eclosure.get(i)) {
                    if (t.valor == 'ε') {
                        estado.add(t.estadoFinal);
                    }
                }
            }

            // System.out.println("Removiendo: " + eclosure.get(0));
            // eclosure.remove(0);
            // eclosure.remove(eclosure.get(i));
            // System.out.println(eclosure.get(i));
        }

        eclosure.clear();

        // System.out.println(eclosure);
        // System.out.println(estado);

        Collections.sort(estado);
        boolean existe = estados.contains(estado);
        if (existe == false) {
            estados.add(estado);
        }

        obtenerMoves(estado, estado);
    }

    // metodo para obtener los moves de cada estado
    // se necesita la lista del estado obtenido anteriormente como parametro
    public void obtenerMoves(ArrayList<Integer> estado, ArrayList<Integer> eclosureList) {

        ArrayList<Transicion> transicionesAceptadas = new ArrayList<Transicion>();

        for (Integer i : eclosureList) {

            for (Transicion t : afn.transiciones) {

                if (t.estadoInicial == i) {
                    // t.mostrarTransicion();

                    if (t.valor != 'ε') {

                        // System.out.println("Agregada transicion:");
                        // t.mostrarTransicion();
                        transicionesAceptadas.add(t);

                    }

                }

            }

        }

        // System.out.println(transicionesAceptadas);

        HashMap<Character, ArrayList<Integer>> diccionario = new HashMap<Character, ArrayList<Integer>>();

        for (Transicion t : transicionesAceptadas) {

            diccionario.put(t.valor, new ArrayList<Integer>());

        }

        for (Transicion t : transicionesAceptadas) {

            ArrayList<Integer> temp = diccionario.get(t.valor);
            temp.add(t.estadoFinal);
            diccionario.put(t.valor, temp);

        }

        // System.out.println(diccionario);

        for (Map.Entry<Character, ArrayList<Integer>> entry : diccionario.entrySet()) {
            Character key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();
            // System.out.println(key + "=" + value);
            Transicion transTemp = new Transicion(estado, key, value);

            for (ArrayList<Integer> arr : estados) {

                System.out.println(arr);

                // for(ArrayList<Integer> arr2: arr){

                // if(arr2 == value){}

                // }
                estados.add(value);
            }

            // transTemp.mostrarTransicion2();
            afd.transiciones.add(transTemp);
        }

        // afd.mostrarAFD2();
        // System.out.println(estados);
        estados.remove(estado);

        listaTransiciones.clear();

        // System.out.println("SIZE ACTUAL: " + estados.size());
        // while (estados.size() != 0) {

        // for (ArrayList<Integer> arr : estados) {

        // eclosure(arr);

        // }

        // }

    }

    public void eclosure(ArrayList<Integer> estado2) {

        System.out.println(estados);
        System.out.println("Estado 2: " + estado2);

        for (Integer i : estado2) {
            listaTransiciones.add(i);
            for (Transicion t : afn.transiciones) {
                if (t.estadoInicial == i) {
                    if (t.valor == 'ε') {
                        ArrayList<Integer> arr = new ArrayList<Integer>();
                        arr.add(t.estadoFinal);
                        eclosure(arr);
                    }
                }
            }
        }

        System.out.println(listaTransiciones);
        obtenerMoves(estado2, listaTransiciones);

    }

}
