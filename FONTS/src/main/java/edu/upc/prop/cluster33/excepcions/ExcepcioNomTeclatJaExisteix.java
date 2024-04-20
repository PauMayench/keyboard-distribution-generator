package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan es prova de crear un teclat amb un nom que ja existeix.
 */
public class ExcepcioNomTeclatJaExisteix extends Excepcio{
    /**
     * Constructor per ExcepcioNomTeclatJaExisteix.
     */
    public ExcepcioNomTeclatJaExisteix() {
        super("Ja hi ha un teclat amb el nom de teclat introduit");
    }

}
