package src.edd;
import java.util.Random;
import java.util.Iterator;
public class Baraja {

     Lista<Carta> cartas;//Las cartas de la baraja
    private Iterator<Carta> iterador;//iterador de la lista de cartas
    private int numDisponibles;//Numero de cartas disponibles
    public static final int numCARTAS=60;//El numero de cartas en la baraja

    public Baraja(){
        cartas=new Lista<Carta>();
        crearBaraja();
        numDisponibles=60;
        iterador=cartas.iterator();
    }

    private void crearBaraja(){
        //Arreglo con los palos (colores de las cartas)
        String[] palos = Carta.PALOS;
        //Hacemos las cartas normales
        for(int i=0;i<palos.length;i++){
            for(int j=0;j<13;j++){
                cartas.add(new Carta(j+1,palos[i]));
            }
        }
        //arreglo con el color de las cartas especiales
        palos=Carta.ESPECIALES;
        //Hacemos bufones
        for(int i=0;i<4;i++){
            cartas.add(new Carta(0,palos[1]));
        }
        //Hacemos magos
        for(int i=0;i<4;i++){
            cartas.add(new Carta(14,palos[0]));
        }
    }



    public void shuffle(){
        Random random = new Random();
        int posicionAleatoria=0;
        Carta carta1,carta2;
        for(int i=0;i<cartas.size()-1;i++){
            posicionAleatoria=random.nextInt(numCARTAS-1);
            carta1=cartas.elementoEnPos(i);
            carta2=cartas.elementoEnPos(posicionAleatoria);
            cartas.eliminaEnPos(i);
            cartas.insert(i, carta2);
            cartas.eliminaEnPos(posicionAleatoria);
            cartas.insert(posicionAleatoria, carta1);
        } 
        iterador=cartas.iterator();
    }

    public Carta CartaSiguiente(){
        Carta carta=null;
        if(!iterador.hasNext()){
            System.out.println("No hay mas cartas");
        }else{
            carta=iterador.next();
            numDisponibles--;
        }
        return carta;
    }


    public Lista<Carta> darCartas(int num){
        if(num>numCARTAS){
            System.out.println("No hay tantas cartas en la baraja");
        }else if(numDisponibles<num){
            System.out.println("No hay suficientes cartas en la baraja");
        }else{
            Lista<Carta> porDar = new Lista<Carta>();
            for(int i=0;i<num;i++){
                porDar.add(CartaSiguiente());
                return porDar;
            }
        }
        return null;

    }

}
