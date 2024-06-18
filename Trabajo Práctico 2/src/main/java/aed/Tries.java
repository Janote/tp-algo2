package aed;
import java.util.*;

public class Tries<T> {
    private Nodo raiz;
    private int cantidad;

    /* Invariante de representacion 
    Si raiz es null, el trie esta vacio.
    Si raiz es distinto de null, el trie tiene al menos un nodo con esPalabra true (es decir, que tiene algun valor guardado)
    Los nodos tienen un solo padre, salvo la raiz que ancestro es null. 
    No hay nodos inutiles, es decir, si un nodo no tiene valor (esPalabra false y valor=null) debe tener alguna de las posiciones del array hijos distinta de null. 
    Un nodo con valor (esPalabra true) puede tener todas las posiciones del array hijos en null.
    Raiz tiene significado null y ancestro null.
    */

    private class Nodo {
        /* Invariante de representacion
         Si esPalabra=true entonces valor!= null.
         Si esPalabra=false entonces valor=null.
         */
        
        /*Descripcion de cada nodo.
         Cada nodo, tiene:
         significado: guarda su respectiva caracter ASCII.
         esPalabra: determina si ese nodo es la ultima letra de la clave.
         valor: se guarda el valor asociado a la clave generada por todos los anteriores significados.
         siguientes: es un Arraylist donde se guardan hijos de acuerdo al codigo ASCII.
         ancestro : Utilizamos ancestro para decir quien es el 'padre' de nuestro nodo. 
         */

        private Character significado;
        private boolean esPalabra;
        private T valor;
        private Nodo ancestro;
        private ArrayList<Nodo> siguientes = new ArrayList<>(256); // 256 posibilidades distintas (por codigo ASCII)

        private Nodo(Character significado , Nodo ancestro) {

            this.significado = significado;
            esPalabra = false; // setea false en cada nodo creado por defecto.
            this.ancestro = ancestro;  
            valor = null;
            for (int i = 0; i < 256; i++) { // queda definida una lista de 256 elementos que estan todos en null
                this.siguientes.add(null);  
            }
        }

    }



    // Complejidad: O(1)

    public Tries() {
        Nodo nuevo_nodo = new Nodo(null, null);
        raiz = nuevo_nodo;
        cantidad = 0;
    }


    // Complejidad: 0(k);  k == largo de la clave

    public void insertar(String clave, T valor) {
        insertarAux(0, clave, valor, raiz);
        cantidad += 1;
    }

    private void insertarAux(int k, String clave, T valor, Nodo puntero) { 
        if (k == clave.length()) {
            puntero.esPalabra = true; // La ultima letra de la palabra sera asignada con 'True'
            puntero.valor = valor; // como es el ultimo caracter de la palabra, le asigno el valor
            return;
        }
        else if (puntero.siguientes.get(((int) clave.charAt(k))) == null && puntero == raiz) {
            Nodo nuevo_nodo = new Nodo(clave.charAt(k),null);
            puntero.siguientes.set((int) clave.charAt(k), nuevo_nodo);
            // No digo nada sobre ancestro, porque cuando creo el nodo ya  es null por defecto.
            insertarAux(k + 1, clave, valor, nuevo_nodo); // hago el paso recursivo, actualizando el caracter.
        }
        else if (puntero.siguientes.get(((int) clave.charAt(k))) == null) // El nodo no fue creado aun, toca agregarlo.
        // donde en el constructor le asigno el caracter.
        {
            Nodo nuevo_nodo = new Nodo(clave.charAt(k), puntero); 
            puntero.siguientes.set((int) clave.charAt(k), nuevo_nodo); // en la posicion del codigo ASCII de mi caracter
            insertarAux(k + 1, clave, valor, nuevo_nodo); // hago el paso recursivo, actualizando el caracter.
        }
        else { // ya existe ese caracter , no hagas nada, solo actualiza el puntero.
            insertarAux(k + 1, clave, valor, puntero.siguientes.get(((int) clave.charAt(k))));
        }
    }



    // Complejidad = O(k) k=largo de la clave

    public boolean esClave(String clave) // Identifica si una palabra es una clave
    {
        return esClaveAux(0, clave, raiz);
    }

    private boolean esClaveAux(int k, String clave, Nodo puntero) {
        if (k == clave.length() && puntero.esPalabra == true) { 
            return true;
        }
        else if (k == clave.length() && puntero.esPalabra == false) { // no es clave, es 'subclave'
            return false;
        }
        else if (puntero.siguientes.get(((int) clave.charAt(k))) == null) { // el caracter no pertenece
            return false;
        }
        return esClaveAux(k + 1, clave, puntero.siguientes.get(((int) clave.charAt(k))));
    }



    // Complejidad O(k) k=largo de la clave

    public T darValor(String clave) // dada una clave, devuelvo su valor.
    {
        return darValorAux(0, clave, raiz);
    }

    private T darValorAux(int k, String clave, Nodo puntero) {
        if (k == clave.length() && puntero.esPalabra == true) {
            return puntero.valor;
        }
        return darValorAux(k + 1, clave, puntero.siguientes.get(((int) clave.charAt(k))));
    }



    // O(1) 

    public int contador_de_claves() {
        return cantidad;
    }



    // O(k) k=largo de la palabra

    public void eliminar(String palabra) {     
        Nodo ultimo = iraUltimoCaracterClave(palabra) ;
        ultimo.esPalabra = false; // ya no es mas palabra.
        eliminarAux(ultimo , palabra, palabra.length() - 1);
        cantidad -= 1;
    }
         
    private void eliminarAux(Nodo puntero, String palabra, int indice) { 
        if(puntero == null ){
            return ; 
        }
        if(cantidadDehijos(puntero) > 0 || puntero.esPalabra ){
            return ;
        }
        else if(puntero.ancestro != null ) {
            puntero.ancestro.siguientes.set(((int)palabra.charAt(indice)), null) ; 
            eliminarAux(puntero.ancestro, palabra, indice - 1 ) ;
        }
    }

    private int cantidadDehijos(Nodo puntero) {
        int contador = 0;
        for (int i = 0; i < puntero.siguientes.size(); i++) {
            if (puntero.siguientes.get(i) != null) {
                contador++;
            }
        }
        return contador;
    }

    // Lo que hace esta funcion es guardarse el nodo del ultimo caracter. O(k) k=largo clave
    private Nodo iraUltimoCaracterClave(String clave){
        return iraUltimoCaracterClaveAux(raiz, clave, 0);
    }

    private Nodo iraUltimoCaracterClaveAux(Nodo puntero, String palabra, int k) // te devuelvo la referencia al ultimo nodo.
    {
        if (k == palabra.length()) {
            return puntero;
        }
         if(puntero.siguientes.get(((int)palabra.charAt(k))) == null){
            return null; // esto es si la palabra/ clave no existia
        }
        else{
            return iraUltimoCaracterClaveAux(puntero.siguientes.get(((int)palabra.charAt(k))), palabra, k + 1); 
        }
    }
    

    
    // O(sumatoria k con k largo de claves en el trie) 

    public String[] listar() {
        ArrayList<String> lista = new ArrayList<>(contador_de_claves());
        String[] res = new String[contador_de_claves()];
        listarAuxiliar(raiz, "", lista); 
        for(int i=0;i<contador_de_claves();i++){
            res[i] = lista.get(i);
        }
        return res;
    }

    private void listarAuxiliar(Nodo puntero, String palabra, ArrayList<String> lista) {
        if (puntero == null) { // O(1)
            return; // O(1)
        }
        if (puntero.esPalabra) { // O(1)
            lista.add(palabra); // O(1)
        }
        for (int i = 0; i < puntero.siguientes.size(); i++) { // O(1) pues O(256)
            if (puntero.siguientes.get(i) != null) { // O(1)
                listarAuxiliar(puntero.siguientes.get(i), palabra + puntero.siguientes.get(i).significado, lista);
            }
        }
    } // la recursion se va a ejecutar una vez por cada posicion no nula en el array list 
}
