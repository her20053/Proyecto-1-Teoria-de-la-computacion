import java.util.ArrayList;
import java.util.Scanner;

public class SimulacionNode {
    
    public AFD afd;
    public String cadena;
    public boolean completado = false;

    public ArrayList<Node> estadoActual = new ArrayList<Node>();


    Scanner sc = new Scanner(System.in);

    public SimulacionNode(AFD afd, String cadena){

        this.afd = afd;
        this.cadena = cadena;

    }

    public boolean analizarCaracter(char c){

        for(Transicion t: afd.transiciones){
            if(t.ein.equals(this.estadoActual)){
                if(t.valor == c){
                    System.out.println(this.estadoActual +" "+c + " " + t.efn );
                    this.estadoActual = t.efn;
                    return true;
                }
            }
        }

        return false;
    }
    

    public void simular(){

        // Asignamos el estado inicial

        this.estadoActual = afd.transiciones.get(0).ein;
        
        
        
        // Recorremos la puta cadena:
        
        
        for(int i = 0; i < cadena.length(); i++){
            
            // System.out.println(this.estadoActual);
            completado = analizarCaracter(cadena.charAt(i));

        }

        
        

    }

}
