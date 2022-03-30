package src.edd;
public class Jugador {
    private String nombre;//nombre del jugador
    private Lista<Carta> cartas;//Lista de cartas que tiene
    private Cola<Carta> cartasTiradas;//Registro de cartas tiradas
    private Cola<Integer> apuestas;//registro de apuestas hechas
    private int apuesta;//el numero que apuesta
    private int ganadas;//numero de trucos ganados
    private int numJugador;//El numero de jugador
    private int puntaje;//El numero de puntos que lleva hasta ahora

    
    public Jugador(String nombre, int numJugador){
        this.nombre=nombre;
        cartas = new Lista<Carta>();
        apuesta=0;
        ganadas=0;
        this.numJugador=numJugador;
        cartasTiradas=new Cola<Carta>();
        apuestas=new Cola<Integer>();
    }

    public String getNombre(){
        return nombre;
    }

    public Lista<Carta> getCartas(){
        return cartas;
    }

    public Cola<Carta> getCartasTiradas(){
        return cartasTiradas;
    }

    public int getApuesta(){
        return apuesta;
    }

    public int getGanadas(){
        return ganadas;
    }

    public int getNumJugador(){
        return numJugador;
    }

    public int getPuntaje(){
        return puntaje;
    }

    public void setCartas(Lista<Carta> cartas){
        this.cartas=cartas;
    }

    public void setApuesta(int apuesta){
        this.apuesta=apuesta;
    }

    public void setGanadas(int ganadas){
        this.ganadas=ganadas;
    }

    public void setPuntaje(int puntaje){
        this.puntaje=puntaje;
    }

    public void sumaPuntaje(int porSumar){
        this.puntaje+=porSumar;
    }

    public void sumaGanadas(int porSumar){
        this.ganadas+=porSumar;
    }

    public String toString(){
        return nombre;
    }
}
