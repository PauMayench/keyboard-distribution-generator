package edu.upc.prop.cluster33.persistencia;

import edu.upc.prop.cluster33.domini.Alfabet;
import edu.upc.prop.cluster33.excepcions.ExcepcioLlegintDeDisc;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * GestorAlfabets gestiona les operacions de lectura i escriptura dels alfabets al disc.
 * Aquesta classe s'encarrega de gestionar els alfabets suportats pel sistema, incloent-hi
 * la seva càrrega des de fitxer i l'emmagatzematge en format JSON.
 *
 * Els alfabets són utilitzats per altres classes per a diferents funcionalitats dins del sistema.
 * El gestor permet la lectura i l'escriptura de l'array d'objectes Alfabet, facilitant així
 * la manipulació d'aquesta informació vital per al funcionament del programa.
 */
public class GestorAlfabets {
    /**
     * Ruta del directori d'emmagatzematge general.
     */
    private static final String PATH_STORAGE = "src/main/java/edu/upc/prop/cluster33/storage/";
    /**
     * Ruta del fitxer on s'emmagatzemen els alfabets.
     */
    private static final String PATH_ALFABETS = PATH_STORAGE + "alfabets.json";

    /**
     * Constructor de la classe GestorAlfabets.
     * Inicialitza el fitxer d'alfabets si no existeix i el carrega des del disc.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error en llegir o escriure el fitxer d'alfabets.
     */
    public GestorAlfabets() throws ExcepcioLlegintDeDisc {

        try {
            File storageDir = new File(PATH_STORAGE);
            if (!storageDir.exists()) {
                storageDir.mkdirs();
            }
        }
        catch(Exception e){
            throw  new ExcepcioLlegintDeDisc();
        }
        File fileAlfabets = new File(PATH_ALFABETS );

        if (!fileAlfabets.exists()) { //inicialitzem el fitxer amb els alfabets

            Alfabet[] alfabets = new Alfabet[5];
            alfabets[0] = new Alfabet("Llati", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            alfabets[1] = new Alfabet("Grec", "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ");
            alfabets[2] = new Alfabet("Cirilic", "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");
            alfabets[3] = new Alfabet("Armeni", "ԱԲԳԴԵԶԷԸԹԺԻԼԽԾԿՀՁՂՃՄՅՆՇՈՉՊՋՌՍՎՏՐՑՒՓՔՕՖ");
            alfabets[4] = new Alfabet("Georgia", "აბგდევზთიკლმნოპჟრსტჳუფქღყშჩცძწჭხჴჯჰ");
            try {
                escriureDiscAlfabets(alfabets);
            } catch (Exception e) {
                throw new ExcepcioLlegintDeDisc("S'ha produit algun error intentant llegir/escriure a disc");

            }
        }

    }

    /**
     * Escriu els alfabets en un fitxer JSON.
     * @param alfabets Array d'objectes Alfabet per escriure al disc.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error d'escriptura.
     */
    private void escriureDiscAlfabets(Alfabet[] alfabets) throws ExcepcioLlegintDeDisc {
        JSONArray jsonArray = new JSONArray();

        for (Alfabet alfabet : alfabets) {
            JSONObject alfabetJson = new JSONObject();
            alfabetJson.put("nom", alfabet.getNom());
            alfabetJson.put("caracters", alfabet.getAlfabet());
            jsonArray.add(alfabetJson);
        }
        try (OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(PATH_ALFABETS), StandardCharsets.UTF_8)) {
        //try (FileWriter file = new FileWriter(PATH_ALFABETS)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new ExcepcioLlegintDeDisc("S'ha produit algun error intentant escriure a disc");        }
    }

    /**
     * Llegeix els alfabets des d'un fitxer JSON.
     * @return Array d'Alfabet llegits des del disc.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error de lectura.
     */
    private Alfabet[] llegirDiscAlfabets() throws ExcepcioLlegintDeDisc {
        ArrayList<Alfabet> alfabetsList = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(PATH_ALFABETS), StandardCharsets.UTF_8)) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject alfabetJson = (JSONObject) obj;
                String nom = (String) alfabetJson.get("nom");
                String caracters = (String) alfabetJson.get("caracters");

                Alfabet alfabet = new Alfabet(nom, caracters);
                alfabetsList.add(alfabet);
            }

        } catch (Exception e) {
            throw new ExcepcioLlegintDeDisc("S'ha produit algun error intentant llegir el disc");
        }

        Alfabet[] alfabetsArray = new Alfabet[alfabetsList.size()];
        return alfabetsList.toArray(alfabetsArray);
    }

    /**
     * Obté tots els alfabets disponibles.
     * @return Array d'objectes Alfabet.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error en llegir els alfabets del disc.
     */
    public Alfabet[] getAlfabets() throws ExcepcioLlegintDeDisc {return llegirDiscAlfabets();}
}
