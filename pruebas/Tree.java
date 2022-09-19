import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
public class Tree {
    ArrayList<Node> listaNodos = new ArrayList<Node>();
    public String expresion;
    
    public Tree(String expresion) {
        this.expresion = expresion;
    }
    public void CreateTree(){
        String [] parts = expresion.split("");
        for(int i = 0; i < parts.length; i++){

            char dato = parts[i].charAt(0);

            if(dato == '.'){
                Node node1 = new Node('.');
                listaNodos.add(node1);
                
            }
            else if(dato == '|'){
                Node node1 = new Node('|');
                listaNodos.add(node1);
            }
            else if(dato == '*'){
                Node node1 = new Node('*');
                listaNodos.add(node1);
            }
            else if(true == Character.isLetter(dato)){
                Node node1 = new Node(dato);
                listaNodos.add(node1);
            }
            else{
                Node node1 = new Node('#');
                listaNodos.add(node1);
            }
        }
        Collections.reverse(listaNodos);
        System.out.println(listaNodos.size());
        for( int i =0; i < parts.length; i++){
            Node nodotemp = listaNodos.get(i);
            if(nodotemp.data == '.'){
                Node nodotemp2 = listaNodos.get(i+1);
                Node nodotemp3 = listaNodos.get(i+2);
                
                if(nodotemp2.data == '*'){
                    Node nodotemp4 = listaNodos.get(i+3);
                    nodotemp.right = nodotemp2;
                    nodotemp.left = nodotemp4;

                    

                }
                else{
                    nodotemp.right = nodotemp2;
                    nodotemp.left = nodotemp3;
                }

            }
            else if(nodotemp.data == '|'){
                Node nodotemp2 = listaNodos.get(i+1);
                if(nodotemp2.data == '*'){
                    Node nodotemp3 = listaNodos.get(i+3);
                    nodotemp.right = nodotemp2;
                    nodotemp.left = nodotemp3;
                }
                else{
                    Node nodotemp3 = listaNodos.get(i+2);
                    nodotemp.right = nodotemp2;
                    nodotemp.left = nodotemp3;
                }
            }
            else if(nodotemp.data == '*'){
                Node nodotemp2 = listaNodos.get(i+1);
                nodotemp.right = nodotemp2;

                
            }
            else if(true == Character.isLetter(nodotemp.data)){
                ;
            }
            else{
                ;
            }

            

        }
    
    }
    public void printArbol(){
        for( int i = 0; i < listaNodos.size(); i++){
            Node nodotemp = listaNodos.get(i);
            System.out.println("Nodo "+ nodotemp.data);
            try{
                System.out.println("Derecha del nodo  "+ nodotemp.right.data);

            }catch(Exception e){
                System.out.println("Derecha del nodo  null ");

            }
            try{
                System.out.println("Izquierda del nodo "+ nodotemp.left.data);

            }catch(Exception e){
                System.out.println("Izquierda del nodo null");
            }
            
        }
    }
    public void printcaracteres(){
        for( int i = 0; i < listaNodos.size(); i++){
            Node nodotemp = listaNodos.get(i);
            System.out.println(nodotemp.data);
        }
    }
    public static void main(String[] args) {

        Tree arbol = new Tree(" ab*.a.b*.#.");
        arbol.CreateTree();
        arbol.printArbol();
        arbol.printcaracteres();

        
    }


}

class Node {
    char data;
    Node left;
    Node right;

    Node(char data) {
        this.data = data;
    }

}
