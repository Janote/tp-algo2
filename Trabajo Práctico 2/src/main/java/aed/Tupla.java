package aed;

public class Tupla {
    Tries<Materia> carrera;
    String nombre;

    // Invariante:
    // no se pide nada.

    public Tupla(Tries<Materia> c, String n){
        this.carrera = c;
        this.nombre = n;
    }
    
}
