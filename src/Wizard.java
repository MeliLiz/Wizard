
package src.edd;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
import java.util.Iterator;

import src.edd.Baraja;
import src.edd.IteradorLista;
import src.edd.Jugador;

import java.io.File;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import java.io.FileNotFoundException;

public class Wizard {
    private static Scanner sc = new Scanner(System.in);

    /**
     * Método menu
     * Donde el usuario decidirá la acción que quiere llevar a cabo
     */
    public static void menu() {
        // Imprimir opciones
        System.out.print("\033[H\033[2J");
        System.out.println("\nWizard\n***Menu***");
        System.out.println("Selecciona una opcion (Ingresa el numero)");
        System.out.println("\n(1) Ver instrucciones");
        System.out.println("(2) Jugar");
        System.out.println("(3) Salir");
        System.out.println("A lo largo del juego podrá teclear -1 para salir o -2 para ver el historial");
        System.out.println("¡Disfrute el juego!");

        // Leer la respuesta
        boolean h = true;
        //variable para manejar el switch
        int respuesta = 0;
        do {
            try {
                respuesta = sc.nextInt();
                //si la respuesta no es un caso definido entonces lanzaremos que la opción no es válida.
                if (respuesta < 1 || respuesta > 3) {
                    System.out.println("\u001B[33m" + "La opcion que ingresaste no es valida" + "\u001B[0m");
                } else {
                    h = false;
                }
            } catch (InputMismatchException e) {
                errNum();
                sc.next();
            }
        } while (h);

        switch (respuesta) {

            case 1:// imprimir instrucciones
                try {
                    File f = new File("src/Reglas.txt");
                    Scanner s = new Scanner(f);
                    while (s.hasNextLine()) {
                        System.out.println(s.nextLine());
                    }
                    System.out.println("\nPresione cualquier tecla seguida de enter para volver al menu");
                    sc.next();
                    menu();
                } catch (FileNotFoundException e) {
                    System.out.println("El archivo no existe");
                }
                break;

            case 3:// Salir del juego
                h = false;
                System.out.println("Saliendo...");
                System.exit(0);
                break;

            case 2:// jugar
                int numJugadores = 0;
                Boolean incorrecto = true;
                int rondas = 0;
                do {
                    System.out.print("\033[H\033[2J");
                    System.out.println("¡Hola! Bienvenido a Wizard");//dando la bienvenida al juego
                    System.out.println("Ingresa el numero de personas que jugarán (entre 3 y 6)");// Preguntando num de
                                                                                                  // jugadores y
                                                                                                  // validando
                    try {
                        numJugadores = sc.nextInt();
                        verificaRespuesta(numJugadores);// Verificar si el usuario quiere salir o ver el historial
                        if (numJugadores < 3 || numJugadores > 6) {
                            System.out.println("\u001B[33m" + "No es un número valido de jugadores. Vuelve a intentarlo" + "\u001B[0m");
                        } else {
                            incorrecto = false;
                        }
                    } catch (InputMismatchException e) {
                        errNum();
                        sc.next();
                    }
                } while (incorrecto);

                // Asignar numero de rondas dependiendo del numero de jugadores
                switch (numJugadores) {
                    case 3:
                        rondas = 20;
                        break;
                    case 4:
                        rondas = 15;
                        break;
                    case 5:
                        rondas = 12;
                        break;
                    case 6:
                        rondas = 10;
                        break;
                    default:
                        break;
                }
                Registro registro = new Registro();// Hacemos un registro
                Lista<Carta[][]> cartasTiradasPorRonda = new Lista<Carta[][]>(); // Arreglo para el registro
                jugar(numJugadores, rondas, cartasTiradasPorRonda, registro);// jugamos
                break;

            default:
                break;
        }
    }// FIN DE MENU

    /**
     * Metodo para jugar las rondas
     * 
     * @param numJugadores
     * @param numRondas
     * @param registro
     */
    public static void jugar(int numJugadores, int numRondas, Lista<Carta[][]> cartasPorRonda, Registro registro) {
        Scanner scan = new Scanner(System.in);
        Baraja baraja = new Baraja();// Creamos la baraja
        Lista<Jugador> jugadores = new Lista<Jugador>();// Creamos la lista de jugadores

        // Pedimos el nombre de los jugadores
        for (int i = 0; i < numJugadores; i++) {
            System.out.println("Ingresa el nombre del jugador " + (i + 1));
            String nombre = scan.nextLine();
            jugadores.add(new Jugador(nombre, i));
        }

        // Jugaremos el numero determinado de rondas
        int contador = 0;
        while (contador < numRondas) {
            System.out
                    .println("\n************************Ronda " + (contador + 1) + "*********************************");
            // Elegimos al barajeador
            Random random = new Random();
            int numRandom = random.nextInt(numJugadores);
            Jugador barajeador = jugadores.elementoEnPos(numRandom);
            System.out.println(barajeador + " barajea las cartas");
            jugadores.listaCircular();// Hacemos la lista de jugadores de forma circular
            baraja.shuffle();// barajeamos
            Iterator<Jugador> it = jugadores.iterator();// Hacemos el iterador
            // repartimos las cartas a los jugadores
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador actual = it.next();
                actual.setCartas(baraja.darCartas(contador + 1));
                System.out.println(actual.getCartas());
            }

            // Sacamos el palo de triunfo de la ronda
            String paloTriunfo = "";
            try {// try-catch para que al ser el ultimo truco no haya palo de triunfo

                Carta paloTriunfoCarta = baraja.CartaSiguiente();
                System.out.println("\u001B[0m" + "Carta sacada para elegir el palo de triunfo: " + paloTriunfoCarta);

                // switch para elegir el palo de triunfo de acurdo con la carta que sacamos
                switch (paloTriunfoCarta.getNumero()) {

                    case 14: // Sale mago, entonces quien barajea escoge el palo de triunfo
                        boolean k = true;
                        int respuesta = 0;
                        String[] elegir = Carta.PALOS;
                        while (k) {
                            System.out.println("La carta es un mago, " + barajeador
                                    + " elige el palo de triunfo (ingresa el numero de carta de 0-3)");
                            for (int i = 0; i < elegir.length; i++) {
                                System.out.print("[" + elegir[i] + "#" + "\u001B[0m " + "] ");
                            }
                            System.out.println();

                            try {
                                respuesta = sc.nextInt();
                                verificaRespuesta(respuesta);/////////////////////////////////////////////////////////////////////////////////////////////////
                                if (respuesta < 0 || respuesta > 3) {
                                    System.out.println("\u001B[33m" + "No ingresaste un numero valido" + "\u001B[0m");
                                    sc.next();
                                } else {
                                    k = false;
                                }
                            } catch (InputMismatchException e) {
                                errNum();
                                sc.next();
                            }
                        }
                        paloTriunfo = elegir[respuesta];
                        break;
                    case 0:// Sale bufón, no hay palo de triunfo
                        paloTriunfo = "";
                        break;
                    default:// sale numero, el palo de triunfo es el palo de la carta que sale
                        paloTriunfo = paloTriunfoCarta.getPalo();
                        break;

                }

            } catch (NullPointerException e) {
                paloTriunfo = "";
            }

            if (paloTriunfo.equals("")) {
                System.out.println("No hay palo de triunfo en esta ronda");
            } else {
                System.out.println("Palo de triunfo de esta ronda: [" + paloTriunfo + "#" + "\u001B[0m " + "]");
            }

            // preguntar apuestas
            it = jugadores.iterator();
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador actual = it.next();
                boolean k = true;
                while (k) {

                    System.out.println("\n" + actual.getNombre() + ", ingresa tu apuesta. Puedes apostar de 0 a "
                            + (contador + 1) + " trucos");
                    // Mostrar el palo de triunfo para que los jugadores puedan apostar
                    if (paloTriunfo.equals("")) {
                        System.out.println("\n<Sin palo de triunfo>");
                    } else {
                        System.out.println("\n<Palo de triunfo: [" + paloTriunfo + "#" + "\u001B[0m" + "]>");
                    }
                    // Mostrar las cartas a cada jugador
                    System.out.println("Estas son tus cartas");
                    System.out.println(actual.getCartas());
                    int apuesta = 0;
                    // Verificar que la respuesta del jugador sea valida
                    try {
                        apuesta = sc.nextInt();
                        verificaRespuesta(apuesta, contador, registro, jugadores);////////////////////////////////////////////////////////////////////////////////////
                        if (apuesta > contador + 1) {
                            System.out.println(
                                    "\u001B[33m" + "Numero no valido para apostar en esta ronda" + "\u001B[0m");
                            // sc.next();
                        } else if (apuesta != -1 && apuesta != -2) {
                            actual.setApuesta(apuesta);
                            k = false;
                        }
                    } catch (InputMismatchException e) {
                        errNum();
                        sc.next();
                    }
                }
            }

            // jugamos los trucos
            // Guardamos al ganador de cada truco en la cola de registro
            registro.getGanadoresDeTrucos()
                    .add(truco(contador + 1, baraja, jugadores, paloTriunfo, barajeador, registro));
            it = jugadores.iterator();
            // Ver la relacion de ganadas con apuestas de cada jugador para tomar su puntaje
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador actual = it.next();

                if (actual.getApuesta() == actual.getGanadas()) {
                    actual.sumaPuntaje(20 + (10 * actual.getGanadas()));
                } else {
                    actual.sumaPuntaje(-10 * (Math.abs(actual.getApuesta() - actual.getGanadas())));
                }
            }
            System.out.println("Puntuaciones de la ronda");
            it = jugadores.iterator();
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador actual = it.next();
                System.out.println(actual.getNombre() + " " + actual.getPuntaje() + " puntos");
            }

            // Reiniciar contador de apuestas en jugadores, las cartas jugadas
            it = jugadores.iterator();
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador actual = it.next();
                actual.setApuesta(0);
                actual.setGanadas(0);
                actual.getCartasTiradas().empty();
            }

            contador++;// Aumentamos el contador de rondas que ya se jugaron
        }
        // Ver quién es el ganador del juego
        Iterator<Jugador> it = jugadores.iterator();
        Jugador ganador = it.next();
        Jugador actual = ganador;
        Lista<Jugador> empate = new Lista<Jugador>();// Si hay empate, hacemos una lista de los jugadores que empataron
        for (int i = 0; i < jugadores.size(); i++) {
            actual = it.next();
            if (ganador.getPuntaje() < actual.getPuntaje()) {
                ganador = actual;
            } else if (ganador.getPuntaje() < actual.getPuntaje()) {
                if (!empate.contains(ganador)) {
                    empate.add(ganador);
                }
                empate.add(actual);
            }
        }
        // Imprimir al o los ganadores del juego
        if(empate.isEmpty()){
            System.out.println("\n &&&&&&&&&&&&&& Ganador del juego: " + ganador.getNombre()+" &&&&&&&&&&&&&&&&&&&");
        }else{
            it=jugadores.iterator();//reiniciamos el iterador
            System.out.print("\n &&&&&&&&&&&&&&&&& Empate entre ");
            for(int i=0; i<empate.size();i++){
                System.out.print(it.next().getNombre()+" ");
            }
            System.out.print("&&&&&&&&&&&&&&&&&&&");
        }
        historial(numRondas, registro, jugadores);

    }

    /**
     * Metodo para jugar los trucos
     * 
     * @param numTrucos
     * @param baraja
     * @param jugadores
     * @param paloTriunfo
     * @param barajeador
     */
    public static Cola<Jugador> truco(int numTrucos, Baraja baraja, Lista<Jugador> jugadores, String paloTriunfo,
            Jugador barajeador, Registro registro) {

        int contador = 1;// numero de truco en el que estamos
        IteradorLista<Jugador> iterador = jugadores.iteradorLista();// iterador de la lista de los jugadores
        Jugador actual = iterador.next();// jugador actual
        Jugador ganador = null;// ganador del truco anterior
        Cola<Jugador> ganadorTruco = new Cola<Jugador>(); // Cola para guardar los jugadores que ganan cada truco
        Carta[][] arrRegistro = new Carta[numTrucos][jugadores.size()];// Arreglo para ir poniendo las cartas que tira
                                                                       // cada jugador en cada truco

        // Hacemos los trucos
        while (contador <= numTrucos) {
            System.out.print("\033[H\033[2J");
            System.out.println("\n*******Truco " + (contador) + "***************");
            String paloLider = "";// El palo lilder del truco

            // Si la iteracion es la primera, el jugador que tirará primero es el de la
            // izquierda del barajeador
            if (contador == 1) {
                while (!actual.getNombre().equals(barajeador.getNombre())) {
                    actual = iterador.next();
                }
                iterador.previous();
                actual = iterador.previous();
            } else {
                while (!actual.getNombre().equals(ganador.getNombre())) {
                    actual = iterador.previous();
                }
            }
            // System.out.println("jugador actual " + actual.getNombre());

            // Turno de cada jugador
            int i = 0;
            while (i < jugadores.size()) {

                // Pedir carta a tirar y verificar la respuesta
                boolean k = true;
                while (k) {
                    System.out.println("\n>>Turno de " + actual.getNombre() + "<<");
                    if (paloTriunfo.equals("")) {
                        System.out.println("<Sin palo de triunfo>");
                    } else {
                        System.out.println("<Palo de triunfo: [" + paloTriunfo + "#" + "\u001B[0m" + "]>");
                    }
                    if (paloLider.equals("")) {
                        System.out.println("<Aun no hay palo lider>");
                    } else {
                        System.out.println("<Palo lider: [" + paloLider + "#" + "\u001B[0m" + "]>");
                    }
                    System.out.println("Elige la carta a tirar (ingresa la posicion de 0 a "
                            + (actual.getCartas().size() - 1) + ")");
                    System.out.println(actual.getCartas());

                    // Leer y verificar la respuesta del jugador
                    // Verificar si se ingreso una posicion valida y si la carta es del palo lider
                    // dependiendo del caso
                    int respuesta = -1;
                    try {
                        respuesta = sc.nextInt();
                        verificaRespuesta(respuesta, contador - 1, registro, jugadores);/////////////////////////////////////////////////////////////////////////////////////
                        if (respuesta < 0 || respuesta > (actual.getCartas().size() - 1)) {
                            if (respuesta != -1 && respuesta != -2) {// Para verificar que en la respuesta no hayan
                                                                     // solicitado el historial
                                System.out.println("\u001B[33m" + "No es una posicion valida " + "\u001B[0m");
                                sc.next();
                            }
                        } else {
                            Lista<Carta> aux = actual.getCartas();
                            Iterator<Carta> iterator = aux.iterator();
                            int l = 0;
                            Carta ayuda = iterator.next();
                            while (l < respuesta && iterator.hasNext()) {
                                ayuda = iterator.next();
                                l++;
                            }
                            if (!paloLider.equals("")) {
                                if (ayuda.getNumero() != 14 && ayuda.getNumero() != 0) {
                                    if (!ayuda.getPalo().equals(paloLider)) {
                                        boolean nohay = true;
                                        iterator = aux.iterator();
                                        Carta ayuda2 = null;
                                        for (int m = 0; m < aux.size(); m++) {
                                            ayuda2 = iterator.next();
                                            if (ayuda2.getPalo().equals(paloLider)) {
                                                nohay = false;
                                            }
                                        }
                                        if (nohay) {
                                            arrRegistro[contador - 1][actual.getNumJugador()] = actual.getCartas()
                                                    .elementoEnPos(respuesta);// Ponemos la carta tirada en el lugar
                                                                              // correspondiente del arreglo para el
                                                                              // registro
                                            actual.getCartasTiradas().push(actual.getCartas().eliminaEnPos(respuesta));
                                            k = false;
                                        } else {
                                            System.out.println("\u001B[36m" + "Debes tirar una carta del palo lider"
                                                    + "\u001B[0m ");
                                            // \u001B[0m reset
                                            // \033[30m
                                            // \u001B[37m
                                        }
                                    } else {
                                        arrRegistro[contador - 1][actual.getNumJugador()] = actual.getCartas()
                                                .elementoEnPos(respuesta);// Ponemos la carta tirada en el lugar
                                                                          // correspondiente del arreglo para el
                                                                          // registro
                                        actual.getCartasTiradas().push(actual.getCartas().eliminaEnPos(respuesta));
                                        k = false;
                                    }
                                } else {
                                    arrRegistro[contador - 1][actual.getNumJugador()] = actual.getCartas()
                                            .elementoEnPos(respuesta);// Ponemos la carta tirada en el lugar
                                                                      // correspondiente del arreglo para el registro
                                    actual.getCartasTiradas().push(actual.getCartas().eliminaEnPos(respuesta));
                                    k = false;
                                }
                            } else {
                                arrRegistro[contador - 1][actual.getNumJugador()] = actual.getCartas()
                                        .elementoEnPos(respuesta);// Ponemos la carta tirada en el lugar correspondiente
                                                                  // del arreglo para el registro
                                actual.getCartasTiradas().push(actual.getCartas().eliminaEnPos(respuesta));
                                k = false;
                            }
                            //System.out.println(actual.getCartas());
                        }
                    } catch (InputMismatchException e) {
                        errNum();
                        sc.next();
                    }

                }

                // Asignacion del palo lider
                if (paloLider.equals("")) {// Si todavía no hay palo lider
                    // Si la cara que tiró el jugador no es mago o bufon, asignamos el palo lider
                    if (actual.getCartasTiradas().peek2().getNumero() != 14
                            && actual.getCartasTiradas().peek2().getNumero() != 0) {
                        paloLider = actual.getCartasTiradas().peek2().getPalo();
                    }
                } // Si no, sigue sin haber palo lider

                // Ver quién va ganando el truco
                if (ganador == null) {// Si todavia no hay ganador
                    ganador = actual;// Ponemos al jugador actual como ganador
                } else {
                    int numCartaGanador = ganador.getCartasTiradas().peek2().getNumero();// Numero de la carta tirada
                                                                                         // por el ganador
                    int numCartaJugador = actual.getCartasTiradas().peek2().getNumero();// Numero de la carta tirada por
                                                                                        // el jugador actual
                    String paloJugador = actual.getCartasTiradas().peek2().getPalo();// Palo de la carta tirada por el
                                                                                     // jugador actual
                    String paloGanador = ganador.getCartasTiradas().peek2().getPalo();// palo de la carta que tiró el
                                                                                      // ganador

                    if (numCartaGanador != 14) {// Si nadie ha tirado un mago

                        if (numCartaJugador == 14) {// Si la carta del jugador actual es mago
                            ganador = actual;// El jugador actual es el ganador

                        } else if (paloJugador.equals(paloTriunfo)) {// si la carta actual coincide con el palo de
                                                                     // triunfo
                            if (paloGanador.equals(paloTriunfo)) {// y la carta ganadora también coincide con el palo de
                                                                  // triunfo
                                if (numCartaGanador < numCartaJugador) {// Vemos cuál es más alta
                                    ganador = actual;
                                }
                            } else {// Si la carta ganadora no es del palo de triunfo
                                ganador = actual;// el ganador se actualiza
                            }
                        } else if (paloJugador.equals(paloLider)) {// si el palo de la carta tirada es del palo lider
                            if (!paloGanador.equals(paloTriunfo)) {// y el palo del ganador no es del palo de triunfo
                                if (paloGanador.equals(paloLider)) {// Si ambos son del palo lider
                                    if (numCartaGanador < numCartaJugador) {// comparamos los numeros de las cartas
                                        ganador = actual;
                                    }
                                } else {// si no, entonces la carta del ganador vale menos que la actual
                                    ganador = actual;// entonces se actualiza
                                }
                            }

                        } else if ((numCartaGanador != 0 || numCartaJugador != 0) && !paloGanador.equals(paloTriunfo)
                                && !paloGanador.equals(paloLider)) {// Entra aquí si son numeros y no son bufones
                            if (numCartaGanador < numCartaJugador) {// Se comparan los numeros
                                ganador = actual;
                            }
                        } else {
                        } // Si son bufones entonces el que lo tiro primero se queda como ganador
                    } // Si la carta del ganador hasta ahora es un mago, ese jugador sigue siendo el
                      // ganador
                }

                // Pasamos al siguiente jugador
                /*
                 * if(i == 0){
                 * iterador.previous();
                 * }
                 */
                actual = iterador.previous();// .next()
                // System.out.println("actual: " + actual.getNombre());
                i++;
            }
            ganador.sumaGanadas(1);// aumentamos el contador de ganadas del jugador
            ganadorTruco.push(ganador);// agregamos al ganador a la cola de ganadores de trucos
            System.out.println(
                    "\u001B[32m" + "### El ganador del truco es " + ganador.getNombre() + " ###" + "\u001B[0m");
            contador++; // aumentamos el contador de trucos hechos
        }
        registro.getCartasTiradas().add(arrRegistro);
        return ganadorTruco;
    }

    /**
     * Metodo para ver el historial hasta ahora
     * 
     * @param numRondas
     * @param registro
     */
    public static void historial(int numRondas, Registro registro, Lista<Jugador> jugadores) {
        System.err.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&& Historial &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
        Lista<Cola<Jugador>> lganadores = registro.getGanadoresDeTrucos();
        Lista<Carta[][]> lCartas = registro.getCartasTiradas();
        Iterator<Cola<Jugador>> it1 = lganadores.iterator();
        Iterator<Carta[][]> it2 = lCartas.iterator();
        Iterator<Jugador> itJugadores= jugadores.iterator();

        for (int i = 0; i < numRondas; i++) {

            System.out.println("** Ronda " + (i + 1) + " ***");
            System.out.println("Cartas tiradas de cada jugador por truco: ");

            Carta[][] aux = it2.next();
            Cola<Jugador> aux2 = it1.next();

            for (int j = 0; j < aux.length; j++) {// numero de trucos
                System.out.println("Truco " + (j + 1));

                for (int k = 0; k < aux[0].length; k++) {
                    System.out.println(itJugadores.next().getNombre()+ " " + aux[j][k]);
                }

                Jugador aux3 = aux2.pop();
                System.out.println("Ganador del truco: " + aux3.getNombre());
                aux2.push(aux3);
            }
        }
        System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
    }

    /**
     * Metodo para imprimir el error de input del usuario
     */
    public static void errNum() {
        System.out.println("\u001B[33m" + "Probablemente no ingresaste un numero" + "\u001B[0m");
        return;
    }

    /**
     * Verifica si el usuario quiere salir o ver el historial
     * 
     * @param respuesta
     * @param numRondas
     * @param registro
     */
    public static void verificaRespuesta(int respuesta, int numRondas, Registro registro, Lista<Jugador> jugadores) {

        if (respuesta == -1) {// Si la respuesta es -1 salir del programa
            historial(numRondas, registro, jugadores);
            System.out.println("Saliendo...");
            System.exit(0);

        }
        if (respuesta == -2) {// Si la respuesta es -2, ver el historial
            historial(numRondas, registro, jugadores);
        }
        return;
    }

    /**
     * Metodo que verifica, aplicar este metodo si sabemos que no hay historial por
     * mostrar
     * 
     * @param respuesta
     */
    public static void verificaRespuesta(int respuesta) {

        if (respuesta == -1) {// Si la respuesta es -1, ver si
            System.out.println("Saliendo del juego, no hay historial por mostrar");
            System.out.println("Saliendo...");
            System.exit(0);
        }
        return;
    }

    /**
     * Metodo principal
     * 
     * @param args
     */
    public static void main(String[] args) {
        menu();
    }
}
