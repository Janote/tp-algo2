package aed;
import java.util.*;


public class Tries {
    Nodo raiz ;
    int tamaño ;
    
    private class Nodo{

        Character significado;
        boolean esPalabra;

        ArrayList<Nodo> siguientes = new ArrayList<>(); //256 posibilidades distintas (por codigo ASCII)
        // en cada una de las posiciones voy a poner el codigo ASCII que le corresponde
        // ej: En la posicion 64 del array, le correspondara a la direccion de memoria del nodo con el significado 'A'.
    
        
    private Nodo( Character significado)
    {
        this.significado = significado ;
        esPalabra = false ; //seteo false en cada nodo creado por defecto.
        this.siguientes  = new ArrayList<>(256) ; 
    
        for (int i = 0; i < 256; i++) {
            siguientes.add(null) ;            
        }
    }
    
}

public Tries()
{
    Nodo nuevo_nodo = new Nodo(null) ;
    raiz = nuevo_nodo ;
    

}


// uso una funcion auxiliar para que cada caracter del string se anada.

private void insertar(String valor) 
{
    insertarAux(0, valor, raiz);
}   


private void insertarAux(int k , String valor, Nodo puntero)
{

    if( k == valor.length())
    {
         puntero.esPalabra = true; // La ultima letra de la palabra sera asignada con 'True'
         return ;
    }

    if(puntero.siguientes.get(((int)valor.charAt(k))) == null )  // si en este caso no esta creada , creo el nodo, donde en el constructor le asigno el caracter.
        {
            Nodo nuevo_nodo = new Nodo(valor.charAt(k)) ; //creo el nuevo nodo

            puntero.siguientes.set((int) valor.charAt(k), nuevo_nodo) ; // en la posicion del codigo ASCII  de mi caracter , le asigno el nodo. 
            
            
            insertarAux(k + 1 , valor, nuevo_nodo); // hago el paso recursivo, actualizando el caracter.
             
        }
 
    else
    {

        insertarAux(k + 1, valor, puntero.siguientes.get(((int)valor.charAt(k))));

    }


}



// si le sacas el segundo if encuentra las posibles palabras.

private boolean buscarPalabra(String palabra)
{
    return buscarPalabraAux(0 , palabra,raiz);


}

private boolean buscarPalabraAux(int k ,String palabra, Nodo puntero)
{
    if( k == palabra.length() && puntero.esPalabra == true )
    {
        return true ;
    }

    if( k == palabra.length() && puntero.esPalabra == false)
    {
        return false; 
    }
    if ( puntero.siguientes.get(((int)palabra.charAt(k))) == null)
    {
        return false;
    }

  
    return   buscarPalabraAux(k + 1 , palabra, puntero.siguientes.get(((int)palabra.charAt(k))));

}



private  int cantidad_de_elementos()
{
    return cantidad_de_elementos_aux(raiz) ; 


}

private int cantidad_de_elementos_aux(Nodo puntero)
{
    int contador = 0 ;

    for (int i = 0; i < puntero.siguientes.size(); i++) {
        
        if(puntero.siguientes.get(i) != null)
        {
        contador = contador + 1 + cantidad_de_elementos_aux(puntero.siguientes.get(i)) ; 
        }
 
    }

    return contador ; 
}









public static void main(String[] args) {
    Tries prueba = new Tries();
    prueba.insertar("amor");
    System.out.println(prueba.buscarPalabra("amor")) ;
    prueba.insertar("amoroso");
    System.out.println(prueba.buscarPalabra("amoroso")) ;
    prueba.insertar("ame");
    System.out.println(prueba.buscarPalabra("ame")) ;
    prueba.insertar("lagartija");
    System.out.println(prueba.buscarPalabra("lagartija")) ;
    System.out.println(prueba.cantidad_de_elementos()); //  
}
}

