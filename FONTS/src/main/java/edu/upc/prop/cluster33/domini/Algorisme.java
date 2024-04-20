package edu.upc.prop.cluster33.domini;
/**
 * Classe abstracta que defineix l'estructura bàsica d'un algorisme per a la generació de layouts de teclats.
 */
public abstract class Algorisme {
    /**
     * Genera i retorna un layout de teclat optimitzat basat en un objecte de freqüències i les dimensions especificades.
     *
     * @param frequenciesObject Objecte que conté les freqüències dels caràcters.
     * @param columnes    Nombre de columnes del teclat.
     * @param files       Nombre de files del teclat.
     * @return Un array bidimensional de caràcters que representa el layout del teclat.
     */
    public abstract char[][] generarLayout(Frequencies frequenciesObject, int columnes, int files);
    /**
     * Retorna el nom de l'algorisme.
     *
     * @return El nom de l'algorisme.
     */
    public abstract String getNom();
    /**
     * Reinicialitza els valors de l'algorisme per a preparar-lo per a noves dades.
     */
    public abstract void init();

}
