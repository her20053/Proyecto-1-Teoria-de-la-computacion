import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Minimizacion {

  public AFD afd;

  public ArrayList<ArrayList<ArrayList<Integer>>> particionesInicialesII = new ArrayList<ArrayList<ArrayList<Integer>>>();
  public ArrayList<ArrayList<ArrayList<Integer>>> particionesInicialesIII = new ArrayList<ArrayList<ArrayList<Integer>>>();
  public ArrayList<ArrayList<ArrayList<Integer>>> particionesFinales = new ArrayList<ArrayList<ArrayList<Integer>>>();

  public ArrayList<ArrayList<Integer>> listaEstados = new ArrayList<ArrayList<Integer>>();

  public boolean huboGen = false;

  public Minimizacion(AFD afd) {
    this.afd = afd;
  }

  public AFD minimizarAFD() {

    ArrayList<ArrayList<ArrayList<Integer>>> particionesIniciales = new ArrayList<ArrayList<ArrayList<Integer>>>();

    HashMap<ArrayList<Integer>, ArrayList<Integer>> movimientosParticiones = new HashMap<ArrayList<Integer>, ArrayList<Integer>>();

    // [[0], a, [1]]
    // [[1], a, [5]]
    // [[1], b, [3]]
    // [[5], b, [7]]
    // [[7], b, [7]]
    // [[3], a, [5]]
    // [[3], b, [3]]

    // {[1]=[1, 2, 4], [3]=[2, 3, 4], [5]=[5, 6, 8], [7]=[6, 7, 8], [0]=[0]}

    // System.out.println("D" + afd.estadosAceptacion);
    // System.out.println("D" + afd.estadosNOAceptacion);

    // D[[5, 10]]
    // D[[5], [4, 8], [5, 9], [0]]

    // System.out.println(afd);

    // [[0], a, [4, 8]]
    // [[0], b, [5]]
    // [[4, 8], a, [4, 8]]
    // [[4, 8], b, [5, 9]]
    // [[5, 9], a, [4, 8]]
    // [[5, 9], b, [5, 10]]
    // [[5, 10], a, [4, 8]]
    // [[5, 10], b, [5]]
    // [[5], a, [4, 8]]
    // [[5], b, [5]]

    ArrayList<ArrayList<Integer>> temp1 = new ArrayList<ArrayList<Integer>>();

    // REVISAMOS LENGTH
    if (afd.estadosNOAceptacion.size() > 0) {
      for (ArrayList<Integer> arr : afd.estadosNOAceptacion) {
        temp1.add(arr);
      }

      particionesIniciales.add(temp1);
    }

    // System.out.println(temp1);

    ArrayList<ArrayList<Integer>> temp2 = new ArrayList<ArrayList<Integer>>();

    for (ArrayList<Integer> arr : afd.estadosAceptacion) {
      temp2.add(arr);
    }

    // System.out.println(temp2);

    particionesIniciales.add(temp2);

    // System.out.println(particionesIniciales);

    // [[[5], [4, 8], [5, 9], [0]], [[5, 10]]]

    // Ahora creamos otra ArrayList<ArrayList<ArrayList<Integer>>> y se iguala a
    // nuestras particiones iniciales:

    for (ArrayList<ArrayList<Integer>> arr : particionesIniciales) {
      particionesInicialesII.add(arr);
    }

    // System.out.println(particionesInicialesII);
    // [[[5], [4, 8], [5, 9], [0]], [[5, 10]]]

    // [[[5], [4, 8], [5, 9], [0]]] 0
    // [ [[5, 10]]] 1

    // Recorremos cada grupo en particionesIniciales:

    for (ArrayList<ArrayList<Integer>> grupo : particionesIniciales) {

      // Revisar si el grupo solo tiene una particion:
      if (grupo.size() != 1) {

        // System.out.println(grupo);
        // [[5], [4, 8], [5, 9], [0]]

        for (ArrayList<Integer> particion : grupo) {

          // Obtenemos los carateres:
          ArrayList<Character> listaCaracteres = afd.obtenerCaracteresAFD();

          // Tenemos que revisar hacia donde se dirige con cada simbolo dicha particion:

          // Por ejemplo tenemos que ver [5] a (hacia donde se dirige)

          for (Transicion t : afd.transiciones) {

            // Comparando la particion con el estado
            if (t.estadosInicial == particion) {

              // System.out.println("ADSDASD");
              // ArrayList<Integer> haciaDonde = new ArrayList<Integer>();

              // Comparando el caracter
              for (Character c : listaCaracteres) {

                ArrayList<Integer> estadoFinalEncontrado = t.estadosFinal;

                if (t.estadosInicial == particion && c == t.valor) {

                  // System.out.println(particion + " " + c + " " + estadoFinalEncontrado);

                  for (int i = 0; i < particionesIniciales.size(); i++) {

                    if (particionesIniciales.get(i).contains(estadoFinalEncontrado)) {
                      // System.out.println("\nParticion comparada: " + particionesIniciales.get(i));
                      // System.out.println("Contiene? " + estadoFinalEncontrado);
                      // // System.out.println(particion + " " + c + " " + estadoFinalEncontrado);
                      // Transicion comodin = new Transicion(particion, c, estadoFinalEncontrado);
                      // System.out.println(comodin);
                      // System.out.println(afd.transiciones.contains(comodin));
                      // haciaDonde.add(i);

                      // System.out.println(particion + " " + c + " " + estadoFinalEncontrado + " va
                      // hacia: " + i);
                      // haciaDonde.add(i);

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

        // System.out.println(haciaDonde);

        // System.out.println(movimientosParticiones);

        // {[5]=[0, 0], [4, 8]=[0, 0], [5, 9]=[0, 1], [0]=[0, 0]}

        // Agrupamos todos los que sean distintos o iguales en el map

        HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> nuevasParticiones = new HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>>();

        for (Map.Entry<ArrayList<Integer>, ArrayList<Integer>> entry : movimientosParticiones.entrySet()) {

          if (nuevasParticiones.containsKey(entry.getValue())) {

            nuevasParticiones.get(entry.getValue()).add(entry.getKey());

          } else {
            ArrayList<ArrayList<Integer>> temporal = new ArrayList<>();
            temporal.add(entry.getKey());
            nuevasParticiones.put(entry.getValue(), temporal);
          }
        }

        // System.out.println(particionesIniciales);

        // System.out.println(movimientosParticiones);
        // {[5]=[0, 0], [4, 8]=[0, 0], [5, 9]=[0, 1], [0]=[0, 0]}

        // System.out.println(nuevasParticiones);
        // {[0, 0]=[[5], [4, 8], [0]], [0, 1]=[[5, 9]]}

        Map.Entry<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> entry = nuevasParticiones.entrySet().iterator()
            .next();

        // System.out.println(entry.getValue());
        // System.out.println(grupo);

        if (entry.getValue() != grupo) {

          particionesInicialesII.remove(grupo);

          ArrayList<ArrayList<ArrayList<Integer>>> temporal = new ArrayList<>();

          for (Map.Entry<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> entrada : nuevasParticiones.entrySet()) {

            // temporal.add(entrada.getValue());
            particionesInicialesII.add(entrada.getValue());

          }

          // int indice = particionesInicialesII.indexOf(grupo);

          // System.out.println(temporal);

          // particionesInicialesII.set(indice, temporal.get(1));

          // for(ArrayList<ArrayList<Integer>> arr: temporal){
          // particionesInicialesII
          // }

        }

      }

    }

    // System.out.println(particionesIniciales);
    // System.out.println(particionesInicialesII);

    if (particionesIniciales != particionesInicialesII) {

      System.out.println(particionesInicialesII);
      generarParticiones(particionesInicialesII);

    }

    // System.out.println(particionesFinales);

    // [[[5, 10]], [[5, 9]], [[5], [0]], [[4, 8]]]

    return contruirAFD();

  }

  public AFD contruirAFD() {

    AFD nuevoAfd = new AFD();

    for (ArrayList<ArrayList<Integer>> array : particionesFinales) {

      ArrayList<Integer> particionIndice0 = array.get(0);

      if (!listaEstados.contains(particionIndice0)) {
        listaEstados.add(particionIndice0);
      }

      for (Transicion trans : afd.transiciones) {

        ArrayList<Character> listaCaracteres = afd.obtenerCaracteresAFD();

        for (Character car : listaCaracteres) {
          if (trans.estadosInicial == particionIndice0 && trans.valor == car) {

            nuevoAfd.transiciones.add(trans);

          }

        }

      }

    }

    return nuevoAfd;
  }

  public void generarParticiones(ArrayList<ArrayList<ArrayList<Integer>>> param) {

    particionesInicialesIII.clear();

    for (ArrayList<ArrayList<Integer>> entradaTemporal : param) {
      particionesInicialesIII.add(entradaTemporal);
    }

    HashMap<ArrayList<Integer>, ArrayList<Integer>> movimientosParticiones = new HashMap<ArrayList<Integer>, ArrayList<Integer>>();

    for (ArrayList<ArrayList<Integer>> grupo : param) {

      if (grupo.size() != 1) {

        // System.out.println(grupo);
        // [[5], [4, 8], [5, 9], [0]]

        for (ArrayList<Integer> particion : grupo) {

          // Obtenemos los carateres:
          ArrayList<Character> listaCaracteres = afd.obtenerCaracteresAFD();

          // Tenemos que revisar hacia donde se dirige con cada simbolo dicha particion:

          // Por ejemplo tenemos que ver [5] a (hacia donde se dirige)

          for (Transicion t : afd.transiciones) {

            // Comparando la particion con el estado
            if (t.estadosInicial == particion) {

              // System.out.println("ADSDASD");
              // ArrayList<Integer> haciaDonde = new ArrayList<Integer>();

              // Comparando el caracter
              for (Character c : listaCaracteres) {

                ArrayList<Integer> estadoFinalEncontrado = t.estadosFinal;

                if (t.estadosInicial == particion && c == t.valor) {

                  // System.out.println(particion + " " + c + " " + estadoFinalEncontrado);

                  for (int i = 0; i < param.size(); i++) {

                    if (param.get(i).contains(estadoFinalEncontrado)) {
                      // System.out.println("\nParticion comparada: " + particionesIniciales.get(i));
                      // System.out.println("Contiene? " + estadoFinalEncontrado);
                      // // System.out.println(particion + " " + c + " " + estadoFinalEncontrado);
                      // Transicion comodin = new Transicion(particion, c, estadoFinalEncontrado);
                      // System.out.println(comodin);
                      // System.out.println(afd.transiciones.contains(comodin));
                      // haciaDonde.add(i);

                      // System.out.println(particion + " " + c + " " + estadoFinalEncontrado + " va
                      // hacia: " + i);
                      // haciaDonde.add(i);

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

        // System.out.println(haciaDonde);

        // System.out.println(movimientosParticiones);

        // {[5]=[0, 0], [4, 8]=[0, 0], [5, 9]=[0, 1], [0]=[0, 0]}

        // Agrupamos todos los que sean distintos o iguales en el map

        HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> nuevasParticiones = new HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>>();

        for (Map.Entry<ArrayList<Integer>, ArrayList<Integer>> entry : movimientosParticiones.entrySet()) {

          if (nuevasParticiones.containsKey(entry.getValue())) {

            nuevasParticiones.get(entry.getValue()).add(entry.getKey());

          } else {
            ArrayList<ArrayList<Integer>> temporal = new ArrayList<>();
            temporal.add(entry.getKey());
            nuevasParticiones.put(entry.getValue(), temporal);
          }
        }

        // System.out.println(particionesIniciales);

        // System.out.println(movimientosParticiones);
        // {[5]=[0, 0], [4, 8]=[0, 0], [5, 9]=[0, 1], [0]=[0, 0]}

        // System.out.println(nuevasParticiones);
        // {[0, 0]=[[5], [4, 8], [0]], [0, 1]=[[5, 9]]}

        Map.Entry<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> entry = nuevasParticiones.entrySet().iterator()
            .next();

        // System.out.println(entry.getValue());
        // System.out.println(grupo);

        if (entry.getValue() != grupo) {

          particionesInicialesIII.remove(grupo);

          ArrayList<ArrayList<ArrayList<Integer>>> temporal = new ArrayList<>();

          for (Map.Entry<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> entrada : nuevasParticiones.entrySet()) {

            // temporal.add(entrada.getValue());
            particionesInicialesIII.add(entrada.getValue());

          }

          // int indice = particionesInicialesII.indexOf(grupo);

          // System.out.println(temporal);

          // particionesInicialesII.set(indice, temporal.get(1));

          // for(ArrayList<ArrayList<Integer>> arr: temporal){
          // particionesInicialesII
          // }

        }
      }
    }

    if (param != particionesInicialesIII) {

      // System.out.println(particionesInicialesIII);
      particionesFinales.clear();
      for (ArrayList<ArrayList<Integer>> e : particionesInicialesIII) {
        particionesFinales.add(e);
      }
      System.out.println(particionesInicialesIII);
      generarParticiones(particionesInicialesIII);

    }

  }

}
