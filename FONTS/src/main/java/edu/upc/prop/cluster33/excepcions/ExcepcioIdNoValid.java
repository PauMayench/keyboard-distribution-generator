package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan s'introdueix un identificador d'un teclat, algorisme o text no vàlid.
 */
public class ExcepcioIdNoValid extends Excepcio{
    /**
     * Constructor per ExcepcioIdNoValid.
     */
    public ExcepcioIdNoValid() {
        super("El id introduit no correspon a cap dels possibles");
    }
}
