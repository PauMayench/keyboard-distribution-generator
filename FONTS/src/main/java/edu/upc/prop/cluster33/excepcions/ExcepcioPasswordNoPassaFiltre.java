package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan la contrasenya introduïda no compleix els requisits de seguretat (minim un caracter i un nombre, llargada minim 5).
 */
public class ExcepcioPasswordNoPassaFiltre extends Excepcio{
    /**
     * Constructor per ExcepcioPasswordNoPassaFiltre.
     */
    public ExcepcioPasswordNoPassaFiltre() {
        super("El password introduit no ha passat el filtre de seguretat. Sisplau, seleccioni un altre");
    }
}
