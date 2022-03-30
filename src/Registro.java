package src.edd;

import src.edd.Jugador;

public class Registro {
    Cola<Jugador> ganadoresPorRonda;
    Cola<Cola<Jugador>> ganadoresDeTrucos;
    Cola<Carta[][]> cartasTiradasJ;

    public Registro(){
        ganadoresDeTrucos=new Cola<Lista<Jugador>>();
        ganadoresPorRonda=new Cola<Jugador>();
        cartasTiradasJ=new Cola<Carta[][]>();
    }

    public Cola<Jugador> getGanadoresPorRonda(){
        return ganadoresPorRonda;
    }

    public Cola<Cola<Jugador>> getGanadoresDeTrucos(){
        return ganadoresDeTrucos;
    }

    public Cola<Carta[][]> getCartasTiradas(){
        return cartasTiradasJ;
    }
}
