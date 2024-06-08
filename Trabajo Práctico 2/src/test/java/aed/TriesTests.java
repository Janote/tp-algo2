package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TriesTests{
    
    @Test
    void nuevo_conjunto_vacio() {
        
        Tries conjunto = new Tries();

        assertEquals(0, conjunto.cantidad_de_elementos());
        assertEquals(false, conjunto.buscarPalabra("cualquier"));
    }

    @Test
    void insertar_un_elemento() {
        Tries conjunto = new Tries();

        conjunto.insertar("caca");

        assertEquals(1, conjunto.cantidad_de_elementos()); // acá lo pensaste como los elementos son la cantidad de nodos y no las palabras
                                                           // para pensar sres
        assertEquals(true, conjunto.buscarPalabra("caca"));
    }

    @Test
    void insertar_elementos_distintas_ramas(){
        Tries conjunto = new Tries();

        conjunto.insertar("Messi");
        conjunto.insertar("Cristiano");
        conjunto.insertar("Bochini");

        assertEquals(3, conjunto.cantidad_de_elementos());
        assertEquals(true, conjunto.buscarPalabra("Cristiano"));
        assertEquals(true, conjunto.buscarPalabra("Messi"));
        assertEquals(true, conjunto.buscarPalabra("Bochini"));
    }

    @Test
    void insertar_varios_elementos(){ //con este voy a buscar comprobar si funciona bien el insertar, los proximos tests son para el pertenece
        Tries conjunto = new Tries();

        conjunto.insertar("Messi");
        conjunto.insertar("Cristiano");
        conjunto.insertar("Maradona");
        conjunto.insertar("Bochini");
        conjunto.insertar("Messirve");
        conjunto.insertar("Maradoniano");

        assertEquals(6, conjunto.cantidad_de_elementos());
        assertEquals(true, conjunto.buscarPalabra("Cristiano"));
        assertEquals(true, conjunto.buscarPalabra("Messi"));
        assertEquals(true, conjunto.buscarPalabra("Bochini"));
        assertEquals(true, conjunto.buscarPalabra("Messirve"));
        assertEquals(true, conjunto.buscarPalabra("Maradoniano"));
        assertEquals(true, conjunto.buscarPalabra("Maradona"));
    }
    // ahora con tests más estrictos para el pertenece (buscarpalabra)
    
    @Test
    void pertenece(){ 
        Tries conjunto = new Tries();

        conjunto.insertar("Messi");
        conjunto.insertar("Cristiano");
        conjunto.insertar("Maradona");
        conjunto.insertar("Bochini");
        conjunto.insertar("Messirve");
        conjunto.insertar("Maradoniano");

        assertEquals(6, conjunto.cantidad_de_elementos());
        assertEquals(true, conjunto.buscarPalabra("Cristiano"));
        assertEquals(true, conjunto.buscarPalabra("Messi"));
        assertEquals(true, conjunto.buscarPalabra("Bochini"));
        assertEquals(true, conjunto.buscarPalabra("Messirve"));
        assertEquals(true, conjunto.buscarPalabra("Maradoniano"));
        assertEquals(true, conjunto.buscarPalabra("Maradona"));
        
        assertEquals(false, conjunto.buscarPalabra("Cristian"));
        assertEquals(false, conjunto.buscarPalabra("Marado"));
        assertEquals(false, conjunto.buscarPalabra("Mes"));
        assertEquals(false, conjunto.buscarPalabra("ano"));

        conjunto.insertar("Cristian");
        conjunto.insertar("Marado");
        
        assertEquals(8, conjunto.cantidad_de_elementos());

        assertEquals(true, conjunto.buscarPalabra("Marado"));
        assertEquals(true, conjunto.buscarPalabra("Cristian"));
        
        assertEquals(true, conjunto.buscarPalabra("Maradona")); 
        assertEquals(true, conjunto.buscarPalabra("Cristiano"));
    }

    @Test
    void borrar_elemento_simple() { // palabras no relacionadas
        Tries conjunto = new Tries();

        conjunto.insertar("hola");
        assertEquals(1, conjunto.cantidad_de_elementos()); 
        assertEquals(true, conjunto.buscarPalabra("hola"));

        conjunto.borrar("hola");
        assertEquals(0, conjunto.cantidad_de_elementos());
        assertEquals(false, conjunto.buscarPalabra("hola"));

        conjunto.insertar("hola");
        conjunto.insertar("chau");
        conjunto.insertar("que");
        conjunto.insertar("tal");
        conjunto.insertar("servicio");
        conjunto.insertar("larguisima esta palabra");

        assertEquals(6, conjunto.cantidad_de_elementos());
        assertEquals(true, conjunto.buscarPalabra("hola"));
        assertEquals(true, conjunto.buscarPalabra("chau"));
        assertEquals(true, conjunto.buscarPalabra("que"));
        assertEquals(true, conjunto.buscarPalabra("tal"));
        assertEquals(true, conjunto.buscarPalabra("servicio"));
        assertEquals(true, conjunto.buscarPalabra("larguisima esta palabra"));

        conjunto.borrar("hola");
        conjunto.borrar("que");
        conjunto.borrar("servicio");

        assertEquals(true, conjunto.buscarPalabra("tal"));
        assertEquals(true, conjunto.buscarPalabra("larguisima esta palabra"));
        assertEquals(true, conjunto.buscarPalabra("chau"));

        assertEquals(3, conjunto.cantidad_de_elementos());
        
        conjunto.borrar("larguisima esta palabra");
        conjunto.borrar("chau");
        conjunto.borrar("tal");
        
        assertEquals(0, conjunto.buscarPalabra());

        assertEquals(false, conjunto.buscarPalabra("hola"));
        assertEquals(false, conjunto.buscarPalabra("chau"));
        assertEquals(false, conjunto.buscarPalabra("que"));
        assertEquals(false, conjunto.buscarPalabra("tal"));
        assertEquals(false, conjunto.buscarPalabra("servicio"));
        assertEquals(false, conjunto.buscarPalabra("larguisima esta palabra"));
    }

    @Test
    void borrar_la_palabra_larga_en_compuesta() { // es decir, si tengo las palabras Mal y Malisimo, borro Malisimo
        
        Tries conjunto = new Tries();

        conjunto.insertar("mal");
        conjunto.insertar("malisimo");
        conjunto.insertar("malisimos estos ejemplos");
        conjunto.insertar("otras palabras");
        conjunto.insertar("otras palabras mas");
        conjunto.insertar("otras palabras mas largas");

        assertEquals(6, conjunto.cantidad_de_elementos());
        assertEquals(true, conjunto.buscarPalabra("mal"));
        assertEquals(true, conjunto.buscarPalabra("malisimo"));
        assertEquals(true, conjunto.buscarPalabra("malisimos estos ejemplos"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras mas"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras mas largas"));

        
        conjunto.borrar("malisimo");
        assertEquals(true, conjunto.buscarPalabra("mal"));
        assertEquals(true, conjunto.buscarPalabra("malisimos estos ejemplos"));
        assertEquals(false, conjunto.buscarPalabra("malisimo"));

        conjunto.borrar("malisimos estos ejemplos");
        assertEquals(true, conjunto.buscarPalabra("mal"));
        assertEquals(false, conjunto.buscarPalabra("malisimos estos ejemplos"));

        conjunto.borrar("otras palabras mas largas");
        assertEquals(true, conjunto.buscarPalabra("otras palabras"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras mas"));
        assertEquals(false, conjunto.buscarPalabra("otras palabras mas largas"));

        conjunto.borrar("otras palabras mas");
        assertEquals(true, conjunto.buscarPalabra("otras palabras"));
        assertEquals(false, conjunto.buscarPalabra("otras palabras mas"));

        assertEquals(2, conjunto.cantidad_de_elementos());
        
    }

    @Test
    void borrar_palabra_corta_en_compuesta() { // es decir, si tengo las palabras Mal y Malisimo, borro Mal
        
        Tries conjunto = new Tries();

        conjunto.insertar("mal");
        conjunto.insertar("malisimo");
        conjunto.insertar("malisimos estos ejemplos");
        conjunto.insertar("otras palabras");
        conjunto.insertar("otras palabras mas");
        conjunto.insertar("otras palabras mas largas");

        assertEquals(6, conjunto.cantidad_de_elementos());
        assertEquals(true, conjunto.buscarPalabra("mal"));
        assertEquals(true, conjunto.buscarPalabra("malisimo"));
        assertEquals(true, conjunto.buscarPalabra("malisimos estos ejemplos"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras mas"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras mas largas"));

        conjunto.borrar("mal");
        assertEquals(false, conjunto.buscarPalabra("mal"));
        assertEquals(true, conjunto.buscarPalabra("malisimo"));
        assertEquals(true, conjunto.buscarPalabra("malisimos estos ejemplos"));

        conjunto.borrar("otras palabras mas");  // acá decidí borrar la del medio
        assertEquals(true, conjunto.buscarPalabra("otras palabras"));
        assertEquals(false, conjunto.buscarPalabra("otras palabras mas"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras mas largas"));

        assertEquals(4, conjunto.cantidad_de_elementos());
    }
    @Test
    void borrar_palabras_con_recorrido_compartido() { // es decir, si tengo las palabras Simulador y Simulacion, si borro alguna la otra debe seguir estando

        Tries conjunto = new Tries();

        conjunto.insertar("cabeza");
        conjunto.insertar("cabecear");
        conjunto.insertar("cabecilla");
        conjunto.insertar("cabecera");

        assertEquals(4, conjunto.cantidad_de_elementos());
        assertEquals(true, conjunto.buscarPalabra("cabeza"));
        assertEquals(true, conjunto.buscarPalabra("cabecera"));
        assertEquals(true, conjunto.buscarPalabra("cabecilla"));
        assertEquals(true, conjunto.buscarPalabra("cabecear"));

        conjunto.borrar("cabecilla");
        assertEquals(true, conjunto.buscarPalabra("cabeza"));
        assertEquals(true, conjunto.buscarPalabra("cabecera"));
        assertEquals(false, conjunto.buscarPalabra("cabecilla"));
        assertEquals(true, conjunto.buscarPalabra("cabecear"));

        conjunto.borrar("cabecear");
        assertEquals(true, conjunto.buscarPalabra("cabeza"));
        assertEquals(true, conjunto.buscarPalabra("cabecera"));
        assertEquals(false, conjunto.buscarPalabra("cabecear"));

        conjunto.borrar("cabeza");
        assertEquals(false, conjunto.buscarPalabra("cabeza"));
        assertEquals(true, conjunto.buscarPalabra("cabecera"));

        assertEquals(1, conjunto.cantidad_de_elementos());
    }

    @Test
    void borrar_e_insertar() { // medio al pedo este, ya se supone que si pasa los anteriores este es innecesario
        Tries conjunto = new Tries();

        conjunto.insertar("mal");
        conjunto.insertar("malisimo");
        conjunto.insertar("malisimos estos ejemplos");
        conjunto.insertar("otras palabras");
        conjunto.insertar("otras palabras mas");
        conjunto.insertar("otras palabras mas largas");

        assertEquals(6,conjunto.cantidad_de_elementos());

        conjunto.borrar("malisimo");
        conjunto.borrar("otras palabras mas largas");

        assertEquals(true, conjunto.buscarPalabra("mal"));
        assertEquals(false, conjunto.buscarPalabra("malisimo"));
        assertEquals(true, conjunto.buscarPalabra("malisimos estos ejemplos"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras mas"));
        assertEquals(false, conjunto.buscarPalabra("otras palabras mas largas"));

        assertEquals(4, conjunto.cantidad_de_elementos());

        conjunto.insertar("malardo");
        conjunto.insertar("malisimos es");
        conjunto.insertar("aburris papish");

        assertEquals(7, conjunto.cantidad_de_elementos());

        conjunto.borrar("mal");
        conjunto.borrar("otras palabras mas");

        assertEquals(5, conjunto.cantidad_de_elementos());

        assertEquals(false, conjunto.buscarPalabra("mal"));
        assertEquals(false, conjunto.buscarPalabra("malisimo"));
        assertEquals(true, conjunto.buscarPalabra("malisimos estos ejemplos"));
        assertEquals(true, conjunto.buscarPalabra("otras palabras"));
        assertEquals(false, conjunto.buscarPalabra("otras palabras mas"));
        assertEquals(false, conjunto.buscarPalabra("otras palabras mas largas"));
        assertEquals(true, conjunto.buscarPalabra("malisimos es"));
        assertEquals(true, conjunto.buscarPalabra("aburris papish"));
        assertEquals(true, conjunto.buscarPalabra("malardo"));
    }
}