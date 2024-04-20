package edu.upc.prop.cluster33.persistencia;

import edu.upc.prop.cluster33.domini.*;
import edu.upc.prop.cluster33.excepcions.*;
import edu.upc.prop.cluster33.utils.TMa;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Locale;

/**
 * La classe GestorUsuaris és responsable de la gestió d'usuaris en el sistema.
 * Aquesta classe ofereix funcionalitats per llegir i escriure informació d'usuaris a disc,
 * així com per gestionar l'accés i la modificació de les dades dels usuaris.
 * Inclou mètodes per crear, modificar, eliminar i consultar informació d'usuaris,
 * a més de gestionar els seus teclats associats.
 *
 * Els usuaris són emmagatzemats en fitxers JSON, i cada usuari té associat un identificador únic.
 * Aquesta classe també gestiona la relació entre els noms d'usuari i els seus identificadors,
 * permetent una fàcil recuperació i manipulació de les dades d'usuari.
 */
public class GestorUsuaris {
    /**
     * Ruta del directori d'emmagatzematge general.
     */
    private static final String PATH_STORAGE = "src/main/java/edu/upc/prop/cluster33/storage/";
    /**
     * Ruta de l'índex dels usuaris.
     */
    private static final String PATH_USUARIS_INDEX = PATH_STORAGE + "usuarisIndex.json";
    /**
     * Ruta del directori on s'emmagatzemen les dades dels usuaris.
     */
    private static final String PATH_USUARIS_DIR = PATH_STORAGE + "usuaris";
    /**
     * Ruta base per a l'emmagatzematge de cada usuari.
     */
    private static final String PATH_USUARI = PATH_USUARIS_DIR + "/usuari";

    /**
     * Constructor de la classe GestorUsuaris.
     * Inicialitza el sistema de persistència d'usuaris, incloent la creació d'un usuari administrador per defecte si és necessari.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura o escriptura de fitxers.
     */
    public GestorUsuaris() throws ExcepcioLlegintDeDisc {
        try {

            File storageDir = new File(PATH_STORAGE);
            if (!storageDir.exists()) {
                storageDir.mkdirs();
            }

            File fileUsuarisIndex = new File(PATH_USUARIS_INDEX);
            File userDir = new File(PATH_USUARIS_DIR);
            File userDirAdmin = new File(PATH_USUARI + "1" + ".json");

            if (!fileUsuarisIndex.exists() || !userDir.exists() || !userDirAdmin.exists()) { // Inicialitzem el fitxer amb el usuari admin per default

                int id = 1;
                Usuari usuariAdmin = new Usuari("admin", "admin", TMa.DRETA, true);
                HashMap<String, Integer> usernamesIds = new HashMap<>();
                usernamesIds.put(usuariAdmin.getUsername(), id);
                escriuDiscUsuarisIndex(usernamesIds);

                if (userDir.exists()) {
                    deleteDirectory(userDir);
                }
                userDir.mkdirs();
                escriureDiscUsuari(usuariAdmin, id);

            }
            else {
                HashMap<String, Integer> indexs = llegirDiscUsuarisIndex();
                HashMap<String, Integer> indexs2 = llegirDiscUsuarisIndex();
                for (String key : indexs.keySet()) {
                    try {
                        llegirDiscUsuari(indexs.get(key));
                    } catch (Exception e) {
                        indexs2.remove(key);
                    }
                }
                escriuDiscUsuarisIndex(indexs2);
            }

        }
        catch (Exception e){
            throw new ExcepcioLlegintDeDisc("S'ha produit algun error intentant llegir/escriure a disc");
        }

    }

    /**
     * Elimina un directori i tots els seus continguts.
     * @param dir Directori a eliminar.
     */
    private void deleteDirectory(File dir) {
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        dir.delete();
    }

    /**
     * Llegeix un usuari des del disc basat en el seu identificador.
     * @param id Identificador de l'usuari.
     * @return Usuari L'objecte Usuari llegit des del disc.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del fitxer de l'usuari.
     */
    private Usuari llegirDiscUsuari(int id) throws ExcepcioLlegintDeDisc {

        GestorAlgorismes gestoralgorismes = new GestorAlgorismes();
        JSONParser parser = new JSONParser();
        Usuari usuari = new Usuari();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(PATH_USUARI + id + ".json"), StandardCharsets.UTF_8)) {
            JSONObject json = (JSONObject) parser.parse(reader);

            usuari.setUsername((String) json.get("username"));
            usuari.setPassword((String) json.get("password"));
            usuari.setMaBona(json.get("maBonaDreta").equals(true) ? TMa.DRETA : TMa.ESQUERRA);
            usuari.setAdmin((Boolean) json.get("admin"));

            HashMap<Integer,Teclat> teclats = new HashMap<Integer,Teclat> ();

            JSONObject teclatsJson = (JSONObject) json.get("teclats");
            for (Object key : teclatsJson.keySet()) {
                String keyStr = (String) key;


                //Teclats
                JSONObject teclatJson = (JSONObject) teclatsJson.get(key.toString());
                String nom = (String) teclatJson.get("nom");
                char[][] layout = convertJsonArrayToLayout((JSONArray) teclatJson.get("layout"));
                Algorisme algorisme = gestoralgorismes.returnAlgorismeTipus((String) teclatJson.get("algorisme"));

                String dataCreacioStr = (String) teclatJson.get("dataCreacio");
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                Date dataCreacio = null;
                dataCreacio = dateFormat.parse(dataCreacioStr);


                    //Frequencies
                    JSONObject frequenciaJson = (JSONObject) teclatJson.get("frequencies");

                    int nombreParaules = ((Long) frequenciaJson.get("NumeroParaules")).intValue();

                    JSONObject frequenciesData = (JSONObject) frequenciaJson.get("Frequencies");
                    TreeMap<String, Integer> llistaFrequencies = new TreeMap<>();
                    for (Object key2Obj : frequenciesData.keySet()) {
                        String key2 = (String) key2Obj;
                        int value = ((Long) frequenciesData.get(key2)).intValue();
                        llistaFrequencies.put(key2, value);
                    }

                        //Alfabet
                        String alfabetLlista = (String) frequenciaJson.get("Alfabet");
                        String nomAlfabet = (String) frequenciaJson.get("AlfabetNom");
                        Alfabet alfabet = new Alfabet(nomAlfabet, alfabetLlista);

                    Frequencies frequencies = new Frequencies(llistaFrequencies, alfabet,nombreParaules);


                Teclat teclat = new Teclat(nom, layout, algorisme, frequencies, dataCreacio);
                teclats.put(Integer.parseInt(keyStr), teclat);
            }
            usuari.setTeclats(teclats);


        } catch (Exception e) {
            throw new ExcepcioLlegintDeDisc();
        }
        return usuari;
    }

    /**
     * Converteix un JSONArray a una matriu de caràcters representant un layout de teclat.
     * @param layoutJson JSONArray representant el layout.
     * @return char[][] Matriu de caràcters del layout.
     */
    private char[][] convertJsonArrayToLayout(JSONArray layoutJson) {
        if (layoutJson == null) {
            return new char[0][0];
        }

        // Assuming layoutJson is an array of arrays
        char[][] layout = new char[layoutJson.size()][];
        for (int i = 0; i < layoutJson.size(); i++) {
            JSONArray rowJson = (JSONArray) layoutJson.get(i);
            char[] row = new char[rowJson.size()];

            for (int j = 0; j < rowJson.size(); j++) {
                String value = (String) rowJson.get(j);
                row[j] = value != null && !value.isEmpty() ? value.charAt(0) : ' '; // default to a space or any default character
            }

            layout[i] = row;
        }

        return layout;
    }

    /**
     * Escriu un usuari al disc basat en el seu identificador.
     * @param usuari Usuari a escriure.
     * @param id Identificador de l'usuari.
     * @throws ExcepcioEscrivintADisc Si es produeix un error durant l'escriptura del fitxer de l'usuari.
     */
    private void escriureDiscUsuari(Usuari usuari, int id) throws ExcepcioEscrivintADisc {

        try{
            JSONObject userJson = new JSONObject();
            userJson.put("username", usuari.getUsername());
            userJson.put("password", usuari.getPassword());
            userJson.put("maBonaDreta", usuari.getMaBona() == TMa.DRETA);
            userJson.put("admin", usuari.isAdmin());


            JSONObject teclatsJson = new JSONObject();
            for (Map.Entry<Integer, Teclat> entry : usuari.getTeclats().entrySet()) {
                JSONObject teclatJson = new JSONObject();
                Teclat teclat = entry.getValue();

                teclatJson.put("nom", teclat.getNom());
                teclatJson.put("layout", convertLayoutToJsonArray(teclat.getLayout()));
                teclatJson.put("algorisme", teclat.getAlgorisme().getNom());
                teclatJson.put("dataCreacio", teclat.getDataCreacio().toString());

                JSONObject frequenciaJson = new JSONObject();
                    Frequencies frequencies = teclat.getFrequencia();

                    TreeMap<String, Integer> llistaFrequencies = frequencies.getLlistaFrequencies();
                    JSONObject frequenciesJson = new JSONObject();

                    for (Map.Entry<String, Integer> entry1 : llistaFrequencies.entrySet()) {
                        frequenciesJson.put(entry1.getKey(), entry1.getValue());
                    }
                    frequenciaJson.put("Frequencies", frequenciesJson);

                    frequenciaJson.put("Alfabet", frequencies.getAlfabet());
                    frequenciaJson.put("NumeroParaules", frequencies.getNumero_paraules());
                    frequenciaJson.put("Alfabet", frequencies.getAlfabet().getAlfabet());
                    frequenciaJson.put("AlfabetNom", frequencies.getAlfabet().getNom());

                teclatJson.put("frequencies", frequenciaJson);

                teclatsJson.put(entry.getKey().toString(), teclatJson);
            }

            userJson.put("teclats", teclatsJson);

                OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(PATH_USUARI + id + ".json"), StandardCharsets.UTF_8);

                //FileWriter file = new FileWriter(PATH_USUARI + id + ".json");
                file.write(userJson.toJSONString());
                file.flush();
        } catch (Exception e) {
            throw new ExcepcioEscrivintADisc();
        }
    }

    /**
     * Converteix una matriu de caràcters (layout de teclat) a un JSONArray.
     * @param layout Matriu de caràcters del layout.
     * @return JSONArray JSONArray representant el layout.
     */
    private JSONArray convertLayoutToJsonArray(char[][] layout) {
        JSONArray layoutArray = new JSONArray();
        for (char[] row : layout) {
            JSONArray rowArray = new JSONArray();
            for (char c : row) {
                rowArray.add(String.valueOf(c));
            }
            layoutArray.add(rowArray);
        }
        return layoutArray;
    }

    /**
     * Escriu l'índex d'usuaris (mapa de noms d'usuari a identificadors) al disc.
     * @param usernamesIndexs Mapa de noms d'usuari a identificadors.
     * @throws ExcepcioEscrivintADisc Si es produeix un error durant l'escriptura del fitxer d'índex.
     */
    private void escriuDiscUsuarisIndex(HashMap<String, Integer> usernamesIndexs) throws ExcepcioEscrivintADisc {
        JSONObject json = new JSONObject();

        for (String username : usernamesIndexs.keySet()) {
            json.put(username, usernamesIndexs.get(username).toString());
        }

        try  {
            OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(PATH_USUARIS_INDEX), StandardCharsets.UTF_8);
            file.write(json.toJSONString());
            file.flush();
        } catch (Exception e) {
            throw new ExcepcioEscrivintADisc();
        }
    }

    /**
     * Llegeix l'índex d'usuaris des del disc.
     * @return HashMap amb els usernames i els seus identificadors corresponents.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del fitxer d'índex.
     */
    private HashMap<String, Integer> llegirDiscUsuarisIndex() throws ExcepcioLlegintDeDisc {
        JSONParser parser = new JSONParser();
        int id = 0;
        HashMap<String,Integer> usuaris = new HashMap<>();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(PATH_USUARIS_INDEX), StandardCharsets.UTF_8)) {
            JSONObject json = (JSONObject) parser.parse(reader);

            for (Object usernameObj : json.keySet()) {
                String username = (String) usernameObj;
                String idStr = (String) json.get(username);
                id = Integer.parseInt(idStr);

                usuaris.put(username,id);
            }
        }
        catch (Exception e){
            throw new ExcepcioLlegintDeDisc();
        }
        return usuaris;

    }

    /**
     * Obté l'identificador d'un usuari donat el seu username.
     * @param username El nom d'usuari.
     * @return Integer Identificador de l'usuari.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del fitxer d'índex.
     */
    private Integer getUserId(String username) throws ExcepcioLlegintDeDisc {
        HashMap<String, Integer> usuaris = llegirDiscUsuarisIndex();
        return usuaris.get(username);
    }

    /**
     * Troba el següent identificador disponible.
     * @return int El següent identificador disponible.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del fitxer d'índex.
     */
    private int getNextAvailableIndex() throws ExcepcioLlegintDeDisc {
        HashMap<String, Integer> usuaris = llegirDiscUsuarisIndex();
        Integer keySenseUtilitzar = 1;
        while(usuaris.containsValue(keySenseUtilitzar)) {
            ++keySenseUtilitzar;
        }
        return keySenseUtilitzar;
    }

    /**
     * Obté un usuari basat en el seu username.
     * @param username El nom d'usuari.
     * @return Usuari L'objecte Usuari corresponent.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del fitxer d'usuari.
     */
    public Usuari getUsuari(String username) throws ExcepcioUsernameNoExisteix, ExcepcioLlegintDeDisc {

        HashMap<String, Integer> usuaris = llegirDiscUsuarisIndex();
        Integer id = usuaris.get(username);
        if (id == null) throw new ExcepcioUsernameNoExisteix();
        return llegirDiscUsuari(id);

    }

    /**
     * Verifica si existeix un usuari amb el username donat.
     * @param username El nom d'usuari a verificar.
     * @return boolean True si l'usuari existeix, fals en cas contrari.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del fitxer d'índex.
     */
    public boolean existeixUsuari(String username) throws ExcepcioLlegintDeDisc {
        HashMap<String, Integer> usuaris = llegirDiscUsuarisIndex();
        return usuaris.containsKey(username);
    }

    /**
     * Crea un nou usuari.
     * @param user L'usuari a crear.
     * @throws ExcepcioUsernameJaExistent Si el nom d'usuari ja existeix.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació de l'usuari.
     */
    public void crearUsuari(Usuari user) throws ExcepcioUsernameJaExistent, ExcepcioErrorDurantLaCreacio {
        try {
        HashMap<String, Integer> usuaris = llegirDiscUsuarisIndex();
        if (usuaris.containsKey(user.getUsername())) throw new ExcepcioUsernameJaExistent(user.getUsername());

        int id = getNextAvailableIndex();
            usuaris.put(user.getUsername(), id);

            escriuDiscUsuarisIndex(usuaris);
            escriureDiscUsuari(user, id);

        }
        catch (ExcepcioUsernameJaExistent e){
            throw new ExcepcioUsernameJaExistent(user.getUsername());
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantLaCreacio();
        }

    }

    /**
     * Elimina un usuari basat en el seu username.
     * @param username El nom d'usuari de l'usuari a eliminar.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'eliminació de l'usuari.
     */
    public void eliminarUsuari(String username) throws ExcepcioUsernameNoExisteix, ExcepcioErrorDurantEsborrat {
        try {
            HashMap<String, Integer> usuaris = llegirDiscUsuarisIndex();
            if (!usuaris.containsKey(username)) throw new ExcepcioUsernameNoExisteix();
            usuaris.remove(username);
            escriuDiscUsuarisIndex(usuaris);
            GestorTexts gestorTexts = new GestorTexts();
            HashMap<Integer, TextPublic> textsPublicsUsuari = gestorTexts.getTextsPublicsUsuari(username);
            for(int key : textsPublicsUsuari.keySet()) gestorTexts.eliminarTextPublic(key);
        }
        catch (ExcepcioUsernameNoExisteix e){
            throw new ExcepcioUsernameNoExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantEsborrat();
        }
    }

    /**
     * Canvia la contrasenya d'un usuari.
     * @param username El nom d'usuari de l'usuari.
     * @param newPassword La nova contrasenya.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació de l'usuari.
     */
    public void canviarPassword(String username, String newPassword) throws ExcepcioUsernameNoExisteix, ExcepcioErrorDurantModificacio {
        try{
            Integer id = getUserId(username);
            if(id == null) throw new ExcepcioUsernameNoExisteix();

            Usuari usuari = llegirDiscUsuari(id);
            usuari.setPassword(newPassword);
            escriureDiscUsuari(usuari, id);
        }
        catch (ExcepcioUsernameNoExisteix e){
            throw new ExcepcioUsernameNoExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantModificacio();
        }
    }

    /**
     * Canvia la mà bona preferida d'un usuari.
     * @param username El nom d'usuari de l'usuari.
     * @param tipusMa El tipus de mà (dreta o esquerra).
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació de l'usuari.
     */
    public void canviarMaBona(String username, TMa tipusMa) throws ExcepcioUsernameNoExisteix, ExcepcioErrorDurantModificacio {
        try{
            Integer id = getUserId(username);
            if(id == null) throw new ExcepcioUsernameNoExisteix();

            Usuari usuari = llegirDiscUsuari(id);
            usuari.setMaBona(tipusMa);
            escriureDiscUsuari(usuari, id);
        }
        catch (ExcepcioUsernameNoExisteix e){
            throw new ExcepcioUsernameNoExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantModificacio();
        }
    }

    /**
     * Assigna l'estatus d'administrador a un usuari.
     * @param username El nom d'usuari de l'usuari a actualitzar.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació de l'usuari.
     */
    public void setAdmin(String username) throws ExcepcioUsernameNoExisteix, ExcepcioErrorDurantModificacio {
        try{
            Integer id = getUserId(username);
            if(id == null) throw new ExcepcioUsernameNoExisteix();

            Usuari usuari = llegirDiscUsuari(id);
            usuari.setAdmin(true);
            escriureDiscUsuari(usuari, id);
        }
        catch (ExcepcioUsernameNoExisteix e){
            throw new ExcepcioUsernameNoExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantModificacio();
        }
    }

    /**
     * Retorna un HashMap amb tots els usuaris.
     * @return HashMap amb els noms d'usuari com a clau i objectes Usuari com a valor.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura de l'índex d'usuaris.
     */
    public HashMap<String, Usuari> getUsuaris() throws ExcepcioLlegintDeDisc {

        HashMap<String, Usuari> usuaris = new HashMap<>();

        HashMap<String, Integer> usernamesIndex = llegirDiscUsuarisIndex();
        for (String username : usernamesIndex.keySet()) {
            usuaris.put(username, new Usuari());
        }

        return usuaris;
    }

    /**
     * Crea un nou teclat per a un usuari.
     * @param username El nom d'usuari de l'usuari.
     * @param nouTeclat L'objecte Teclat a afegir.
     * @throws ExcepcioNomTeclatJaExisteix Si el nom del teclat ja existeix per a aquest usuari.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del teclat.
     */
    public void crearTeclat(String username, Teclat nouTeclat) throws ExcepcioNomTeclatJaExisteix, ExcepcioErrorDurantLaCreacio {

        try {
            int id = getUserId(username);
            Usuari usuari = llegirDiscUsuari(id);
            HashMap<Integer, Teclat> teclats = usuari.getTeclats();
            for (Teclat teclat : teclats.values()) {
                if (teclat.getNom().equals(nouTeclat.getNom())) throw new ExcepcioNomTeclatJaExisteix();
            }
            Integer keySenseUtilitzar = 1;
            while (teclats.containsKey(keySenseUtilitzar)) {
                ++keySenseUtilitzar;
            }
            teclats.put(keySenseUtilitzar, nouTeclat);
            usuari.setTeclats(teclats);
            escriureDiscUsuari(usuari, id);
        }
        catch (ExcepcioNomTeclatJaExisteix e){
            throw new ExcepcioNomTeclatJaExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantLaCreacio();
        }

    }

    /**
     * Modifica un teclat existent d'un usuari.
     * @param username El nom d'usuari de l'usuari.
     * @param idTeclat L'identificador del teclat a modificar.
     * @param nouTeclat L'objecte Teclat actualitzat.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del teclat.
     */
    public void modificarTeclat(String username, Integer idTeclat, Teclat nouTeclat) throws ExcepcioIdNoValid, ExcepcioErrorDurantModificacio {
        try {
            int id = getUserId(username);
            Usuari usuari = llegirDiscUsuari(id);
            HashMap<Integer, Teclat> teclats = usuari.getTeclats();
            if (!teclats.containsKey(idTeclat)) throw new ExcepcioIdNoValid();
            teclats.remove(idTeclat);
            teclats.put(idTeclat, nouTeclat);
            usuari.setTeclats(teclats);
            escriureDiscUsuari(usuari, id);
        }
        catch (ExcepcioIdNoValid e){
            throw new ExcepcioIdNoValid();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantModificacio();
        }

    }

    /**
     * Modifica el nom d'un teclat existent d'un usuari.
     * @param username El nom d'usuari de l'usuari.
     * @param idTeclat L'identificador del teclat a modificar.
     * @param nouNom El nou nom per al teclat.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si el nou nom del teclat ja existeix.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del teclat.
     */
    public void modificarTeclatNom(String username, Integer idTeclat, String nouNom) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioErrorDurantModificacio {
        try {
            int id = getUserId(username);
            Usuari usuari = llegirDiscUsuari(id);
            HashMap<Integer, Teclat> teclats = usuari.getTeclats();
            if (!teclats.containsKey(idTeclat)) throw new ExcepcioIdNoValid();
            for (Teclat teclat : teclats.values())
                if (teclat.getNom().equals(nouNom)) throw new ExcepcioNomTeclatJaExisteix();
            Teclat teclat = teclats.get(idTeclat);
            teclat.setNom(nouNom);
            teclats.put(idTeclat, teclat);
            usuari.setTeclats(teclats);
            escriureDiscUsuari(usuari, id);
        }
        catch (ExcepcioIdNoValid e){
            throw new ExcepcioIdNoValid();
        }
        catch (ExcepcioNomTeclatJaExisteix e){
            throw new ExcepcioNomTeclatJaExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantModificacio();
        }
    }

    /**
     * Elimina un teclat d'un usuari.
     * @param username El nom d'usuari de l'usuari.
     * @param idTeclat L'identificador del teclat a eliminar.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'eliminació del teclat.
     */
    public void removeTeclat(String username, Integer idTeclat) throws ExcepcioIdNoValid, ExcepcioErrorDurantEsborrat {
        try {
            int id = getUserId(username);
            Usuari usuari = llegirDiscUsuari(id);
            HashMap<Integer, Teclat> teclats = usuari.getTeclats();
            if (!teclats.containsKey(idTeclat)) throw new ExcepcioIdNoValid();
            teclats.remove(idTeclat);
            usuari.setTeclats(teclats);
            escriureDiscUsuari(usuari, id);
        }
        catch (ExcepcioIdNoValid e){
            throw new ExcepcioIdNoValid();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantEsborrat();
        }

    }



}



