package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan ocorre un error inesperat durant la modificació de dades.
 */
public class ExcepcioErrorDurantModificacio extends Excepcio{
    /**
     * Constructor per ExcepcioErrorDurantModificacio.
     */
    public ExcepcioErrorDurantModificacio() {
        super("S'ha produit un error inesperat durant la modificacio, sisplau torni a intentar-ho");
    }
}
