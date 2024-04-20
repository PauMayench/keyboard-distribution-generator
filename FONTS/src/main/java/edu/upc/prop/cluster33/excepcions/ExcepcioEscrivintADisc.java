package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan es produeix un error escrivint dades al disc.
 */
public class ExcepcioEscrivintADisc extends Excepcio{
    /**
     * Constructor per ExcepcioEscrivintADisc.
     */
    public ExcepcioEscrivintADisc() {
        super("ES'ha produit algun error intentant escriure a disc");
    }
}
