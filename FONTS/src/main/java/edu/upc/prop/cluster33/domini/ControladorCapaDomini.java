package edu.upc.prop.cluster33.domini;

import edu.upc.prop.cluster33.excepcions.*;
import edu.upc.prop.cluster33.persistencia.GestorAlfabets;
import edu.upc.prop.cluster33.persistencia.GestorAlgorismes;
import edu.upc.prop.cluster33.persistencia.GestorTexts;
import edu.upc.prop.cluster33.persistencia.GestorUsuaris;
import edu.upc.prop.cluster33.utils.TMa;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Classe que actua com a controlador de la capa de domini, gestionant usuaris, textos, algorismes i alfabets.
 */
public class ControladorCapaDomini {
    /**
     * Gestor d'usuaris de l'aplicació.
     */
    private final GestorUsuaris gestorUsuaris;
    /**
     * Gestor de textos de l'aplicació.
     */
    private final GestorTexts gestorTexts;
    /**
     * Gestor d'algorismes de l'aplicació.
     */
    private final GestorAlgorismes gestorAlgorismes;
    /**
     * Gestor d'alfabets de l'aplicació.
     */
    private final GestorAlfabets gestorAlfabets;
    /**
     * Usuari actualment actiu en l'aplicació.
     */
    private Usuari usuari;
    /**
     * Número de files per a la generació de layouts de teclats.
     */
    static final int FILES = 6;
    /**
     * Número de columnes per a la generació de layouts de teclats.
     */
    static final int COLUMNES = 7;

    /**
     * Constructor de ControladorCapaDomini. Inicialitza els gestors necessaris.
     *
     * @throws ExcepcioLlegintDeDisc           Si es produeix un error llegint de disc.
     * @throws ExcepcioErrorDurantLaCreacio   Si es produeix un error durant la creació.
     */
    public ControladorCapaDomini() throws ExcepcioLlegintDeDisc, ExcepcioErrorDurantLaCreacio {

            gestorUsuaris = new GestorUsuaris();
            gestorTexts = new GestorTexts();
            gestorAlgorismes = new GestorAlgorismes();
            gestorAlfabets = new GestorAlfabets();

    }

    // LOGIN REGISTRE LOGOUT
    /**
     * Inicia sessió amb un usuari existent.
     *
     * @param username El nom d'usuari.
     * @param password La contrasenya de l'usuari.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioPasswordIncorrecte Si la contrasenya és incorrecta.
     * @throws ExcepcioLlegintDeDisc Si hi ha un problema llegint dades del disc.
     */
    public void login(String username, String password) throws ExcepcioUsernameNoExisteix, ExcepcioPasswordIncorrecte, ExcepcioLlegintDeDisc {
        Usuari user = gestorUsuaris.getUsuari(username);
        if (user == null) throw new ExcepcioUsernameNoExisteix();
        String pass = user.getPassword();
        if (pass != null && pass.equals(password)) {
            usuari = user;
        }
        else throw new ExcepcioPasswordIncorrecte();
    }

    /**
     * Registra un nou usuari en el sistema.
     *
     * @param username  El nom d'usuari.
     * @param password  La contrasenya.
     * @param maBona    La mà dominant de l'usuari.
     * @param admin     Indica si l'usuari és administrador.
     * @throws ExcepcioUsernameJaExistent Si el nom d'usuari ja existeix.
     * @throws ExcepcioPasswordNoPassaFiltre Si la contrasenya no compleix els criteris de seguretat.
     * @throws ExcepcioFormatMaIncorrecte Si el format de la mà bona no és correcte.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació de l'usuari.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint dades del disc.
     */
    public void registre(String username, String password, String maBona, boolean admin) throws ExcepcioUsernameJaExistent, ExcepcioPasswordNoPassaFiltre, ExcepcioFormatMaIncorrecte, ExcepcioErrorDurantLaCreacio, ExcepcioLlegintDeDisc {
        if (gestorUsuaris.existeixUsuari(username)) throw new ExcepcioUsernameJaExistent();
        if (!filtrePassword(password)) throw new ExcepcioPasswordNoPassaFiltre(); //format contrasenya no correcte

        //creem usuari
        TMa tipusMa = TMa.DRETA ;
        if (maBona.equals("Esquerra")) tipusMa = TMa.ESQUERRA;
        else if(!maBona.equals("Dreta")) throw new ExcepcioFormatMaIncorrecte();
        Usuari user = new Usuari(username, password, tipusMa, admin);

        try {
            gestorUsuaris.crearUsuari(user);
        }
        catch(Exception e){
            throw new ExcepcioErrorDurantLaCreacio();
        }
        usuari = user;
    }

    /**
     * Comprova si la contrasenya passa el filtre de seguretat.
     *
     * @param password La contrasenya a comprovar.
     * @return True si la contrasenya passa el filtre, false en cas contrari.
     */
    private boolean filtrePassword(String password){
        if (password.length() < 5) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasNumber = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            }

            if (hasLetter && hasNumber) {
                return true;
            }
        }

        return false;
    }

    /**
     * Canvia la contrasenya de l'usuari actual.
     *
     * @param oldpassword La contrasenya actual.
     * @param newPassword La nova contrasenya.
     * @throws ExcepcioPasswordIncorrecte Si l'antiga contrasenya no és correcta.
     * @throws ExcepcioPasswordNoPassaFiltre Si la nova contrasenya no passa el filtre de seguretat.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació de la contrasenya.
     */
    public void canviarPassword(String oldpassword, String newPassword) throws ExcepcioPasswordIncorrecte, ExcepcioPasswordNoPassaFiltre,ExcepcioErrorDurantModificacio {
        if(!oldpassword.equals(usuari.getPassword()))
            throw new ExcepcioPasswordIncorrecte();

        if (!filtrePassword(newPassword))
            throw new ExcepcioPasswordNoPassaFiltre();
        try {
            gestorUsuaris.canviarPassword(usuari.getUsername(), newPassword);
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantModificacio();
        }
        usuari.setPassword(newPassword);
    }

    /**
     * Canvia la mà bona de l'usuari actual.
     *
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació de la mà bona.
     */
    public void canviarMaBona() throws ExcepcioErrorDurantModificacio  {
         
        TMa maNova = TMa.ESQUERRA;;
        if ( usuari.getMaBona() == TMa.ESQUERRA){
            maNova = TMa.DRETA;
        }
        try {
            gestorUsuaris.canviarMaBona(usuari.getUsername(), maNova);
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantModificacio();
        }
        usuari.setMaBona(maNova);
    }

    /**
     * Tanca la sessió de l'usuari actual.
     */
    public void logout() {
        usuari = null;
    }

    /**
     * Elimina l'usuari actual del sistema.
     *
     * @param username El nom d'usuari de l'usuari a eliminar.
     * @param password La contrasenya de l'usuari a eliminar.
     * @throws ExcepcioUsernamePasswordIncorrectes Si el nom d'usuari o la contrasenya no coincideixen amb l'usuari actual.
     * @throws ExcepcioIntentEsborrarAdmin Si es tracta d'un intent d'esborrar un compte d'administrador.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'eliminació de l'usuari.
     */
    public void eliminarPropiUsuari(String username,String password) throws ExcepcioUsernamePasswordIncorrectes, ExcepcioIntentEsborrarAdmin, ExcepcioErrorDurantEsborrat {
        if (!username.equals(usuari.getUsername()) || !password.equals(usuari.getPassword()))
            throw new ExcepcioUsernamePasswordIncorrectes();
        if (usuari.getUsername().equals("admin"))
            throw new ExcepcioIntentEsborrarAdmin();
        try {
            gestorUsuaris.eliminarUsuari(usuari.getUsername());
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantEsborrat();
        }
        usuari = null;
    }

    /**
     * Retorna la mà bona de l'usuari actual.
     *
     * @return Un string que representa la mà bona de l'usuari ("Dreta" o "Esquerra").
     */
    public String getMaBona()   {
         
        return (usuari.getMaBona() == TMa.DRETA) ? "Dreta" : "Esquerra";
    }

    /**
     * Comprova si l'usuari actual és administrador.
     *
     * @return True si l'usuari actual és administrador, false en cas contrari.
     */
    public boolean isAdmin()  {

        return usuari.isAdmin();
    }

    //GESTIO DE TECLATS

    /**
     * Retorna un HashMap amb l'ID i el nom de cada teclat. L'usuari podrà seleccionar el teclat per aquest ID.
     *
     * @return Un HashMap amb l'ID i el nom de cada teclat.
     */
    public HashMap<Integer,String> getTeclats()   {
         
        HashMap<Integer,String> teclatsOut = new HashMap<>();
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        for(Map.Entry<Integer, Teclat> entry : teclats.entrySet())
            teclatsOut.put(entry.getKey(), entry.getValue().getNom());
        return teclatsOut;
    }

    /**
     * Retorna un vector de strings amb les propietats del teclat especificat.
     *
     * @param id L'ID del teclat a consultar.
     * @return Un vector de strings amb les propietats del teclat.
     * @throws ExcepcioIdNoValid Si l'ID proporcionat no és vàlid.
     */
    public Vector<String> getTeclat(Integer id) throws ExcepcioIdNoValid  {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(id);
        if(teclat == null) throw new ExcepcioIdNoValid();
        return teclat.getInfo();
    }

    /**
     * Retorna el nom de l'algorisme utilitzat per un teclat específic.
     *
     * @param idTeclat L'ID del teclat del qual es vol obtenir el nom de l'algorisme.
     * @return El nom de l'algorisme del teclat.
     * @throws ExcepcioIdNoValid Si l'ID proporcionat no és vàlid.
     */
    public String getAlgorismeTeclat(Integer idTeclat) throws ExcepcioIdNoValid  {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(idTeclat);
        if(teclat == null) throw new ExcepcioIdNoValid();
        return teclat.getInfo().elementAt(1);
    }

    /**
     * Retorna el nom del teclat especificat.
     *
     * @param idTeclat L'ID del teclat a consultar.
     * @return El nom del teclat.
     * @throws ExcepcioIdNoValid Si l'ID proporcionat no és vàlid.
     */
    public String getNomTeclat(Integer idTeclat) throws ExcepcioIdNoValid  {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(idTeclat);
        if(teclat == null) throw new ExcepcioIdNoValid();
        return teclat.getNom();
    }

    /**
     * Elimina un teclat específic de l'usuari actual.
     *
     * @param idTeclat L'ID del teclat a eliminar.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'eliminació del teclat.
     * @throws ExcepcioIdNoValid Si l'ID proporcionat no és vàlid.
     */
    public void eliminarTeclat(Integer idTeclat) throws ExcepcioErrorDurantEsborrat, ExcepcioIdNoValid  {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        try {
            gestorUsuaris.removeTeclat(usuari.getUsername(), idTeclat);
        }
        catch(ExcepcioIdNoValid e) {
            throw new ExcepcioIdNoValid();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantEsborrat();
        }
        teclats.remove(idTeclat);
    }

    // FUNCIONS AUXILIARS GET PER A PRESENTACIO PER A CREAR UN TECLAT

    /**
     * Retorna un HashMap amb els IDs i els noms dels algorismes disponibles.
     *
     * @return Un HashMap on les claus són els IDs i els valors són els noms dels algorismes.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public HashMap<Integer,String> getAlgorismes() throws ExcepcioLlegintDeDisc {
        HashMap<Integer,String> algorismesOut = new HashMap<>();
        HashMap<Integer,Algorisme> algorismes = gestorAlgorismes.getAlgorismes();
        for(Map.Entry<Integer, Algorisme> entry : algorismes.entrySet())
            algorismesOut.put(entry.getKey(), entry.getValue().getNom());
        return algorismesOut;
    }


    /**
     * Retorna un HashMap amb els IDs i els noms dels texts públics disponibles.
     *
     * @return Un HashMap on les claus són els IDs i els valors són els noms dels texts públics.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public HashMap<Integer,String> getTextsPublics() throws ExcepcioLlegintDeDisc {
        HashMap<Integer,String> textsOut = new HashMap<>();
        HashMap<Integer,TextPublic> textsPublics = gestorTexts.getTextsPublics();
        for(Map.Entry<Integer, TextPublic> entry : textsPublics.entrySet())
            textsOut.put(entry.getKey(), entry.getValue().getNom());
        return textsOut;
    }

    /**
     * Retorna un HashMap amb els IDs i els noms dels texts predefinits disponibles.
     *
     * @return Un HashMap on les claus són els IDs i els valors són els noms dels texts predefinits.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public HashMap<Integer,String> getTextsPredefinits() throws ExcepcioLlegintDeDisc {
        HashMap<Integer,String> textsOut = new HashMap<>();
        HashMap<Integer,TextPredefinit> textsPredefinits = gestorTexts.getTextsPredefinits();
        for(Map.Entry<Integer, TextPredefinit> entry : textsPredefinits.entrySet())
            textsOut.put(entry.getKey(), entry.getValue().getNom());
        return textsOut;
    }


    //CREAR TECLATS

    /**
     * Genera les frequencies i crida a la funció per crear un teclat.
     *
     * @param algorismeId        L'ID de l'algorisme a utilitzar.
     * @param frequenciesString  Una cadena de text amb les frequencies.
     * @param nom                El nom del teclat a crear.
     * @throws ExcepcioIdNoValid Si l'ID de l'algorisme no és vàlid.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del teclat.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb aquest nom.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les frequencies proporcionades.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void crearTeclatFrequencies(Integer algorismeId, String frequenciesString, String nom) throws ExcepcioIdNoValid, ExcepcioErrorDurantLaCreacio, ExcepcioNomTeclatJaExisteix, ExcepcioFrequencies, ExcepcioLlegintDeDisc {
        Frequencies frequencies = new Frequencies();
        Alfabet[] alfabets = gestorAlfabets.getAlfabets();
        frequencies.llegir(frequenciesString, alfabets);
        crearTeclat(nom, algorismeId, frequencies);
    }

    /**
     * Genera les frequencies a partir d'un fitxer de text d'usuari i crida a la funció per crear un teclat.
     *
     * @param algorismeId     L'ID de l'algorisme a utilitzar.
     * @param contingutText   El contingut del text per generar les frequencies.
     * @param nom             El nom del teclat a crear.
     * @throws ExcepcioIdNoValid Si l'ID de l'algorisme no és vàlid.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del teclat.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb aquest nom.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les frequencies generades.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void crearTeclatFitxerTextUsuari(Integer algorismeId, String contingutText, String nom) throws ExcepcioIdNoValid, ExcepcioErrorDurantLaCreacio, ExcepcioNomTeclatJaExisteix, ExcepcioFrequencies, ExcepcioLlegintDeDisc {

        Frequencies frequencies = new Frequencies();
        Alfabet[] alfabets = gestorAlfabets.getAlfabets();

        frequencies.genera(contingutText, alfabets);
        crearTeclat(nom, algorismeId, frequencies);

    }

    /**
     * Genera les frequencies a partir d'un text públic i crida a la funció per crear un teclat.
     *
     * @param algorismeId     L'ID de l'algorisme a utilitzar.
     * @param idTextPublic    L'ID del text públic per generar les frequencies.
     * @param nom             El nom del teclat a crear.
     * @throws ExcepcioIdNoValid Si l'ID de l'algorisme o del text no és vàlid.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del teclat.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb aquest nom.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les frequencies generades.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void crearTeclatFitxerTextPublic(Integer algorismeId, Integer idTextPublic, String nom) throws ExcepcioIdNoValid, ExcepcioErrorDurantLaCreacio, ExcepcioNomTeclatJaExisteix, ExcepcioFrequencies, ExcepcioLlegintDeDisc {

        Text text = gestorTexts.getTextPublic(idTextPublic);

        String contentsFitxer;
        contentsFitxer = text.llegirContingut();

        Frequencies frequencies = new Frequencies();
        Alfabet[] alfabets = gestorAlfabets.getAlfabets();

        frequencies.genera(contentsFitxer, alfabets);
        crearTeclat(nom, algorismeId, frequencies);

    }

    /**
     * Genera les frequencies a partir d'un text predefinit i crida a la funció per crear un teclat.
     *
     * @param algorismeId        L'ID de l'algorisme a utilitzar.
     * @param idTextPredefinit   L'ID del text predefinit per generar les frequencies.
     * @param nom                El nom del teclat a crear.
     * @throws ExcepcioIdNoValid Si l'ID de l'algorisme o del text no és vàlid.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del teclat.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb aquest nom.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les frequencies generades.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void crearTeclatFitxerTextPredefinit(Integer algorismeId, Integer idTextPredefinit, String nom) throws ExcepcioIdNoValid, ExcepcioErrorDurantLaCreacio, ExcepcioNomTeclatJaExisteix, ExcepcioFrequencies, ExcepcioLlegintDeDisc {
        Text text = gestorTexts.getTextPredefinit(idTextPredefinit);

        String contentsFitxer;
        contentsFitxer = text.llegirContingut();

        Frequencies frequencies = new Frequencies();
        Alfabet[] alfabets = gestorAlfabets.getAlfabets();

        frequencies.genera(contentsFitxer, alfabets);
        crearTeclat(nom, algorismeId, frequencies);
    }

    /**
     * Crea un teclat amb les especificacions donades.
     *
     * @param nom          El nom del teclat a crear.
     * @param algorismeId  L'ID de l'algorisme a utilitzar.
     * @param frequencies  L'objecte Frequencies amb les frequencies del teclat.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del teclat.
     * @throws ExcepcioIdNoValid Si l'ID de l'algorisme no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb aquest nom.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    private void crearTeclat(String nom, Integer algorismeId, Frequencies frequencies) throws ExcepcioErrorDurantLaCreacio, ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioLlegintDeDisc {

        Algorisme algorisme = gestorAlgorismes.getAlgorisme(algorismeId);
        algorisme.init();

        char[][] layout = algorisme.generarLayout(frequencies, COLUMNES, FILES);

        Teclat teclat = new Teclat(nom, layout, algorisme, frequencies);

        try {
            gestorUsuaris.crearTeclat(usuari.getUsername(), teclat);
        }
        catch(ExcepcioNomTeclatJaExisteix e) {
            throw new ExcepcioNomTeclatJaExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantLaCreacio();
        }

        try {
            usuari = gestorUsuaris.getUsuari(usuari.getUsername());
        }
        catch(ExcepcioUsernameNoExisteix e){
            throw new ExcepcioErrorDurantLaCreacio();
        }

    }

    //MODIFICAR TECLATS


    /**
     * Modifica el nom d'un teclat existent.
     *
     * @param idTeclat  L'ID del teclat a modificar.
     * @param nouNom    El nou nom per al teclat.
     * @throws ExcepcioIdNoValid Si l'ID del teclat no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb el nou nom.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del teclat.
     */
    public void modificarTeclatNom(Integer idTeclat, String nouNom) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioErrorDurantModificacio  {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(idTeclat);
        Teclat nouTeclat = teclat.shallowCopy();
        nouTeclat.setNom(nouNom);

        gestorUsuaris.modificarTeclatNom(usuari.getUsername(), idTeclat, nouNom);
        teclats.put(idTeclat, nouTeclat);

    }

    /**
     * Modifica l'algorisme d'un teclat existent.
     *
     * @param idTeclat        L'ID del teclat a modificar.
     * @param idNouAlgorsime  L'ID del nou algorisme a utilitzar.
     * @throws ExcepcioIdNoValid Si l'ID del teclat o del nou algorisme no és vàlid.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del teclat.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void modificarTeclatAlgorisme(Integer idTeclat, Integer idNouAlgorsime) throws ExcepcioIdNoValid, ExcepcioErrorDurantModificacio, ExcepcioLlegintDeDisc {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(idTeclat);

        Teclat nouTeclat = teclat.shallowCopy();
        Algorisme algorisme = gestorAlgorismes.getAlgorisme(idNouAlgorsime);

        char[][] layout = algorisme.generarLayout(nouTeclat.getFrequencia(), COLUMNES, FILES);
        nouTeclat.setLayout(layout);
        nouTeclat.setAlgorisme(algorisme);
        try {
            gestorUsuaris.modificarTeclat(usuari.getUsername(), idTeclat, nouTeclat);
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantModificacio();
        }

        teclats.put(idTeclat, nouTeclat);
    }


    /**
     * Modifica les frequencies d'un teclat existent.
     *
     * @param idTeclat           L'ID del teclat a modificar.
     * @param frequenciesString  La cadena de text amb les noves frequencies.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les frequencies.
     * @throws ExcepcioIdNoValid Si l'ID del teclat no és vàlid.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del teclat.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void modificarTeclatFrequencies(Integer idTeclat, String frequenciesString) throws ExcepcioFrequencies, ExcepcioIdNoValid, ExcepcioErrorDurantModificacio, ExcepcioLlegintDeDisc {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(idTeclat);
        if (teclat == null) throw new ExcepcioIdNoValid();

        Teclat nouTeclat = teclat.shallowCopy();
        Algorisme algorisme = nouTeclat.getAlgorisme();

        Alfabet[] alfabets = gestorAlfabets.getAlfabets();
        Frequencies frequencies = new Frequencies();
        frequencies.llegir(frequenciesString, alfabets);

        char[][] layout = algorisme.generarLayout(frequencies, COLUMNES, FILES);
        nouTeclat.setLayout(layout);
        try {
            gestorUsuaris.modificarTeclat(usuari.getUsername(), idTeclat, nouTeclat);
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantModificacio();
        }
        teclats.put(idTeclat, nouTeclat);
    }


    /**
     * Modifica les frequencies d'un teclat existent utilitzant un text d'usuari.
     *
     * @param idTeclat            L'ID del teclat a modificar.
     * @param contingutsFitxer    El contingut del text d'usuari.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les frequencies generades.
     * @throws ExcepcioIdNoValid Si l'ID del teclat no és vàlid.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del teclat.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void modificarTeclatFrequenciesFitxerTextUsuari(Integer idTeclat, String contingutsFitxer) throws ExcepcioFrequencies, ExcepcioIdNoValid, ExcepcioErrorDurantModificacio, ExcepcioLlegintDeDisc {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(idTeclat);
        if (teclat == null) throw new ExcepcioIdNoValid();

        Teclat nouTeclat = teclat.shallowCopy();
        Algorisme algorisme = nouTeclat.getAlgorisme();

        Alfabet[] alfabets = gestorAlfabets.getAlfabets();
        Frequencies frequencies = new Frequencies();
        frequencies.genera(contingutsFitxer, alfabets);

        char[][] layout = algorisme.generarLayout(frequencies, COLUMNES, FILES);
        nouTeclat.setLayout(layout);
        try {
            gestorUsuaris.modificarTeclat(usuari.getUsername(), idTeclat, nouTeclat);
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantModificacio();
        }
        teclats.put(idTeclat, nouTeclat);
    }


    /**
     * Modifica les frequencies d'un teclat existent utilitzant un text públic.
     *
     * @param idTeclat  L'ID del teclat a modificar.
     * @param idText    L'ID del text públic.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les frequencies generades.
     * @throws ExcepcioIdNoValid Si l'ID del teclat o del text no és vàlid.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del teclat.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void modificarTeclatFrequenciesFitxerTextPublic(Integer idTeclat, Integer idText) throws ExcepcioFrequencies, ExcepcioIdNoValid, ExcepcioErrorDurantModificacio, ExcepcioLlegintDeDisc {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(idTeclat);
        if (teclat == null) throw new ExcepcioIdNoValid();

        Text text = gestorTexts.getTextPublic(idText);
        String contingutsFitxer =  text.llegirContingut();

        Teclat nouTeclat = teclat.shallowCopy();
        Algorisme algorisme = nouTeclat.getAlgorisme();

        Alfabet[] alfabets = gestorAlfabets.getAlfabets();
        Frequencies frequencies = new Frequencies();
        frequencies.genera(contingutsFitxer, alfabets);

        char[][] layout = algorisme.generarLayout(frequencies, COLUMNES, FILES);
        nouTeclat.setLayout(layout);


        try {
            gestorUsuaris.modificarTeclat(usuari.getUsername(), idTeclat, nouTeclat);
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantModificacio();
        }
        teclats.put(idTeclat, nouTeclat);
    }

    /**
     * Modifica les frequencies d'un teclat existent utilitzant un text predefinit.
     *
     * @param idTeclat  L'ID del teclat a modificar.
     * @param idText    L'ID del text predefinit.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les frequencies generades.
     * @throws ExcepcioIdNoValid Si l'ID del teclat o del text no és vàlid.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del teclat.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void modificarTeclatFrequenciesFitxerTextPredefinit(Integer idTeclat, Integer idText) throws ExcepcioFrequencies, ExcepcioIdNoValid, ExcepcioErrorDurantModificacio, ExcepcioLlegintDeDisc {
         
        HashMap<Integer,Teclat> teclats = usuari.getTeclats();
        Teclat teclat = teclats.get(idTeclat);
        if (teclat == null) throw new ExcepcioIdNoValid();

        Text text = gestorTexts.getTextPredefinit(idText);
        String contingutsFitxer =  text.llegirContingut();

        Teclat nouTeclat = teclat.shallowCopy();
        Algorisme algorisme = nouTeclat.getAlgorisme();

        Alfabet[] alfabets = gestorAlfabets.getAlfabets();
        Frequencies frequencies = new Frequencies();
        frequencies.genera(contingutsFitxer, alfabets);

        char[][] layout = algorisme.generarLayout(frequencies, COLUMNES, FILES);
        nouTeclat.setLayout(layout);


        try {
            gestorUsuaris.modificarTeclat(usuari.getUsername(), idTeclat, nouTeclat);
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantModificacio();
        }
        teclats.put(idTeclat, nouTeclat);
    }


    //GESTIONAR TEXTOS PUBLICS DE L'USUARI

    /**
     * Retorna un map amb els IDs i els noms dels texts públics de l'usuari.
     *
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     * @return Un HashMap amb els IDs com a claus i els noms dels texts públics com a valors.
     */
    public HashMap<Integer,String> getTextsPublicsUsuari() throws ExcepcioLlegintDeDisc {
         
        HashMap<Integer,String> textsOut = new HashMap<>();
        HashMap<Integer, TextPublic> textsPublicsUsuari = gestorTexts.getTextsPublicsUsuari(usuari.getUsername());
        for (Map.Entry<Integer, TextPublic> entry : textsPublicsUsuari.entrySet()){
            String title = entry.getValue().getNom();
            textsOut.put(entry.getKey(), title);
        }
        return textsOut;
    }


    /**
     * Afegeix un text públic a l'usuari.
     *
     * @param nom              El nom del text públic.
     * @param contingutFitxer  El contingut del text públic.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del text.
     * @throws ExcepcioNomTextPublicJaExisteix Si ja existeix un text públic amb aquest nom.
     */
    public void afegirTextPublic(String nom, String contingutFitxer) throws ExcepcioErrorDurantLaCreacio, ExcepcioNomTextPublicJaExisteix  {
         
        TextPublic text = new TextPublic(nom, contingutFitxer, usuari.getUsername());
        try {
            gestorTexts.crearTextPublic(text);
        }
        catch(ExcepcioNomTextPublicJaExisteix e){
            throw new ExcepcioNomTextPublicJaExisteix();
        }
        catch(Exception e){
            throw new ExcepcioErrorDurantLaCreacio();
        }

    }

    /**
     * Elimina un text públic de l'usuari.
     *
     * @param idTextPublicUsuari L'ID del text públic a eliminar.
     * @throws ExcepcioIdNoValid Si l'ID del text públic no és vàlid.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrat del text.
     */
    public void eliminarTextPublic(Integer idTextPublicUsuari) throws ExcepcioIdNoValid, ExcepcioErrorDurantEsborrat{
        try {
            gestorTexts.eliminarTextPublic(idTextPublicUsuari);
        }
        catch(ExcepcioIdNoValid e) {
            throw new ExcepcioIdNoValid();
        }
        catch(Exception e){
            throw new ExcepcioErrorDurantEsborrat();
        }
    }


    /**
     * Modifica el nom d'un text públic existent.
     *
     * @param idTextPublicUsuari L'ID del text públic a modificar.
     * @param nom                El nou nom per al text públic.
     * @throws ExcepcioIdNoValid Si l'ID del text públic no és vàlid.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del text.
     * @throws ExcepcioNomTextPublicJaExisteix Si ja existeix un text públic amb aquest nom.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void modificarTextPublicNom(Integer idTextPublicUsuari, String nom) throws ExcepcioIdNoValid, ExcepcioErrorDurantModificacio, ExcepcioNomTextPublicJaExisteix, ExcepcioLlegintDeDisc {

        TextPublic textPublicUsuari = gestorTexts.getTextPublic(idTextPublicUsuari);
        textPublicUsuari.setNom(nom);

        try {
            gestorTexts.modificarTextPublic(idTextPublicUsuari, textPublicUsuari);
        }
        catch(ExcepcioNomTextPublicJaExisteix e){
            throw new ExcepcioNomTextPublicJaExisteix();
        }
        catch(Exception e){
            throw new ExcepcioErrorDurantModificacio();
        }
    }

    /**
     * Retorna el contingut d'un text públic d'un usuari identificat per la seva ID.
     *
     * @param idTextPublicUsuari L'ID del text públic de l'usuari.
     * @return El contingut del text públic.
     * @throws ExcepcioIdNoValid Si l'ID proporcionat no correspon a cap text públic de l'usuari.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint el contingut del text públic.
     */
    public String getTextPublic(Integer idTextPublicUsuari) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc {

        TextPublic textPublicUsuari = gestorTexts.getTextPublic(idTextPublicUsuari);
        return textPublicUsuari.llegirContingut();

    }

    //USUARI ADMIN

    //Gestio d'usuaris


    /**
     * Retorna una llista amb els noms d'usuari existents.
     *
     * @throws ExcepcioUsuariNoEsAdmin Si l'usuari actual no té permisos d'administrador.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     * @return Un array de Strings amb els noms d'usuari.
     */
    public String[] getUsuaris() throws ExcepcioUsuariNoEsAdmin, ExcepcioLlegintDeDisc {
         
        String[] usuarisOut;
        if(usuari.isAdmin()) {
            HashMap<String,Usuari> usuaris = gestorUsuaris.getUsuaris();
            usuarisOut = new String[usuaris.size()];
            int i = 0;
            for (Map.Entry<String, Usuari> entry : usuaris.entrySet()) {
                usuarisOut[i] = entry.getKey();
                ++i;
            }
        }
        else throw new ExcepcioUsuariNoEsAdmin();
        return usuarisOut;
    }

    /**
     * Converteix un usuari en administrador.
     *
     * @param username El nom d'usuari a convertir en administrador.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació de l'estatus de l'usuari.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioUsuariNoEsAdmin Si l'usuari actual no té permisos d'administrador.
     */
    public void setAdmin(String username) throws ExcepcioErrorDurantModificacio, ExcepcioUsernameNoExisteix, ExcepcioUsuariNoEsAdmin  {
         
        if(usuari.isAdmin()){
            try {
                gestorUsuaris.setAdmin(username);
            }
            catch (ExcepcioUsernameNoExisteix e) {
                throw new ExcepcioUsernameNoExisteix();
            }
            catch (Exception e) {
                throw new ExcepcioErrorDurantModificacio();
            }
        }
        else throw new ExcepcioUsuariNoEsAdmin();

    }

    /**
     * Elimina un usuari del sistema.
     *
     * @param username El nom d'usuari a eliminar.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrat de l'usuari.
     * @throws ExcepcioUsuariNoEsAdmin Si l'usuari actual no té permisos d'administrador.
     * @throws ExcepcioUsuariEsAdmin Si l'usuari a eliminar és l'administrador principal.
     */
    public void eliminarUsuari(String username) throws ExcepcioUsernameNoExisteix, ExcepcioErrorDurantEsborrat, ExcepcioUsuariNoEsAdmin, ExcepcioUsuariEsAdmin {

        if (username.equals( "admin")) throw new ExcepcioUsuariEsAdmin();
        if(usuari.isAdmin()){
            try {
                gestorUsuaris.eliminarUsuari(username);
            }
            catch (ExcepcioUsernameNoExisteix e) {
                throw new ExcepcioUsernameNoExisteix();
            }
            catch (Exception e) {
                throw new ExcepcioErrorDurantEsborrat();
            }

        }
        else throw new ExcepcioUsuariNoEsAdmin();
    }

//*******************************************************************************************************


    //Gestio texts predefinits


    /**
     * Afegeix un text predefinit al sistema.
     *
     * @param nom              El nom del text predefinit.
     * @param contingutFitxer  El contingut del text predefinit.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del text.
     * @throws ExcepcioNomTextPredefinitJaExisteix Si ja existeix un text predefinit amb aquest nom.
     */
    public void afegirTextPredefinit(String nom, String contingutFitxer) throws ExcepcioErrorDurantLaCreacio, ExcepcioNomTextPredefinitJaExisteix{
        TextPredefinit text = new TextPredefinit(nom, contingutFitxer);
        try {
            gestorTexts.crearTextPredefinit(text);
        }
        catch(ExcepcioNomTextPredefinitJaExisteix e){
            throw new ExcepcioNomTextPredefinitJaExisteix();
        }
        catch(Exception e){
            throw new ExcepcioErrorDurantLaCreacio();
        }
    }

    /**
     * Elimina un text predefinit del sistema.
     *
     * @param idTextPredefinit L'ID del text predefinit a eliminar.
     * @throws ExcepcioIdNoValid Si l'ID del text predefinit no és vàlid.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrat del text.
     */
    public void eliminarTextPredefinit(Integer idTextPredefinit) throws ExcepcioIdNoValid, ExcepcioErrorDurantEsborrat{
        try {
            gestorTexts.eliminarTextPredefinit(idTextPredefinit);
        }
        catch(ExcepcioIdNoValid e) {
            throw new ExcepcioIdNoValid();
        }
        catch(Exception e){
            throw new ExcepcioErrorDurantEsborrat();
        }
    }

    /**
     * Modifica el nom d'un text predefinit existent.
     *
     * @param idTextPredefinit L'ID del text predefinit a modificar.
     * @param nom              El nou nom per al text predefinit.
     * @throws ExcepcioIdNoValid Si l'ID del text predefinit no és vàlid.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del text.
     * @throws ExcepcioNomTextPredefinitJaExisteix Si ja existeix un text predefinit amb aquest nom.
     * @throws ExcepcioLlegintDeDisc Si hi ha un error llegint dades del disc.
     */
    public void modificarTextPredefinitNom(Integer idTextPredefinit, String nom) throws ExcepcioIdNoValid, ExcepcioErrorDurantModificacio, ExcepcioNomTextPredefinitJaExisteix, ExcepcioLlegintDeDisc {
        TextPredefinit textPredefinit = gestorTexts.getTextPredefinit(idTextPredefinit);
        textPredefinit.setNom(nom);
        try {
            gestorTexts.modificarTextPredefinit(idTextPredefinit, textPredefinit);
        }
        catch(ExcepcioNomTextPredefinitJaExisteix e){
            throw new ExcepcioNomTextPredefinitJaExisteix();
        }
        catch(Exception e){
            throw new ExcepcioErrorDurantModificacio();
        }

    }

    /**
     * Retorna el contingut d'un text predefinit identificat per la seva ID.
     *
     * @param idTextPredefinit L'ID del text predefinit.
     * @return El contingut del text predefinit.
     * @throws ExcepcioIdNoValid Si l'ID proporcionat no correspon a cap text predefinit.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint el contingut del text predefinit.
     */
    public String getTextPredefinit(Integer idTextPredefinit) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc {
        TextPredefinit textPredefinit = gestorTexts.getTextPredefinit(idTextPredefinit);
        return textPredefinit.llegirContingut();
    }


}
