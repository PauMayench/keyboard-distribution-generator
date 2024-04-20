package edu.upc.prop.cluster33.excepcions;

/**
 * Classe que representa una excepció genèrica de l'aplicació.
 * Aquesta excepció s'utilitza com a classe base per a altres excepcions específiques.
 */
public abstract class Excepcio extends Exception{

    private static final long serialVersionUID = 1L;


    public Excepcio() {
        super();
    }

    public Excepcio(String s) {
        super(s);
    }
}