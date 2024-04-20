package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan la combinació de nom d'usuari i contrasenya proporcionada no és correcta.
 */
public class ExcepcioUsernamePasswordIncorrectes extends Excepcio{
    /**
     * Constructor per a ExcepcioUsernamePasswordIncorrectes.
     */
    public ExcepcioUsernamePasswordIncorrectes() {
        super("L'usuari o el password introduits no coincideixen, l'esborrat no s'ha realitzat");
    }

}
