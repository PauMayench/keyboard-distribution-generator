package edu.upc.prop.cluster33.excepcions;

/**
 * Excepció llançada quan un usuari no administrador intenta realitzar una tasca d'administrador.
 */
public class ExcepcioUsuariNoEsAdmin extends Excepcio{
    /**
     * Constructor per ExcepcioUsuariNoEsAdmin.
     */
    public ExcepcioUsuariNoEsAdmin() {
        super("L'usuari logejat no te permisos d'administrador");
    }
}
