package aed;
// Invariante de representacion
// El siguiente al ultimo debe ser null, el anterior al primero debe ser null. 
// Si un nodo n no es el ultimo, entonces debe existir un unico nodo m tal que el siguiente de n es m.
// Al contar los nodos desde el primero al ultimo deberia dar el size. 
public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int size;


    private class Nodo {
        T valor;
        Nodo sig;
        Nodo ant;

        Nodo(T v) {
            valor = v; 
            sig = null; 
            ant = null;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        size = 0;
    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (size == 0) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.sig = primero;
            primero.ant = nuevo;
            primero = nuevo;
        }
        size += 1;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (size == 0) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.ant = ultimo;
            ultimo.sig = nuevo;
            ultimo = nuevo;
        }
        size += 1;
    }

    public T obtener(int i) {
        int contador = 0;
        Nodo actual = primero;
        while (contador < i){
            actual = actual.sig;
            contador += 1;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        Nodo actual = primero;
        Nodo prev = null;
        for (int j = 0; j < i; j++) {
            prev = actual;
            actual = actual.sig;
            }
        if (i == 0) {
            primero = actual.sig;
        } else {
            prev.sig = actual.sig;
        }
        size -= 1;
    }

    public void modificarPosicion(int indice, T elem) {
        int contador = 0;
        Nodo nuevo = new Nodo(elem);
        Nodo actual = primero;
        Nodo last = ultimo;
        if (indice == 0 || indice == size - 1){
            if (indice == 0){
                primero = nuevo;
                primero.sig = actual.sig;
                actual.sig.ant = nuevo;
            } else {
                ultimo = nuevo;
                ultimo.ant = last.ant;
                last.ant.sig = nuevo;
            }
        } else {
            while (contador < indice){
                actual = actual.sig;
                contador += 1;
            }
            actual.ant.sig = nuevo;
            actual.sig.ant = nuevo;
            nuevo.sig = actual.sig;
            nuevo.ant = actual.ant;
        }
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<>();
        Nodo actual = primero;
        while (actual != null) {
            nuevaLista.agregarAtras(actual.valor);
            actual = actual.sig;
        }
        nuevaLista.size = size;
        return nuevaLista;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo actual = lista.primero;
        while (actual != null) {
            this.agregarAtras(actual.valor);
            actual = actual.sig;
        }
    }
    
    @Override
    public String toString() {
        Nodo actual = primero;
        String lista = "[";
        while (actual != null) {
            lista = lista + actual.valor.toString();
            if (actual != ultimo) {
                lista = lista + ", ";
            } else {
                lista = lista + "]";
            }
            actual = actual.sig;
        }
        return lista;
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }

    private class ListaIterador implements Iterador<T> {
    	int dedito;
        Nodo actual;

        public ListaIterador(){
            dedito = 0;
            actual = primero;
        }

        public boolean haySiguiente() {
	        return 0 <= dedito && dedito < size;
        }
        
        public boolean hayAnterior() {
	        return 0 < dedito && dedito <= size;
        }

        public T siguiente() {
            T valor = actual.valor;
            dedito = dedito + 1;
            actual = actual.sig;
            return valor;
        }
        

        public T anterior() {
            T valor;
            if (actual == null){
                valor = ultimo.valor;
                actual = ultimo;
            } else {
                valor = actual.ant.valor;
                actual = actual.ant;
            }
            dedito = dedito - 1;
            return valor;
        }
    }

}
