package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan el text o llista de freqüències proporcionat és buit.
 */
public class ExcepcioTextBuit extends Excepcio{
    /**
     * Constructor per ExcepcioTextBuit.
     */
    public ExcepcioTextBuit() {
        super("El text o llista de freqüències proporcionat no té contingut, assegureu-vos que heu escollit " +
                "el text correcte.");
    }
}
