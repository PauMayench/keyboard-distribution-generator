package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan hi ha problemes llegint dades des del disc.
 */
public class ExcepcioLlegintDeDisc extends Excepcio{

    /**
     * Constructor per defecte per a ExcepcioLlegintDeDisc.
     */
    public ExcepcioLlegintDeDisc() {
        super("S'ha produit un error al intentar llegir el disc");
    }
    /**
     * Constructor per a ExcepcioLlegintDeDisc amb detall de missatge.
     * @param missatge Missatge detallat de l'error.
     */
    public ExcepcioLlegintDeDisc(String missatge) {
        super(missatge);

    }
}
