package aed;

public class Materia {
    public ListaEnlazada<String> alumnos;
    public int[] docentes;
    public ListaEnlazada<Tupla> materiageneral;

    // Invariante:
    // materiageneral guarda en tuplas en la primer posicion la referencia al trie de la materia equivalente y en la segunda posicion el nombre de dicha materia equivalente.

    public Materia(){
        this.alumnos = new ListaEnlazada<String>();
        this.docentes = new int[4];
        this.materiageneral = new ListaEnlazada<Tupla>();
    }


}
