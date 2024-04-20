package edu.upc.prop.cluster33.excepcions;

/**
 * Excepció que s'activa quan hi ha hagut un error durant l'eliminació de dades.
 */
public class ExcepcioErrorDurantEsborrat extends Excepcio{
    /**
     * Constructor per a ExcepcioErrorDurantEsborrat.
     */
    public ExcepcioErrorDurantEsborrat() {
        super("S'ha produit un error inesperat durant l'esborrat, sisplau torni a intentar-ho");
    }
}
