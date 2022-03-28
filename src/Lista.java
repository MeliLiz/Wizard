package src.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;
// iterador
//next

public class Lista<T> implements Collection<T> {

    // Clase Nodo
    private class Nodo {
        public T elemento;
        public Nodo anterior;
        public Nodo siguiente;

        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    // Iterador
    private class Iterador implements IteradorLista<T> {
        public Nodo anterior;
        public Nodo siguiente;

        public Iterador() {
            siguiente = cabeza;
        }

        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T regresar = siguiente.elemento;

            this.anterior = this.siguiente;
            this.siguiente = siguiente.siguiente;
            return regresar;

        }

        @Override
        public boolean hasPrevious() {
            return anterior != null;
        }

        @Override
        public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            T regresar = anterior.elemento;

            this.siguiente = this.anterior;
            this.anterior = anterior.anterior;
            return regresar;

        }

        @Override
        public void start() {
            this.anterior = null;
            this.siguiente = cabeza;
        }

        @Override
        public void end() {
            this.anterior = ultimo;
            this.siguiente = null;
        }

    }

    private Nodo cabeza;
    private Nodo ultimo;
    private int longi;

    /**
     * Agrega un elemento a la lista.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void add(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento es null");
        }
        agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento es null");
        }
        Nodo nuevo = new Nodo(elemento);
        if (cabeza == null) {
            this.cabeza = this.ultimo = nuevo;
        } else {
            this.cabeza.anterior = nuevo;
            nuevo.siguiente = this.cabeza;
            this.cabeza = nuevo;
        }
        longi++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento es null");
        }
        Nodo nuevo = new Nodo(elemento);
        if (cabeza == null) {
            this.cabeza = this.ultimo = nuevo;
        } else {
            this.ultimo.siguiente = nuevo;
            nuevo.anterior = this.ultimo;
            this.ultimo = nuevo;
        }
        longi++;
    }

    private Nodo buscaElemento(T elemento) {
        Nodo n = cabeza;
        while (n != null) {
            if (elemento.equals(n.elemento)) {
                return n;
            }
            n = n.siguiente;
        }
        return null;
    }

    /**
     * Elimina un elemento de la lista.
     * 
     * @param elemento el elemento a eliminar.
     */
    public boolean delete(T elemento) {
        if (elemento == null)
            return false;
        Nodo n = buscaElemento(elemento);
        if (n == null) {
            return false;
        }
        if (longi == 1) {
            empty();
            return true;
        }
        if (n == cabeza) {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
            longi--;
            return true;
        }
        if (n == ultimo) {
            ultimo = ultimo.anterior;
            ultimo.siguiente = null;
            longi--;
            return true;
        }
        n.siguiente.anterior = n.anterior;
        n.anterior.siguiente = n.siguiente;
        longi--;
        return true;
    }

    /**
     * Regresa un elemento de la lista. (Ultimo)
     * y lo elimina.
     * 
     * @return El elemento a sacar.
     */
    public T pop() {
        T valor = ultimo.elemento;
        ultimo = ultimo.anterior;
        ultimo.siguiente = null;
        longi--;
        return valor;
    }

    /**
     * Regresa el número de elementos en la lista.
     * 
     * @return el número de elementos en la lista.
     */
    public int size() {
        return longi;
    }

    /**
     * Nos dice si un elemento está contenido en la lista.
     * 
     * @param elemento el elemento que queremos verificar si está contenido en
     *                 la lista.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean contains(T elemento) {
        if (buscaElemento(elemento) == null) {
            return false;
        }
        return true;
    }

    /**
     * Vacía la lista.
     * 
     */
    public void empty() {
        cabeza = ultimo = null;
        longi = 0;
    }

    /**
     * Nos dice si la lista es vacía.
     * 
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean isEmpty() {
        return longi == 0;
    }

    /**
     * Regresa una copia de la lista.
     * 
     * @return una copia de la lista.
     */
    public Lista<T> clone() {
        Lista<T> nueva = new Lista<T>();
        Nodo nodo = cabeza;
        while (nodo != null) {
            nueva.add(nodo.elemento);
            nodo = nodo.siguiente;
        }
        return nueva;
    }

    /**
     * Nos dice si la coleccion es igual a otra coleccion recibida.
     * 
     * @param coleccion la coleccion con el que hay que comparar.
     * @return <tt>true</tt> si la coleccion es igual a la coleccion recibido
     *         <tt>false</tt> en otro caso.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lista)) {
            System.out.println("El ejemplar no es una lista");
            return false;
        }
        @SuppressWarnings("unchecked")
        Lista<T> lista2 = (Lista<T>) o;
        if (this.longi != lista2.longi) {
            System.out.println("Los tamaños no son iguales.");
            return false;
        }
        if (this.isEmpty() && lista2.isEmpty()) {
            return true;
        }
        if ((this.isEmpty() && !lista2.isEmpty()) || (lista2.isEmpty() && !this.isEmpty())) {
            return false;
        }
        Nodo aux1 = this.cabeza;
        Nodo aux2 = lista2.cabeza;
        while (aux1 != null && aux2 != null) {
            if (!aux1.elemento.equals(aux2.elemento)) {
                return false;
            }
            aux1 = aux1.siguiente;
            aux2 = aux2.siguiente;
        }
        return true;
    }

    /**
     * Metodo que invierte el orden de la lista .
     * 
     */
    // Este metodo es de O(n) porque va recorriendo toda la lista cambiando las
    // referencias al siguiente y anterior de cada nodo
    // Memoria O(1) porque la memoria que usamos no depende de la cantidad de datos
    // en la lista
    public void reverse() {
        if (!isEmpty() && longi != 1) {
            // Nodos auxiliares
            Nodo aux = cabeza;
            Nodo aux2 = cabeza.anterior;
            // Cambiamos la referencia de los nodos
            while (aux.siguiente != null) {
                aux.anterior = aux.siguiente;
                aux.siguiente = aux2;
                aux2 = aux;
                aux = aux.anterior;
            }
            aux.anterior = null;
            aux.siguiente = aux2;
            aux2 = cabeza;
            // Cambiamos referencia de cabeza y ultimo
            cabeza = aux;
            ultimo = aux2;
        }
    }

    /**
     * Regresa una representacion en cadena de la coleccion.
     * 
     * @return una representacion en cadena de la coleccion.
     *         a -> b -> c -> d
     */
    public String toString() {
        String cadena = "";
        if (isEmpty()) {
            return "";
        } else {
            Nodo actual = cabeza; // Nodo para iterar la lista
            while (actual.siguiente != null) {
                cadena += actual.elemento + ", ";
                actual = actual.siguiente;
            }
            cadena += actual.elemento;
        }
        return cadena;
    }

    /**
     * Junta dos listas siempre y cuando sean del mismo tipo.
     * El resultado queda en la lista original y la lista de parametro queda vacia
     * 
     * @throws IllegalArgumentException Si la lista dada es vacia
     */
    public void append(Lista<T> lista) {
        if (lista.isEmpty()) {
            lista.cabeza = cabeza;
            lista.ultimo = ultimo;
            lista.longi = longi;
            lista.empty();
        } else if (isEmpty()) { // Hace que la lista sea igual a la lista dada
            cabeza = lista.cabeza;
            ultimo = lista.ultimo;
            longi = lista.longi;
            lista.empty();
        } else if (cabeza.elemento.getClass().equals(lista.cabeza.elemento.getClass())) { // Verifica que las listas
                                                                                          // sean del mismo tipo y las
                                                                                          // une
            ultimo.siguiente = lista.cabeza;
            lista.cabeza.anterior = ultimo;
            lista.cabeza = cabeza;
            ultimo = lista.ultimo;
            longi = lista.longi = longi + lista.longi;
            lista.empty();
        }
        return;
    }

    /**
     * Regresa un entero con la posicion del elemento.
     * Solo nos importara la primera aparicion del elemento
     * Empieza a contar desde 0.
     * 
     * @param elemento elemento del cual queremos conocer la posicion.
     * @return entero con la posicion del elemento
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public int indexOf(T elemento) {
        int contador = 0;
        Nodo n = cabeza; // Nodo para iterar la lista
        while (n != null) {
            if (elemento.equals(n.elemento)) {
                return contador;
            }
            n = n.siguiente;
            contador++;
        }
        throw new IllegalArgumentException("****El elemento buscado no esta en la lista****");

    }

    /**
     * Inserta un elemento en un indice explicito.
     *
     * Si el indice es menor que cero, el elemento se agrega al inicio de la
     * lista. Si el indice es mayor o igual que el numero de elementos en la
     * lista, el elemento se agrega al fina de la misma. En otro caso, despues
     * de mandar llamar el metodo, el elemento tendra el indice que se
     * especifica en la lista.
     * 
     * @param i        el indice donde insertar el elemento. Si es menor que 0 el
     *                 elemento se agrega al inicio, y si es mayor o igual que el
     *                 numero
     *                 de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void insert(int i, T elemento) {
        if (i <= 0) {
            agregaInicio(elemento);
        } else if (i >= longi) {
            agregaFinal(elemento);
        } else {
            Nodo nodo = new Nodo(elemento);// El nuevo nodo
            Nodo actual = cabeza;// Nodo para iterar la lista
            for (int j = 0; j < (i - 1); j++) {
                actual = actual.siguiente;
            }
            nodo.anterior = actual;
            nodo.siguiente = actual.siguiente;
            actual.siguiente.anterior = nodo;
            actual.siguiente = nodo;
            longi++;
        }
        return;
    }

    /**
     * Elimina el elemento que esta en la posicion dada
     * 
     * @param i
     */
    public T eliminaEnPos(int i) {
        T regreso;
        if (i <= 0) {
            regreso = cabeza.elemento;
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        } else if (i >= longi) {
            regreso = pop();
        } else {
            Nodo actual = cabeza;// Nodo para iterar la lista
            int contador = 0;
            while (contador != i) {
                actual = actual.siguiente;
                contador++;
            }
            regreso = actual.elemento;
            actual.anterior.siguiente = actual.siguiente;
            actual.siguiente.anterior = actual.anterior;
        }
        longi--;
        return regreso;
    }

    /**
     * Regresa el elemento que esta en la posicion dada
     * 
     * @param i
     */
    public T elementoEnPos(int i) {
        T regreso;
        if (i <= 0) {
            regreso = cabeza.elemento;
        } else if (i >= longi) {
            regreso = ultimo.elemento;
        } else {
            Nodo actual = cabeza;// Nodo para iterar la lista
            int contador = 0;
            while (contador != i) {
                actual = actual.siguiente;
                contador++;
            }
            regreso = actual.elemento;
        }
        return regreso;
    }

    /**
     * Metodo para obtener una lista alternando los elementos de 2 listas
     * El resultado queda en la lista original y la lista 2 queda vacia.
     * 
     * @param lista
     */
    // Este metodo es de complejidad O(n) u O(m) porque las veces que se repite el
    // ciclo while depende de la longitud de alguuna de las dos listas que manejamos
    // (la que tiene menor longitud). De esta manera la complejidad pertenece
    // tambien al orden de O(m+n).
    // La memoria es de O(1) porque la memoria que utilizamos es constante, no
    // depende de la cantidad de datos de las listas.
    public void mezclaAlternada(Lista<T> lista) {
        if (isEmpty()) { // Si nuestra lista es vacia solo cambia sus referencias
            cabeza = lista.cabeza;
            ultimo = lista.ultimo;
            longi = lista.longi;
            lista.empty();
        } else if (lista.isEmpty()) {// Si la lista dada es vacia, solo pone sus referencias igual que nuestra lista
            lista.cabeza = cabeza;
            lista.ultimo = ultimo;
            lista.longi = longi;
            lista.empty();
        } else if (longi == 1) {// Si nuestra lista solo tiene un nodo, solo juntamos la otra lista
            append(lista);
        } else if (longi <= lista.longi) {// Si la nuestra lista tiene longitud menor o igual a la de la lista dada
                                          // hacemos el ciclo dependiendo de la longitud de nuestra lista
            Nodo actual = cabeza;// Nodo para iterar la lista 1
            Nodo actual2 = lista.cabeza;// Nodo para iterar la lista 2
            // Cambiar referencias de los nodos de ambas listas
            for (int i = 0; i < (longi - 1); i++) {
                actual.siguiente.anterior = actual2;
                actual2.anterior = actual;
                actual2 = actual2.siguiente;
                actual2.anterior.siguiente = actual.siguiente;
                actual.siguiente = actual2.anterior;
                actual = actual.siguiente.siguiente;
            }
            actual.siguiente = actual2;
            actual2.anterior = actual;
            // Cambiar referencias de la cabeza y ultimo
            ultimo = lista.ultimo;
            // Actualizar longitud
            longi = lista.longi = longi + lista.longi;
            lista.empty();
        } else if (lista.longi < longi) {// Si nuestra lista tiene mayor longitud que la lilsta dada, el ciclo for
                                         // depende de la longitud de la lista dada
            Nodo actual = cabeza;// Nodo para iterar la lista 1
            Nodo actual2 = lista.cabeza;// Nodo para iterar la lista 2
            for (int i = 0; i < (lista.longi - 1); i++) {
                actual.siguiente.anterior = actual2;
                actual2.anterior = actual;
                actual2 = actual2.siguiente;
                actual2.anterior.siguiente = actual.siguiente;
                actual.siguiente = actual2.anterior;
                actual = actual.siguiente.siguiente;
            }
            actual2.anterior = actual;
            actual2.siguiente = actual.siguiente;
            actual2.siguiente.anterior = actual2;
            actual.siguiente = actual2;
            // actualizar longitud
            longi = longi + lista.longi;
            lista.empty();
        }
        return;
    }

    /**
     * Metodo para hacer la lista circular
     */
    public void listaCircular() {
        cabeza.anterior = ultimo;
        ultimo.siguiente = cabeza;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * 
     * @return un iterador para recorrer la lista en una dirección.
     */
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * 
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
