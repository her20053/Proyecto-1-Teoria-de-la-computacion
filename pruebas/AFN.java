import java.util.ArrayList;

public class AFN {

    public ArrayList<Transicion> transiciones = new ArrayList<Transicion>();

    public AFN() {
    }

    public void mostrarAFN() {

        for (Transicion t : transiciones) {

            System.out.print("[" + t.estadoInicial + ", ");
            System.out.print(t.valor + ", ");
            System.out.print(t.estadoFinal + "]");
            System.out.println();

        }

    }

}
