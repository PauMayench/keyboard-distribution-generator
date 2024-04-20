package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan es detecta que ja existeix un text predefinit amb el mateix nom.
 */
public class ExcepcioNomTextPredefinitJaExisteix extends Excepcio{
    /**
     * Constructor per ExcepcioNomTextPredefinitJaExisteix.
     */
    public ExcepcioNomTextPredefinitJaExisteix() {
        super("Ja existeix un text predefinit amb aquest nom");
    }
}
