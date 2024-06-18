package aed;

public class SistemaSIU {
    
    private Tries<Carrera> carreras;
    private Tries<Integer> alumnos;

    // Invariante: 
    // Para todo alumno que aparezca en alumnos de la clase Materia ese alumno tiene que estar en alumnos general.
    // El entero que es valor de alumnos general se corresponde con la cantidad de veces que aparece ese alumno en las distintas clases Materia.
    // Hay aliaising entre las clases Materia de aquellas materias que son equivalentes.

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    // O(sumatoria |c|*|Mc| con c en C + sumatoria doble |n| con n en Nm y m en M + E)
    // 1) sumatoria |c|*|Mc| con c en C: para cada carrera uso esClave, darValor e insertar que tienen una complejidad de el largo de cada materia por esa carrera.
    // 2) sumatoria doble |n| con n en Nm: para cada "materia general" es decir para cada InfoMateria tengo que recorrer el conjunto de los nombres de esa materia para cada carrera.
    // 3) O(E): recorro las libretas universitarias, es decir todos los estudiantes.

    public SistemaSIU(InfoMateria[] materiasEnCarreras, String[] libretasUniversitarias){
        carreras = new Tries<Carrera>();
        alumnos = new Tries<Integer>();
        for (int i=0; i<materiasEnCarreras.length; i=i+1){ // O(M) recorro las materias "generales"
        Materia materia = null;
            for (int j=0; j<materiasEnCarreras[i].getParesCarreraMateria().length; j=j+1){ // O(Nm) recorro para una materia las carreras con el nombre especifico de esa materia
                String nombreCarrera = materiasEnCarreras[i].getParesCarreraMateria()[j].getCarrera();
                String nombreMateria = materiasEnCarreras[i].getParesCarreraMateria()[j].getNombreMateria();
                Carrera carrera; // veo si la carrera ya esta definida y sino la defino lo hago tantas veces como carreras haya
                if (carreras.esClave(nombreCarrera)){ // O (|nombreCarrera|) 
                    carrera = carreras.darValor(nombreCarrera); // O (|nombreCarrera|) 
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
            alumnos.insertar(libretasUniversitarias[i],0); // O(1) pues longitud acotada 
        }
    } 



    // O(|c| + |m|)
    // 1) |c|: usamos la operacion dar valor que tiene complejidad largo de carrera.
    // 2) |m|: usamos la operacion dar valor que tiene complejidad largo de la materia. 

    public void inscribir(String estudiante, String carrera, String materia){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); // O(|m|)
        ListaEnlazada<String> estudiantes = valorMateria.alumnos; // O(1)
        estudiantes.agregarAtras(estudiante); // O(1)
        Integer materiasinscriptas = alumnos.darValor(estudiante); // O(|estudiante|)=O(1) pues el largo de estudiantes es un string de longitud ACOTADA
        alumnos.insertar(estudiante, materiasinscriptas+1); // O(1)
    } 



    // O(|c| + |m|)
    // 1) |c|: usamos la operacion dar valor que tiene complejidad largo de carrera.
    // 2) |m|: usamos la operacion dar valor que tiene complejidad largo de la materia. 

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



    // O(|c| + |m|)
    // 1) |c|: usamos la operacion dar valor que tiene complejidad largo de carrera.
    // 2) |m|: usamos la operacion dar valor que tiene complejidad largo de la materia. 

    public int[] plantelDocente(String materia, String carrera){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); // O(|m|)
        return valorMateria.docentes; // O(1)
    }



    // O(|c| + |m| + sumaroria |n| con n en Nm + Em)
    // 1) |c|: usamos la operacion dar valor que tiene complejidad largo de carrera.
    // 2) |m|: usamos la operacion dar valor que tiene complejidad largo de la materia. 
    // 3) sumaroria |n| con n en Nm: hay un bucle en el cual iteramos sobre todos los nombres de de la materia m (Nm) y usamos la operacion eliminar que tiene complejidad largo del nombre. 
    // 4) Em: tenemos un bucle que recorre todos los estudiantes de la materia (con operaciones O(1)).      

    public void cerrarMateria(String materia, String carrera){
        Materia datos = carreras.darValor(carrera).materias.darValor(materia); //O(|carrera|+|materia|)
        ListaEnlazada<String> estudiantes = datos.alumnos;
        Iterador<String> iteradoralumnos = estudiantes.iterador(); // O(1)
        while(iteradoralumnos.haySiguiente()){ // O(Em)
            String actual = iteradoralumnos.siguiente(); // O(1)
            Integer cantidad = alumnos.darValor(actual); // O(1) pues longitud acotada
            alumnos.insertar(actual, cantidad-1); // O(1) pues longitud acotada        
        }
        ListaEnlazada<Tupla> materias = datos.materiageneral;
        Iterador<Tupla> iteradormateria = materias.iterador(); // O(1)
        while(iteradormateria.haySiguiente()){ // O(sumatoria |n| con n en Nm)
            Tupla actual = iteradormateria.siguiente(); // O(1)
            Tries<Materia> triemateria = actual.carrera; // O(1)
            String nombre = actual.nombre; // O(1)
            triemateria.eliminar(nombre); // O(|nombre|)
        }
    } 



    // O(|c| + |m|)
    // 1) |c|: usamos la operacion dar valor que tiene complejidad largo de carrera.
    // 2) |m|: usamos la operacion dar valor que tiene complejidad largo de la materia. 

    public int inscriptos(String materia, String carrera){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); //(|m|)
        ListaEnlazada<String> estudiantes = valorMateria.alumnos; // O(1)
        return estudiantes.longitud(); // O(1)
    } 



    // O(|c| + |m|)
    // 1) |c|: usamos la operacion dar valor que tiene complejidad largo de carrera.
    // 2) |m|: usamos la operacion dar valor que tiene complejidad largo de la materia. 

    public boolean excedeCupo(String materia, String carrera){
        Carrera valorCarrera = carreras.darValor(carrera); // O(|c|)
        Materia valorMateria = valorCarrera.materias.darValor(materia); // O(|m|)
        int[] docentes = valorMateria.docentes; // O(1)
        int cupo = cupo(docentes); // O(1)
        if(cupo<valorMateria.alumnos.longitud()){ // O(1)
            return true; // O(1)
        }
        else{
            return false; // O(1)
        }
    }

    private int cupo(int[] docentes){
        return minimo(250*docentes[0], minimo(100*docentes[1], minimo(20*docentes[2], 30*docentes[3])));
    } // O(1)

    private int minimo(int a, int b){
        if(a<=b){
            return a;
        } else{
            return b;
        }
    } // O(1)


    // O(sumatoria |c| con c en C)
    // 1) sumatoria |c| con c en C: usamos la operacion listar sobre el trie carrera.

    public String[] carreras(){
        return carreras.listar();
    } 


    // O(|c| + sumatoria |mc| con mc en Mc)
    // 1) |c|: usamos la operacion dar valor que tiene complejidad largo de carrera.
    // 2) sumatoria |mc| con mc en Mc: usamos la operacion listar sobre el trie materia.

    public String[] materias(String carrera){
        Tries<Materia> materia = carreras.darValor(carrera).materias; // O(|carrera|)
        return materia.listar(); // O(sumatoria |mc| con mc en Mc)
    } 


    // O(1)
    // 1) 1: usamos la operacion dar valor sobre una variable acotada. O(|estudiante|)=O(1) pues el largo de estudiantes es un string de longitud ACOTADA.

    public int materiasInscriptas(String estudiante){
        return alumnos.darValor(estudiante); 
    } 
}
