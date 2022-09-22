import java.util.ArrayList;
import java.util.Scanner;

public class SimulacionAFN {
    
    public AFN afn;
    public String cadena;
    public boolean completado = false;

    public Integer estadoActual = 0;


    Scanner sc = new Scanner(System.in);

    public SimulacionAFN(AFN afn, String cadena){

        this.afn = afn;
        this.cadena = cadena;

    }

    public boolean analizarCaracter(char c){

        for(Transicion t: afn.transiciones){
            if(t.estadoInicial == this.estadoActual){
                System.out.println(t.valor);
                if(t.valor == c){
                    System.out.println(this.estadoActual +" "+c + " " + t.estadoFinal );
                    this.estadoActual = t.estadoFinal;
                    return true;
                }
            }
        }

        return false;
    }
    

    public void simular(){

        // Asignamos el estado inicial

        this.estadoActual = afn.transiciones.get(0).estadoInicial;
        
        
        
        // Recorremos la puta cadena:
        
        
        for(int i = 0; i < cadena.length(); i++){
            
            // System.out.println(this.estadoActual);
            completado = analizarCaracter(cadena.charAt(i));

        }

        
        

    }



}
