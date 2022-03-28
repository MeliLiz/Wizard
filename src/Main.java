package src.edd;
public class Main {
    public static void main(String[] args){
        Baraja baraja = new Baraja();
        System.out.println(baraja.cartas);
        //baraja.CartaSiguiente();
        System.out.println(baraja.CartaSiguiente());
        System.out.println(baraja.CartaSiguiente());
        baraja.shuffle();
        System.out.println(baraja.cartas);
        System.out.println(baraja.CartaSiguiente());
    }
}
