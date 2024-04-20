package edu.upc.prop.cluster33.excepcions;

/**
 * Excepció llançada quan s'intenta eliminar a l'administrador principal (admin).
 */
public class ExcepcioIntentEsborrarAdmin extends Excepcio{
    /**
     * Constructor per ExcepcioUsuariEsAdmin.
     */
    public ExcepcioIntentEsborrarAdmin() {
        super("S'esta intentant esborrar al administrador del sistema");
    }
}
