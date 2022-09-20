import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Tree {
    ArrayList<Node> listaNodos = new ArrayList<Node>();
    // ArrayList<Node> listaNodos = new ArrayList<Node>();
    HashMap<Integer, ArrayList<Node>> tabla = new HashMap<Integer, ArrayList<Node>>();
    public String expresion;
    public int contador = 1;

    public Tree(String expresion) {
        this.expresion = expresion;
    }

    public ArrayList<Node> followPos(Node n) {

        if (n.data == '.') {
            ArrayList<Node> copiaFinal = new ArrayList<Node>();
            ArrayList<Node> first = n.left.lastPos;
            ArrayList<Node> last = n.right.firstPos;
            for (Node nodo : first) {
                ArrayList<Node> copia = nodo.followPos;
                for (Node nodo2 : last) {
                    // nodo.followPos.add(nodo2);
                    copia.add(nodo2);
                    // System.out.println("copia" + copia);
                    nodo.followPos = copia;
                    // System.out.println("nodo" + nodo.followPos);
                    copiaFinal = copia;
                }

            }
            // System.out.println("copiaFinal" + copiaFinal);
            for (Node nod : copiaFinal) {
                // System.out.println(nod.followPos);
            }
            return copiaFinal;
        }

        if (n.data == '*') {
            ArrayList<Node> tempFinal = new ArrayList<Node>();
            ArrayList<Node> first = n.right.firstPos;
            ArrayList<Node> last = n.right.lastPos;
            for (Node nodo : first) {
                ArrayList<Node> temp = new ArrayList<Node>();
                for (Node nodo2 : last) {
                    temp.add(nodo2);
                    // System.out.println("temp" + temp);
                    nodo.followPos = temp;
                    // System.out.println("nodo" + nodo.followPos);
                    tempFinal = temp;
                }
                // System.out.println(temp);
            }
            // System.out.println("tempFinal" + tempFinal);
            // return tempFinal;
        }
        return null;

    }

    public Boolean nulleable(Node n) {

        if (Character.isLetter(n.data) || n.data == '#') {
            return false;
        }

        if (n.data == '|') {
            return nulleable(n.left) || nulleable(n.right);
        }

        if (n.data == '.') {
            return nulleable(n.left) && nulleable(n.right);
        }

        if (n.data == '*') {
            return true;
        }

        return true;

    }

    public ArrayList<Node> firstPos(Node n) {

        if (Character.isLetter(n.data) || n.data == '#') {
            // System.out.println("FP Se entro a char");
            ArrayList<Node> temp = new ArrayList<Node>();
            temp.add(n);
            n.firstPos = temp;
            return temp;
        }

        if (n.data == '|') {
            // System.out.println("FP Se entro a |");

            ArrayList<Node> temp = new ArrayList<Node>();
            temp.add(n.left);
            temp.add(n.right);
            n.firstPos = temp;
            return temp;
        }

        if (n.data == '.') {
            // System.out.println("FP Se entro a .");

            ArrayList<Node> temp = new ArrayList<Node>();
            if (nulleable(n.left)) {
                for (Node nod : firstPos(n.left)) {
                    temp.add(nod);
                }
                for (Node nod : firstPos(n.right)) {
                    temp.add(nod);
                }
                n.firstPos = temp;
                return temp;
            } else {
                for (Node nod : firstPos(n.left)) {
                    temp.add(nod);
                }
                n.firstPos = temp;
                return temp;
            }
        }

        if (n.data == '*') {
            // System.out.println("FP Se entro a *");

            ArrayList<Node> temp = new ArrayList<Node>();
            for (Node nod : firstPos(n.right)) {
                temp.add(nod);
            }
            n.firstPos = temp;
            return temp;
        }

        return null;

    }

    public ArrayList<Node> lastPos(Node n) {

        if (Character.isLetter(n.data) || n.data == '#') {
            // System.out.println("LP Se entro a char");
            ArrayList<Node> temp = new ArrayList<Node>();
            temp.add(n);
            n.lastPos = temp;
            return temp;
        }

        if (n.data == '|') {
            // System.out.println("LP Se entro a |");
            ArrayList<Node> temp = new ArrayList<Node>();
            temp.add(n.left);
            temp.add(n.right);
            n.lastPos = temp;
            return temp;
        }

        if (n.data == '.') {
            // System.out.println("LP Se entro a .");
            ArrayList<Node> temp = new ArrayList<Node>();
            if (nulleable(n.right)) {
                for (Node nod : lastPos(n.left)) {
                    temp.add(nod);
                }
                for (Node nod : lastPos(n.right)) {
                    temp.add(nod);
                }
                n.lastPos = temp;
                return temp;
            } else {
                for (Node nod : lastPos(n.right)) {
                    temp.add(nod);
                }
                n.lastPos = temp;
                return temp;
            }
        }

        if (n.data == '*') {
            // System.out.println("LP Se entro a *");
            ArrayList<Node> temp = new ArrayList<Node>();
            for (Node nod : lastPos(n.right)) {
                temp.add(nod);
            }
            n.lastPos = temp;
            return temp;
        }

        return null;

    }

    public void CreateTree() {
        String[] parts = expresion.split("");
        for (int i = 0; i < parts.length; i++) {

            char dato = parts[i].charAt(0);

            if (dato == '.') {
                Node node1 = new Node('.');
                listaNodos.add(node1);

            } else if (dato == '|') {
                Node node1 = new Node('|');
                listaNodos.add(node1);
            } else if (dato == '*') {
                Node node1 = new Node('*');
                node1.nulleable = true;
                listaNodos.add(node1);
            } else if (true == Character.isLetter(dato)) {
                Node node1 = new Node(dato);
                node1.numnode = contador;
                contador++;
                listaNodos.add(node1);
            } else {
                Node node1 = new Node('#');
                node1.numnode = contador;
                contador++;
                listaNodos.add(node1);
            }
        }
        Collections.reverse(listaNodos);
        // System.out.println(listaNodos.size());
        // System.out.println(listaNodos);
        for (int i = 0; i < parts.length; i++) {
            Node nodotemp = listaNodos.get(i);
            if (nodotemp.data == '.') {
                Node nodotemp2 = listaNodos.get(i + 1);
                Node nodotemp3 = listaNodos.get(i + 2);

                if (nodotemp2.data == '*') {
                    Node nodotemp4 = listaNodos.get(i + 3);
                    nodotemp.right = nodotemp2;
                    nodotemp.left = nodotemp4;

                } else {
                    nodotemp.right = nodotemp2;
                    nodotemp.left = nodotemp3;
                }

            } else if (nodotemp.data == '|') {
                Node nodotemp2 = listaNodos.get(i + 1);
                if (nodotemp2.data == '*') {
                    Node nodotemp3 = listaNodos.get(i + 3);
                    nodotemp.right = nodotemp2;
                    nodotemp.left = nodotemp3;
                } else {
                    Node nodotemp3 = listaNodos.get(i + 2);
                    nodotemp.right = nodotemp2;
                    nodotemp.left = nodotemp3;
                }
            } else if (nodotemp.data == '*') {
                Node nodotemp2 = listaNodos.get(i + 1);
                nodotemp.right = nodotemp2;

            } else if (true == Character.isLetter(nodotemp.data)) {
                ;
            } else {
                ;
            }

        }

    }

    public void printArbol() {
        // System.out.println(listaNodos);
        for (int i = 0; i < listaNodos.size(); i++) {
            Node nodotemp = listaNodos.get(i);
            System.out.println("Nodo " + nodotemp.data);

            // try {
            // System.out.println("Derecha del nodo " + nodotemp.right.data);

            // } catch (Exception e) {
            // System.out.println("Derecha del nodo null ");

            // }
            // try {
            // System.out.println("Izquierda del nodo " + nodotemp.left.data);

            // } catch (Exception e) {
            // System.out.println("Izquierda del nodo null");
            // }

            // System.out.println(nodotemp.firstPos);
            // try {
            // String firstPosTemp = "";
            // for (Node n : nodotemp.firstPos) {
            // if (n != null) {

            // firstPosTemp += n.data;
            // }
            // }
            System.out.println(nodotemp.data);
            System.out.println("FirstPos: " + nodotemp.firstPos);
            System.out.println("LastPos: " + nodotemp.lastPos);
            System.out.println("FollowPos: " + nodotemp.followPos);

            // } catch (Exception e) {
            // }

        }
    }

    public void printcaracteres() {
        for (int i = 0; i < listaNodos.size(); i++) {
            Node nodotemp = listaNodos.get(i);
            System.out.println(nodotemp.data);
        }
    }

    public static void main(String[] args) {

        // Tree arbol = new Tree("ab*.a.b*.#.");
        Tree arbol = new Tree("ab|*a.b.b.#.");
        arbol.CreateTree();
        arbol.loadRules();
        arbol.llenarDiccionarioFollows();
        System.out.println(arbol.tabla);
        arbol.construccionAFD();
        // arbol.printArbol();
        // arbol.printcaracteres();

    }

    public ArrayList<Character> obtenerListaCaracteres() {

        ArrayList<Character> result = new ArrayList<Character>();

        for (Node t : listaNodos) {

            if (!result.contains(t.data) && t.numnode != 0 && t.data != '#') {
                result.add(t.data);
            }

        }

        return result;

    }

    public AFD construccionAFD() {

        // {1=[1, 2, 3], 2=[1, 2, 3], 3=[4], 4=[5], 5=[6]}

        // Obtenemos siempre el primer estado 1:

        ArrayList<Node> FPestadoInicial = tabla.get(1);

        // Obtenemos todos los characteres posibles en el AFD:
        ArrayList<Character> listaCaracteres = obtenerListaCaracteres();

        // Ahora creamos una ArrayList<Nodes> para cada caracter y la almacenamos en un
        // Hashmap:
        HashMap<Character, ArrayList<ArrayList<Node>>> mapaMovimientos = new HashMap<Character, ArrayList<ArrayList<Node>>>();
        for (char c : listaCaracteres) {
            mapaMovimientos.put(c, new ArrayList<ArrayList<Node>>());
        }

        // Recorremos su followpos para almacenar movimientos y nuevos estados:
        for (Node n : FPestadoInicial) {

            // Llenamos el diccionario con los caracteres y sus movimientos con los
            // followPos de cada uno:
            if (mapaMovimientos.containsKey(n.data)) {
                mapaMovimientos.get(n.data).add(n.followPos);
            } else {
                ArrayList<ArrayList<Node>> temp = new ArrayList<ArrayList<Node>>();
                temp.add(n.followPos);
                mapaMovimientos.put(n.data, temp);
            }

        }

        // System.out.println(mapaMovimientos);
        // {a=[[1, 2, 3], [4]], b=[[1, 2, 3]]}

        // Revisamos si se generan nuevos estados:
        for (Map.Entry<Character, ArrayList<ArrayList<Node>>> mov : mapaMovimientos.entrySet()) {
            if (mov.getValue().size() > 1) {
                ArrayList<Node> nuevo = new ArrayList<Node>();
                for (ArrayList<Node> Listnode : mov.getValue()) {
                    // System.out.println(Listnode);
                    nuevo.addAll(Listnode);
                }
                // System.out.println(nuevo);
                // [1, 2, 3, 4]
                for (int i = 1; i < mov.getValue().size(); i++) {
                    mov.getValue().remove(i);
                }
                // System.out.println(mov.getValue());
                // [[1, 2, 3]]
                mov.getValue().add(nuevo);
            }
        }

        System.out.println("Para: " + FPestadoInicial + " ( 1 ) sus estados son: " + mapaMovimientos);
        // {a=[[1, 2, 3], [1, 2, 3, 4]], b=[[1, 2, 3]]}

        return null;

    }

    public void llenarDiccionarioFollows() {

        for (int i = 0; i < listaNodos.size(); i++) {
            Node nodotemp = listaNodos.get(i);

            // System.out.println(nodotemp.data);
            // System.out.println("asdasd: " + nodotemp.firstPos);
            // System.out.println("LastasdasdPos: " + nodotemp.lastPos);
            // System.out.println("FollasdasdowPos: " + nodotemp.followPos);

            // b
            // FirstPos: [5]
            // LastPos: [5]
            // FollowPos: [6]
            // Nodo .

            // HashMap<Integer, ArrayList<Integer>> tabla = new HashMap<Integer,
            // ArrayList<Integer>>();

            if (nodotemp.followPos.size() != 0) {
                ArrayList<Node> temporal = new ArrayList<>();
                for (Node n : nodotemp.followPos) {
                    temporal.add(n);
                }
                tabla.put(nodotemp.numnode, temporal);
            }
        }

    }

    public void loadRules() {
        Collections.reverse(listaNodos);
        for (Node n : listaNodos) {
            nulleable(n);
            firstPos(n);
            lastPos(n);
            followPos(n);
        }
    }

}

class Node {
    public char data;
    public Node left;
    public Node right;

    public Boolean nulleable;
    public int numnode;
    public ArrayList<Node> firstPos = new ArrayList<Node>();
    public ArrayList<Node> lastPos = new ArrayList<Node>();
    public ArrayList<Node> followPos = new ArrayList<Node>();

    Node(char data) {
        this.data = data;
    }

    public String toString() {
        return Integer.toString(numnode);
    }

}
