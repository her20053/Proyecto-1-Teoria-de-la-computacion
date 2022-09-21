import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinimizacionNode {

    public AFD afd;

    public ArrayList<ArrayList<ArrayList<Node>>> particionesInicialesII = new ArrayList<ArrayList<ArrayList<Node>>>();
    public ArrayList<ArrayList<ArrayList<Node>>> particionesInicialesIII = new ArrayList<ArrayList<ArrayList<Node>>>();
    public ArrayList<ArrayList<ArrayList<Node>>> particionesFinales = new ArrayList<ArrayList<ArrayList<Node>>>();

    public ArrayList<ArrayList<Node>> listaEstados = new ArrayList<ArrayList<Node>>();

    public MinimizacionNode(AFD afd) {
        this.afd = afd;
    }

    public AFD minimizarAFD() {

        ArrayList<ArrayList<ArrayList<Node>>> particionesIniciales = new ArrayList<ArrayList<ArrayList<Node>>>();

        HashMap<ArrayList<Node>, ArrayList<Integer>> movimientosParticiones = new HashMap<ArrayList<Node>, ArrayList<Integer>>();

        ArrayList<ArrayList<Node>> temp1 = new ArrayList<ArrayList<Node>>();

        if (afd.estadosNOAceptacionNode.size() > 0) {
            for (ArrayList<Node> arr : afd.estadosNOAceptacionNode) {
                temp1.add(arr);
            }

            particionesIniciales.add(temp1);
        }

        ArrayList<ArrayList<Node>> temp2 = new ArrayList<ArrayList<Node>>();

        for (ArrayList<Node> arr : afd.estadosAceptacionNode) {
            temp2.add(arr);
        }

        particionesIniciales.add(temp2);

        // System.out.println(particionesIniciales);
        // [[[1, 2, 3], [1, 2, 3, 4], [1, 2, 3, 5]], [[1, 2, 3, 6]]]

        for (ArrayList<ArrayList<Node>> arr : particionesIniciales) {
            particionesInicialesII.add(arr);
        }

        for (ArrayList<ArrayList<Node>> grupo : particionesIniciales) {

            // Revisar si el grupo solo tiene una particion:
            if (grupo.size() != 1) {

                // System.out.println("Grupo: " + grupo);
                // [[5], [4, 8], [5, 9], [0]]

                for (ArrayList<Node> particion : grupo) {

                    // Obtenemos los carateres:
                    ArrayList<Character> listaCaracteres = afd.obtenerCaracteresAFD();

                    for (Transicion t : afd.transiciones) {

                        ArrayList<Integer> lista1 = new ArrayList<Integer>();
                        for (Node nod : particion) {
                            lista1.add(nod.numnode);
                        }

                        ArrayList<Integer> lista2 = new ArrayList<Integer>();
                        for (Node nod : t.ein) {
                            lista2.add(nod.numnode);
                        }

                        // System.out.println(lista1);
                        // System.out.println(lista2);
                        // System.out.println();

                        if (t.ein == particion || lista1.equals(lista2)) {

                            // System.out.println(t);
                            // System.out.println("COMPARACION EXITOSA: " + particion + " " + t.ein);

                            // ArrayList<Integer> haciaDonde = new ArrayList<Integer>();

                            // Comparando el caracter
                            for (Character c : listaCaracteres) {

                                ArrayList<Node> estadoFinalEncontrado = t.efn;

                                if ((t.ein == particion || lista1.equals(lista2)) && c == t.valor) {

                                    // System.out.println(particion + " " + c + " " + estadoFinalEncontrado);

                                    for (int i = 0; i < particionesIniciales.size(); i++) {

                                        if (particionesIniciales.get(i).contains(estadoFinalEncontrado)) {

                                            if (movimientosParticiones.containsKey(particion)) {
                                                movimientosParticiones.get(particion).add(i);
                                            } else {
                                                ArrayList<Integer> temporal = new ArrayList<Integer>();
                                                temporal.add(i);
                                                movimientosParticiones.put(particion, temporal);
                                            }

                                        }

                                    }

                                    // System.out.println(movimientosParticiones);

                                }

                            }

                        }
                    }
                }

                // System.out.println("Movimientos de particiones: " + movimientosParticiones);
                HashMap<ArrayList<Integer>, ArrayList<ArrayList<Node>>> nuevasParticiones = new HashMap<ArrayList<Integer>, ArrayList<ArrayList<Node>>>();

                for (Map.Entry<ArrayList<Node>, ArrayList<Integer>> entry : movimientosParticiones.entrySet()) {

                    if (nuevasParticiones.containsKey(entry.getValue())) {

                        nuevasParticiones.get(entry.getValue()).add(entry.getKey());

                    } else {
                        ArrayList<ArrayList<Node>> temporal = new ArrayList<>();
                        temporal.add(entry.getKey());
                        nuevasParticiones.put(entry.getValue(), temporal);
                    }
                }

                // System.out.println(nuevasParticiones);

                Map.Entry<ArrayList<Integer>, ArrayList<ArrayList<Node>>> entry = nuevasParticiones.entrySet()
                        .iterator()
                        .next();

                if (entry.getValue() != grupo) {

                    particionesInicialesII.remove(grupo);

                    ArrayList<ArrayList<ArrayList<Node>>> temporal = new ArrayList<>();

                    for (Map.Entry<ArrayList<Integer>, ArrayList<ArrayList<Node>>> entrada : nuevasParticiones
                            .entrySet()) {

                        particionesInicialesII.add(entrada.getValue());

                    }

                }

                // System.out.println(particionesInicialesII);

            }

        }

        if (particionesIniciales != particionesInicialesII) {

            // System.out.println(particionesInicialesII);
            generarParticiones(particionesInicialesII);

        }

        return contruirAFD();

    }

    public void generarParticiones(ArrayList<ArrayList<ArrayList<Node>>> param) {

        particionesInicialesIII.clear();

        for (ArrayList<ArrayList<Node>> entradaTemporal : param) {
            particionesInicialesIII.add(entradaTemporal);
        }

        HashMap<ArrayList<Node>, ArrayList<Integer>> movimientosParticiones = new HashMap<ArrayList<Node>, ArrayList<Integer>>();

        for (ArrayList<ArrayList<Node>> grupo : param) {

            if (grupo.size() != 1) {

                // System.out.println(grupo);
                // System.out.println(grupo);
                // [[5], [4, 8], [5, 9], [0]]

                for (ArrayList<Node> particion : grupo) {

                    // System.out.println();

                    // Obtenemos los carateres:
                    ArrayList<Character> listaCaracteres = afd.obtenerCaracteresAFD();

                    // Tenemos que revisar hacia donde se dirige con cada simbolo dicha particion:

                    // Por ejemplo tenemos que ver [5] a (hacia donde se dirige)

                    for (Transicion t : afd.transiciones) {

                        ArrayList<Integer> lista1 = new ArrayList<Integer>();
                        // System.out.println("\nCOMPARACION: " + particion + " " + t.ein);
                        for (Node nod : particion) {
                            lista1.add(nod.numnode);
                        }

                        ArrayList<Integer> lista2 = new ArrayList<Integer>();
                        for (Node nod : t.ein) {
                            lista2.add(nod.numnode);
                        }

                        // System.out.println(lista1);
                        // System.out.println(lista2);

                        // Comparando la particion con el estado

                        // System.out.println(particion);

                        if (t.ein == particion || lista1.equals(lista2)) {

                            // System.out.println("ADSDASD");
                            // ArrayList<Integer> haciaDonde = new ArrayList<Integer>();

                            // Comparando el caracter
                            for (Character c : listaCaracteres) {

                                ArrayList<Node> estadoFinalEncontrado = t.efn;

                                if ((t.ein == particion || lista1.equals(lista2)) && c == t.valor) {

                                    // System.out.println(particion + " " + c + " " + estadoFinalEncontrado);

                                    for (int i = 0; i < param.size(); i++) {

                                        if (param.get(i).contains(estadoFinalEncontrado)) {

                                            if (movimientosParticiones.containsKey(particion)) {
                                                movimientosParticiones.get(particion).add(i);
                                            } else {
                                                ArrayList<Integer> temporal = new ArrayList<Integer>();
                                                temporal.add(i);
                                                movimientosParticiones.put(particion, temporal);
                                            }

                                        }

                                    }

                                }

                                // System.out.println(haciaDonde);
                                // Para este punto ya tenemos hacia donde se dirige con cada caracter

                            }

                        }

                    }

                }

                // System.out.println(movimientosParticiones);

                HashMap<ArrayList<Integer>, ArrayList<ArrayList<Node>>> nuevasParticiones = new HashMap<ArrayList<Integer>, ArrayList<ArrayList<Node>>>();

                for (Map.Entry<ArrayList<Node>, ArrayList<Integer>> entry : movimientosParticiones.entrySet()) {

                    if (nuevasParticiones.containsKey(entry.getValue())) {

                        nuevasParticiones.get(entry.getValue()).add(entry.getKey());

                    } else {
                        ArrayList<ArrayList<Node>> temporal = new ArrayList<>();
                        temporal.add(entry.getKey());
                        nuevasParticiones.put(entry.getValue(), temporal);
                    }
                }

                Map.Entry<ArrayList<Integer>, ArrayList<ArrayList<Node>>> entry = nuevasParticiones.entrySet()
                        .iterator()
                        .next();

                // System.out.println(entry.getValue());
                // System.out.println(grupo);

                if (entry.getValue() != grupo) {

                    particionesInicialesIII.remove(grupo);

                    ArrayList<ArrayList<ArrayList<Node>>> temporal = new ArrayList<>();

                    for (Map.Entry<ArrayList<Integer>, ArrayList<ArrayList<Node>>> entrada : nuevasParticiones
                            .entrySet()) {

                        // temporal.add(entrada.getValue());
                        particionesInicialesIII.add(entrada.getValue());

                    }

                }

                // System.out.println(nuevasParticiones);
            }
        }

        if (param != particionesInicialesIII) {

            // System.out.println(particionesInicialesIII);
            particionesFinales.clear();
            for (ArrayList<ArrayList<Node>> e : particionesInicialesIII) {
                particionesFinales.add(e);
            }
            // System.out.println(particionesInicialesIII);
            // System.out.println(particionesInicialesIII);
            generarParticiones(particionesInicialesIII);

        }

    }

    public AFD contruirAFD() {

        AFD nuevoAfd = new AFD();

        System.out.println(this.afd.transiciones);

        // System.out.println(particionesFinales);

        for (ArrayList<ArrayList<Node>> array : particionesFinales) {

            ArrayList<Node> particionIndice0 = array.get(0);

            if (!listaEstados.contains(particionIndice0)) {
                listaEstados.add(particionIndice0);
            }

            for (Transicion trans : afd.transiciones) {

                ArrayList<Character> listaCaracteres = afd.obtenerCaracteresAFD();

                for (Character car : listaCaracteres) {

                    ArrayList<Integer> lista1 = new ArrayList<Integer>();
                    // System.out.println("\nCOMPARACION: " + particion + " " + t.ein);
                    for (Node nod : particionIndice0) {
                        lista1.add(nod.numnode);
                    }

                    ArrayList<Integer> lista2 = new ArrayList<Integer>();
                    for (Node nod : trans.ein) {
                        lista2.add(nod.numnode);
                    }

                    // System.out.println("EIN " + trans.ein);
                    // System.out.println("Part " + particionIndice0);

                    if ((trans.ein == particionIndice0 || lista1.equals(lista2)) && trans.valor == car) {

                        nuevoAfd.transiciones.add(trans);

                    }

                }

            }

        }

        return nuevoAfd;
    }

}
