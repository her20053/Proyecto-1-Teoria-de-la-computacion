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

    public String toString() {

        String result = "";

        for (Transicion t : transiciones) {

            result += "[" + t.estadoInicial + ", " + t.valor + ", " + t.estadoFinal + "]" + "\n";

        }

        return result;

    }

    public ArrayList<Character> obtenerListaCaracteres() {

        ArrayList<Character> result = new ArrayList<Character>();

        for (Transicion t : transiciones) {

            if (!result.contains(t.valor) && t.valor != 'ε') {
                result.add(t.valor);
            }

        }

        return result;

    }

}
