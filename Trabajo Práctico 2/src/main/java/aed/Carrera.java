package aed;

public class Carrera {
    String carrera;
    Tries<Materia> materias;

    public Carrera(String carrera){
        this.carrera = carrera;
        this.materias = new Tries<Materia>();
    }
}
