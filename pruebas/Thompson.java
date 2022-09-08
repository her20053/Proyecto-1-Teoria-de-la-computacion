import java.util.List;
import java.util.Stack;

public class Thompson {
    // Epsilon: ε

    public String er;

    public int cantidadEstadosGenerados = 0;

    public Thompson(String expresionRegular) {
        this.er = expresionRegular;
    }

    public AFN reglaSimbolo(char simbolo) {

        AFN temporal = new AFN();

        int estadoInicial = cantidadEstadosGenerados;

        cantidadEstadosGenerados++;

        int estadoFinal = cantidadEstadosGenerados;

        cantidadEstadosGenerados++;

        Transicion transicionTemporal = new Transicion(estadoInicial, simbolo, estadoFinal);

        temporal.transiciones.add(transicionTemporal);

        return temporal;

    }

    public AFN reglaOR(AFN afn1, AFN afn2) {

        AFN afn3 = new AFN();

        int estado1temp = cantidadEstadosGenerados;
        cantidadEstadosGenerados++;
        int estado2temp = cantidadEstadosGenerados;
        cantidadEstadosGenerados++;

        // System.out.println(estado1temp);
        // System.out.println(estado2temp);

        Transicion transicion1 = new Transicion(estado1temp, 'ε', afn1.transiciones.get(0).estadoInicial);
        Transicion transicion2 = new Transicion(estado1temp, 'ε', afn2.transiciones.get(0).estadoInicial);

        afn3.transiciones.add(transicion1);
        afn3.transiciones.add(transicion2);

        // Transicion transicion3 = afn1.transiciones.get(0);

        for (Transicion t : afn1.transiciones) {
            afn3.transiciones.add(t);
        }

        // Transicion transicion4 = afn2.transiciones.get(0);
        for (Transicion t : afn2.transiciones) {
            afn3.transiciones.add(t);
        }

        Transicion ultimaTransicionAFN1 = afn1.transiciones.get(afn1.transiciones.size() - 1);
        int ultimosEstadoAFN1 = ultimaTransicionAFN1.estadoFinal;

        Transicion ultimaTransicionAFN2 = afn2.transiciones.get(afn2.transiciones.size() - 1);
        int ultimosEstadoAFN2 = ultimaTransicionAFN2.estadoFinal;

        Transicion transicion5 = new Transicion(ultimosEstadoAFN1, 'ε', estado2temp);
        Transicion transicion6 = new Transicion(ultimosEstadoAFN2, 'ε', estado2temp);

        afn3.transiciones.add(transicion5);
        afn3.transiciones.add(transicion6);

        return afn3;

    }

    public AFN reglaKleen(AFN afn1) {

        AFN afn2 = new AFN();

        int estado1temp = cantidadEstadosGenerados;
        cantidadEstadosGenerados++;
        int estado2temp = cantidadEstadosGenerados;
        cantidadEstadosGenerados++;

        Transicion transicion1 = new Transicion(estado1temp, 'ε', afn1.transiciones.get(0).estadoInicial);

        afn2.transiciones.add(transicion1);

        for (Transicion t : afn1.transiciones) {
            afn2.transiciones.add(t);
        }

        Transicion ultimaTransicionAFN1 = afn1.transiciones.get(afn1.transiciones.size() - 1);
        int ultimosEstadoAFN1 = ultimaTransicionAFN1.estadoFinal;

        Transicion transicion2 = new Transicion(ultimosEstadoAFN1, 'ε', estado2temp);
        Transicion transicion3 = new Transicion(ultimosEstadoAFN1, 'ε', afn1.transiciones.get(0).estadoInicial);
        Transicion transicion4 = new Transicion(estado1temp, 'ε', estado2temp);

        afn2.transiciones.add(transicion2);
        afn2.transiciones.add(transicion3);
        afn2.transiciones.add(transicion4);

        return afn2;

    }

    public AFN reglaConcat(AFN afn1, AFN afn2) {

        // [(1e2),(2e3),(3e4)] - [(5e6),(6e7)] = [(1e2),(2e3),(3e4),(4e6),(6e7)]

        AFN afn3 = new AFN();

        for (Transicion t : afn1.transiciones) {
            afn3.transiciones.add(t);
        }

        int estadoFinalAFN1 = afn1.transiciones.get(afn1.transiciones.size() - 1).estadoFinal; // 4

        int estadoInicialAFN2 = afn2.transiciones.get(0).estadoInicial;

        for (Transicion t : afn2.transiciones) {

            if (t.estadoInicial == estadoInicialAFN2) {
                t.estadoInicial = estadoFinalAFN1;
            }

        }

        for (Transicion t : afn2.transiciones) {

            afn3.transiciones.add(t);

        }

        return afn3;

    }

    private static boolean isOperator(char operator) {
        return List.of('*', '|', '+', '.').contains(operator);
    }

    public AFN analizarPostfix() {

        Stack<AFN> stack = new Stack<AFN>();

        for (int i = 0; i < this.er.length(); i++) {

            char cActual = this.er.charAt(i);

            if (!isOperator(cActual)) {

                AFN temporal = reglaSimbolo(cActual);

                stack.push(temporal);

                System.out.println("Push simbolo: " + temporal);

            } else {

                if (cActual == '+' || cActual == '|' || cActual == '.') {

                    AFN afn2 = stack.pop();
                    AFN afn1 = stack.pop();

                    if (cActual == '+' || cActual == '|') {
                        AFN afn3 = reglaOR(afn1, afn2);
                        stack.push(afn3);
                    } else {
                        AFN afn3 = reglaConcat(afn1, afn2);
                        stack.push(afn3);
                    }

                } else {

                    AFN afn1 = stack.pop();

                    AFN afnNuevo = reglaKleen(afn1);

                    stack.push(afnNuevo);

                }

            }

        }

        return stack.peek();

    }

}
