package edu.upc.prop.cluster33.domini;

import java.util.*;

/**
 * Classe que representa un teclat amb un layout específic, algorisme, freqüències i data de creació.
 */
public class Teclat {
    /**
     * Nom del teclat.
     */
    private String nom;
    /**
     * Layout del teclat, representat com una matriu de caràcters.
     */
    private char[][] layout;
    /**
     * Algorisme utilitzat per a generar el layout del teclat.
     */
    private Algorisme algorisme;
    /**
     * Data de creació del teclat.
     */
    private Date dataCreacio;
    /**
     * Freqüències de les paraules associades al teclat.
     */
    private Frequencies frequencies;

    /**
     * Constructor per a crear un nou objecte Teclat.
     * @param nom Nom del teclat.
     * @param layout Layout del teclat en forma de matriu de caràcters.
     * @param Algorisme Algorisme utilitzat per generar el layout.
     * @param frequencies Frequencies utilitzades en el teclat.
     */
    public Teclat(String nom, char[][] layout, Algorisme Algorisme, Frequencies frequencies) {
        this.nom = nom;
        this.algorisme = Algorisme;
        this.layout = layout;
        this.frequencies = frequencies;
        this.dataCreacio = new Date();
    }

    /**
     * Constructor amb data específica per a crear un nou objecte Teclat.
     * @param nom Nom del teclat.
     * @param layout Layout del teclat.
     * @param Algorisme Algorisme utilitzat per generar el layout.
     * @param frequencies Frequencies utilitzades en el teclat.
     * @param date Data de creació del teclat.
     */
    public Teclat(String nom, char[][] layout, Algorisme Algorisme, Frequencies frequencies, Date date) {
        this.nom = nom;
        this.algorisme = Algorisme;
        this.layout = layout;
        this.frequencies = frequencies;
        this.dataCreacio = date;
    }

    /**
     * Retorna el nom del teclat.
     * @return Nom del teclat.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retorna el layout del teclat.
     * @return Layout del teclat.
     */
    public char[][] getLayout() {
        return this.layout;
    }

    /**
     * Retorna l'algorisme utilitzat en el teclat.
     * @return Algorisme del teclat.
     */
    public Algorisme getAlgorisme(){
        return this.algorisme;
    }

    /**
     * Retorna la data de creació del teclat.
     * @return Data de creació del teclat.
     */
    public Date getDataCreacio(){
        return this.dataCreacio;
    }

    /**
     * Retorna les frequencies associades al teclat.
     * @return Frequencies del teclat.
     */
    public Frequencies getFrequencia(){
        return this.frequencies;
    }

    /**
     * Retorna la informació del teclat en un Vector.
     * @return Vector amb la informació del teclat.
     */
    public Vector<String> getInfo() {
        Vector<String> info = new Vector<>();
        info.add(nom);
        info.add(this.algorisme.getNom());
        info.add(dataCreacio.toString());
        info.add(this.frequencies.getNomAlfabet());
        for (char[] fila : layout)
            info.add(new String(fila));
        return info;
    }



    /**
     * Modifica el nom del teclat.
     * @param nom Nou nom del teclat.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifica el layout del teclat.
     * @param layout Nou layout del teclat.
     */
    public void setLayout(char[][] layout) {
        this.layout = layout;
    }

    /**
     * Modifica l'algorisme del teclat.
     * @param algorisme Nou algorisme per al teclat.
     */
    public void setAlgorisme(Algorisme algorisme) {
        this.algorisme = algorisme;
    }

    /**
     * Afegeix o modifica les frequencies del teclat.
     * @param frequencies Noves frequencies per al teclat.
     */
    public void addFrequencia(Frequencies frequencies) {
        this.frequencies = frequencies;
    }

    /**
     * Retorna una còpia superficial (shallow copy) de l'objecte Teclat.
     * @return Una còpia superficial del Teclat.
     */
    public Teclat shallowCopy(){
        return new Teclat(this.nom, this.layout, this.algorisme, this.frequencies);
    }
}
