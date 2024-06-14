package aed;

public class Carrera {
    String carrera;
    Tries<Materia> materias;

    // Invariante:
    // materias contiene todas aquellos nombres de materia asociados a dicha carrera como claves.

    public Carrera(String carrera){
        this.carrera = carrera;
        this.materias = new Tries<Materia>();
    }
}
