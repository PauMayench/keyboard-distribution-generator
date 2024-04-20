package edu.upc.prop.cluster33.presentacio;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * Aquesta classe representa la vista per a la creació de teclats basats en textos.
 * Proporciona una interfície d'usuari per seleccionar textos preexistents i generar teclats.
 */
public class VistaCreacioTextos {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;

    /**
     * Constructor de la classe VistaCreacioTextos.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaCreacioTextos(ControladorCapaPresentacio c){
        cPresentacio = c;
    }

    /**
     * Mostra la interfície d'usuari per a la selecció de textos i creació de teclats.
     * L'usuari pot escollir entre textos públics, predefinits o proporcionar el seu propi text.
     *
     * @param idAlgorisme Identificador de l'algorisme seleccionat per a la generació del teclat.
     * @param op Opció seleccionada per l'usuari (1 per a textos públics, 2 per a textos predefinits).
     * @param idTeclat Identificador del teclat a modificar, en cas de ser una modificació.
     */
    public void mostra(Integer idAlgorisme, int op, int idTeclat) {
        Stage window = new Stage();
        window.setResizable(true);
        window.setTitle("Key-Layout Generator");
        GridPane layout = new GridPane();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(80);
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(10);


        //Quadre Text Posar Nom
        TextField tFieldNom = new TextField();
        tFieldNom.setPrefWidth(460);
        tFieldNom.setPrefHeight(40);
        tFieldNom.setPromptText("Introdueix el nom del teclat aquí");
        GridPane.setConstraints(tFieldNom, 1, 2);

        if (idTeclat > -1) tFieldNom.setVisible(false);

        //Llista de textos possibles
        ListView<String> llistaTextos = new ListView<String>();

        HashMap<Integer, String> textosPublics = new HashMap<>();
        HashMap<Integer, String> textosPredefinits = new HashMap<>();
        try {
            textosPublics = cPresentacio.getTextsPublics();
            textosPredefinits = cPresentacio.getTextsPredefinits();
        }
        catch (Exception e1){
            cPresentacio.mostraError(e1.getMessage());
        }
        //si op es 1, es fa amb textos publics
        if(op == 1){
            //Creem la llista de textos
            ObservableList<String> itemsTextosPublics = FXCollections.observableArrayList();
            for(Map.Entry<Integer, String> entry : textosPublics.entrySet()) {
                itemsTextosPublics.add(entry.getValue());
            }
            llistaTextos.setItems(itemsTextosPublics);

            llistaTextos.setPrefHeight(100);
            llistaTextos.setPrefWidth(100);
        }
        //si op es 2, es fa amb textos predefinits
        else{
            ObservableList<String> itemsTextosPredefinits = FXCollections.observableArrayList();
            for(Map.Entry<Integer, String> entry : textosPredefinits.entrySet()) {
                itemsTextosPredefinits.add(entry.getValue());
            }
            llistaTextos.setItems(itemsTextosPredefinits);

            llistaTextos.setPrefHeight(250);
            llistaTextos.setPrefWidth(100);
        }
        GridPane.setConstraints(llistaTextos, 1, 0);

        //Label informatiu dels alfabets:
        Label infoAlfabets = new Label("Alfabets disponibles: Llatí, Ciríl·lic, Grec, Georgià i Armeni");
        infoAlfabets.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        GridPane.setColumnSpan(infoAlfabets, 2);
        GridPane.setConstraints(infoAlfabets, 1, 6);

        //Label Llati:
        Label infoLlati = new Label("Llati: Demà serà un gran dia!");
        infoLlati.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoLlati, 2);
        GridPane.setConstraints(infoLlati, 1, 7);

        //Label Ciríl·lic:
        Label infoCirilic = new Label("Ciríl·lic: Завтра будет великий день!");
        infoCirilic.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoCirilic, 2);
        GridPane.setConstraints(infoCirilic, 1, 8);

        //Label Grec:
        Label infoGrec = new Label("Grec: Αύριο θα είναι μια υπέροχη μέρα");
        infoGrec.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoGrec, 2);
        GridPane.setConstraints(infoGrec, 1, 9);

        //Label Armeni:
        Label infoArmeni = new Label("Armeni: Վաղը հիանալի օր է լինելու:");
        infoArmeni.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoArmeni, 2);
        GridPane.setConstraints(infoArmeni, 1, 10);

        //Label Georgià:
        Label infoGeorgia = new Label("Georgià: ხვალ დიდი დღე იქნება!");
        infoGeorgia.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoGeorgia, 2);
        GridPane.setConstraints(infoGeorgia, 1, 11);



        //Boto confirmar
        Button botoConfirmar = new Button("Confirmar");
        botoConfirmar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoConfirmar.setMinWidth(623);
        botoConfirmar.setMinHeight(40);
        HashMap<Integer, String> finalTextosPublics = textosPublics;
        HashMap<Integer, String> finalTextosPredefinits = textosPredefinits;
        botoConfirmar.setOnAction(e -> {
            if(idTeclat == -1 &&  tFieldNom.getText().isEmpty()){
                cPresentacio.mostraError("Introdueixi un nom al teclat");
            }
            else {
                Window ownerWindow = botoConfirmar.getScene().getWindow(); // Get the owner window from the button

                cPresentacio.mostraLoading("Carregant...", () -> {
                    boolean success = true;
                    int idTextSeleccionat = 0;
                    //Si op es 1, textos publics
                    if (op == 1) {
                        String textSeleccionat = llistaTextos.getSelectionModel().getSelectedItem();
                        for (Map.Entry<Integer, String> entry : finalTextosPublics.entrySet()) {
                            if (entry.getValue().equals(textSeleccionat)) {
                                idTextSeleccionat = entry.getKey();
                            }
                        }
                        try {
                            if (idTeclat == -1)
                                cPresentacio.crearTeclatFitxerTextPublic(idAlgorisme, idTextSeleccionat, tFieldNom.getText());
                            else cPresentacio.modificarTeclatFrequenciesFitxerTextPublic(idTeclat, idTextSeleccionat);
                        } catch (Exception e1) {
                            Platform.runLater(() -> {
                                cPresentacio.mostraError(e1.getMessage(), ownerWindow);
                            });
                            success = false;
                        }
                    }
                    //Si op es 2, textos predefinits
                    else {
                        String textSeleccionat = llistaTextos.getSelectionModel().getSelectedItem();
                        for (Map.Entry<Integer, String> entry : finalTextosPredefinits.entrySet()) {
                            if (entry.getValue().equals(textSeleccionat)) {
                                idTextSeleccionat = entry.getKey();
                            }
                        }
                        try {
                            if (idTeclat == -1)
                                cPresentacio.crearTeclatFitxerTextPredefinit(idAlgorisme, idTextSeleccionat, tFieldNom.getText());
                            else
                                cPresentacio.modificarTeclatFrequenciesFitxerTextPredefinit(idTeclat, idTextSeleccionat);
                        } catch (Exception e1) {
                            Platform.runLater(() -> {
                                cPresentacio.mostraError(e1.getMessage(), ownerWindow);
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
        GridPane.setConstraints(botoConfirmar, 1, 4);



        //Boto veure text
        Button botoVeureText = new Button("Veure text");
        botoVeureText.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        GridPane.setConstraints(botoVeureText, 1, 5);
        botoVeureText.setPrefWidth(623);

        botoVeureText.setOnAction(e -> {

            if(op == 1){
                int idTextSeleccionat = 0;
                String textSeleccionat = llistaTextos.getSelectionModel().getSelectedItem();
                for(Map.Entry<Integer, String> entry : finalTextosPublics.entrySet()){
                    if(entry.getValue().equals(textSeleccionat)){
                        idTextSeleccionat = entry.getKey();
                    }
                }

                try{
                    String textPub = cPresentacio.getTextPublic(idTextSeleccionat);
                    mostraPopUpText(textPub);

                }
                catch (Exception e1){
                    cPresentacio.mostraError(e1.getMessage());
                }
            }
            else if(op == 2){
                int idTextSeleccionat = 0;
                String textSeleccionat = llistaTextos.getSelectionModel().getSelectedItem();
                for(Map.Entry<Integer, String> entry : finalTextosPredefinits.entrySet()){
                    if(entry.getValue().equals(textSeleccionat)){
                        idTextSeleccionat = entry.getKey();
                    }
                }

                try{
                    String textPre = cPresentacio.getTextPredefinit(idTextSeleccionat);
                    mostraPopUpText(textPre);
                }
                catch (Exception e1){
                    cPresentacio.mostraError(e1.getMessage());
                }
            }


        });



        //Boto Enrere
        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 12);
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
                tFieldNom, botoConfirmar, botoEnrere, llistaTextos, infoAlfabets, infoLlati, infoCirilic, infoGeorgia, infoGrec, infoArmeni, botoVeureText
        );

        Scene escena = new Scene(layout, 800, 700);
        window.setScene(escena);
        window.show();
    }

    /**
     * Mostra una finestra emergent amb el text seleccionat.
     * Aquesta finestra permet visualitzar el contingut complet del text públic o predefinit.
     *
     * @param textP El text a mostrar en la finestra emergent.
     */
    private void mostraPopUpText(String textP){
        Stage window = new Stage();
        window.setTitle("Visualització text públic");
        window.initModality(Modality.APPLICATION_MODAL);
        GridPane layout = new GridPane();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(40);
        c3.setPercentWidth(40);
        c4.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3, c4);

        Text text = new Text();
        GridPane.setConstraints(text, 0, 0);
        text.setText(textP);

        layout.getChildren().addAll(
                text
        );
        Scene escena = new Scene(layout, 400, 250);
        window.setScene(escena);
        window.showAndWait();
    }

}
