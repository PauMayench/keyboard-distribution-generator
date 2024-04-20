package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan es detecta que ja existeix un text públic amb el mateix nom.
 */
public class ExcepcioNomTextPublicJaExisteix extends Excepcio{
    /**
     * Constructor per ExcepcioNomTextPublicJaExisteix.
     */
    public ExcepcioNomTextPublicJaExisteix() {
        super("Ja existeix un text public amb aquest nom");
    }
}
