package edu.upc.prop.cluster33.domini;

/**
 * Classe que representa un text amb títol i contingut.
 */
public class Text {
    /**
     * Títol del text.
     */
    private String titol;
    /**
     * Contingut del text.
     */
    private String contingut;

    /**
     * Constructor per crear un nou text sense títol.
     * @param contingut Contingut del text.
     */
    public Text(String contingut) {
        this.contingut = contingut;
        this.titol = "";
    }

    /**
     * Constructor per crear un nou text amb títol i contingut.
     * @param titol Títol del text.
     * @param contingut Contingut del text.
     */
    public Text(String titol, String contingut) {
        this.titol = titol;
        this.contingut = contingut;
    }

    /**
     * Constructor de còpia per a crear una nova instància de Text basada en un altre objecte Text.
     * @param tx Objecte Text original.
     */
    public Text(Text tx) {
        this.titol = tx.titol;
        this.contingut = tx.contingut;
    }

    /**
     * Retorna el títol del text.
     * @return Títol del text.
     */
    public String getNom() {
        return this.titol;
    }

    /**
     * Retorna el contingut del text.
     * @return Contingut del text.
     */
    public String llegirContingut() {
        return this.contingut;
    }

    /**
     * Modifica el títol del text.
     * @param titol Nou títol per al text.
     */
    public void setNom(String titol) {
        this.titol = titol;
    }

    /**
     * Modifica el contingut del text.
     * @param text Nou contingut per al text.
     */
    public void setContingut(String text) {
        this.contingut = text;
    }
}
