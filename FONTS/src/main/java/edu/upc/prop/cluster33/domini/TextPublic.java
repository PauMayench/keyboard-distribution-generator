package edu.upc.prop.cluster33.domini;

/**
 * Classe que representa un text públic, extensió de la classe Text. Inclou informació de l'usuari.
 */
public class TextPublic extends Text{
    /**
     * Nom d'usuari associat al text públic.
     */
    private String username;

    /**
     * Constructor per a crear un text públic només amb contingut.
     * @param contingut Contingut del text públic.
     */
    public TextPublic(String contingut) {
        super(contingut);
    }

    /**
     * Constructor per a crear un text públic amb títol, contingut i username de l'usuari.
     * @param titol Títol del text públic.
     * @param contingut Contingut del text públic.
     * @param username Username de l'usuari associat al text.
     */
    public TextPublic(String titol, String contingut, String username){
        super(titol, contingut);
        this.username = username;
    }

    /**
     * Retorna l'username de l'usuari associat al text públic.
     * @return Username de l'usuari.
     */
    public String getUsuariUsername() {
        return this.username;
    }

    /**
     * Modifica l'username de l'usuari associat al text públic.
     * @param nom Nou username per a l'usuari.
     */
    public void setUsername(String nom) {
        this.username = nom;
    }

}
