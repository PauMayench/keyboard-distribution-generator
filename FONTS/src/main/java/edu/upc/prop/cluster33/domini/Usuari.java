package edu.upc.prop.cluster33.domini;

import edu.upc.prop.cluster33.utils.TMa;

import java.util.HashMap;

/**
 * Classe que representa un usuari del sistema.
 */
public class Usuari {
    /**
     * Nom d'usuari.
     */
    private String username;
    /**
     * Contrasenya de l'usuari.
     */
    private String password;
    /**
     * Enum que indica la mà bona de l'usuari.
     */
    private TMa maBona;
    /**
     * Indica si l'usuari té privilegis d'administrador.
     */
    boolean admin;
    /**
     * Col·lecció que relaciona cada teclat amb el seu identificador.
     */
    private HashMap<Integer, Teclat> teclats;

    /**
     * Constructor buit que inicialitza un usuari amb valors predeterminats.
     */
    public Usuari() {
        this.username = "";
        this.password = "";
        this.maBona = TMa.DRETA;
        this.admin = false;
    }

    /**
     * Constructor per a inicialitzar un usuari amb els atributs especificats.
     * @param username Nom d'usuari.
     * @param password Contrasenya de l'usuari.
     * @param maBona Mà bona de l'usuari (DRETA o ESQUERRA).
     * @param admin Indica si l'usuari té privilegis d'administrador.
     */
    public Usuari(String username, String password, TMa maBona, boolean admin) {
        this.username = username;
        this.password = password;
        this.maBona = maBona;
        this.teclats = new HashMap<Integer, Teclat>();
        this.admin = admin;
    }

    /**
     * Constructor de còpia per a crear un nou usuari a partir d'un altre.
     * @param Us Usuari original del qual es fa la còpia.
     */
    public Usuari(Usuari Us) {
        this.username = Us.username;
        this.password = Us.password;
        this.maBona = Us.maBona.copia();
        this.teclats = new HashMap<Integer, Teclat>(Us.teclats);
        this.admin = Us.admin;
    }

    /**
     * Retorna el nom d'usuari.
     * @return El nom d'usuari.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Retorna la contrasenya de l'usuari.
     * @return La contrasenya.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Retorna la mà bona de l'usuari.
     * @return La mà bona (DRETA o ESQUERRA).
     */
    public TMa getMaBona() {
        return this.maBona;
    }

    /**
     * Verifica si l'usuari és administrador.
     * @return True si és administrador, false en cas contrari.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Retorna els teclats associats a l'usuari.
     * @return Un HashMap amb els teclats de l'usuari.
     */
    public HashMap<Integer, Teclat> getTeclats() {
        return this.teclats;
    }

    /**
     * Verifica si l'usuari té un teclat específic.
     * @param id Identificador del teclat.
     * @return True si l'usuari té el teclat, false en cas contrari.
     */
    public boolean isTeclat(Integer id) {
        return this.teclats.containsKey(id);
    }

    /**
     * Estableix la contrasenya de l'usuari.
     * @param password La nova contrasenya.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Estableix el nom d'usuari.
     * @param username El nou nom d'usuari.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Estableix la mà bona de l'usuari.
     * @param maBona La nova mà bona (DRETA o ESQUERRA).
     */
    public void setMaBona(TMa maBona) {
        this.maBona = maBona;
    }

    /**
     * Afegeix un teclat a l'usuari.
     * @param id Identificador del teclat.
     * @param teclat Objecte Teclat a afegir.
     */
    public void addTeclat(Integer id, Teclat teclat) {
        this.teclats.put(id,teclat);
    }

    /**
     * Elimina un teclat de l'usuari.
     * @param id Identificador del teclat a eliminar.
     */
    public void eliminarTeclat(Integer id) {
        this.teclats.remove(id);
    }

    /**
     * Estableix si l'usuari és administrador.
     * @param b Estat administrador (true o false).
     */
    public void setAdmin(boolean b) {
        this.admin = b;
    }

    /**
     * Estableix els teclats de l'usuari.
     * @param teclats Un HashMap amb els teclats a establir.
     */
    public void setTeclats(HashMap<Integer,Teclat> teclats) {
        this.teclats = teclats;
    }
}
