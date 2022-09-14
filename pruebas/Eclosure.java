import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Eclosure {

    static AFN afn = new AFN();

    static AFD afd = new AFD();

    static ArrayList<Integer> listaTransiciones = new ArrayList<Integer>();

    static ArrayList<ArrayList<Integer>> estadosGenerados = new ArrayList<ArrayList<Integer>>();
    static HashMap<ArrayList<Integer>, ArrayList<Integer>> diccionarioEstadosGenerados = new HashMap<ArrayList<Integer>, ArrayList<Integer>>();

    static Scanner sc = new Scanner(System.in);

    public static AFN reOrganizarAFN(AFN afn) {

        AFN nuevoAFN = new AFN();

        int numero = 0;

        HashMap<Integer, Integer> diccionarioEstados = new HashMap<Integer, Integer>();

        for (Transicion t : afn.transiciones) {

            if (!diccionarioEstados.containsKey(t.estadoInicial)) {
                diccionarioEstados.put(t.estadoInicial, numero);
                numero++;
            }
            if (!diccionarioEstados.containsKey(t.estadoFinal)) {
                diccionarioEstados.put(t.estadoFinal, numero);
                numero++;
            }

        }

        for (Transicion t : afn.transiciones) {

            Transicion t2 = new Transicion(
                    diccionarioEstados.get(t.estadoInicial),
                    t.valor,
                    diccionarioEstados.get(t.estadoFinal));
            nuevoAFN.transiciones.add(t2);
        }

        return nuevoAFN;

    }

    public static void obtenerUltimo(Integer inicial) {

        if (listaTransiciones.contains(inicial)) {
            return;
        }
        listaTransiciones.add(inicial);

        for (Transicion t : afn.transiciones) {
            if (t.estadoInicial == inicial) {
                if (t.valor == 'ε') {
                    // sc.nextLine();
                    obtenerUltimo(t.estadoFinal);
                }
            }
        }
    }

    public static AFD convertirAFN(AFN afnParam) {

        // ---------------------------- COMIENZO DEL E-CLOSURE(0)
        // ----------------------------

        Transicion t = afnParam.transiciones.get(0);

        int estado = t.estadoInicial;

        afn = afnParam;

        // System.out.println(afn.obtenerListaCaracteres());

        // Comenzamos con el estado 0:
        // Este metodo llena la lista de Transiciones con todos los estados con lo que
        // conecte 0 a epsilon:
        // Por lo que tenemos que estar seguros de tener una lista vacia de la siguiente
        // manera:
        listaTransiciones.clear();
        // COmenzamos la recurrsividad para llenar la lista:
        obtenerUltimo(estado);
        // Ordenamos la lista para una mejor comprension:
        Collections.sort(listaTransiciones);

        // System.out.println("E-Closure:(" + estado + "): {" + listaTransiciones +
        // "}");
        // System.out.println(listaTransiciones);
        // En teoria ya tenemos nuestro primer estado por lo que lo agregamos a la lista
        // de EstadosGenerados:
        // System.out.println(listaTransiciones);

        ArrayList<Integer> arrTrasicionesTemporal = new ArrayList<Integer>();
        for (Integer i : listaTransiciones) {
            arrTrasicionesTemporal.add(i);
        }

        estadosGenerados.add(arrTrasicionesTemporal);
        ArrayList<Integer> tempo = new ArrayList<Integer>();
        tempo.add(estado);
        diccionarioEstadosGenerados.put(tempo, arrTrasicionesTemporal);
        // Ahora que tenemos los estados, necesitamos ver con los caracteres posibles
        // del afn a donde conectan, para formar los nuevos estados:

        // Creamos un hashmap para almacenar todos los movimientos desde los caracteres:
        HashMap<Character, ArrayList<Integer>> diccionarioMovimientos = new HashMap<Character, ArrayList<Integer>>();

        for (Character c : afn.obtenerListaCaracteres()) {
            for (Transicion transicion : afn.transiciones) {
                for (Integer i : listaTransiciones) {
                    if (transicion.estadoInicial == i && transicion.valor == c) {
                        // System.out.println(transicion);

                        if (diccionarioMovimientos.containsKey(c)) {
                            diccionarioMovimientos.get(c).add(transicion.estadoFinal);
                        } else {
                            ArrayList<Integer> arrtemp = new ArrayList<Integer>();
                            arrtemp.add(transicion.estadoFinal);
                            diccionarioMovimientos.put(c, arrtemp);
                        }
                    }
                }
            }
        }

        // Transicion movimientoDireccion = new Transicion(tempo, c, estadosFinal)

        // Para este punto el diccionario de movimientos se lleno con las posibilidades
        // de todos los caracteres: {a=[4, 8], b=[5]} o {a=[5], b=[8]}
        for (Map.Entry<Character, ArrayList<Integer>> entrada : diccionarioMovimientos.entrySet()) {
            Transicion moviDireccion = new Transicion(tempo, entrada.getKey(), entrada.getValue());
            afd.transiciones.add(moviDireccion);
            // System.out.println("Move(E-Closure(" + estado + ") , " + entrada.getKey() + "
            // ) = {" + entrada.getValue() + "}");
        }
        // System.out.println(diccionarioMovimientos);

        // ------------------------------- FIN DEL E-CLOSURE(0)
        // -------------------------------

        // Ahora necesitamos recorrer el nuevo diccionario de movimientos con cada
        // entrada:

        for (Map.Entry<Character, ArrayList<Integer>> entrada : diccionarioMovimientos.entrySet()) {
            // for (Integer numero : entrada.getValue()) {
            // eClosureGen(numero);
            // }
            eClosureGen(entrada.getValue());
        }

        return afd;
    }

    public static void eClosureGen(ArrayList<Integer> estados) {
        listaTransiciones.clear();
        // COmenzamos la recurrsividad para llenar la lista:
        for (Integer i : estados) {
            obtenerUltimo(i);
        }
        // Ordenamos la lista para una mejor comprension:
        Collections.sort(listaTransiciones);
        // System.out.println(listaTransiciones);
        // System.out.println("\nE-Closure:(" + estados + "): {" + listaTransiciones +
        // "}");
        // En teoria ya tenemos nuestro primer estado por lo que lo agregamos a la lista
        // de EstadosGenerados:
        // if (estadosGenerados.contains(listaTransiciones)) {
        // return;
        // }
        // System.out.println(listaTransiciones);

        ArrayList<Integer> arrTrasicionesTemporal = new ArrayList<Integer>();
        for (Integer i : listaTransiciones) {
            arrTrasicionesTemporal.add(i);
        }

        if (estadosGenerados.contains(arrTrasicionesTemporal)) {
            // System.out.println("Lista de estados ya generada previamente...");
            return;
        }

        estadosGenerados.add(arrTrasicionesTemporal);
        diccionarioEstadosGenerados.put(estados, arrTrasicionesTemporal);
        // Ahora que tenemos los estados, necesitamos ver con los caracteres posibles
        // del afn a donde conectan, para formar los nuevos estados:

        // Creamos un hashmap para almacenar todos los movimientos desde los caracteres:
        HashMap<Character, ArrayList<Integer>> diccionarioMovimientos = new HashMap<Character, ArrayList<Integer>>();

        for (Character c : afn.obtenerListaCaracteres()) {
            for (Transicion transicion : afn.transiciones) {
                for (Integer i : listaTransiciones) {
                    if (transicion.estadoInicial == i && transicion.valor == c) {
                        // System.out.println(transicion);
                        if (diccionarioMovimientos.containsKey(c)) {
                            diccionarioMovimientos.get(c).add(transicion.estadoFinal);
                        } else {
                            ArrayList<Integer> arrtemp = new ArrayList<Integer>();
                            arrtemp.add(transicion.estadoFinal);
                            diccionarioMovimientos.put(c, arrtemp);
                        }
                    }
                }
            }
        }

        // Para este punto el diccionario de movimientos se lleno con las posibilidades
        // de todos los caracteres: {a=[4, 8], b=[5]} o {a=[5], b=[8]}
        for (Map.Entry<Character, ArrayList<Integer>> entrada : diccionarioMovimientos.entrySet()) {
            Transicion moviDireccion = new Transicion(estados, entrada.getKey(), entrada.getValue());
            afd.transiciones.add(moviDireccion);
            // System.out.println("Move(E-Closure(" + estados + ") , " + entrada.getKey() +
            // " ) = {" + entrada.getValue() + "}");
        }

        for (Map.Entry<Character, ArrayList<Integer>> entrada : diccionarioMovimientos.entrySet()) {
            // for (Integer numero : entrada.getValue()) {
            // eClosureGen(numero);
            // }
            eClosureGen(entrada.getValue());
        }
        // System.out.println(diccionarioMovimientos);
    }

}
