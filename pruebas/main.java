import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Main {

    public static void main(String[] args) {

        // String expresionRegular = "(b|b)*abb(a|b)*";
        // String expresionRegular = "a.(a|b)*.b";
        // String expresionRegular = "a.a*|b.b*";
        // String expresionRegular = "a.a*.b*.(a|b)(a|b.a*.b*.(a|b))*";
        // String expresionRegular = "((a|b)*.a.(a|b)*.a.(a|b)*)*b";
        // String expresionRegular = "(a|b)*.a.b.b";
        // String expresionRegular = "(a*|b*)*";
        // String expresionRegular = "a.b*.a.b*";

        // String expresionRegular = "(a|b)*.a.b.b";
        String expresionRegular = "(a|b)*";


        String cadenaUniversad = "abbbbbabb";

        // abbbbbabb

        // String expresionRegular = "(0|ε).((1|ε)|ε).0*";

        // String expresionRegular = "(a|b)*.((a|(b.b))*)";

        // ------------------------------------------------------------------

        // Postfix

        String postfix = Postfix.infixToPostfix(expresionRegular);

        System.out.println("\nExpresion postfix: " + postfix + "\n");

        // ------------------------------------------------------------------

        // Thompson

        Thompson thompson = new Thompson(postfix);

        // Tiempo 1

        AFN afnFinal = thompson.analizarPostfix();

        // Tiempo - Tiempo 1

        // System.out.println("AFN generado por Thompson: \n");

        // afnFinal.mostrarAFN();

        // Escribir el AFN generado por Thompson a su archivo:

        // ------------------------------------------------------------------

        // Subconjuntos

        // Subconjuntos subconjunto = new Subconjuntos(afnFinal);

        // System.out.println("\nSubconjuntos");
        // subconjunto.obtenerEclosures();

        AFN afnReorganizado = Eclosure.reOrganizarAFN(afnFinal);

        // System.out.println(afnReorganizado);

        try {
            FileWriter fw = new FileWriter("GeneracionDeAFN.txt");
            ArrayList<Integer> estados = new ArrayList<Integer>();
            for (Transicion t : afnReorganizado.transiciones) {
                if (!estados.contains(t.estadoInicial)) {
                    estados.add(t.estadoInicial);
                }
                if (!estados.contains(t.estadoFinal)) {
                    estados.add(t.estadoFinal);
                }
            }
            String sfinal = "(";
            for (Integer i : estados) {
                sfinal += " " + i.toString();
            }
            sfinal += " )";

            String cfinal = "(";
            for (Character c : afnReorganizado.obtenerListaCaracteres()) {
                cfinal += " " + c.toString();
            }
            cfinal += " " + "ε )";

            fw.write("ESTADOS:\n");
            fw.write(sfinal);
            fw.write("\nSIMBOLOS\n");
            fw.write(cfinal);
            fw.write("\nINICIO:\n");
            fw.write("[ " + afnReorganizado.transiciones.get(0).estadoInicial + " ]");
            fw.write("\nFINAL\n");
            fw.write("[ " + afnReorganizado.transiciones.get(afnReorganizado.transiciones.size() - 1).estadoFinal
                    + " ]");
            fw.write("\nTRANSICIONES:\n");
            fw.write(afnReorganizado.toString());
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        AFD afd = Eclosure.convertirAFN(afnReorganizado);
        // System.out.println(Eclosure.estadosGenerados);

        // System.out.println(afd.transiciones);

        // System.out.println("AFD generado por subconjuntos: \n");

        // System.out.println(afd);

        // System.out.println(Eclosure.estadosGenerados);
        // System.out.println(Eclosure.diccionarioEstadosGenerados);
        afd.generarEstadosAceptacion(afnReorganizado.transiciones
                .get(afnReorganizado.transiciones.size() - 1).estadoFinal, Eclosure.diccionarioEstadosGenerados);
        // System.out.println(afd.estadosAceptacion);
        // System.out.println(afd.estadosNOAceptacion);

        try {
            FileWriter fw = new FileWriter("ConversionAFNaAFD.txt");
            ArrayList<ArrayList<Integer>> estados = new ArrayList<ArrayList<Integer>>();
            for (Transicion t : afd.transiciones) {
                if (!estados.contains(t.estadosInicial)) {
                    estados.add(t.estadosInicial);
                }
                if (!estados.contains(t.estadosFinal)) {
                    estados.add(t.estadosFinal);
                }
            }
            String sfinal = "(";
            for (ArrayList<Integer> i : estados) {
                sfinal += " " + i.toString();
            }
            sfinal += " )";

            String cfinal = "(";
            for (Character c : afd.obtenerCaracteresAFD()) {
                cfinal += " " + c.toString();
            }
            cfinal += " )";

            String aceptacion = "(";

            for (ArrayList<Integer> arr : afd.estadosAceptacion) {
                aceptacion += " " + arr.toString();
            }
            aceptacion += " )";

            fw.write("ESTADOS:\n");
            fw.write(sfinal);
            fw.write("\nSIMBOLOS\n");
            fw.write(cfinal);
            fw.write("\nINICIO:\n");
            fw.write("[ " + afd.transiciones.get(0).estadosInicial + " ]");
            fw.write("\nFINAL\n");
            fw.write(aceptacion);
            fw.write("\nTRANSICIONES:\n");
            fw.write(afd.toString());
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        // ------------------------------------------------------------------

        // REGEX a AFD directo

        Tree arbol = new Tree(postfix + "#.");

        arbol.CreateTree();
        arbol.loadRules();
        arbol.llenarDiccionarioFollows();
        AFD arbolAFD = arbol.construccionAFD();

        arbolAFD.generarEstadosAceptacion2(arbol.listaNodos.get(arbol.listaNodos.size() - 2).numnode,
                arbol.listaEstadosGenerados);

        // System.out.println(arbolAFD.transiciones);

        try {
            FileWriter fw = new FileWriter("GeneracionAFDDirecto.txt");
            ArrayList<ArrayList<Integer>> estados = new ArrayList<ArrayList<Integer>>();
            
            for (Transicion t : afd.transiciones) {
                if (!estados.contains(t.estadosInicial)) {
                    estados.add(t.estadosInicial);
                }
                if (!estados.contains(t.estadosFinal)) {
                    estados.add(t.estadosFinal);
                }
            }
            String sfinal = "(";
            for (ArrayList<Integer> i : estados) {
                sfinal += " " + i.toString();
            }
            sfinal += " )";

            String cfinal = "(";
            for (Character c : afd.obtenerCaracteresAFD()) {
                cfinal += " " + c.toString();
            }
            cfinal += " )";

            String aceptacion = "(";

            for (ArrayList<Integer> arr : afd.estadosAceptacion) {
                aceptacion += " " + arr.toString();
            }
            aceptacion += " )";

            fw.write("ESTADOS:\n");
            fw.write(arbol.listaEstadosGenerados.toString());
            fw.write("\nSIMBOLOS\n");
            fw.write(arbolAFD.obtenerCaracteresAFD().toString());
            fw.write("\nINICIO:\n");
            fw.write("[ " + arbol.listaNodos.get(arbol.listaNodos.size() - 1).firstPos + " ]");
            fw.write("\nFINAL\n");
            // System.out.println("ACEP" + afdMinimizado2.estadosAceptacionNode);
            fw.write(arbolAFD.estadosAceptacionNode.toString());
            fw.write("\nTRANSICIONES:\n");
            fw.write(arbolAFD.mostrarAFDDirecto());
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        MinimizacionNode minimizacion2 = new MinimizacionNode(arbolAFD);

        AFD afdMinimizado2 = minimizacion2.minimizarAFD();

        // System.out.println(afdMinimizado2.mostrarAFDDirecto());

        try {
            FileWriter fw = new FileWriter("MinimizacionDeAFD Directo.txt");
            ArrayList<ArrayList<Integer>> estados = new ArrayList<ArrayList<Integer>>();
            
            for (Transicion t : afd.transiciones) {
                if (!estados.contains(t.estadosInicial)) {
                    estados.add(t.estadosInicial);
                }
                if (!estados.contains(t.estadosFinal)) {
                    estados.add(t.estadosFinal);
                }
            }
            String sfinal = "(";
            for (ArrayList<Integer> i : estados) {
                sfinal += " " + i.toString();
            }
            sfinal += " )";

            String cfinal = "(";
            for (Character c : afd.obtenerCaracteresAFD()) {
                cfinal += " " + c.toString();
            }
            cfinal += " )";

            String aceptacion = "(";

            for (ArrayList<Integer> arr : afd.estadosAceptacion) {
                aceptacion += " " + arr.toString();
            }
            aceptacion += " )";

            fw.write("ESTADOS:\n");
            fw.write(arbol.listaEstadosGenerados.toString());
            fw.write("\nSIMBOLOS\n");
            fw.write(afdMinimizado2.obtenerCaracteresAFD().toString());
            fw.write("\nINICIO:\n");
            fw.write("[ " + arbol.listaNodos.get(arbol.listaNodos.size() - 1).firstPos + " ]");
            fw.write("\nFINAL\n");
            // System.out.println("ACEP" + afdMinimizado2.estadosAceptacionNode);
            fw.write(arbolAFD.estadosAceptacionNode.toString());
            fw.write("\nTRANSICIONES:\n");
            fw.write(afdMinimizado2.mostrarAFDDirecto());
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        // ------------------------------------------------------------------

        // Minimizacion por particiones

        afd.listaCaracteres = afnReorganizado.obtenerListaCaracteres();

        Minimizacion minimizacion = new Minimizacion(afd);

        AFD afdMinimizado = minimizacion.minimizarAFD();

        // System.out.println(afdMinimizado);

        // System.out.println(minimizacion.listaEstados);

        try {
            FileWriter fw = new FileWriter("MinimizacionDeAFD AFN.txt");
            ArrayList<ArrayList<Integer>> estados = new ArrayList<ArrayList<Integer>>();
            // 
            for (Transicion t : afd.transiciones) {
                if (!estados.contains(t.estadosInicial)) {
                    estados.add(t.estadosInicial);
                }
                if (!estados.contains(t.estadosFinal)) {
                    estados.add(t.estadosFinal);
                }
            }
            String sfinal = "(";
            for (ArrayList<Integer> i : estados) {
                sfinal += " " + i.toString();
            }
            sfinal += " )";

            String cfinal = "(";
            for (Character c : afd.obtenerCaracteresAFD()) {
                cfinal += " " + c.toString();
            }
            cfinal += " )";

            String aceptacion = "(";

            for (ArrayList<Integer> arr : afd.estadosAceptacion) {
                aceptacion += " " + arr.toString();
            }
            aceptacion += " )";

            fw.write("ESTADOS:\n");
            fw.write(minimizacion.particionesFinales.toString());
            fw.write("\nSIMBOLOS\n");
            fw.write(afdMinimizado.obtenerCaracteresAFD().toString());
            fw.write("\nINICIO:\n");
            fw.write("[ " + minimizacion.particionesFinales.get(0) + " ]");
            fw.write("\nFINAL\n");
            // System.out.println("ACEP" + afdMinimizado2.estadosAceptacionNode);
            fw.write(arbolAFD.estadosAceptacionNode.toString());
            fw.write("\nTRANSICIONES:\n");
            fw.write(afdMinimizado.toString());
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        SimulacionAFD simulacionAFD = new SimulacionAFD(afdMinimizado, cadenaUniversad);

        long start = System.currentTimeMillis();

        simulacionAFD.simular();

        System.out.println("Resultado de la simulacion AFD Thompson: " + simulacionAFD.completado);

        System.out.println("La simulacion tardo aproximadamente: " + (System.currentTimeMillis() - start) + " ms.");

        System.out.println();

        SimulacionNode simulacionNode = new SimulacionNode(arbolAFD, cadenaUniversad);

        start = System.currentTimeMillis();

        simulacionNode.simular();

        System.out.println("Resultado de la simulacion AFD Directo: " + simulacionNode.completado);

        System.out.println("La simulacion tardo aproximadamente: " + (System.currentTimeMillis() - start) + " ms.");

        System.out.println();

        SimulacionAFD simulacionAFD2 = new SimulacionAFD(afdMinimizado, cadenaUniversad);

        start = System.currentTimeMillis();

        simulacionAFD2.simular();

        System.out.println("Resultado de la simulacion AFD Thompson minimizado: " + simulacionAFD2.completado);

        System.out.println("La simulacion tardo aproximadamente: " + (System.currentTimeMillis() - start) + " ms.");

        System.out.println();

        SimulacionNode simulacionNode2 = new SimulacionNode(afdMinimizado2, cadenaUniversad);

        start = System.currentTimeMillis();

        simulacionNode2.simular();

        System.out.println("Resultado de la simulacion AFD Directo minimizado: " + simulacionNode2.completado);

        System.out.println("La simulacion tardo aproximadamente: " + (System.currentTimeMillis() - start) + " ms.");

        System.out.println();

        // SimulacionAFN simulacionAFN = new SimulacionAFN(afnReorganizado, cadenaUniversad);

        // simulacionAFN.simular();

        // System.out.println("Resultado de la simulacion AFN: " + simulacionAFN.completado);

        // System.out.println();









        
        // Necesitamos el afd y la cadena 

    }

}
