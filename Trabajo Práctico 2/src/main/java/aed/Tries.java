package aed;

import java.util.*;

public class Tries<T> {
    Nodo raiz;
    int tamaño;

    private class Nodo {

        Character significado;
        boolean esPalabra;
        T valor;
        

        ArrayList<Nodo> siguientes = new ArrayList<>(); // 256 posibilidades distintas (por codigo ASCII)
        // en cada una de las posiciones voy a poner el codigo ASCII que le corresponde
        // ej: En la posicion 64 del array, le correspondara a la direccion de memoria
        // del nodo con el significado 'A' porque su codigo ASCII es 65.

        ArrayList<Nodo> ancestros = new ArrayList<>();

        private Nodo(Character significado) {
            this.significado = significado;
            esPalabra = false; // seteo false en cada nodo creado por defecto.
            this.siguientes = new ArrayList<>(256);
            this.ancestros = new ArrayList<>(256);
            this.valor = null; // creo por defecto el nodo sin valor , es decir que
            for (int i = 0; i < 256; i++) { // O(256) == O(1) xq existe c tal que 256 <= 1 * c
                siguientes.add(null);
                ancestros.add(null);
            }
        }

    }

    public Tries() {
        Nodo nuevo_nodo = new Nodo(null);
        raiz = nuevo_nodo;

    }

    // uso una funcion auxiliar para que cada caracter del string se anada.

    private void insertar(String clave, T valor) {
        insertarAux(0, clave, valor, raiz);
    }

    private void insertarAux(int k, String clave, T valor, Nodo puntero) { // Inserta en el trie, una clave, con su
                                                                           // respectivo valor.

        if (k == clave.length()) {

            puntero.esPalabra = true; // La ultima letra de la palabra sera asignada con 'True'
            puntero.valor = valor; // como es el ultimo caracter de la palabra, le asigno el valor
            return;

        }
        // este if solo hago porque no quiero crearle `ancestros` a la raiz.

        else if (puntero.siguientes.get(((int) clave.charAt(k))) == null && puntero == raiz) {
            Nodo nuevo_nodo = new Nodo(clave.charAt(k));

            puntero.siguientes.set((int) clave.charAt(k), nuevo_nodo);

            insertarAux(k + 1, clave, valor, nuevo_nodo); // hago el paso recursivo, actualizando el caracter.
        }

        else if (puntero.siguientes.get(((int) clave.charAt(k))) == null && puntero != raiz) // si en este caso no esta
                                                                                             // creada , creo el nodo,
        // donde en el constructor le asigno el caracter.
        {
            Nodo nuevo_nodo = new Nodo(clave.charAt(k)); // creo el nuevo nodo

            puntero.siguientes.set((int) clave.charAt(k), nuevo_nodo); // en la posicion del codigo ASCII de mi caracter
                                                                       // , le asigno el nodo.

            nuevo_nodo.ancestros.add(((int) puntero.significado), puntero); // anado

            insertarAux(k + 1, clave, valor, nuevo_nodo); // hago el paso recursivo, actualizando el caracter.
        }

        else { // ya existe ese caracter , no hagas nada, solo actualiza el puntero.

            insertarAux(k + 1, clave, valor, puntero.siguientes.get(((int) clave.charAt(k))));

        }
    }

    private boolean esClave(String clave) // Identifica si una palabra es una clave
    {
        return esClaveAux(0, clave, raiz);

    }

    private boolean esClaveAux(int k, String clave, Nodo puntero) {
        if (k == clave.length() && puntero.esPalabra == true) {
            return true;
        }

        if (k == clave.length() && puntero.esPalabra == false) {
            return false;
        }
        if (puntero.siguientes.get(((int) clave.charAt(k))) == null) {
            return false;
        }

        return esClaveAux(k + 1, clave, puntero.siguientes.get(((int) clave.charAt(k))));

 
    }

    // requiere { esClave(clave)}
    private T darValor(String clave) // dada una clave(valida), devuelvo su valor.
    {
        return darValorAux(0, clave, raiz);

    }

    private T darValorAux(int k, String clave, Nodo puntero) {
        if (k == clave.length() && puntero.esPalabra == true) {
            return puntero.valor;
        }
        return darValorAux(k + 1, clave, puntero.siguientes.get(((int) clave.charAt(k))));

    }

    /*
     * // Esto cuenta la cantidad de nodos que hay, la comento porque me parece que es inutil, pero la dejo por si nos sirve dsp.
     * 
     * private int cantidad_de_elementos() { // cuenta nodos
     * return cantidad_de_elementos_aux(raiz);
     * 
     * }
     * 
     * private int cantidad_de_elementos_aux(Nodo puntero) {
     * int contador = 0;
     * 
     * for (int i = 0; i < puntero.siguientes.size(); i++) {
     * 
     * if (puntero.siguientes.get(i) != null) {
     * contador = contador + 1 +
     * cantidad_de_elementos_aux(puntero.siguientes.get(i));
     * }
     * 
     * }
     * 
     * return contador;
     * }
     */


    // Devuelve la cantidad de claves que hay en esta instancia.

    private int contador_de_claves() {
        return contador_de_clavesAux(raiz);
    }

    private int contador_de_clavesAux(Nodo puntero) {
        int contador = 0;

        for (int i = 0; i < puntero.siguientes.size(); i++) {

            if (puntero.siguientes.get(i) != null && puntero.siguientes.get(i).esPalabra == true) {
                contador = contador + 1 + contador_de_clavesAux(puntero.siguientes.get(i));

            }

            else if (puntero.siguientes.get(i) != null) {
                contador = contador + contador_de_clavesAux(puntero.siguientes.get(i));

            }

        }
        
        return contador; 
    }




    // dsp la termino, todo esto es para eliminar




    private void eliminar(String palabra) {
        if (this.esClave(palabra) == false) {

            System.out.println("No pertenece al trie la palabra");

        }

        eliminarAux(raiz, palabra);

    }

    private void eliminarAux(Nodo puntero, String palabra) {
        // Separo en casos: 1ero veo de que si uno de sus elementos no pertenezca al
        // trie

        Nodo ultimo_Nodo = iralUltimoNodo(raiz, palabra, 0);

    }

    // si el ultimo nodo tiene hijos, no borrar el subarbolito
    // En otor caso, ir borrando de abajo hacia arriba , y a la primera en que tenga
    // un hijo, no borro mas.

    private int cantidadDehijos(Nodo puntero) {
        int contador = 0;
        for (int i = 0; i < puntero.siguientes.size(); i++) {
            if (puntero.siguientes.get(i) != null) {
                contador++;
            }
        }
        return contador;

    }

    private Nodo iralUltimoNodo(Nodo puntero, String palabra, int k) // te devuelvo la referencia del ultimo nodo.
    {
        Nodo temporal; // este lo uso para guardar el nodo para devolver o para la prox instrucion.
        if (k == palabra.length()) {
            return puntero;

        }

        return iralUltimoNodo(puntero.siguientes.get(((int) palabra.charAt(k))), palabra, k + 1);

    }

    // en cada posicion del array `lista`, es una clave, primero hago esto (anado / recupero todas las claves) y luego hago la comparacion lexi; 
    // voy a hacer tantas iteraciones como claves tenga

    private ArrayList listar() // Esta tieene complejidad largo palabras + total de palabras
    {

        ArrayList<String> lista = new ArrayList<>(contador_de_claves());

        listarAuxiliar(raiz, ""  , lista);


        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
        return lista; 


    }

    private void listarAuxiliar(Nodo puntero, String palabra , ArrayList<String> lista)
    {
        
        if(puntero == null)
        {
            return ; 
        }

        else if(puntero.esPalabra)
        {
            lista.add(palabra) ; 

        }



       for (int i = 0; i < puntero.siguientes.size(); i++) {
        

            if ( puntero.siguientes.get(i) != null && puntero.siguientes.get(i).esPalabra == false ) {

                String nuevaPalabra = palabra + puntero.siguientes.get(i).significado ; 
                 
                listarAuxiliar(puntero.siguientes.get(i), nuevaPalabra, lista) ;   
            }

       }

    }





    public static void main(String[] args) {
        Tries<String> prueba = new Tries<>();

        prueba.insertar("clavi", "valor");
        System.out.println(prueba.contador_de_claves() );

    }

}