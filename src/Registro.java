package src.edd;

import src.edd.Jugador;

public class Registro {
    //Lista<Jugador> ganadoresPorRonda;
    Lista<Cola<Jugador>> ganadoresDeTrucos;
    Lista<Carta[][]> cartasTiradasJ;

    public Registro(){
        ganadoresDeTrucos=new Lista<Cola<Jugador>>();
        //ganadoresPorRonda=new Lista<Jugador>();
        cartasTiradasJ=new Lista<Carta[][]>();
    }

    //public Lista<Jugador> getGanadoresPorRonda(){
      
      //  return ganadoresPorRonda;
    //}

    public Lista<Cola<Jugador>> getGanadoresDeTrucos(){
        return ganadoresDeTrucos;
    }

    public Lista<Carta[][]> getCartasTiradas(){
        return cartasTiradasJ;
    }
}
