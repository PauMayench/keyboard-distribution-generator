package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció genèrica per problemes relacionats amb les freqüències.
 */
public class ExcepcioFrequencies extends Excepcio{
    /**
     * Constructor per a ExcepcioFrequencies.
     * @param errorFrequencies Missatge detallat de l'error.
     */
    public ExcepcioFrequencies (String errorFrequencies){
        super(errorFrequencies);
    }
}
