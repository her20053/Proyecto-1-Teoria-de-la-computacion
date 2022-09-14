import java.util.ArrayList;
import java.util.HashMap;

public class Conjunto {

    public int valorEClosure;
    public ArrayList<Integer> listaTransiciones;
    public HashMap<Character, ArrayList<Integer>> movimientos;

    public Conjunto(int valorEClosure, ArrayList<Integer> listaTransiciones,
            HashMap<Character, ArrayList<Integer>> movimientos) {

        this.valorEClosure = valorEClosure;
        this.listaTransiciones = listaTransiciones;
        this.movimientos = movimientos;

    }

}
