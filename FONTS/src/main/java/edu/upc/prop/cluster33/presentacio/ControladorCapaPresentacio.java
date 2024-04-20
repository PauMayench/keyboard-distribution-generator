package edu.upc.prop.cluster33.presentacio;

import edu.upc.prop.cluster33.domini.ControladorCapaDomini;
import edu.upc.prop.cluster33.excepcions.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


import java.util.HashMap;
import java.util.Vector;

/**
 * Aquesta classe serveix com a controlador de la capa de presentació,
 * actuant com a intermediari entre la vista i el domini.
 */
public class ControladorCapaPresentacio {


    /**
     * Controlador de la capa de domini.
     */
    private ControladorCapaDomini controladorDomini;

    /**
     * Vista de la pantalla inicial.
     */
    VistaPantallaIni vprincipal;

    /**
     * Vista de la pantalla de login.
     */
    VistaLogin vlogin;

    /**
     * Vista del formulari de registre.
     */
    VistaRegistre vregistre;

    /**
     * Vista del menú principal.
     */
    VistaMenuPrincipal vMenuP;

    /**
     * Vista de creació de teclat.
     */
    VistaCreacioTeclat vCreacioTeclat;

    /**
     * Vista de creació de freqüències manualment.
     */
    VistaCreacioFreqManual vCreacioFreqManual;

    /**
     * Vista de creació de teclats a partir d'un fitxer.
     */
    VistaCreacioFitxer vCreacioFitxer;

    /**
     * Vista de creació de textos.
     */
    VistaCreacioTextos vCreacioTextos;

    /**
     * Vista de gestió de teclats.
     */
    VistaGestionarTeclats vGestionarTeclats;

    /**
     * Vista de gestió d'usuari.
     */
    VistaGestioUsuari vGestioUsuari;

    /**
     * Vista per veure detalls d'un teclat.
     */
    VistaVeureTeclat vVeureTeclat;

    /**
     * Vista per eliminar teclats.
     */
    VistaEliminarTeclat vEliminarTeclat;

    /**
     * Vista per modificar teclats.
     */
    VistaModificarTeclat vModificarTeclat;

    /**
     * Vista per penjar textos públics.
     */
    VistaPenjarTextPublic vPenjarTextPublic;

    /**
     * Vista per penjar textos predefinits.
     */
    VistaPenjarTextPredefinit vPenjarTextPredefinit;

    /**
     * Vista de menú de gestió de textos públics.
     */
    VistaMenuGestioTextosPublics vGestioTextPublic;

    /**
     * Vista de menú de gestió de textos predefinits.
     */
    VistaMenuGestioTextosPredefinits vGestioTextPredefinit;

    /**
     * Nom d'usuari actualment actiu.
     */
    String username;

    /**
     * Vista de gestió d'usuaris.
     */
    VistaGestioUsuaris vGestioUsuaris;
    /**
     * Constructor de la classe ControladorCapaPresentacio.
     * Inicialitza el controlador de domini i totes les vistes associades a l'aplicació.
     *
     * @throws Exception Si es produeix algun error durant la inicialització.
     */
    public ControladorCapaPresentacio() throws Exception{
        try{
            controladorDomini = new ControladorCapaDomini();
        }
        catch (Exception e){
            mostraError(e.getMessage());
        }
        vprincipal = new VistaPantallaIni(this);
        vlogin = new VistaLogin(this);
        vregistre = new VistaRegistre(this);
        vMenuP = new VistaMenuPrincipal(this);
        vCreacioTeclat = new VistaCreacioTeclat(this);
        vCreacioFreqManual = new VistaCreacioFreqManual(this);
        vCreacioFitxer = new VistaCreacioFitxer(this);
        vCreacioTextos = new VistaCreacioTextos(this);
        vGestionarTeclats = new VistaGestionarTeclats(this);
        vGestioUsuari = new VistaGestioUsuari(this);
        vVeureTeclat = new VistaVeureTeclat(this);
        vEliminarTeclat = new VistaEliminarTeclat(this);
        vModificarTeclat = new VistaModificarTeclat(this);
        vPenjarTextPublic = new VistaPenjarTextPublic(this);
        vPenjarTextPredefinit = new VistaPenjarTextPredefinit(this);
        vGestioTextPublic = new VistaMenuGestioTextosPublics(this);
        vGestioTextPredefinit = new VistaMenuGestioTextosPredefinits(this);
        vGestioUsuaris = new VistaGestioUsuaris(this);
    }

    /**
     * Mostra un missatge d'error a l'usuari.
     *
     * @param merror El missatge d'error a mostrar.
     */
    public void mostraError(String merror){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("ERROR: " + merror);
        alert.showAndWait();
    }

    /**
     * Mostra un missatge d'error amb una finestra propietària especificada.
     *
     * @param merror El missatge d'error a mostrar.
     * @param ownerWindow La finestra propietària en la qual es mostrarà l'alerta.
     */
    public void mostraError(String merror, Window ownerWindow) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(ownerWindow); // Set the owner of the alert
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("ERROR: " + merror);
        alert.showAndWait();
    }

    /**
     * Mostra un missatge informatiu a l'usuari.
     *
     * @param missatge El missatge informatiu a mostrar.
     */
    public void mostraInfo(String missatge){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacio");
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    /**
     * Mostra una finestra de càrrega amb un missatge i executa una tasca en un nou fil.
     *
     * @param missatge El missatge que es mostrarà a la finestra de càrrega.
     * @param task La tasca a executar en segon pla.
     * @param ownerWindow La finestra propietària d'aquest diàleg.
     */
    public void mostraLoading(String missatge, Runnable task, Window ownerWindow) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(StageStyle.UNDECORATED); // No window decorations
        dialogStage.initOwner(ownerWindow); // Set the owner of the dialog

        VBox vbox = new VBox(new ProgressIndicator(), new Label(missatge));
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);

        // Start the task in a new thread
        new Thread(() -> {
            try {
                task.run();
            } finally {
                // Close the dialog on the JavaFX Application Thread
                Platform.runLater(dialogStage::close);
            }
        }).start();

        dialogStage.showAndWait();
    }

    /**
     * Inicialitza i mostra la vista principal de l'aplicació.
     */
    public void Inicialitza() {
        vprincipal.mostra();
        username = "";
    }

    /**
     * Mostra la vista de login de l'aplicació.
     */
    public void mostraLogin() {
        vlogin.mostra();
    }

    /**
     * Mostra la vista de registre de l'aplicació.
     */
    public void mostraRegistre(){
        vregistre.mostra();
    }

    /**
     * Mostra el menú principal de l'aplicació.
     */
    public void mostraMenuPrincipal() {vMenuP.mostra();}

    /**
     * Mostra la vista de creació de teclat.
     */
    public void mostraVistaCreacioTeclat() {vCreacioTeclat.mostra();}

    /**
     * Mostra la vista de creació de freqüències manuals.
     *
     * @param idAlgorisme Identificador de l'algorisme seleccionat.
     * @param idTeclat Identificador del teclat per al qual es crearan les freqüències.
     */
    public void mostraVistaCreacioFreqManual(Integer idAlgorisme, int idTeclat) {vCreacioFreqManual.mostra(idAlgorisme, idTeclat);}

    /**
     * Mostra la vista de creació d'un teclat mitjançant un fitxer.
     *
     * @param idAlgorisme Identificador de l'algorisme seleccionat.
     * @param op Opció seleccionada per a la creació del teclat.
     * @param idTeclat Identificador del teclat que es vol modificar o crear.
     * @throws ExcepcioIdNoValid si l'identificador del teclat no és vàlid.
     */
    public void mostraVistaCreacioFitxer(Integer idAlgorisme, int op, int idTeclat) throws ExcepcioIdNoValid {
        vCreacioFitxer.mostra(idAlgorisme, op, idTeclat);
    }

    /**
     * Mostra la vista de creació de textos.
     *
     * @param idAlgorisme Identificador de l'algorisme seleccionat.
     * @param op Opció seleccionada per a la creació de textos.
     * @param idTeclat Identificador del teclat que es vol utilitzar.
     */
    public void mostraVistaCreacioTextos(Integer idAlgorisme, int op, int idTeclat){
        vCreacioTextos.mostra(idAlgorisme, op, idTeclat);
    }

    /**
     * Mostra la vista per gestionar els teclats existents.
     */
    public void mostraGestionarTeclats()  {vGestionarTeclats.mostra();}

    /**
     * Mostra la vista per gestionar les dades de l'usuari.
     */
    public void mostraGestioUsuari() {vGestioUsuari.mostra();}

    /**
     * Mostra la vista per visualitzar un teclat específic.
     *
     * @param idTeclat Identificador del teclat que es vol visualitzar.
     */
    public void mostraVeureTeclat(int idTeclat) {
        vVeureTeclat.mostra(idTeclat);
    }

    /**
     * Mostra la vista per eliminar un teclat específic.
     *
     * @param nomTeclat Nom del teclat que es vol eliminar.
     * @param idTeclat Identificador del teclat que es vol eliminar.
     */
    public void mostraEliminarTeclat(String nomTeclat, int idTeclat) {
        vEliminarTeclat.mostra(nomTeclat, idTeclat);
    }

    /**
     * Mostra la vista per modificar un teclat específic.
     *
     * @param nomTeclat Nom del teclat que es vol modificar.
     * @param idTeclat Identificador del teclat que es vol modificar.
     */
    public void mostraModificarTeclat(String nomTeclat, int idTeclat) {
        vModificarTeclat.mostra(nomTeclat, idTeclat);
    }

    /**
     * Mostra la vista per a penjar u text Públic
     */
    public void mostraPenjarTextPublic(){
        vPenjarTextPublic.mostra();
    }

    /**
     * Mostra la vista per a penjar u text Predefinit
     */
    public void mostraPenjarTextPredefinit() {
        vPenjarTextPredefinit.mostra();
    }

    /**
     * Mostra la vista per gestionar els texts públics.
     */
    public void mostraGestioTextPublic(){
        vGestioTextPublic.mostra();
    }

    /**
     * Mostra la vista per gestionar els texts predefinits.
     */
    public void mostraGestioTextPredefinit(){
        vGestioTextPredefinit.mostra();
    }

    /**
     * Mostra la vista de gestio d'usuaris
     */
    public void mostraGestioUsuaris(){
        vGestioUsuaris.mostra();
    }

    /**
     * Inicia una instància de l'aplicació amb un usuari específic.
     *
     * @param username Nom d'usuari per iniciar sessió.
     * @param password Contrasenya de l'usuari per iniciar sessió.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioPasswordIncorrecte Si la contrasenya és incorrecta.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint el disc.
     */
    public void iniciaInstancia(String username, String password) throws ExcepcioUsernameNoExisteix, ExcepcioPasswordIncorrecte, ExcepcioLlegintDeDisc {
        controladorDomini.login(username, password);
        this.username = username;
    }

    /**
     * Registra un nou usuari al sistema.
     *
     * @param username Nom d'usuari per registrar.
     * @param password Contrasenya per al nou usuari.
     * @param maBona Indica la mà bona de l'usuari.
     * @param isAdmin Indica si l'usuari serà administrador.
     * @throws ExcepcioUsernameJaExistent Si el nom d'usuari ja existeix.
     * @throws ExcepcioPasswordNoPassaFiltre Si la contrasenya no passa el filtre de seguretat.
     * @throws ExcepcioFormatMaIncorrecte Si el format de la mà bona és incorrecte.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació de l'usuari.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint el disc.
     */
    public void enregistraUsuari(String username, String password, String maBona, boolean isAdmin) throws ExcepcioUsernameJaExistent, ExcepcioPasswordNoPassaFiltre, ExcepcioFormatMaIncorrecte, ExcepcioErrorDurantLaCreacio, ExcepcioLlegintDeDisc {
        controladorDomini.registre(username, password, maBona, isAdmin);
        this.username = username;
    }

    /**
     * Tanca la sessió de l'usuari actual.
     *
     * @throws ExcepcioErrorGuardantDades Si es produeix un error guardant les dades durant el tancament de sessió.
     */
    public void logout() throws ExcepcioErrorGuardantDades{
        controladorDomini.logout() ;
        this.username = null;
    }

    /**
     * Retorna el nom d'usuari de la sessió actual.
     *
     * @return El nom d'usuari de l'usuari actual.
     */
    public String getUsername(){return this.username;}

    /**
     * Canvia la contrasenya de l'usuari actual.
     *
     * @param passAntic La contrasenya antiga de l'usuari.
     * @param passNou La nova contrasenya per a l'usuari.
     * @throws ExcepcioPasswordIncorrecte Si la contrasenya antiga és incorrecta.
     * @throws ExcepcioPasswordNoPassaFiltre Si la nova contrasenya no passa el filtre de seguretat.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació de la contrasenya.
     */
    public void canviaPassword(String passAntic, String passNou) throws ExcepcioPasswordIncorrecte, ExcepcioPasswordNoPassaFiltre, ExcepcioErrorDurantModificacio {
        controladorDomini.canviarPassword(passAntic, passNou);
    }

    /**
     * Obté la mà bona de l'usuari actual.
     *
     * @return La mà bona de l'usuari actual.
     */
    public String getMaBona(){
        return controladorDomini.getMaBona();
    }

    /**
     * Canvia la mà bona de l'usuari actual.
     *
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació de la mà bona.
     */
    public void canviaMaBona() throws ExcepcioErrorDurantModificacio {
        controladorDomini.canviarMaBona();
    }

    /**
     * Esborra l'usuari actual del sistema.
     *
     * @param username Nom d'usuari de l'usuari a esborrar.
     * @param password Contrasenya de l'usuari a esborrar.
     * @throws ExcepcioUsernamePasswordIncorrectes Si el nom d'usuari o la contrasenya són incorrectes.
     * @throws ExcepcioIntentEsborrarAdmin Si s'intenta esborrar un usuari administrador.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrat.
     */
    public void esborraUsuari(String username, String password) throws ExcepcioUsernamePasswordIncorrectes, ExcepcioIntentEsborrarAdmin, ExcepcioErrorDurantEsborrat{
        controladorDomini.eliminarPropiUsuari(username, password);
    }

    /**
     * Retorna un mapa amb els identificadors i noms dels teclats disponibles.
     *
     * @return Un HashMap on la clau és l'ID del teclat i el valor és el nom del teclat.
     */
    public HashMap<Integer,String> getTeclats(){
        return controladorDomini.getTeclats();
    }

    /**
     * Obté la informació d'un teclat específic.
     *
     * @param id Identificador del teclat.
     * @return Un vector amb la informació del teclat.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     */
    public Vector<String> getTeclat(int id) throws ExcepcioIdNoValid{
        return controladorDomini.getTeclat(id);
    }

    /**
     * Retorna un mapa amb els identificadors i noms dels algorismes disponibles.
     *
     * @return Un HashMap on la clau és l'ID de l'algorisme i el valor és el nom de l'algorisme.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint el disc.
     */
    public HashMap<Integer,String> getAlgorismes() throws ExcepcioLlegintDeDisc {
        return controladorDomini.getAlgorismes();
    }

    /**
     * Obté el nom de l'algorisme en ús per un teclat específic.
     *
     * @param id Identificador del teclat.
     * @return Nom de l'algorisme en ús pel teclat especificat.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     */
    public String getAlgorismeEnUs(int id) throws ExcepcioIdNoValid{
        return controladorDomini.getAlgorismeTeclat(id);
    }

    /**
     * Modifica l'algorisme d'un teclat específic.
     *
     * @param idTeclat Identificador del teclat a modificar.
     * @param idAlgorisme Identificador del nou algorisme a assignar.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat o de l'algorisme no és vàlid.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint el disc.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació.
     */
    public void modificarTeclatAlgorisme(int idTeclat, int idAlgorisme) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc, ExcepcioErrorDurantModificacio {
        controladorDomini.modificarTeclatAlgorisme(idTeclat, idAlgorisme);
    }

    /**
     * Obté el nom d'un teclat específic segons el seu identificador.
     *
     * @param id Identificador del teclat.
     * @return Nom del teclat.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     */
    public String getNomTeclat(int id) throws ExcepcioIdNoValid{
        return controladorDomini.getNomTeclat(id);
    }

    /**
     * Modifica el nom d'un teclat específic.
     *
     * @param id Identificador del teclat a modificar.
     * @param nouNom Nou nom per al teclat.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb el mateix nom.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació.
     */
    public void modificarTeclatNom(int id, String nouNom) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioErrorDurantModificacio {
        controladorDomini.modificarTeclatNom(id, nouNom);
    }

    /**
     * Elimina un teclat del sistema.
     *
     * @param id Identificador del teclat a eliminar.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrat.
     */
    public void eliminarTeclat(int id) throws ExcepcioIdNoValid, ExcepcioErrorDurantEsborrat {
        controladorDomini.eliminarTeclat(id);
    }

    /**
     * Crea un nou teclat a partir d'una entrada manual de freqüències.
     *
     * @param idAlgorisme Identificador de l'algorisme a utilitzar.
     * @param frequencies Cadena de text amb les freqüències.
     * @param nomTeclat Nom del nou teclat.
     * @throws ExcepcioIdNoValid Si l'identificador de l'algorisme no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb el mateix nom.
     * @throws ExcepcioMesDeUnAlfabetAlhora Si les freqüències pertanyen a més d'un alfabet.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació.
     * @throws ExcepcioFrequencies Si hi ha algun error amb les freqüències introduïdes.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public void crearTeclatInputManual(int idAlgorisme, String frequencies, String nomTeclat) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioMesDeUnAlfabetAlhora, ExcepcioErrorDurantLaCreacio, ExcepcioFrequencies, ExcepcioLlegintDeDisc {
        controladorDomini.crearTeclatFrequencies(idAlgorisme, frequencies, nomTeclat);
    }

    /**
     * Crea un teclat basat en un fitxer de text proporcionat per l'usuari.
     *
     * @param idAlgorisme Identificador de l'algorisme a utilitzar.
     * @param text Text a partir del qual es crearà el teclat.
     * @param nomTeclat Nom del nou teclat.
     * @throws ExcepcioIdNoValid Si l'identificador de l'algorisme no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb el mateix nom.
     * @throws ExcepcioMesDeUnAlfabetAlhora Si el text pertany a més d'un alfabet.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les freqüències del text.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public void crearTeclatFitxerTextUsuari(int idAlgorisme, String text, String nomTeclat) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioMesDeUnAlfabetAlhora, ExcepcioErrorDurantLaCreacio, ExcepcioFrequencies, ExcepcioLlegintDeDisc {
        controladorDomini.crearTeclatFitxerTextUsuari(idAlgorisme, text, nomTeclat);
    }

    /**
     * Obté els texts públics disponibles.
     *
     * @return HashMap amb els identificadors i els noms dels texts públics.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public HashMap<Integer,String> getTextsPublics() throws ExcepcioLlegintDeDisc {
        return controladorDomini.getTextsPublics();
    }

    /**
     * Obté els texts predefinits disponibles.
     *
     * @return HashMap amb els identificadors i els noms dels texts predefinits.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public HashMap<Integer,String> getTextsPredefinits() throws ExcepcioLlegintDeDisc {
        return controladorDomini.getTextsPredefinits();
    }

    /**
     * Crea un teclat basat en un text públic.
     *
     * @param idAlgorisme Identificador de l'algorisme a utilitzar.
     * @param idTextPublic Identificador del text públic a utilitzar.
     * @param nomTeclat Nom del nou teclat.
     * @throws ExcepcioIdNoValid Si l'identificador de l'algorisme o del text públic no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb el mateix nom.
     * @throws ExcepcioMesDeUnAlfabetAlhora Si el text pertany a més d'un alfabet.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les freqüències del text.
     * @throws ExcepcioEscrivintADisc Si es produeix un error escrivint al disc.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public void crearTeclatFitxerTextPublic(int idAlgorisme, int idTextPublic, String nomTeclat) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioMesDeUnAlfabetAlhora, ExcepcioErrorDurantLaCreacio, ExcepcioFrequencies, ExcepcioEscrivintADisc, ExcepcioLlegintDeDisc {
        controladorDomini.crearTeclatFitxerTextPublic(idAlgorisme, idTextPublic, nomTeclat);
    }

    /**
     * Crea un teclat basat en un text predefinit.
     *
     * @param idAlgorisme Identificador de l'algorisme a utilitzar.
     * @param idTextPredefinit Identificador del text predefinit a utilitzar.
     * @param nomTeclat Nom del nou teclat.
     * @throws ExcepcioIdNoValid Si l'identificador de l'algorisme o del text predefinit no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si ja existeix un teclat amb el mateix nom.
     * @throws ExcepcioMesDeUnAlfabetAlhora Si el text pertany a més d'un alfabet.
     * @throws ExcepcioFrequencies Si hi ha un problema amb les freqüències del text.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public void crearTeclatFitxerTextPredefinit (int idAlgorisme, int idTextPredefinit, String nomTeclat) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioMesDeUnAlfabetAlhora, ExcepcioFrequencies, ExcepcioErrorDurantLaCreacio, ExcepcioLlegintDeDisc {
        controladorDomini.crearTeclatFitxerTextPredefinit(idAlgorisme, idTextPredefinit, nomTeclat);
    }

    /**
     * Afegeix un nou text públic al sistema.
     *
     * @param nomTextPublic Nom del text públic a afegir.
     * @param text Contingut del text públic.
     * @throws ExcepcioNomTextPublicJaExisteix Si ja existeix un text públic amb el mateix nom.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació.
     */
    public void afegirTextPublic(String nomTextPublic, String text) throws ExcepcioNomTextPublicJaExisteix, ExcepcioErrorDurantLaCreacio{
        controladorDomini.afegirTextPublic(nomTextPublic, text);
    }

    /**
     * Afegeix un nou text predefinit al sistema.
     *
     * @param nom Nom del text predefinit a afegir.
     * @param contingutFitxer Contingut del text predefinit.
     * @throws ExcepcioNomTextPredefinitJaExisteix Si ja existeix un text predefinit amb el mateix nom.
     * @throws ExcepcioErrorDurantLaCreacio Si es produeix un error durant la creació.
     */
    public void afegirTextPredefinit(String nom, String contingutFitxer) throws ExcepcioNomTextPredefinitJaExisteix, ExcepcioErrorDurantLaCreacio {
        controladorDomini.afegirTextPredefinit(nom, contingutFitxer);
    }

    /**
     * Obté el contingut d'un text públic.
     *
     * @param id Identificador del text públic.
     * @return Contingut del text públic.
     * @throws ExcepcioIdNoValid Si l'identificador no és vàlid.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public String getTextPublic(int id) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc {
        return controladorDomini.getTextPublic(id);
    }

    /**
     * Obté els texts públics creats per l'usuari.
     *
     * @return HashMap amb els identificadors i els noms dels texts públics de l'usuari.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public HashMap<Integer,String> getTextsPublicsUsuari() throws ExcepcioLlegintDeDisc {
        return controladorDomini.getTextsPublicsUsuari();
    }

    /**
     * Elimina un text públic del sistema.
     *
     * @param id Identificador del text públic a eliminar.
     * @throws ExcepcioIdNoValid Si l'identificador no és vàlid.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrat.
     */
    public void eliminarTextPublic(int id) throws ExcepcioIdNoValid, ExcepcioErrorDurantEsborrat {
        controladorDomini.eliminarTextPublic(id);
    }

    /**
     * Comprova si l'usuari actual és administrador.
     *
     * @return Cert si l'usuari és administrador, fals en cas contrari.
     */
    public boolean isAdmin(){
        return controladorDomini.isAdmin();
    }

    /**
     * Obté el contingut d'un text predefinit.
     *
     * @param id Identificador del text predefinit.
     * @return Contingut del text predefinit.
     * @throws ExcepcioIdNoValid Si l'identificador no és vàlid.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public String getTextPredefinit(int id) throws ExcepcioIdNoValid, ExcepcioLlegintDeDisc {
        return controladorDomini.getTextPredefinit(id);
    }

    /**
     * Elimina un text predefinit del sistema.
     *
     * @param id Identificador del text predefinit a eliminar.
     * @throws ExcepcioIdNoValid Si l'identificador no és vàlid.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrat.
     */
    public void eliminarTextPredefinit(int id) throws ExcepcioIdNoValid, ExcepcioErrorDurantEsborrat {
        controladorDomini.eliminarTextPredefinit(id);
    }

    /**
     * Obté una llista dels usuaris del sistema.
     *
     * @return Array amb els noms d'usuari.
     * @throws ExcepcioUsuariNoEsAdmin Si l'usuari que fa la petició no és administrador.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     */
    public String[] getUsuaris() throws ExcepcioUsuariNoEsAdmin, ExcepcioLlegintDeDisc {
        return controladorDomini.getUsuaris();
    }

    /**
     * Assigna rol d'administrador a un usuari.
     *
     * @param username Nom d'usuari al qual assignar el rol d'administrador.
     * @throws ExcepcioIdNoValid Si l'identificador no és vàlid.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioUsuariNoEsAdmin Si l'usuari que fa la petició no és administrador.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació.
     */
    public void setAdmin(String username) throws ExcepcioIdNoValid, ExcepcioUsernameNoExisteix, ExcepcioUsuariNoEsAdmin, ExcepcioErrorDurantModificacio {
        controladorDomini.setAdmin(username);
    }

    /**
     * Elimina el rol d'administrador d'un usuari.
     *
     * @param username Nom d'usuari al qual eliminar el rol d'administrador.
     * @throws ExcepcioIdNoValid Si l'identificador no és vàlid.
     * @throws ExcepcioUsuariEsAdmin Si l'usuari és administrador.
     * @throws ExcepcioErrorDurantEsborrat Si es produeix un error durant l'esborrat.
     * @throws ExcepcioUsernameNoExisteix Si el nom d'usuari no existeix.
     * @throws ExcepcioUsuariNoEsAdmin Si l'usuari que fa la petició no és administrador.
     */
    public void eliminaAdminUsuari(String username) throws ExcepcioIdNoValid, ExcepcioUsuariEsAdmin, ExcepcioErrorDurantEsborrat, ExcepcioUsernameNoExisteix, ExcepcioUsuariNoEsAdmin {
        controladorDomini.eliminarUsuari(username);
    }

    /**
     * Modifica les freqüències d'un teclat a partir d'una entrada manual.
     *
     * @param idTeclat Identificador del teclat a modificar.
     * @param frequencies Cadena de text amb les freqüències.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioFrequencies Si hi ha un error en les freqüències proporcionades.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació.
     */
    public void modificarTeclatFrequenciesInputManual(int idTeclat, String frequencies) throws ExcepcioIdNoValid, ExcepcioFrequencies, ExcepcioLlegintDeDisc, ExcepcioErrorDurantModificacio {
        controladorDomini.modificarTeclatFrequencies(idTeclat, frequencies);
    }

    /**
     * Modifica les freqüències d'un teclat a partir d'un fitxer de text d'un usuari.
     *
     * @param idTeclat Identificador del teclat a modificar.
     * @param text Cadena de text amb el contingut del fitxer.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioMesDeUnAlfabetAlhora Si el text conté caràcters de més d'un alfabet.
     * @throws ExcepcioFrequencies Si hi ha un error en les freqüències.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació.
     */
    public void modificarTeclatFrequenciesFitxerTextUsuari(int idTeclat, String text) throws ExcepcioIdNoValid, ExcepcioMesDeUnAlfabetAlhora, ExcepcioFrequencies, ExcepcioLlegintDeDisc, ExcepcioErrorDurantModificacio {
        controladorDomini.modificarTeclatFrequenciesFitxerTextUsuari(idTeclat, text);
    }

    /**
     * Modifica les freqüències d'un teclat a partir d'un text públic.
     *
     * @param idTeclat Identificador del teclat a modificar.
     * @param idTextPublic Identificador del text públic a utilitzar.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si el nom del teclat ja existeix.
     * @throws ExcepcioMesDeUnAlfabetAlhora Si el text conté caràcters de més d'un alfabet.
     * @throws ExcepcioFrequencies Si hi ha un error en les freqüències.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació.
     */
    public void modificarTeclatFrequenciesFitxerTextPublic(int idTeclat, int idTextPublic) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioMesDeUnAlfabetAlhora, ExcepcioFrequencies, ExcepcioLlegintDeDisc, ExcepcioErrorDurantModificacio {
        controladorDomini.modificarTeclatFrequenciesFitxerTextPublic(idTeclat, idTextPublic);
    }

    /**
     * Modifica les freqüències d'un teclat a partir d'un text predefinit.
     *
     * @param idTeclat Identificador del teclat a modificar.
     * @param idTextPredefinit Identificador del text predefinit a utilitzar.
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     * @throws ExcepcioNomTeclatJaExisteix Si el nom del teclat ja existeix.
     * @throws ExcepcioMesDeUnAlfabetAlhora Si el text conté caràcters de més d'un alfabet.
     * @throws ExcepcioFrequencies Si hi ha un error en les freqüències.
     * @throws ExcepcioLlegintDeDisc Si es produeix un error llegint del disc.
     * @throws ExcepcioErrorDurantModificacio Si es produeix un error durant la modificació.
     */
    public void modificarTeclatFrequenciesFitxerTextPredefinit(int idTeclat, int idTextPredefinit) throws ExcepcioIdNoValid, ExcepcioNomTeclatJaExisteix, ExcepcioMesDeUnAlfabetAlhora, ExcepcioFrequencies, ExcepcioLlegintDeDisc, ExcepcioErrorDurantModificacio {
        controladorDomini.modificarTeclatFrequenciesFitxerTextPredefinit(idTeclat, idTextPredefinit);
    }


}