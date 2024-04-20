package edu.upc.prop.cluster33.domini;

import java.text.Normalizer;
import java.util.*;
import edu.upc.prop.cluster33.excepcions.*;
/**
 * La classe Frequencies s'encarrega de gestionar les freqüències de les paraules en un determinat text o conjunt de paraules.
 * Aquesta classe és clau per a operacions que involucren la freqüència de les paraules, com l'optimització de teclats o l'anàlisi de textos.
 */
public class Frequencies {
    /**
     * Llista que conté les freqüències de les paraules. Les claus són les paraules i els valors són les seves freqüències.
     */
    private TreeMap<String, Integer> llistaFrequencies;
    /**
     * Alfabet associat a les freqüències.
     */
    private Alfabet alfabet;
    /**
     * Nombre total de paraules en el conjunt analitzat.
     */
    private int numero_paraules;

    /**
     * Constructor per defecte.
     * Inicialitza un nou TreeMap per a llistaFrequencies, assigna un nou Alfabet i establir numero_paraules a 0.
     */
    public Frequencies() {
        llistaFrequencies = new TreeMap<>();
        alfabet = new Alfabet();
        numero_paraules = 0;
    }

    /**
     * Constructor amb paràmetres.
     * @param llistaFrequencies TreeMap amb les paraules i les seves freqüències.
     * @param alfabet Alfabet utilitzat en el text o conjunt de paraules.
     * @param numero_paraules Nombre total de paraules diferents.
     */
    public Frequencies(TreeMap<String, Integer> llistaFrequencies, Alfabet alfabet, int numero_paraules) {
        this.llistaFrequencies = llistaFrequencies;
        this.alfabet = alfabet;
        this.numero_paraules = numero_paraules;
    }

    /**
     * Retorna la llista de freqüències.
     * @return TreeMap amb les paraules i les seves freqüències.
     */
    public TreeMap<String, Integer> getLlistaFrequencies() {
        return llistaFrequencies;
    }

    /**
     * Retorna el nombre total de paraules diferents.
     * @return Nombre total de paraules.
     */
    public int getNumero_paraules() {
        return numero_paraules;
    }

    /**
     * Retorna l'alfabet utilitzat.
     * @return Instància de l'alfabet.
     */
    public Alfabet getAlfabet() {
        return alfabet;
    }

    /**
     * Retorna el nom de l'alfabet.
     * @return Nom de l'alfabet.
     */
    public String getNomAlfabet() {
        return alfabet.getNom();
    }

    /**
     * Genera les freqüències a partir d'una cadena de text.
     * @param s Cadena de text a processar.
     * @param llistaAlfabets Array d'alfabets possibles.
     * @throws ExcepcioFrequencies si es produeix algun error en el processament.
     */
    public void genera(String s, Alfabet[] llistaAlfabets) throws ExcepcioFrequencies/*ExcepcioTextBuit, ExcepcioMesDeUnAlfabetAlhora, ExcepcioAlfabetNoTrobat*/{
        String prohibits = " |/!¡¿?@#$%&*+.,:_=)([]{}ªº<>1234567890ao։ \n";
        String text = s.toUpperCase();
        text = Normalizer.normalize(text, Normalizer.Form.NFKD);
        text = text.replaceAll("\\p{M}", "");
        int mida = text.length();
        if (mida == 0) throw new ExcepcioFrequencies("El text/llistat de frequencies proporcionat no té contingut (està buit).");
        int j = 0;
        boolean firstCharacterFound = false;
        llistaFrequencies = new TreeMap<>();
        char ch = ' ';
        while(j < mida) {
            ch = text.charAt(j);
            if (Character.isLetter(ch)) {
                if (!firstCharacterFound) {
                    firstCharacterFound = true;
                    int it = 0;
                    boolean alfabetTrobat = false;
                    while (!alfabetTrobat && it < llistaAlfabets.length) {
                        if (llistaAlfabets[it].getAlfabet().contains(""+ch)) {
                            alfabetTrobat = true;
                            alfabet = llistaAlfabets[it];
                        }
                        ++it;
                        if (it == llistaAlfabets.length && !alfabetTrobat) throw new ExcepcioFrequencies("L'alfabet del text/llistat de frequencies no ha sigut reconegut: no existeix al sistema.");
                    }
                } else {
                    if (!alfabet.getAlfabet().contains(""+ch)) throw new ExcepcioFrequencies("El text/llistat de frequencies proporcionat conté caràcters de més d'un alfabet alhora.");
                }
            }
            ++j;
        }
        if (alfabet.getNom().equals("Cirilic")) {
            /*text = Normalizer.normalize(text, Normalizer.Form.NFKD);
            text = text.replaceAll("\\p{M}", "");*/
            text = s.toUpperCase();
        }
        String paraulaProces = "";
        for (int i = 0; i < mida; ++i) {
            Character actual = text.charAt(i);
            if (!prohibits.contains(""+actual)) {
                paraulaProces += actual;
            }
            if (prohibits.contains(""+actual) || (i == mida-1)) {
                String key = paraulaProces;
                paraulaProces = "";
                if (!key.isEmpty()) {
                    if (llistaFrequencies.containsKey(key)) {
                        Integer oldv = llistaFrequencies.get(key);
                        llistaFrequencies.replace(key, oldv, oldv+1);
                    } else {
                        llistaFrequencies.put(key, 1);
                    }
                }
            }
        }
        numero_paraules = llistaFrequencies.size();
    }

    /**
     * Llegeix i processa un llistat de freqüències.
     * @param t Text amb el llistat de freqüències.
     * @param llistaAlfabets Array d'alfabets possibles.
     * @throws ExcepcioFrequencies si es produeix algun error en el processament.
     */
    public void llegir(String t, Alfabet[] llistaAlfabets) throws ExcepcioFrequencies/*ExcepcioFormatFrequenciesIncorrecte, ExcepcioTextBuit, ExcepcioMesDeUnAlfabetAlhora, ExcepcioAlfabetNoTrobat*/{
        String s = t.toUpperCase();
        s = Normalizer.normalize(s, Normalizer.Form.NFKD);
        s = s.replaceAll("\\p{M}", "");
        if (s.isEmpty()) throw new ExcepcioFrequencies("El text/llistat de frequencies proporcionat no té contingut (està buit).");
        int mida = s.length();
        int j = 0;
        boolean firstCharacterFound = false;
        char ch = ' ';
        while(j < mida) {
            ch = s.charAt(j);
            if (Character.isLetter(ch)) {
                if (!firstCharacterFound) {
                    firstCharacterFound = true;
                    int it = 0;
                    boolean alfabetTrobat = false;
                    while (!alfabetTrobat && it < llistaAlfabets.length) {
                        if (llistaAlfabets[it].getAlfabet().contains(""+ch)) {
                            alfabetTrobat = true;
                            alfabet = llistaAlfabets[it];
                        }
                        ++it;
                        if (it == llistaAlfabets.length && !alfabetTrobat) throw new ExcepcioFrequencies("L'alfabet del text/llistat de frequencies no ha sigut reconegut: no existeix al sistema.");
                    }
                } else {
                    if (!alfabet.getAlfabet().contains(""+ch)) throw new ExcepcioFrequencies("El text/llistat de frequencies proporcionat conté caràcters de més d'un alfabet alhora.");
                }
            }
            ++j;
        }
        if (alfabet.getNom().equals("Cirilic")) s = t.toUpperCase();
        /*Character ch = s.charAt(0);
        alfabet.determinaAlfabet(ch);*/
        llistaFrequencies = new TreeMap<>();
        String prohibits = " |/!¡¿?@#$%&*+.,:_=)([]{}ªº<>";
        Boolean guioPassat = false;
        boolean startedWriting = false;
        String paraulaActual = "";
        String valorActual = "";
        for (int i = 0; i < s.length(); ++i) {
            Character actual = s.charAt(i);
            if (!startedWriting) {
                if (Character.isLetter(actual)) startedWriting = true;
                paraulaActual += actual;
            } else {
                if (prohibits.contains(""+actual)) {
                    numero_paraules = 0;
                    throw new ExcepcioFrequencies("El format del llistat de frequencies proporcionat es incorrecte.");
                }
                if (actual != '-' && !guioPassat) {
                    paraulaActual += actual;
                }
                else if (actual == '-') guioPassat = true;
                else if (actual != '\n' && actual != ';') {
                    valorActual += actual;
                }
                else {
                    int valor = Integer.parseInt(valorActual);
                    if (!llistaFrequencies.containsKey(paraulaActual) && !paraulaActual.isEmpty()) {
                        llistaFrequencies.put(paraulaActual, valor);
                    }
                    paraulaActual = "";
                    valorActual = "";
                    guioPassat = false;
                }
            }
        }
        numero_paraules = llistaFrequencies.size();
    }
}