package edu.upc.prop.cluster33.excepcions;

/**
 * Excepció llançada quan es detecta que un nom d'usuari ja està en ús per un altre usuari.
 */
public class ExcepcioUsernameJaExistent extends Excepcio {
    /**
     * Constructor per defecte per a ExcepcioUsernameJaExistent.
     */
    public ExcepcioUsernameJaExistent() {
        super("Un altre usuari amb el mateix username ja existeix. Sisplau escolleixi un altre");
    }
    /**
     * Constructor per a ExcepcioUsernameJaExistent amb el username.
     * @param username El nom d'usuari que causa el conflicte.
     */
    public ExcepcioUsernameJaExistent(String username) {
        super(String.format("Un altre usuari amb username %s ja existeix. Sisplau escolleixi un altre", username));
    }


}