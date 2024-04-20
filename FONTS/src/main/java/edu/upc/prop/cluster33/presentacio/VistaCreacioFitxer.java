package edu.upc.prop.cluster33.presentacio;

import edu.upc.prop.cluster33.excepcions.ExcepcioIdNoValid;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Aquesta classe representa la vista de creació de fitxers per al teclat.
 * Proporciona una interfície d'usuari per seleccionar un fitxer i generar
 * un teclat a partir del seu contingut.
 */
public class VistaCreacioFitxer {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;

    /**
     * Constructor de la classe VistaCreacioFitxer.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaCreacioFitxer(ControladorCapaPresentacio c){
        cPresentacio = c;
    }

    /**
     * Llegeix el contingut d'un fitxer de text i el retorna com una cadena de caràcters.
     *
     * @param file El fitxer a llegir.
     * @return Una cadena de text amb el contingut del fitxer.
     * @throws IOException Si es produeix un error durant la lectura del fitxer.
     */
    private String readfile(File file) throws IOException {
        StringBuilder contingut = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String line;

        while((line = reader.readLine()) != null){
            contingut.append(line);
            contingut.append("\n");
        }
        reader.close();
        return contingut.toString();
    }

    /**
     * Mostra la interfície d'usuari per a la creació de teclats a partir d'un fitxer.
     * L'usuari pot seleccionar un fitxer i introduir un nom per al nou teclat.
     *
     * @param idAlgorisme Identificador de l'algorisme de creació del teclat.
     * @param op Opció seleccionada per l'usuari (1 per freqüències, 2 per text normal).
     * @param idTeclat Identificador del teclat a modificar (en cas de no ser una creació nova).
     * @throws ExcepcioIdNoValid Si l'identificador del teclat no és vàlid.
     */
    public void mostra(Integer idAlgorisme, int op, int idTeclat) throws ExcepcioIdNoValid {
        Stage window = new Stage();
        window.setResizable(true);
        window.setTitle("Key-Layout Generator");
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(10);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(80);
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);

        //Titol
        /*Label titol = new Label("Segueixi el format indicat a la següent capsa de text seguint el format indicat.");
        titol.setStyle("-fx-font-size: 16;");
        titol.setPrefHeight(40);
        titol.setPrefWidth(480);
        GridPane.setValignment(titol, VPos.CENTER);
        GridPane.setHalignment(titol, HPos.CENTER);
        GridPane.setConstraints(titol, 1, 0);*/

        //Text info
        Label info = new Label("FORMAT FREQÜÈNCIES: (POSAR CURSOR A SOBRE PER VEURE)");
        info.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: red;");
        info.setTooltip(new Tooltip("FORMAT:\n" +
                "PARAULA1-FREQUENCIA1\n" +
                "PARAULA2-FREQUENCIA2\n" +
                "       ...          \n" +
                "PARAULAN-FREQUENCIAN;\n" +
                "(Punt i coma després de l'última paraula és molt important!!!!!)\n" +
                "NO ÉS NECESSARI QUE LES PARAULES ESTIGUIN EN MAJÚSCULA"));
        GridPane.setColumnSpan(info, 2);
        GridPane.setConstraints(info, 0, 1);
        if (op == 2) info.setVisible(false);

        //FileChooser
        AtomicReference<String> contingut = new AtomicReference<>("");
        Button selectFileButton = new Button("Selecciona fitxer");
        GridPane.setColumnSpan(selectFileButton,2);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona fitxer");
        selectFileButton.setOnAction(e ->{
            File selectedFile =  fileChooser.showOpenDialog(window);
            if(selectedFile != null){
                try{
                    contingut.set(readfile(selectedFile));
                }
                catch (Exception ex){
                    cPresentacio.mostraError(ex.getMessage());
                }
            }
        });

        //Quadre Text Posar Nom
        TextField tFieldNom = new TextField();
        tFieldNom.setPrefWidth(460);
        tFieldNom.setPrefHeight(40);
        tFieldNom.setPromptText("Introdueix el nom del teclat aquí");
        GridPane.setConstraints(tFieldNom, 1, 2);

        if (idTeclat > -1) {
            tFieldNom.setVisible(false);
        }

        Vector<String> dadesTeclat;
        if (idTeclat > -1) {
            dadesTeclat = cPresentacio.getTeclat(idTeclat);
        }

        //Label informatiu dels alfabets:
        Label infoAlfabets = new Label("Alfabets disponibles: Llatí, Ciríl·lic, Grec, Georgià i Armeni");
        infoAlfabets.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        GridPane.setColumnSpan(infoAlfabets, 2);
        GridPane.setConstraints(infoAlfabets, 1, 4);

        //Label Llati:
        Label infoLlati = new Label("Llati: Demà serà un gran dia!");
        infoLlati.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoLlati, 2);
        GridPane.setConstraints(infoLlati, 1, 5);

        //Label Ciríl·lic:
        Label infoCirilic = new Label("Ciríl·lic: Завтра будет великий день!");
        infoCirilic.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoCirilic, 2);
        GridPane.setConstraints(infoCirilic, 1, 6);

        //Label Grec:
        Label infoGrec = new Label("Grec: Αύριο θα είναι μια υπέροχη μέρα");
        infoGrec.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoGrec, 2);
        GridPane.setConstraints(infoGrec, 1, 7);

        //Label Armeni:
        Label infoArmeni = new Label("Armeni: Վաղը հիանալի օր է լինելու:");
        infoArmeni.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoArmeni, 2);
        GridPane.setConstraints(infoArmeni, 1, 8);

        //Label Georgià:
        Label infoGeorgia = new Label("Georgià: ხვალ დიდი დღე იქნება!");
        infoGeorgia.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoGeorgia, 2);
        GridPane.setConstraints(infoGeorgia, 1, 9);

        //Boto confirmar
        Button botoConfirmar = new Button("Confirmar");
        botoConfirmar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoConfirmar.setMinWidth(623);
        botoConfirmar.setMinHeight(40);
        botoConfirmar.setOnAction(e -> {
            if(idTeclat == -1 &&  tFieldNom.getText().isEmpty()){
                cPresentacio.mostraError("Introdueixi un nom al teclat");
            }
            else {
                Window ownerWindow = botoConfirmar.getScene().getWindow(); // Get the owner window from the button

                //Si op es 1, ve de les freq
                cPresentacio.mostraLoading("Carregant...", () -> {
                    boolean success = true;
                    if (op == 1) {
                        try {
                            if (idTeclat == -1)
                                cPresentacio.crearTeclatInputManual(idAlgorisme, contingut.get(), tFieldNom.getText());
                            else cPresentacio.modificarTeclatFrequenciesInputManual(idTeclat, contingut.get());


                        } catch (Exception ex) {
                            Platform.runLater(() -> {
                                cPresentacio.mostraError(ex.getMessage(), ownerWindow);
                            });
                            success = false;
                        }

                    }
                    //Si op es 2, es un text normal
                    else if (op == 2) {
                        try {
                            if (idTeclat == -1)
                                cPresentacio.crearTeclatFitxerTextUsuari(idAlgorisme, contingut.get(), tFieldNom.getText());
                            else cPresentacio.modificarTeclatFrequenciesFitxerTextUsuari(idTeclat, contingut.get());


                        } catch (Exception ex) {
                            Platform.runLater(() -> {
                                cPresentacio.mostraError(ex.getMessage(), ownerWindow);
                            });
                            success = false;
                        }

                    }
                    if (success) {
                        Platform.runLater(() -> {

                            cPresentacio.mostraInfo("El teclat amb nom " + tFieldNom.getText() + " s'ha generat correctament!");
                            window.close();
                            cPresentacio.mostraMenuPrincipal();
                        });
                    }
                }, ownerWindow);
            }
        });

        GridPane.setConstraints(botoConfirmar, 1, 3);

        //Boto Enrere
        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 10);
        GridPane.setColumnSpan(botoEnrere, 2);
        botoEnrere.setOnAction(e -> {
            if (idTeclat == -1) cPresentacio.mostraVistaCreacioTeclat();
            else {
                try {
                    cPresentacio.mostraModificarTeclat(cPresentacio.getTeclat(idTeclat).get(0), idTeclat);
                } catch (Exception ex) {
                    cPresentacio.mostraError(ex.getMessage());
                }
            }
            window.close();
        });

        layout.getChildren().addAll(
                selectFileButton, tFieldNom, botoConfirmar, botoEnrere, infoAlfabets, infoLlati, infoCirilic, infoGrec,
                infoArmeni, infoGeorgia, info
        );
        Scene escena = new Scene(layout, 800, 600);
        window.setScene(escena);
        window.show();
    }

}
