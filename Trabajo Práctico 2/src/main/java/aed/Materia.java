package aed;
import java.util.ArrayList;

public class Materia {
    String materia;
    public ArrayList<String> alumnos;
    public int[] docentes;
    public ListaEnlazada<Tupla> materiageneral;

    public Materia(String materia){
        this.materia = materia;
        this.alumnos = new ArrayList<String>();
        this.docentes = new int[4];
        this.materiageneral = new ListaEnlazada<Tupla>();
    }


}
