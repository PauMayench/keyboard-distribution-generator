package edu.upc.prop.cluster33.persistencia;

import edu.upc.prop.cluster33.domini.Algorisme;
import edu.upc.prop.cluster33.domini.BranchAndBoundEager;
import edu.upc.prop.cluster33.domini.SimulatedAnnealing;
import edu.upc.prop.cluster33.excepcions.ExcepcioIdNoValid;
import edu.upc.prop.cluster33.excepcions.ExcepcioLlegintDeDisc;

import org.json.simple.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.json.simple.parser.JSONParser;

/**
 * GestorAlgorismes gestiona les operacions de lectura i escriptura dels algorismes al disc.
 * Aquesta classe s'encarrega de gestionar els algorismes de disseny de teclats disponibles
 * en el sistema, permetent la seva càrrega des de fitxer i l'emmagatzematge en format JSON.
 *
 * La classe proporciona funcionalitats per inicialitzar els algorismes predefinits, llegir
 * i escriure aquests algorismes a disc, així com recuperar i utilitzar-los segons les necessitats
 * del programa. Això inclou la gestió dels algorismes 'BranchAndBoundEager' i 'SimulatedAnnealing'.
 */
public class GestorAlgorismes {
    /**
     * Ruta del directori d'emmagatzematge general.
     */
    private static final String PATH_STORAGE = "src/main/java/edu/upc/prop/cluster33/storage/";
    /**
     * Ruta del fitxer on s'emmagatzemen els algorismes.
     */
    private static final String PATH_ALGORISMES = PATH_STORAGE + "algorismes.json";

    /**
     * Constructor de GestorAlgorismes. Inicialitza el gestor, verificant l'existència del fitxer
     * d'algorismes i l'inicialitza si no existeix.
     *
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura o escriptura a disc.
     */
    public GestorAlgorismes() throws ExcepcioLlegintDeDisc {
        File fileAlgorismes = new File(PATH_ALGORISMES);
        try {
            File storageDir = new File(PATH_STORAGE);
            if (!storageDir.exists()) {
                storageDir.mkdirs();
            }
        }
        catch(Exception e){
            throw  new ExcepcioLlegintDeDisc();
        }
        if (!fileAlgorismes.exists()) { // Inicialitzem el fitxer amb els algorismes

            HashMap<Integer, Algorisme> algorismes = new HashMap<>();
            algorismes.put(1, new BranchAndBoundEager());
            algorismes.put(2, new SimulatedAnnealing());
            try {
                escriureDiscAlgorismes(algorismes);
            }
            catch(Exception e){
                throw new ExcepcioLlegintDeDisc("S'ha produit algun error intentant llegir/escriure a disc");
            }
        }

    }

    /**
     * Escriu els algorismes al disc en format JSON.
     *
     * @param algorismes HashMap amb els algorismes a escriure.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error d'escriptura a disc.
     */
    private void escriureDiscAlgorismes(HashMap<Integer, Algorisme> algorismes) throws ExcepcioLlegintDeDisc {
        JSONObject jsonObject = new JSONObject();

        for (Integer key : algorismes.keySet()) {
            Algorisme algorisme = algorismes.get(key);
            JSONObject algorismeJson = new JSONObject();
            algorismeJson.put("tipus", algorisme.getNom());
            jsonObject.put(String.valueOf(key),algorismeJson);
        }

        try (FileWriter file = new FileWriter(PATH_ALGORISMES)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new ExcepcioLlegintDeDisc("S'ha produit algun error intentant escriure a disc");
        }
    }

    /**
     * Llegeix els algorismes des del disc.
     *
     * @return HashMap amb els algorismes llegits.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del disc.
     */
    private HashMap<Integer, Algorisme> llegirDiscAlgorismes() throws ExcepcioLlegintDeDisc {
        HashMap<Integer, Algorisme> algorismes = new HashMap<>();
        JSONParser parser = new JSONParser();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(PATH_ALGORISMES), StandardCharsets.UTF_8)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            int a = 0;
            for (Object key : jsonObject.keySet()) {
                String keyStr = (String) key;
                JSONObject algorismeJson = (JSONObject) jsonObject.get(keyStr);

                String tipus = (String) algorismeJson.get("tipus");
                Algorisme algorisme = returnAlgorismeTipus(tipus);

                if (algorisme != null) {
                    algorismes.put(Integer.parseInt(keyStr), algorisme);
                }
            }

        }
        catch (Exception e) {
            throw new ExcepcioLlegintDeDisc("S'ha produit algun error intentant llegir de disc");
        }
        return algorismes;
    }

    /**
     * Retorna un objecte Algorisme basat en un tipus donat.
     *
     * @param tipus El tipus de l'algorisme a retornar.
     * @return Algorisme corresponent al tipus donat, o null si no es troba.
     */
    public Algorisme returnAlgorismeTipus(String tipus) {
        BranchAndBoundEager branchAndBoundEager = new BranchAndBoundEager();
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
        if(tipus.equals(branchAndBoundEager.getNom())) return branchAndBoundEager;
        if(tipus.equals(simulatedAnnealing.getNom())) return simulatedAnnealing;
        else return null;
    }

    /**
     * Obté tots els algorismes disponibles.
     *
     * @return HashMap amb tots els algorismes.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del disc.
     */
    public HashMap<Integer, Algorisme> getAlgorismes() throws ExcepcioLlegintDeDisc {
        return llegirDiscAlgorismes();
    }

    /**
     * Obté un algorisme específic pel seu identificador.
     *
     * @param idAlgoritme Identificador de l'algorisme.
     * @return Algorisme corresponent a l'identificador donat.
     * @throws ExcepcioIdNoValid Si l'identificador donat no correspon a cap algorisme.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del disc.
     */
    public Algorisme getAlgorisme(int idAlgoritme) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc {
        HashMap<Integer, Algorisme> algorismes = llegirDiscAlgorismes();
        if (!algorismes.containsKey(idAlgoritme)) throw new ExcepcioIdNoValid();
        return algorismes.get(idAlgoritme);
    }

}
