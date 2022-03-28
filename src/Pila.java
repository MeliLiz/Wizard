package src.edd;

public class Pila<T> extends PushPop<T>{
    
    
    // Agregar al inicio.
    public void push(T elemento){
        if(elemento == null){
            throw new IllegalArgumentException("");
        }
        Nodo aux = new Nodo(elemento);
        if(isEmpty()){
            this.cabeza=ultimo=aux;
            longi++;
            return ;
        }
        aux.siguiente = cabeza;
        cabeza = aux;
        longi ++;

    }

    /**
     * Regresa un clon de la estructura.
     * 
     * @return un clon de la estructura.
     */
    public Pila<T> clone(){
        /*Pila<T> nueva = new Pila<T>();
        if (this.isEmpty()) {
            return nueva;
        }
        nueva.push(this.cabeza.elemento);
        Nodo n = this.cabeza;
        while (n.siguiente != null) {
           nueva.push(n.siguiente.elemento);
           n = n.siguiente;
        }
        return nueva;*/
        Pila<T> nueva = new Pila<T>();
        Pila<T> auxiliar = new Pila<T>();
        if (this.isEmpty()) {
            return nueva;
        }
        auxiliar.push(this.cabeza.elemento);
        Nodo n = this.cabeza;
        while (n.siguiente != null) {
           auxiliar.push(n.siguiente.elemento);
           n = n.siguiente;
        }
        nueva.push(auxiliar.cabeza.elemento);
        n = auxiliar.cabeza;
        while (n.siguiente != null) {
           nueva.push(n.siguiente.elemento);
           n = n.siguiente;
        }
        return nueva;

    }

    public String toString(){
        /*if (this.isEmpty()) {
            return "";
        }
        String regreso = this.cabeza.elemento.toString();
        Nodo n = this.cabeza;
        while (n.siguiente != null) {
            regreso += ", " + n.siguiente.elemento.toString();
            n = n.siguiente;
        }
        return regreso;*/
        if (this.isEmpty()) {
            return "";
        }
        Lista<T> lista = new Lista<T>();
        lista.add(this.cabeza.elemento);
        Nodo n=this.cabeza;
        while (n.siguiente != null) {
            lista.agregaFinal(n.siguiente.elemento);
            n = n.siguiente;
        } 
        lista.reverse();
        return lista.toString();
    }

    }



