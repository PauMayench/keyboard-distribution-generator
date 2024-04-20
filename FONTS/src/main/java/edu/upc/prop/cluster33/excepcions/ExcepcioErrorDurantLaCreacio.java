package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció que s'activa quan hi ha hagut un error durant la creació de dades.
 */
public class ExcepcioErrorDurantLaCreacio extends Excepcio{
    /**
     * Constructor per a ExcepcioErrorDurantLaCreacio.
     */
    public ExcepcioErrorDurantLaCreacio() {
        super("S'ha produit un error durant la creacio, sisplau torni a intentar-ho");
    }
}
