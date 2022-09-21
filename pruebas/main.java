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
        String expresionRegular = "(a|b)*.a.b.b";
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

        System.out.println("AFN generado por Thompson: \n");

        // afnFinal.mostrarAFN();

        // Escribir el AFN generado por Thompson a su archivo:

        // ------------------------------------------------------------------

        // Subconjuntos

        // Subconjuntos subconjunto = new Subconjuntos(afnFinal);

        // System.out.println("\nSubconjuntos");
        // subconjunto.obtenerEclosures();

        AFN afnReorganizado = Eclosure.reOrganizarAFN(afnFinal);

        System.out.println(afnReorganizado);

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

        System.out.println("AFD generado por subconjuntos: \n");

        System.out.println(afd);

        // System.out.println(Eclosure.estadosGenerados);
        System.out.println(Eclosure.diccionarioEstadosGenerados);
        afd.generarEstadosAceptacion(afnReorganizado.transiciones
                .get(afnReorganizado.transiciones.size() - 1).estadoFinal, Eclosure.diccionarioEstadosGenerados);
        System.out.println(afd.estadosAceptacion);
        System.out.println(afd.estadosNOAceptacion);

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

        // Minimizacion por particiones

        afd.listaCaracteres = afnReorganizado.obtenerListaCaracteres();

        Minimizacion minimizacion = new Minimizacion(afd);

        AFD afdMinimizado = minimizacion.minimizarAFD();

    }

}
