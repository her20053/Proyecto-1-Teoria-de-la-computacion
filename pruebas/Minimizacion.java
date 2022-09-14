import java.util.ArrayList;

public class Minimizacion {

  public AFD afd;

  public Minimizacion(AFD afd) {
    this.afd = afd;
  }

  public AFD minimizarAFD() {

    ArrayList<ArrayList<Integer>> particiones = new ArrayList<ArrayList<Integer>>();

    ArrayList<ArrayList<Integer>> particionesAceptadas = new ArrayList<ArrayList<Integer>>();

    // [[0], a, [1]]
    // [[1], a, [5]]
    // [[1], b, [3]]
    // [[5], b, [7]]
    // [[7], b, [7]]
    // [[3], a, [5]]
    // [[3], b, [3]]

    // {[1]=[1, 2, 4], [3]=[2, 3, 4], [5]=[5, 6, 8], [7]=[6, 7, 8], [0]=[0]}

    for (ArrayList<Integer> arr : particiones) {

      if (arr.size() == 1) {
        particionesAceptadas.add(arr);
      } else {
        for (Transicion t : afd.transiciones) {

          for (Character c : afd.listaCaracteres) {

          }

        }
      }

    }

    return null;

  }

}
