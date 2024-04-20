package edu.upc.prop.cluster33.excepcions;
/**
 * Excepció llançada quan no es poden guardar les dades correctament.
 */
public class ExcepcioErrorGuardantDades extends Excepcio{
    /**
     * Constructor per ExcepcioErrorGuardantDades.
     */
    public ExcepcioErrorGuardantDades() {
        super("Hi ha hagut un error guardant les dades. Sisplau, torni a intentar-ho");
    }
}
