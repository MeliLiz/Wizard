package src.edd;
public class Jugador {
    private String nombre;
    private Lista<Carta> cartas;
    private int apuesta;
    private int ganadas;
    
    public Jugador(String nombre){
        this.nombre=nombre;
        cartas = new Lista<Carta>();
        apuesta=0;
        ganadas=0;
    }

    public String getNombre(){
        return nombre;
    }

    public Lista<Carta> getCartas(){
        return cartas;
    }

    public int getApuesta(){
        return apuesta;
    }

    public int getGanadas(){
        return ganadas;
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

    public String toString(){
        return nombre;
    }
}
