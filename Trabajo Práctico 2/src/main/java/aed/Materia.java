package aed;

public class Materia {
    public ListaEnlazada<String> alumnos;
    public int[] docentes;
    public ListaEnlazada<Tupla> materiageneral;

    public Materia(){
        this.alumnos = new ListaEnlazada<String>();
        this.docentes = new int[4];
        this.materiageneral = new ListaEnlazada<Tupla>();
    }


}
