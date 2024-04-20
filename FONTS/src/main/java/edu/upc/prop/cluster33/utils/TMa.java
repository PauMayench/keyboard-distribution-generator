package edu.upc.prop.cluster33.utils;

/**
 * Enumeració que representa la mà predominant d'un usuari.
 */
public enum TMa {
    /**
     * Constant per a la mà dreta.
     */
    DRETA,

    /**
     * Constant per a la mà esquerra.
     */
    ESQUERRA;

    /**
     * Retorna una còpia de l'actual instància de TMa.
     * @return Una nova instància de TMa que és una còpia de l'actual.
     */
    public TMa copia() {
        return this;
    }
}
