package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan es fa referència a un nom d'usuari que no existeix en el sistema.
 */
public class ExcepcioUsernameNoExisteix extends Excepcio {
    /**
     * Constructor per defecte per a ExcepcioUsernameNoExisteix.
     */
    public ExcepcioUsernameNoExisteix() {
        super("Username no detectat");
    }
    /**
     * Constructor per a ExcepcioUsernameNoExisteix amb un missatge personalitzat.
     * @param username El nom d'usuari que no s'ha trobat.
     */
    public ExcepcioUsernameNoExisteix(String username) {
        super(String.format("No existeix cap usuari amb username %s al sistema", username));
    }
}
