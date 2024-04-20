package edu.upc.prop.cluster33.excepcions;

/**
 * Excepció llançada quan s'intenta eliminar a l'administrador principal (admin).
 */
public class ExcepcioUsuariEsAdmin extends Excepcio{
    /**
     * Constructor per ExcepcioUsuariEsAdmin.
     */
    public ExcepcioUsuariEsAdmin() {
        super("ERROR: No es pot eliminar admin. L'usuari que s'esta intentant eliminar és l'administrador principal");
    }
}
