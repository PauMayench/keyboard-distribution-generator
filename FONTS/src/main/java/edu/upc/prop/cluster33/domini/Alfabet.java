package edu.upc.prop.cluster33.domini;

import java.util.TreeMap;

/**
 * Classe que representa un alfabet amb un nom i una cadena de caràcters que el defineix.
 */
public class Alfabet {
    /**
     * Nom de l'alfabet.
     */
    private String nom;
    /**
     * Cadena de caràcters que defineix l'alfabet.
     */
    private String alfabet;

    /**
     * Constructor per defecte. Crea un alfabet buit.
     */
    public Alfabet() {
        alfabet = "";
    }

    /**
     * Constructor amb nom i alfabet.
     *
     * @param name El nom de l'alfabet.
     * @param alfa La cadena de caràcters que defineix l'alfabet.
     */
    public Alfabet(String name, String alfa) {
        nom = name;
        alfabet = alfa;
    }

    /**
     * Retorna l'alfabet.
     *
     * @return La cadena de caràcters que defineix l'alfabet.
     */
    public String getAlfabet(){
        return alfabet;
    }

    /**
     * Retorna el nom de l'alfabet.
     *
     * @return El nom de l'alfabet.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retorna la mida de l'alfabet.
     *
     * @return El nombre de caràcters de l'alfabet.
     */
    public int getMida() {
        //Capturar excepción alfabeto vacío.
        return alfabet.length();
    }

    /**
     * Retorna l'índex d'un caràcter dins de l'alfabet.
     *
     * @param c El caràcter a cercar.
     * @return L'índex del caràcter en l'alfabet.
     * @throws IllegalStateException Si l'alfabet és buit.
     */
    public int getIndex(char c) throws IllegalStateException{
        //Capturar excepcion alfabeto vacio.
        return alfabet.indexOf(c);
    }

    /**
     * Retorna el caràcter a l'índex especificat de l'alfabet.
     *
     * @param i L'índex del caràcter que es vol obtenir.
     * @return El caràcter en la posició especificada.
     */
    public char getCharAtIndex(int i) {
        return alfabet.charAt(i);
    }

    /**
     * Determina l'alfabet basant-se en un caràcter i un magatzem d'alfabets.
     *
     * @param c El caràcter a utilitzar per determinar l'alfabet.
     * @param magatzemAlfabets El magatzem de cadenes amb els noms i definicions dels alfabets.
     */
    public void determinaAlfabet(Character c, TreeMap<String, String> magatzemAlfabets) {
        for (String key : magatzemAlfabets.keySet()) {
            String a = magatzemAlfabets.get(key);
            if (a.contains(""+c)) {
                alfabet = a;
                nom = key;
            }
        }
    }
}

