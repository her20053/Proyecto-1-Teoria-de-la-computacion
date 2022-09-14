import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AFD {

  public ArrayList<Transicion> transiciones = new ArrayList<>();
  public ArrayList<ArrayList<Integer>> estadosAceptacion = new ArrayList<>();
  public ArrayList<ArrayList<Integer>> estadosNOAceptacion = new ArrayList<>();
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

}
