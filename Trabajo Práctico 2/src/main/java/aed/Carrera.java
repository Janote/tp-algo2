package aed;

public class Carrera {
    String carrera;
    Trie<Materia> materias;

    public Carrera(String carrera){
        this.carrera = carrera;
        this.materias = new Trie<Materia>();
    }
}
