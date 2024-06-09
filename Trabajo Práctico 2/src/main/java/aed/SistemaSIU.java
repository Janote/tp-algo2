package aed;

public class SistemaSIU {
    private Tries<Carrera> carreras;
    private Tries<Integer> alumnos;

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(){
        carreras = new Tries<Carrera>();
        alumnos = new Tries<Integer>();
    }
    
    public SistemaSIU(InfoMateria[] materiasEnCarreras, String[] libretasUniversitarias){
        this();
        for (int i=0; i<materiasEnCarreras.length; i=i+1){ // O(M) recorro las materias "generales"
        Materia materia = null;
            for (int j=0; j<materiasEnCarreras[i].carreras.length; j=j+1){ // O(Nm) recorro para una materia las carreras con el nombre especifico de esa materia
                String nombreCarrera = materiasEnCarreras[i].carreras[j];
                String nombreMateria = materiasEnCarreras[i].nombresEnCarreras[j];
                Carrera carrera; // veo si la carrera ya esta definida y sino la defino lo hago tantas veces como carreras haya
                if (carreras.esClave(nombreCarrera)){ // O (|nombreCarrera|) 
                    carrera = carreras.darValor(nombreCarrera);
                } else {
                    carrera = new Carrera(nombreCarrera);
                    carreras.insertar(nombreCarrera, carrera); // O(|nombreCarrera|)
                }
                if(j==0){ // veo si ya itere sobre la "materia general" si ya lo hice solo agrego la tupla, sino creo Materia
                    materia = new Materia();
                }
                carrera.materias.insertar(nombreMateria, materia); // O(|Nm|)
                Tupla tupla = new Tupla(carrera.materias, nombreMateria);
                materia.materiageneral.agregarAtras(tupla);
            }        
        }
        for(int i=0; i<libretasUniversitarias.length; i=i+1){ // O(E)
            alumnos.insertar(libretasUniversitarias[i],0);
        }
    }

    public void inscribir(String estudiante, String carrera, String materia){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); // O(|m|)
        ListaEnlazada<String> estudiantes = valorMateria.alumnos; // O(1)
        estudiantes.agregarAtras(estudiante); // O(1)
        Integer materiasinscriptas = alumnos.darValor(estudiante); // O(|E|)=O(1) pues el largo de estudiantes es un string de longitud ACOTADA
        alumnos.insertar(estudiante, materiasinscriptas+1); // O(1)
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); // O(|m|)
        int[] docentes = valorMateria.docentes; // O(1)
        if(cargo == CargoDocente.PROF){ // O(1)
            docentes[0] += 1; // O(1)
        } else if(cargo == CargoDocente.JTP){ // O(1)
            docentes[1] += 1; // O(1)
        } else if(cargo == CargoDocente.AY1){ // O(1)
            docentes[2] += 1; // O(1)
        } else{
            docentes[3] += 1; // O(1)
        }
    }

    public int[] plantelDocente(String materia, String carrera){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); // O(|m|)
        return valorMateria.docentes; // O(1)
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public int inscriptos(String materia, String carrera){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); //(|m|)
        ListaEnlazada<String> estudiantes = valorMateria.alumnos; // O(1)
        return estudiantes.longitud(); // O(1)
    }

    public boolean excedeCupo(String materia, String carrera){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); // O(|m|)
        int[] docentes = valorMateria.docentes; // O(1)
        int cupo = (docentes[0]*250 + docentes[1]*100 + docentes[2]*20 + docentes[3]*30);
        if(cupo<inscriptos(materia, carrera)){
            return true;
        }
        else{
            return false;
        }
    }

    public String[] carreras(){
        return carreras.listar();
    }

    public String[] materias(String carrera){
        return carreras.darValor(carrera).materias.listar();
    }

    public int materiasInscriptas(String estudiante){
        return alumnos.darValor(estudiante);
    }
}
