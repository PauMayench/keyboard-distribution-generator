package edu.upc.prop.cluster33.excepcions;

/**
 * Excepció que s'activa quan no es pot trobar un alfabet específic dins del sistema.
 */
public class ExcepcioAlfabetNoTrobat extends Excepcio{
    /**
     * Constructor per a ExcepcioAlfabetNoTrobat
     */
    public ExcepcioAlfabetNoTrobat() {
        super("No s'ha pogut determinar a quin alfabet pertany el text/llista de freqüencies proporcionat. És possible que no" +
                "existeixi al sistema o bé s'hagi escrit malament.");
    }
}
