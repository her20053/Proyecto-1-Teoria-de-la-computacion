import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Tree {
    ArrayList<Node> listaNodos = new ArrayList<Node>();
    public String expresion;
    public int contador = 1;

    public Tree(String expresion) {
        this.expresion = expresion;
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
            System.out.println("FP Se entro a char");
            ArrayList<Node> temp = new ArrayList<Node>();
            temp.add(n);
            n.firstPos = temp;
            return temp;
        }

        if (n.data == '|') {
            System.out.println("FP Se entro a |");

            ArrayList<Node> temp = new ArrayList<Node>();
            temp.add(n.left);
            temp.add(n.right);
            n.firstPos = temp;
            return temp;
        }

        if (n.data == '.') {
            System.out.println("FP Se entro a .");

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
            System.out.println("FP Se entro a *");

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
            System.out.println("LP Se entro a char");
            ArrayList<Node> temp = new ArrayList<Node>();
            temp.add(n);
            n.lastPos = temp;
            return temp;
        }

        if (n.data == '|') {
            System.out.println("LP Se entro a |");
            ArrayList<Node> temp = new ArrayList<Node>();
            temp.add(n.left);
            temp.add(n.right);
            n.lastPos = temp;
            return temp;
        }

        if (n.data == '.') {
            System.out.println("LP Se entro a .");
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
            System.out.println("LP Se entro a *");
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
                contador ++;
                listaNodos.add(node1);
            } else {
                Node node1 = new Node('#');
                node1.numnode = contador;
                contador ++;
                listaNodos.add(node1);
            }
        }
        Collections.reverse(listaNodos);
        System.out.println(listaNodos.size());
        System.out.println(listaNodos);
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
        System.out.println(listaNodos);
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

            System.out.println("FirstPos: " + nodotemp.firstPos);
            System.out.println("FirstPos: " + nodotemp.lastPos);

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

        Tree arbol = new Tree("ab*.a.b*.#.");
        //Tree arbol = new Tree("ab|*a.b.b.#.");
        arbol.CreateTree();
        arbol.loadRules();
        arbol.printArbol();
        // arbol.printcaracteres();

    }

    public void loadRules() {
        Collections.reverse(listaNodos);
        for (Node n : listaNodos) {
            nulleable(n);
            firstPos(n);
            lastPos(n);
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

    Node(char data) {
        this.data = data;
    }

    public String toString() {
        return Integer.toString(numnode);
    }

}
