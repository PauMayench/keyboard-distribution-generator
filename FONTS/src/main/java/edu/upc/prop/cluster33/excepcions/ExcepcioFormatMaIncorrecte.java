package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan la mà bona introduïda no segueix el format esperat.
 */
public class ExcepcioFormatMaIncorrecte extends Excepcio{
    /**
     * Constructor per ExcepcioFormatMaIncorrecte.
     */
    public ExcepcioFormatMaIncorrecte() {
        super("La ma bona s'ha introduit incorrectament. Sisplau segueixi el format indicat");
    }
}
