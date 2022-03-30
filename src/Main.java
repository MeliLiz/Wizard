package src.edd;
public class Main {
    public static void main(String[] args){
        /*Baraja baraja = new Baraja();
        System.out.println(baraja.cartas);
        //baraja.CartaSiguiente();
        System.out.println(baraja.CartaSiguiente());
        System.out.println(baraja.CartaSiguiente());
        baraja.shuffle();
        System.out.println(baraja.cartas);
        System.out.println(baraja.CartaSiguiente());*/
        Lista<Integer> lista=new Lista<Integer>();
        System.out.println(lista);
        System.out.println("listo");
        lista.add(1);
        System.out.println(lista);
        lista.eliminaEnPos(0);
        System.out.println(lista);

    }
}
