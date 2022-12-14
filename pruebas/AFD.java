import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AFD {

  public ArrayList<Transicion> transiciones = new ArrayList<>();
  public ArrayList<ArrayList<Integer>> estadosAceptacion = new ArrayList<>();
  public ArrayList<ArrayList<Integer>> estadosNOAceptacion = new ArrayList<>();

  public ArrayList<ArrayList<Node>> estadosAceptacionNode = new ArrayList<>();
  public ArrayList<ArrayList<Node>> estadosNOAceptacionNode = new ArrayList<>();

  public ArrayList<Character> listaCaracteres = new ArrayList<>();

  public AFD() {
  }

  public String toString() {

    String result = "";

    for (Transicion t : transiciones) {
      result += "[" + t.estadosInicial + ", " + t.valor + ", " + t.estadosFinal + "]" + "\n";
    }

    return result;

  }

  public String mostrarAFDDirecto() {
    String result = "";

    for (Transicion t : transiciones) {
      result += "[" + t.ein + ", " + t.valor + ", " + t.efn + "]" + "\n";
    }

    return result;
  }

  public void generarEstadosAceptacion(int estadoFinal, HashMap<ArrayList<Integer>, ArrayList<Integer>> diccionario) {

    for (Map.Entry<ArrayList<Integer>, ArrayList<Integer>> entry : diccionario.entrySet()) {

      // System.out.println(estadoFinal);

      if (entry.getValue().contains(estadoFinal)) {
        estadosAceptacion.add(entry.getKey());

      } else {
        estadosNOAceptacion.add(entry.getKey());
      }

    }

  }

  public void generarEstadosAceptacion2(int estadoFinal, ArrayList<ArrayList<Node>> listado) {

    for (ArrayList<Node> arr : listado) {

      boolean fueAgregadoArr = false;

      for (Node n : arr) {

        if (n.numnode == estadoFinal) {
          estadosAceptacionNode.add(arr);
          fueAgregadoArr = true;
        }

      }

      if (!fueAgregadoArr) {

        estadosNOAceptacionNode.add(arr);

      }

    }

  }

  public ArrayList<Character> obtenerCaracteresAFD() {
    ArrayList<Character> temp = new ArrayList<Character>();
    for (Transicion t : transiciones) {
      if (!temp.contains(t.valor) || t.valor == '#') {
        temp.add(t.valor);
      }
    }
    return temp;
  }

}
