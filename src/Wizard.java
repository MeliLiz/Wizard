package src.edd;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
import java.util.Iterator;

import src.edd.Baraja;
import src.edd.IteradorLista;
import src.edd.Jugador;

import java.io.File;
import java.io.FileNotFoundException;

public class Wizard {
    private static Scanner sc = new Scanner(System.in);

    public static void menu() {
        // Imprimir opciones
        System.out.println("\nWizard\n***Menu***");
        System.out.println("Selecciona una opcion (Ingresa el numero)");
        System.out.println("\n(1) Ver instrucciones");
        System.out.println("(2) Jugar");
        System.out.println("(3) Salir");

        // Leer la respuesta
        boolean h = true;
        int respuesta = 0;
        do {
            try {
                respuesta = sc.nextInt();
                if (respuesta < 1 || respuesta > 3) {
                    System.out.println("La opcion que ingresaste no es valida");
                } else {
                    h = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Probablemente no ingresaste un numero");
                sc.next();
            }
        } while (h);

        switch (respuesta) {
            case 1:// imprimir instrucciones
                try {
                    File f = new File("Reglas.txt");
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
                    System.out.println("Ingresa el numero de personas que jugarán (entre 3 y 6)");// Preguntando num de
                                                                                                  // jugadores y
                                                                                                  // validando
                    try {
                        numJugadores = sc.nextInt();
                        if (numJugadores < 3 || numJugadores > 6) {
                            System.out.println("No es un numero valido de jugadores");
                        } else {
                            incorrecto = false;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Probablemente no ingresaste un numero");
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
                int[][] registro = new int[numJugadores][rondas]; // Arreglo para el registro
                jugar(numJugadores, rondas, registro);// jugamos
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
    public static void jugar(int numJugadores, int numRondas, int[][] registro) {
        Scanner scan = new Scanner(System.in);
        Baraja baraja = new Baraja();// Creamos la baraja
        Lista<Jugador> jugadores = new Lista<Jugador>();// Creamos la lista de jugadores

        // Pedimos el nombre de los jugadores
        for (int i = 0; i < numJugadores; i++) {
            System.out.println("Ingresa el nombre del jugador " + (i + 1));
            String nombre = scan.nextLine();
            jugadores.add(new Jugador(nombre));
        }

        // Jugaremos el numero determinado de rondas
        int contador = 0;
        while (contador < numRondas) {
            System.out.println("Ronda " + (contador + 1));
            // Elegimos al barajeador
            Random random = new Random();
            int numRandom = random.nextInt(numJugadores - 1);
            Jugador barajeador = jugadores.elementoEnPos(numRandom);
            System.out.println(barajeador + " barajea las cartas");
            jugadores.listaCircular();// Hacemos la lista de jugadores de forma circular
            baraja.shuffle();// barajeamos

            // Sacamos el palo de triunfo de la ronda
            String paloTriunfo = "";
            try {// try-catch para que al ser el ultimo truco no haya palo de triunfo

                Carta paloTriunfoCarta = baraja.CartaSiguiente();
                System.out.println("Carta sacada para elegir el palo de triunfo: " + paloTriunfoCarta);

                // switch para elegir el palo de triunfo de acurdo con la carta que sacamos
                switch (paloTriunfoCarta.getNumero()) {

                    case 14: // Sale mago, entonces quien barajea escoge el palo de triunfo
                        boolean k = true;
                        int respuesta = 0;
                        String[] elegir = Carta.PALOS;
                        while (k) {
                            System.out.println("La carta es un mago, " + barajeador
                                    + " elige el palo de triunfo (ingresa el numero de carta de 0-3");
                            for (int i = 0; i < elegir.length; i++) {
                                System.out.print("[" + elegir[i] + "#" + "\u001B[37m" + "] ");
                            }
                            System.out.println();

                            try {
                                respuesta = sc.nextInt();
                                if (respuesta < 0 || respuesta > 3) {
                                    System.out.println("No ingresaste un numero valido");
                                    sc.next();
                                } else {
                                    k = false;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Probablemente no ingresaste un numero");
                                sc.next();
                            }
                        }
                        paloTriunfo = elegir[respuesta];
                        break;
                    case 0:// Sale bufón, no hay palo de triunfo
                        paloTriunfo = null;
                    default:// sale numero, el palo de triunfo es el palo de la carta que sale
                        paloTriunfo = paloTriunfoCarta.getPalo();
                        break;

                }

            } catch (NullPointerException e) {
                paloTriunfo = null;
            }

            if (paloTriunfo.equals(null)) {
                System.out.println("No hay palo de triunfo en esta ronda");
            } else {
                System.out.println("Palo de triunfo de esta ronda: [" + paloTriunfo + "#" + "\u001B[37m" + "]");
            }

            // preguntar apuestas
            Iterator<Jugador> it = jugadores.iterator();
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador actual = it.next();
                boolean k = true;
                while (k) {
                    System.out.println(
                            actual.getNombre() + ", ingresa tu apuesta. Puedes apostar de 0 a " + (contador + 1)
                                    + " trucos");

                    int apuesta = 0;
                    try {
                        apuesta = sc.nextInt();
                        if (apuesta > contador + 1) {
                            System.out.println("Numero no valido para apostar en esta ronda");
                            // sc.next();
                        } else {
                            actual.setApuesta(apuesta);
                            k = false;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Probablemente no ingresaste un numero");
                        sc.next();
                    }
                }
            }
            // jugamos los trucos
            truco(contador + 1, baraja, jugadores, paloTriunfo, barajeador);
            contador++;
        }

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
    public static void truco(int numTrucos, Baraja baraja, Lista<Jugador> jugadores, String paloTriunfo,
            Jugador barajeador) {

        int contador = 1;// numero de truco en el que estamos
        IteradorLista<Jugador> iterador = jugadores.iteradorLista();// iterador de la lista de los jugadores
        Jugador actual = iterador.next();// jugador actual
        Jugador ganador = null;// ganador del truco anterior

        // Hacemos los trucos
        while (contador <= numTrucos) {
            // repartimos las cartas a los jugadores
            for (int i = 0; i < jugadores.size(); i++) {
                actual = iterador.next();
                actual.setCartas(baraja.darCartas(contador));
            }
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
                    actual = iterador.next();
                }
            }
            System.out.println("jugador actual " + actual.getNombre());

            // Turno de cada jugador
            int i = 0;
            while (i < jugadores.size()) {
                // Pedir carta a tirar y verificar la respuesta
                boolean k = true;
                while (k) {
                    System.out.println(
                            "Turno de " + actual.getNombre() + "\nElige la carta a tirar (ingresa la posicion de 0 a "
                                    + (actual.getCartas().size() - 1) + ")");
                    System.out.println(actual.getCartas());

                    // Leer y verificar la respuesta del jugador
                    int respuesta = -1;
                    try {
                        respuesta = sc.nextInt();
                        if (respuesta < 0 || respuesta > (actual.getCartas().size() - 1)) {
                            System.out.println("No es una posicion valida ");
                            sc.next();
                        } else {
                            // Quitar la carta de la lista de cartas del jugador y ponerla en una lista de
                            // registros

                            k = false;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Probablemente no ingresaste un numero");
                        sc.next();
                    }

                }
                if (i == 0) {
                    iterador.next();
                }
                actual = iterador.next();
                System.out.println("actual" + actual.getNombre());
                i++;
            }

            contador++;
        }
    }

    public static void main(String[] args) {
        menu();
    }
}
