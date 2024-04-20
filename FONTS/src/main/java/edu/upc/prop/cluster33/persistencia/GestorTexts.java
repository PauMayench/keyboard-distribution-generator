package edu.upc.prop.cluster33.persistencia;

import edu.upc.prop.cluster33.domini.TextPublic;
import edu.upc.prop.cluster33.domini.TextPredefinit;
import edu.upc.prop.cluster33.excepcions.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * GestorTexts gestiona les operacions de lectura i escriptura dels textos predefinits i públics al disc.
 * Aquesta classe s'encarrega de gestionar el manteniment i accés als textos predefinits i textos públics,
 * permetent la seva càrrega des de fitxer i l'emmagatzematge en format JSON.
 */
public class GestorTexts {
    /**
     * Ruta del directori d'emmagatzematge general.
     */
    private static final String PATH_STORAGE = "src/main/java/edu/upc/prop/cluster33/storage/";
    /**
     * Ruta del fitxer on s'emmagatzemen els textos públics.
     */
    private static final String PATH_TEXTS_PUBLICS = PATH_STORAGE + "textsPublics.json";
    /**
     * Ruta del fitxer on s'emmagatzemen els textos predefinits.
     */
    private static final String PATH_TEXTS_PREDEFINITS = PATH_STORAGE + "textsPredefinits.json";

    /**
     * Constructor de GestorTexts. Inicialitza el gestor, verificant l'existència dels fitxers
     * de textos predefinits i públics i els inicialitza si no existeixen.
     *
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació dels fitxers.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura o escriptura a disc.
     */
    public GestorTexts() throws ExcepcioErrorDurantLaCreacio, ExcepcioLlegintDeDisc {
        try{
                File storageDir = new File(PATH_STORAGE);
                if (!storageDir.exists()) {
                    storageDir.mkdirs();
                }

            File filePredefinits = new File(PATH_TEXTS_PREDEFINITS);

            if (!filePredefinits.exists()) { // per a inicialitzar, escrivim texts predefinits default
                HashMap<Integer, TextPredefinit> textsPredefinits = new HashMap<>();
                TextPredefinit textPredefinit1 = new TextPredefinit("Article Científic", "Title:\n" +
                        "Exploring the Impact of Nanotechnology on Plant Growth in Martian Soil Simulants\n" +
                        "\n" +
                        "Abstract:\n" +
                        "This study investigates the effects of nanotechnology-enhanced fertilizers on plant growth in Martian soil simulants. By incorporating nano-scale nutrients, we aim to understand how these advanced fertilizers can improve agricultural productivity in extraterrestrial environments.\n" +
                        "\n" +
                        "Introduction:\n" +
                        "With the growing interest in Martian colonization, sustainable agriculture on Mars is a critical area of research. Martian soil, however, lacks essential nutrients found in Earth's soil, posing a significant challenge for plant growth. Recent advancements in nanotechnology offer potential solutions to this problem.\n" +
                        "\n" +
                        "Methodology:\n" +
                        "Martian soil simulants were prepared based on the composition data from NASA's Mars missions. Nanotechnology-enhanced fertilizers, containing nano-scale nitrogen, phosphorus, and potassium, were applied. A control group with traditional fertilizers was also maintained. The growth of a variety of plants, including leafy greens and tubers, was monitored over a 90-day period.\n" +
                        "\n" +
                        "Results:\n" +
                        "Plants treated with nanotechnology-enhanced fertilizers showed a 40% increase in growth rate and a 30% increase in yield compared to the control group. Enhanced root development and improved nutrient absorption were also observed.\n" +
                        "\n" +
                        "Discussion:\n" +
                        "The results suggest that nanotechnology can play a significant role in addressing the challenges of Martian agriculture. The increased efficiency of nutrient absorption in plants indicates that nanotechnology-enhanced fertilizers could be a key component in developing sustainable agricultural practices on Mars.\n" +
                        "\n" +
                        "Conclusion:\n" +
                        "This study demonstrates the potential of nanotechnology in enhancing plant growth in Martian soil simulants. Further research is needed to explore the long-term effects and scalability of this approach for future Mars colonization efforts.\n" +
                        "\n" +
                        "Keywords:\n" +
                        "Mars, Nanotechnology, Agriculture, Soil Simulants, Extraterrestrial Farming");
                TextPredefinit textPredefinit2 = new TextPredefinit("Narrativa catalana", "En una terra llunyana, anomenada Aethoria, on les muntanyes tocaven els núvols i els rius cantaven cançons antigues, lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivia un jove anomenat Elandor, que somiava amb aventures més enllà dels confins del seu poble tranquil. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                        "\n" +
                        "Una nit, sota un cel estrellat, Elandor va trobar una pedra lluent, caiguda del firmament. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Aquesta pedra era una clau per a secrets antics i portes oblidades de mons distants.\n" +
                        "\n" +
                        "Amb la pedra en mà, Elandor va emprendre un viatge a través de boscos encantats, on les criatures parlen en enigmes. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Va conèixer una sàvia anomenada Miriel, que parlava amb els arbres i coneixia els camins secrets de la terra.\n" +
                        "\n" +
                        "Junts, van travessar terres que mai abans havien estat trepitjades per peus mortals. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Enfrontant perills inimaginables, van descobrir la veritat amagada darrere de la pedra lluent: era una porta a un altre món.\n" +
                        "\n" +
                        "En aquest món, el cel brillava amb colors que no tenien nom, i les estrelles parlaven en una llengua antiga. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Aquí, Elandor i Miriel van trobar un drac savi, guardià de secrets universals.\n" +
                        "\n" +
                        "El drac els va parlar de l'equilibri dels mons i de com la pedra era essencial per mantenir la pau. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Amb aquesta nova saviesa, Elandor i Miriel van tornar al seu món, determinats a protegir l'harmonia de tots els mons.\n" +
                        "\n" +
                        "La seva aventura havia canviat la terra d'Aethoria per sempre. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Amb coratge i coneixement nous, van ser coneguts com els Guardians de les Estrelles, protectors de les veritats antigues i els mons oblidats.\n" +
                        "\n" +
                        "I així, les històries d'Elandor i Miriel van ser teixides en el tapís del temps, una cançó que mai no s'oblida, un conte que sempre es recorda. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
                TextPredefinit textPredefinit3 = new TextPredefinit("Grec: Historia i Mitologia grega", "Φυσικά, μπορώ να δημιουργήσω ένα πιο σύντομο κείμενο που χρησιμοποιεί αποκλειστικά ελληνικά γράμματα, σε στυλ με θεματολογία εμπνευσμένη από την αρχαία ελληνική μυθολογία:\n" +
                        "\n" +
                        "Εν τω μυθικώ κόσμω της αρχαίας Ελλάδας, όπου οι θεοί κατοικούν εν τω Ολύμπω και οι ήρωες ταξιδεύουν εις αναζήτηση κλέους, ο Αργοναύτης επλέει επί των κυμάτων. Κατα την διάρκεια του ταξιδιού, αποκαλύφθηκαν αρχαία μυστικά και θησαυροί κρυμμένοι εν της γης κόλποις. Οι θεοί παρατηρούν από ψηλά, μεταξύ των αστερισμών, επηρεάζοντας την μοίρα των θνητών. Στο τέλος, η αλήθεια αποκαλύφθηκε στον ήρωα, φέρνοντας σοφία και κατανόηση των αιώνιων μυστηρίων του κόσμου.\n" +
                        "\n" +
                        "Αυτό το κείμενο παρέχει μια μικρή γεύση από το πνεύμα και την ατμόσφαιρα της αρχαίας ελληνικής μυθολογίας, σε συνδυασμό με το στυλ το");
                TextPredefinit textPredefinit4 = new TextPredefinit("Ciríl·lic: Text Tecnic", "В деревне, где древние ремесла передаются из поколения в поколение, мастера тщательно создают матрешки, символы русской культуры. Начиная с выбора дерева, каждая матрешка рождается из любви и мастерства. Каждый кусок дерева обрабатывается с заботой, формируя основу для будущих кукол. Затем начинается процесс росписи, где каждая матрешка получает уникальный облик, отражающий русские народные сказания и традиции. Слои красок наносятся медленно, оживляя деревянные фигурки яркими цветами и узорами. После завершения росписи, матрешки собираются вместе, создавая гармоничный набор, где каждая меньшая кукла помещается внутрь большей. Эти куклы не просто игрушки, они несут в себе дух российской культуры, истории и искусства. И так, из поколения в поколение, этот традиционный ремесленный навык сохраняется, являясь частью богатого культурного наследия России.");
                TextPredefinit textPredefinit5 = new TextPredefinit("Recepta en Armeni", "Կատարելու լորեմ իպսում ճաշը, սկսեք լորեմ ալիքում աղցանից: Այնուհետև ավելացրեք իպսում պանիր և սեզոնային լորեմ բաղադրատոմսեր: Խառնել եւ թխել մինչև համեմատած ոսկեգույն գույնի հասնելը:\n" +
                        "\n" +
                        "Սերվիրեք լորեմ տոլմային համեմատած իպսում սոուսով: Համեմատեք լորեմ պանիրը համեմատած լորեմ լոլիկով եւ վարունգով, ավելացնելով իպսում բաղադրատոմսեր: Տեղադրեք այն մեջ թխվածքի մեջ եւ թխեք մինչև համեմատած մաշկը լինի կարմիր ու մեղմ:\n" +
                        "\n" +
                        "Այս լորեմ իպսում ճաշը մեծագույն է լորեմ ընտրանքի համար");
                TextPredefinit textPredefinit6 = new TextPredefinit("Descripció d'art en georgia", "ამ ლორე იპსუმ ტექსტში, ჩვენ აღწერთ ნახატს, რომელიც წარმოადგენს სახელმწიფო ლანდშაფტს. ლორემ იპსუმ ტყეებია და მდინარეები, ცის ლურჯი ფერი და მზის კიდევ მეტი ნათელი. ყოველი ხატულა იყოფა ისტორიაში და იქნება აღწერილი ამ ტექსტში. ნახატის ყოველი ნაწილია მნიშვნელოვანი, მთების მწვანე ფერი, ცის ლურჯი და წყლის განათება. თითქოს ნახატი ცოცხლობს და სუნთქავს, ყოველი ფერის და ფორმის გამო. ეს ლორე იპსუმ ნახატი წარმოადგენს სახელმწიფო ლანდშაფტის არტისტულ ხედვას.\n");
                textsPredefinits.put(1, textPredefinit1);
                textsPredefinits.put(2, textPredefinit2);
                textsPredefinits.put(3, textPredefinit3);
                textsPredefinits.put(4, textPredefinit4);
                textsPredefinits.put(5, textPredefinit5);
                textsPredefinits.put(6, textPredefinit6);
                escriureDiscTextsPredefinits(textsPredefinits);


            }

            File filePublics = new File(PATH_TEXTS_PUBLICS);
            if (!filePublics.exists()) { //inicialitzem el document buit
                HashMap<Integer, TextPublic> textPublicHashMap = new HashMap<>();
                escriureDiscTextsPublics(textPublicHashMap);
            }

        }
            catch (Exception e){
            throw new ExcepcioLlegintDeDisc("S'ha produit algun error intentant llegir/escriure a disc");
        }
        

    }

    //TEXTS PREDEFINITS
    /**
     * Llegeix els textos predefinits des del disc.
     *
     * @return HashMap amb els textos predefinits llegits.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura del disc.
     */
    private HashMap<Integer, TextPredefinit> llegirDiscTextsPredefinits() throws ExcepcioLlegintDeDisc {
        HashMap<Integer, TextPredefinit> textsPredefinits = new HashMap<>();
        JSONParser parser = new JSONParser();

        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(PATH_TEXTS_PREDEFINITS), StandardCharsets.UTF_8);
            JSONObject json = (JSONObject) parser.parse(reader);

            for (Object key : json.keySet()) {
                String keyStr = (String) key;
                JSONObject textJson = (JSONObject) json.get(keyStr);

                String titol = (String) textJson.get("titol");
                String contingut = (String) textJson.get("contingut");

                TextPredefinit textPredefinit = new TextPredefinit(titol, contingut);
                textsPredefinits.put(Integer.parseInt(keyStr), textPredefinit);
            }

        } catch (Exception e) {
            throw new ExcepcioLlegintDeDisc();
        }
        return textsPredefinits;
    }

    /**
     * Escriu els textos predefinits al disc en format JSON.
     *
     * @param textsPredefinits HashMap amb els textos predefinits a escriure.
     * @throws ExcepcioEscrivintADisc Si es produeix un error d'escriptura a disc.
     */
    private void escriureDiscTextsPredefinits(HashMap<Integer, TextPredefinit> textsPredefinits) throws ExcepcioEscrivintADisc {
        try {
            JSONObject jsonObject = new JSONObject();

            for (Map.Entry<Integer, TextPredefinit> entry : textsPredefinits.entrySet()) {
                JSONObject textJson = new JSONObject();
                textJson.put("titol", entry.getValue().getNom());
                textJson.put("contingut", entry.getValue().llegirContingut());

                jsonObject.put(String.valueOf(entry.getKey()), textJson);
            }

            OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(PATH_TEXTS_PREDEFINITS), StandardCharsets.UTF_8);

            file.write(jsonObject.toJSONString());
            file.flush();


        } catch (Exception e) {
            throw new ExcepcioEscrivintADisc();
        }
    }

    /**
     * Obté un HashMap amb tots els textos predefinits.
     * @return HashMap amb clau Integer i valor TextPredefinit.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura de disc.
     */
    public HashMap<Integer, TextPredefinit> getTextsPredefinits() throws ExcepcioLlegintDeDisc {
        return llegirDiscTextsPredefinits();
    }

    /**
     * Obté un text predefinit específic donat el seu identificador.
     * @param idTextPredefinit Identificador del text predefinit.
     * @return Instància de TextPredefinit corresponent a l'identificador.
     * @throws ExcepcioIdNoValid Si l'identificador no correspon a cap text predefinit.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura de disc.
     */
    public TextPredefinit getTextPredefinit(Integer idTextPredefinit) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc {
        HashMap<Integer, TextPredefinit> TextsPredefinits = llegirDiscTextsPredefinits();
        if (!TextsPredefinits.containsKey(idTextPredefinit)) throw new ExcepcioIdNoValid();
        return TextsPredefinits.get(idTextPredefinit);
    }

    /**
     * Crea un nou text predefinit.
     * @param text Instància de TextPredefinit a crear.
     * @throws ExcepcioNomTextPredefinitJaExisteix Si el nom del text ja existeix.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del text.
     */
    public void crearTextPredefinit(TextPredefinit text) throws ExcepcioNomTextPredefinitJaExisteix, ExcepcioErrorDurantLaCreacio {

        try {
            HashMap<Integer, TextPredefinit> textsPredefinits = llegirDiscTextsPredefinits();

            for (TextPredefinit existingText : textsPredefinits.values()) {
                if (existingText.getNom().equals(text.getNom())) {
                    throw new ExcepcioNomTextPredefinitJaExisteix();
                }
            }

            Integer primerIdDisponible = 1;
            while (textsPredefinits.containsKey(primerIdDisponible)) {
                ++primerIdDisponible;
            }

            textsPredefinits.put(primerIdDisponible, text);

            escriureDiscTextsPredefinits(textsPredefinits);
        }
        catch (ExcepcioNomTextPredefinitJaExisteix e) {
            throw new ExcepcioNomTextPredefinitJaExisteix();
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantLaCreacio();
        }

    }

    /**
     * Elimina un text predefinit donat el seu identificador.
     * @param idText Identificador del text predefinit a eliminar.
     * @throws ExcepcioIdNoValid Si l'identificador no correspon a cap text predefinit.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrament del text.
     */
    public void eliminarTextPredefinit(Integer idText) throws ExcepcioIdNoValid, ExcepcioErrorDurantEsborrat {
        try {
            HashMap<Integer, TextPredefinit> textsPredefinits = llegirDiscTextsPredefinits();

            if (!textsPredefinits.containsKey(idText)) {
                throw new ExcepcioIdNoValid();
            }

            textsPredefinits.remove(idText);

            escriureDiscTextsPredefinits(textsPredefinits);
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantEsborrat();
        }
    }

    /**
     * Modifica un text predefinit existent.
     * @param idText Identificador del text predefinit a modificar.
     * @param newText Instància de TextPredefinit amb les noves dades.
     * @throws ExcepcioIdNoValid Si l'identificador no correspon a cap text predefinit.
     * @throws ExcepcioNomTextPredefinitJaExisteix Si el nou nom del text ja existeix.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la modificació del text.
     */
    public void modificarTextPredefinit(Integer idText, TextPredefinit newText) throws ExcepcioIdNoValid, ExcepcioNomTextPredefinitJaExisteix, ExcepcioErrorDurantLaCreacio {

        try {
            HashMap<Integer, TextPredefinit> textsPredefinits = llegirDiscTextsPredefinits();

            if (!textsPredefinits.containsKey(idText)) {
                throw new ExcepcioIdNoValid();
            }

            for (Map.Entry<Integer, TextPredefinit> entry : textsPredefinits.entrySet()) {
                if (!entry.getKey().equals(idText) && entry.getValue().getNom().equals(newText.getNom())) {
                    throw new ExcepcioNomTextPredefinitJaExisteix();
                }
            }

            textsPredefinits.put(idText, newText);
            escriureDiscTextsPredefinits(textsPredefinits);
        }
        catch (ExcepcioIdNoValid e){
            throw new ExcepcioIdNoValid();
        }
        catch (ExcepcioNomTextPredefinitJaExisteix e){
            throw new ExcepcioNomTextPredefinitJaExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantLaCreacio();
        }
    }


    //TEXTS PUBLICS

    /**
     * Escriu els textos públics en el disc.
     * @param textsPublics HashMap amb clau Integer i valor TextPublic.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant l'escriptura al disc.
     */
    private void escriureDiscTextsPublics(HashMap<Integer, TextPublic> textsPublics) throws ExcepcioLlegintDeDisc {
        try {
            JSONObject jsonObject = new JSONObject();

            for (Map.Entry<Integer, TextPublic> entry : textsPublics.entrySet()) {
                JSONObject textJson = new JSONObject();
                textJson.put("titol", entry.getValue().getNom());
                textJson.put("username", entry.getValue().getUsuariUsername());
                textJson.put("contingut", entry.getValue().llegirContingut());

                jsonObject.put(String.valueOf(entry.getKey()), textJson);
            }
            OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(PATH_TEXTS_PUBLICS), StandardCharsets.UTF_8);

                file.write(jsonObject.toJSONString());
                file.flush();

        } catch (Exception e) {
            throw new ExcepcioLlegintDeDisc();
        }
    }

    /**
     * Llegeix els textos públics des del disc.
     * @return HashMap amb clau Integer i valor TextPublic.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura de disc.
     */
    private HashMap<Integer, TextPublic> llegirDiscTextsPublics() throws ExcepcioLlegintDeDisc {
        HashMap<Integer, TextPublic> textsPublics = new HashMap<>();
        JSONParser parser = new JSONParser();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(PATH_TEXTS_PUBLICS), StandardCharsets.UTF_8)) {
            JSONObject json = (JSONObject) parser.parse(reader);

            for (Object key : json.keySet()) {
                String keyStr = (String) key;
                JSONObject textJson = (JSONObject) json.get(keyStr);

                String titol = (String) textJson.get("titol");
                String username = (String) textJson.get("username");
                String contingut = (String) textJson.get("contingut");

                TextPublic textPublic = new TextPublic(titol, contingut, username);
                textsPublics.put(Integer.parseInt(keyStr), textPublic);
            }

        } catch (Exception e) {
            throw new ExcepcioLlegintDeDisc();
        }
        return textsPublics;
    }

    /**
     * Obté un HashMap amb tots els textos públics.
     * @return HashMap amb clau Integer i valor TextPublic.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura de disc.
     */
    public HashMap<Integer, TextPublic> getTextsPublics() throws ExcepcioLlegintDeDisc {
        return llegirDiscTextsPublics();
    }

    /**
     * Obté un text públic específic donat el seu identificador.
     * @param idTextPublic Identificador del text públic.
     * @return Instància de TextPublic corresponent a l'identificador.
     * @throws ExcepcioIdNoValid Si l'identificador no correspon a cap text públic.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura de disc.
     */
    public TextPublic getTextPublic(Integer idTextPublic) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc {
        HashMap<Integer, TextPublic> TextsPublics = llegirDiscTextsPublics();
        if (!TextsPublics.containsKey(idTextPublic)) throw new ExcepcioIdNoValid();
        return TextsPublics.get(idTextPublic);
    }

    /**
     * Crea un nou text públic.
     * @param text Instància de TextPublic a crear.
     * @throws ExcepcioNomTextPublicJaExisteix Si el nom del text ja existeix.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació del text.
     */
    public void crearTextPublic(TextPublic text) throws ExcepcioNomTextPublicJaExisteix, ExcepcioErrorDurantLaCreacio {

        try {
            HashMap<Integer, TextPublic> textsPublics = llegirDiscTextsPublics();

            for (TextPublic existingText : textsPublics.values()) {
                if (existingText.getNom().equals(text.getNom())) {
                    throw new ExcepcioNomTextPublicJaExisteix();
                }
            }

            Integer primerIdDisponible = 1;
            while (textsPublics.containsKey(primerIdDisponible)) {
                ++primerIdDisponible;
            }

            textsPublics.put(primerIdDisponible, text);
            escriureDiscTextsPublics(textsPublics);
        }
        catch (Exception e) {
            throw new ExcepcioErrorDurantLaCreacio();
        }
    }

    /**
     * Elimina un text públic basat en el seu identificador.
     * @param idText Identificador del text públic a eliminar.
     * @throws ExcepcioIdNoValid Si l'identificador proporcionat no correspon a cap text públic.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'eliminació del text públic.
     */
    public void eliminarTextPublic(Integer idText) throws ExcepcioIdNoValid, ExcepcioErrorDurantEsborrat {

        try {
            HashMap<Integer, TextPublic> textsPublics = llegirDiscTextsPublics();

            if (!textsPublics.containsKey(idText)) {
                throw new ExcepcioIdNoValid();
            }

            textsPublics.remove(idText);
            escriureDiscTextsPublics(textsPublics);
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantEsborrat();
        }
    }

    /**
     * Modifica un text públic existent.
     * @param idText Identificador del text públic a modificar.
     * @param newText Instància de TextPublic amb les noves dades.
     * @throws ExcepcioIdNoValid Si l'identificador no correspon a cap text públic.
     * @throws ExcepcioNomTextPublicJaExisteix Si el nou nom del text ja existeix.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació del text.
     */
    public void modificarTextPublic(Integer idText, TextPublic newText) throws ExcepcioIdNoValid, ExcepcioNomTextPublicJaExisteix, ExcepcioErrorDurantModificacio {

        try {
            HashMap<Integer, TextPublic> textsPublics = llegirDiscTextsPublics();

            if (!textsPublics.containsKey(idText)) {
                throw new ExcepcioIdNoValid();
            }

            for (Map.Entry<Integer, TextPublic> entry : textsPublics.entrySet()) {
                if (!entry.getKey().equals(idText) && entry.getValue().getNom().equals(newText.getNom())) {
                    throw new ExcepcioNomTextPublicJaExisteix();
                }
            }

            textsPublics.put(idText, newText);
            escriureDiscTextsPublics(textsPublics);
        }
        catch (ExcepcioIdNoValid e){
            throw new ExcepcioIdNoValid();
        }
        catch (ExcepcioNomTextPublicJaExisteix e){
            throw new ExcepcioNomTextPublicJaExisteix();
        }
        catch (Exception e){
            throw new ExcepcioErrorDurantModificacio();
        }
    }

    /**
     * Obté un HashMap amb tots els textos públics d'un usuari específic.
     * @param username Nom d'usuari.
     * @return HashMap amb clau Integer i valor TextPublic.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura de disc.
     */
    public HashMap<Integer, TextPublic> getTextsPublicsUsuari(String username) throws ExcepcioLlegintDeDisc {
        HashMap<Integer, TextPublic>  TextsPublics = llegirDiscTextsPublics();
        HashMap<Integer, TextPublic> textsPublicsUsuari = new HashMap<>();
        for (Integer i : TextsPublics.keySet()) {
            if (username.equals(TextsPublics.get(i).getUsuariUsername())) {
                textsPublicsUsuari.put(i, TextsPublics.get(i));
            }
        }
        return textsPublicsUsuari;
    }

    /**
     * Obté un text públic d'un usuari específic basat en l'identificador.
     * @param idText Identificador del text públic.
     * @return TextPublic corresponent a l'identificador.
     * @throws ExcepcioIdNoValid Si l'identificador proporcionat no correspon a cap text públic.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error durant la lectura dels textos públics des del disc.
     */
    public TextPublic getTextPublicUsuari(Integer idText) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc {
        HashMap<Integer, TextPublic>  TextsPublics = llegirDiscTextsPublics();
        if (!TextsPublics.containsKey(idText)) throw new ExcepcioIdNoValid();
        return TextsPublics.get(idText);
    }

}
