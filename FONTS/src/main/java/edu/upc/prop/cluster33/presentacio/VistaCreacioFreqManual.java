package edu.upc.prop.cluster33.presentacio;


import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Aquesta classe representa la vista per a la creació manual de freqüències de teclats.
 * Proporciona una interfície d'usuari per introduir freqüències de paraules manualment
 * i generar un teclat a partir d'aquestes.
 */
public class VistaCreacioFreqManual {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;
    /**
     * Constructor de la classe VistaCreacioFreqManual.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaCreacioFreqManual(ControladorCapaPresentacio c) {
        cPresentacio = c;
    }

    /**
     * Mostra la interfície d'usuari per a la creació manual de freqüències de teclats.
     * L'usuari pot introduir freqüències en un format específic i un nom per al nou teclat.
     *
     * @param idAlgorisme Identificador de l'algorisme de creació del teclat.
     * @param idTeclat Identificador del teclat a modificar (en cas de no ser una creació nova).
     */
    public void mostra(Integer idAlgorisme, int idTeclat) {
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
        Label titol = new Label("Segueixi el format indicat a la següent capsa de text seguint el format indicat.");
        titol.setStyle("-fx-font-size: 14;");
        titol.setPrefHeight(40);
        titol.setPrefWidth(480);
        GridPane.setValignment(titol, VPos.CENTER);
        GridPane.setHalignment(titol, HPos.CENTER);
        GridPane.setConstraints(titol, 1, 0);

        //Text info
        Label info = new Label("FORMAT: (POSAR CURSOR A SOBRE PER VEURE)");
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

        //Area d'escriptura Frequencies
        TextArea quadreText = new TextArea();
        quadreText.setPrefHeight(350);
        GridPane.setConstraints(quadreText, 1, 2);

        //Quadre Text Posar Nom
        TextField tFieldNom = new TextField();
        tFieldNom.setPrefWidth(460);
        tFieldNom.setPrefHeight(65);
        tFieldNom.setPromptText("Introdueix el nom del teclat aquí");
        GridPane.setConstraints(tFieldNom, 1, 3);

        if (idTeclat > -1) tFieldNom.setVisible(false);

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
                Window ownerWindow = botoConfirmar.getScene().getWindow();
                try {
                    String name = "";
                    if (idTeclat == -1) name = tFieldNom.getText();
                    else {
                        name = cPresentacio.getTeclat(idTeclat).get(0);
                    }
                    String finalName = name;
                    cPresentacio.mostraLoading("Carregant...", () -> {
                        boolean success = true;
                        try {
                            if (idTeclat == -1) {
                                cPresentacio.crearTeclatInputManual(idAlgorisme, quadreText.getText(), finalName);
                            } else {
                                cPresentacio.modificarTeclatFrequenciesInputManual(idTeclat, quadreText.getText());
                            }
                        } catch (Exception ex) {
                            Platform.runLater(() -> {
                                cPresentacio.mostraError(ex.getMessage(), ownerWindow);
                            });
                            success = false;
                        }
                        if (success) {
                            Platform.runLater(() -> {
                                cPresentacio.mostraInfo("El teclat amb nom " + finalName + " s'ha generat correctament!");
                                window.close();
                                cPresentacio.mostraMenuPrincipal();
                            });
                        }
                    }, ownerWindow);
                } catch (Exception ex) {
                    cPresentacio.mostraError(ex.getMessage());
                }
            }

        });
        GridPane.setConstraints(botoConfirmar, 1, 4);

        //Label informatiu dels alfabets:
        Label infoAlfabets = new Label("Alfabets disponibles: Llatí, Ciríl·lic, Grec, Georgià i Armeni");
        infoAlfabets.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        GridPane.setColumnSpan(infoAlfabets, 2);
        GridPane.setConstraints(infoAlfabets, 1, 5);

        //Label Llati:
        Label infoLlati = new Label("Llati: Demà serà un gran dia!");
        infoLlati.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoLlati, 2);
        GridPane.setConstraints(infoLlati, 1, 6);

        //Label Ciríl·lic:
        Label infoCirilic = new Label("Ciríl·lic: Завтра будет великий день!");
        infoCirilic.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoCirilic, 2);
        GridPane.setConstraints(infoCirilic, 1, 7);

        //Label Grec:
        Label infoGrec = new Label("Grec: Αύριο θα είναι μια υπέροχη μέρα");
        infoGrec.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoGrec, 2);
        GridPane.setConstraints(infoGrec, 1, 8);

        //Label Armeni:
        Label infoArmeni = new Label("Armeni: Վաղը հիանալի օր է լինելու:");
        infoArmeni.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoArmeni, 2);
        GridPane.setConstraints(infoArmeni, 1, 9);

        //Label Georgià:
        Label infoGeorgia = new Label("Georgià: ხვალ დიდი დღე იქნება!");
        infoGeorgia.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(infoGeorgia, 2);
        GridPane.setConstraints(infoGeorgia, 1, 10);

        //Boto Enrere
        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 11);
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
                titol, quadreText, tFieldNom, botoConfirmar, infoAlfabets, infoLlati, infoArmeni, infoCirilic, infoGrec, infoGeorgia,
                botoEnrere, info
        );
        Scene escena = new Scene(layout, 800, 700);
        window.setScene(escena);
        window.show();
    }
}
