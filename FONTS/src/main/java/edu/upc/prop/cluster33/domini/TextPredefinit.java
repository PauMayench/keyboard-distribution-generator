package edu.upc.prop.cluster33.domini;


/**
 * Classe que representa un text predefinit, extensió de la classe Text.
 */
public class TextPredefinit extends Text{
    /**
     * Constructor per a crear un text predefinit només amb títol.
     * @param titol Títol del text predefinit.
     */
    public TextPredefinit(String titol) {
        super(titol);
    }

    /**
     * Constructor per a crear un text predefinit amb títol i contingut.
     * @param titol Títol del text predefinit.
     * @param contingut Contingut del text predefinit.
     */
    public TextPredefinit(String titol, String contingut) {
        super(titol, contingut);
    }

    /**
     * Constructor de còpia per a crear un text predefinit a partir d'un altre objecte Text.
     * @param tx Objecte Text original.
     */
    public TextPredefinit(Text tx) {
        super(tx);
    }
}
