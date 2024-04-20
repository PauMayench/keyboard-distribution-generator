package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan la contrasenya introduïda no és correcta.
 */
public class ExcepcioPasswordIncorrecte extends Excepcio {
    /**
     * Constructor per ExcepcioPasswordIncorrecte.
     */
    public ExcepcioPasswordIncorrecte() {
        super("El password introduit no es correcte");
    }


}
