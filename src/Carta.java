package src.edd;

public class Carta {
    private int numero;
    private String palo;

    public static final String rojo = "\u001B[31m";
    public static final String verde = "\u001B[32m";
    public static final String amarillo = "\u001B[33m";
    public static final String azul = "\u001B[34m";
    public static final String morado = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static final String blanco = "\u001B[37m";

    public static final String[] PALOS = { rojo, amarillo, verde, azul };
    public static final String[] ESPECIALES = { morado, cyan };

    public Carta(int numero, String palo) {
        this.numero = numero;
        this.palo = palo;
    }

    public int getNumero() {
        return numero;
    }

    public String getPalo() {
        return palo;
    }

    public String toString() {
        return "[" + palo + numero + blanco + "]";
    }

}
