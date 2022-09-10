import java.util.ArrayList;

public class AFD {

  public ArrayList<Transicion> transiciones = new ArrayList<Transicion>();

  public AFD() {
  }

  public void mostrarAFD() {

    for (Transicion t : transiciones) {

      System.out.print("[" + t.estadoInicial + ", ");
      System.out.print(t.valor + ", ");
      System.out.print(t.estadoFinal + "]");
      System.out.println();

    }

  }

}
