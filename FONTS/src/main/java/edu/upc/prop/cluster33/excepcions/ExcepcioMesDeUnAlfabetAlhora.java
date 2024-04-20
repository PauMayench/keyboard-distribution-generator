package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan un text o llista de freqüències conté caràcters de més d'un alfabet.
 */
public class ExcepcioMesDeUnAlfabetAlhora extends Excepcio{
    /**
     * Constructor per ExcepcioMesDeUnAlfabetAlhora.
     */
    public ExcepcioMesDeUnAlfabetAlhora() {
        super("El text o llista de freqüències proporcionat conté caràcters de més d'un alfabet alhora.");
    }
}
