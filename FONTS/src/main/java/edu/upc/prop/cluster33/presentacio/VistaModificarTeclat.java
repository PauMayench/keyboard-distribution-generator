package edu.upc.prop.cluster33.presentacio;


import edu.upc.prop.cluster33.excepcions.ExcepcioIdNoValid;
import edu.upc.prop.cluster33.excepcions.ExcepcioLlegintDeDisc;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.HashMap;
import java.util.Vector;

/**
 * Aquesta classe representa la vista per modificar un teclat existent.
 * Proporciona una interfície d'usuari per canviar la configuració del teclat.
 */
public class VistaModificarTeclat {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;
    /**
     * Nom general del teclat que es modifica. Serveix per mantenir un registre
     * del nom actual del teclat durant el procés de modificació.
     */
    String nomTeclatGeneral;
    /**
     * Constructor de la classe VistaModificarTeclat.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaModificarTeclat(ControladorCapaPresentacio c) {cPresentacio = c;}

    /**
     * Mostra la finestra per modificar un teclat existent.
     *
     * @param nomTeclat El nom del teclat a modificar.
     * @param idTeclat L'identificador del teclat a modificar.
     */
    public void mostra(String nomTeclat, int idTeclat) {
        nomTeclatGeneral = nomTeclat;
        Stage window = new Stage();
        window.setResizable(true);
        window.setTitle("Key-Layout Generator");
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));

        // 3 Columnes: 10% 80% 10%
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(80);
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);

        for (int i = 0; i < 10; ++i) {
            RowConstraints fila = new RowConstraints();
            fila.setPercentHeight(10);
            layout.getRowConstraints().add(fila);
        }

        //Separadors (No restricció de files...)
        Separator separador1 = new Separator();
        Separator separador2 = new Separator();
        Separator separador3 = new Separator();
        Separator separador4 = new Separator();
        GridPane.setColumnSpan(separador1, 3);
        GridPane.setColumnSpan(separador2, 3);
        GridPane.setColumnSpan(separador3, 3);
        GridPane.setColumnSpan(separador4, 3);
        GridPane.setConstraints(separador1, 0, 1);
        GridPane.setConstraints(separador2, 0, 3);
        GridPane.setConstraints(separador3, 0, 5);
        GridPane.setConstraints(separador4, 0, 7);

        //Titol
        Label titol = new Label("Què desitges fer amb el teclat " + nomTeclat + "?");
        titol.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        GridPane.setConstraints(titol, 1, 0);

        //Boto Canviar Nom
        Button botoCanviarNom = new Button("Canviar nom del teclat");
        botoCanviarNom.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoCanviarNom.setPrefHeight(40);
        botoCanviarNom.setPrefWidth(460);
        botoCanviarNom.setOnAction(e -> {
            mostraPopUpCanviNom(idTeclat, nomTeclat);
            window.close();

        });
        GridPane.setConstraints(botoCanviarNom, 1, 2);

        //Boto Canviar Input
        Button botoCanviarInput = new Button("Canviar input del teclat");
        botoCanviarInput.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoCanviarInput.setPrefHeight(40);
        botoCanviarInput.setPrefWidth(460);
        botoCanviarInput.setOnAction(e -> {
            try {
                mostraPopUpModificarInput(idTeclat, nomTeclat);
                window.close();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
            }
        });
        GridPane.setConstraints(botoCanviarInput, 1, 4);

        //Boto Canviar Estrategia
        Button botoCanviarEstrategia = new Button("Canviar Estratègia del teclat");
        botoCanviarEstrategia.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoCanviarEstrategia.setPrefHeight(40);
        botoCanviarEstrategia.setPrefWidth(460);
        botoCanviarEstrategia.setOnAction(e -> {
            try {
                mostraPopUpModificarEstrategia(idTeclat, nomTeclat);
                window.close();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
            }
        });
        GridPane.setConstraints(botoCanviarEstrategia, 1, 6);

        //Boto Enrere
        Button botoEnrere = new Button("Enrere");
        GridPane.setColumnSpan(botoEnrere, 2);
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 8);
        botoEnrere.setOnAction(e -> {
            cPresentacio.mostraGestionarTeclats();
            window.close();
        });

        layout.getChildren().addAll(
                separador1, separador2, separador3, titol, botoCanviarNom, botoCanviarInput, botoCanviarEstrategia,
                separador4, botoEnrere
        );
        Scene escena = new Scene(layout, 600, 600);
        window.setScene(escena);
        window.show();
    }

    /**
     * Mostra una finestra emergent per canviar el nom d'un teclat.
     *
     * @param idTeclat L'identificador del teclat.
     * @param nomTeclat El nom actual del teclat.
     */
    private void mostraPopUpCanviNom(int idTeclat, String nomTeclat) {
        Stage window = new Stage();
        window.setTitle("Canvi de nom de teclat");
        window.initModality(Modality.APPLICATION_MODAL);
        GridPane layout = new GridPane();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        c1.setPercentWidth(20);
        c2.setPercentWidth(70);
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);

        //Titol
        Label info = new Label("Nou nom");
        info.setStyle("-fx-font-size: 14;");
        GridPane.setConstraints(info, 0, 0);

        //textField nou nom
        TextField tFieldNom = new TextField();
        tFieldNom.setPromptText("Introdueix el nou nom aqui");
        GridPane.setConstraints(tFieldNom, 1, 0);
        window.setOnCloseRequest((WindowEvent event)-> {
                    window.close();
                    cPresentacio.mostraModificarTeclat(nomTeclat, idTeclat);
                }
        );
        //Boto confirmar
        Button botoConfirmar = new Button("Confirmar");
        botoConfirmar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoConfirmar.setOnAction(e -> {
            String choice = tFieldNom.getText();
            if (choice.isEmpty()) window.close();
            else {
                nomTeclatGeneral = choice;
                try {
                    cPresentacio.modificarTeclatNom(idTeclat, choice);
                } catch (Exception ex) {
                    cPresentacio.mostraError(ex.getMessage());
                }
                cPresentacio.mostraInfo("El teclat amb ha canviat de nom a " + choice + " correctament!");
                window.close();
                cPresentacio.mostraMenuPrincipal();
            }
        });
        GridPane.setConstraints(botoConfirmar, 1, 1, 1, 1, HPos.CENTER, VPos.TOP);
        layout.getChildren().addAll(
                info, tFieldNom, botoConfirmar
        );
        Scene escena = new Scene(layout, 600, 300);
        window.setScene(escena);
        window.showAndWait();
    }

    /**
     * Mostra una finestra emergent per modificar l'input d'un teclat.
     *
     * @param idTeclat L'identificador del teclat.
     * @param nomTeclat El nom del teclat.
     * @throws ExcepcioIdNoValid Si l'ID del teclat no és vàlid.
     */
    private void mostraPopUpModificarInput(int idTeclat, String nomTeclat) throws ExcepcioIdNoValid {
        Stage window = new Stage();
        window.setTitle("Canvi d'input de teclat");
        window.initModality(Modality.APPLICATION_MODAL);
        GridPane layout = new GridPane();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        ColumnConstraints c5 = new ColumnConstraints();
        c1.setPercentWidth(20);
        c2.setPercentWidth(20);
        c3.setPercentWidth(20);
        c4.setPercentWidth(20);
        c5.setPercentWidth(20);
        layout.getColumnConstraints().addAll(c1, c2, c3, c4, c5);

        Vector<String> DadesTeclat = cPresentacio.getTeclat(idTeclat);

        //Combo Box de Opcions
        ObservableList<String> llistaOpcions = FXCollections.observableArrayList(
                "Afegir Freqüències Manualment",
                       "Importar fitxer de Freqüències",
                        "Importar fitxer de text pla",
                        "Usar un text públic",
                        "Usar un text predefinit"
        );
        ComboBox<String> selector = new ComboBox<>();
        selector.setPromptText("Selecciona el nou mètode de input");
        selector.setItems(llistaOpcions);
        GridPane.setColumnSpan(selector, 2);
        GridPane.setColumnSpan(selector, 3);
        GridPane.setConstraints(selector, 1, 0);

        //Botó de confirmació
        Button botoConfirmar = new Button("Confirmar");
        botoConfirmar.setTextFill(Color.DARKGREEN);
        botoConfirmar.setOnAction(e -> {
            if (selector.getSelectionModel().isEmpty()) {
                cPresentacio.mostraError("No s'ha seleccionat cap mètode, si us plau, revisi que ha emplenat el camp de selecció");
            }
            else {
                String opc = selector.getSelectionModel().getSelectedItem();
                if (opc.equals("Afegir Freqüències Manualment")) {
                    window.close();
                    cPresentacio.mostraVistaCreacioFreqManual(null, idTeclat);
                    
                }
                else if (opc.equals("Importar fitxer de Freqüències")) {
                    try {
                        window.close();
                        cPresentacio.mostraVistaCreacioFitxer(null, 1, idTeclat);
                    } catch (Exception ex) {
                        cPresentacio.mostraError(ex.getMessage());
                        window.close();
                    }
                }
                else if (opc.equals("Importar fitxer de text pla")) {
                    try {
                        window.close();
                        cPresentacio.mostraVistaCreacioFitxer(null, 2, idTeclat);
                    } catch (Exception ex) {
                        cPresentacio.mostraError(ex.getMessage());
                        window.close();
                    }
                }
                else if (opc.equals("Usar un text públic")) {
                    window.close();
                    cPresentacio.mostraVistaCreacioTextos(null, 1, idTeclat);
                }
                else {
                    cPresentacio.mostraVistaCreacioTextos(null, 2, idTeclat);
                    window.close();
                }
            }
        });
        GridPane.setColumnSpan(botoConfirmar, 2);
        GridPane.setConstraints(botoConfirmar, 1, 1);

        //Botó de cancel·lació
        Button botoCancelar = new Button("Cancel·lar");
        botoCancelar.setTextFill(Color.RED);
        botoCancelar.setOnAction(e -> {
            window.close();
            cPresentacio.mostraModificarTeclat(nomTeclat, idTeclat);
        });
        window.setOnCloseRequest((WindowEvent event)-> {
            window.close();
            cPresentacio.mostraModificarTeclat(nomTeclat, idTeclat);
        }
        );
        GridPane.setColumnSpan(botoCancelar, 2);
        GridPane.setConstraints(botoCancelar, 3, 1);

        layout.getChildren().addAll(
                selector, botoConfirmar, botoCancelar
        );
        Scene escena = new Scene(layout, 400, 250);
        window.setScene(escena);
        window.showAndWait();
    }

    /**
     * Mostra una finestra emergent per modificar l'estratègia d'un teclat.
     *
     * @param idTeclat L'identificador del teclat.
     * @param nomTeclat El nom del teclat.
     * @throws ExcepcioLlegintDeDisc Si hi ha problemes llegint dades del disc.
     */
    private void mostraPopUpModificarEstrategia(int idTeclat, String nomTeclat) throws ExcepcioLlegintDeDisc {
        Stage window = new Stage();
        window.setTitle("Canvi d'input de teclat");
        window.initModality(Modality.APPLICATION_MODAL);
        GridPane layout = new GridPane();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        ColumnConstraints c5 = new ColumnConstraints();
        c1.setPercentWidth(20);
        c2.setPercentWidth(20);
        c3.setPercentWidth(20);
        c4.setPercentWidth(20);
        c5.setPercentWidth(20);
        layout.getColumnConstraints().addAll(c1, c2, c3, c4, c5);

        //ComboBox selecció algorisme nou
        ObservableList<String> optAlgorismes = FXCollections.observableArrayList();
        ComboBox<String> cboxAlgorismes = new ComboBox<>();
        HashMap<Integer, String> alg2;
        alg2 = cPresentacio.getAlgorismes();
        for (int i : alg2.keySet()) {
            String a = alg2.get(i);
            optAlgorismes.add(a);
        }

        String avisInicial = "Selecciona el nou algorisme";
        cboxAlgorismes.setItems(optAlgorismes);
        cboxAlgorismes.setPromptText(avisInicial);
        GridPane.setColumnSpan(cboxAlgorismes, 3);
        GridPane.setConstraints(cboxAlgorismes, 1, 0);

        //Botó confirmar
        Button botoConfirmar = new Button("Confirmar");
        botoConfirmar.setTextFill(Color.DARKGREEN);
        botoConfirmar.setOnAction(e -> {
            if (cboxAlgorismes.getSelectionModel().isEmpty()) {
                cPresentacio.mostraError("No s'ha seleccionat cap algorisme. Assegura't que has emplenat correctament tots els camps.");
            }
            else {
                String algorismeSeleccionat = cboxAlgorismes.getValue();
                boolean gotit = false;
                for (int i : alg2.keySet()) {
                    if (alg2.get(i).equals(algorismeSeleccionat) && !gotit) {
                        gotit = true;
                        Window ownerWindow = botoConfirmar.getScene().getWindow(); // Get the owner window from the button

                        cPresentacio.mostraLoading("Carregant...", () -> {
                            boolean success = true;

                            try {
                                cPresentacio.modificarTeclatAlgorisme(idTeclat, i);
                            } catch (Exception ex) {
                                Platform.runLater(() -> {
                                    cPresentacio.mostraError(ex.getMessage(), ownerWindow);
                                });
                                success = false;
                            }
                            if(success) {
                                Platform.runLater(() -> {
                                    cPresentacio.mostraInfo("El teclat ha canviat la seva estrategia correctament!");
                                    window.close();
                                    cPresentacio.mostraMenuPrincipal();
                                });
                            }
                        }, ownerWindow);
                    }
                }
                window.close();
            }
        });
        GridPane.setColumnSpan(botoConfirmar, 2);
        GridPane.setConstraints(botoConfirmar, 1, 1);

        //Botó de cancel·lació
        Button botoCancelar = new Button("Cancel·lar");
        botoCancelar.setTextFill(Color.RED);
        botoCancelar.setOnAction(e -> {
            window.close();
            cPresentacio.mostraModificarTeclat(nomTeclat, idTeclat);
        });
        window.setOnCloseRequest((WindowEvent event)-> {
            window.close();
            cPresentacio.mostraModificarTeclat(nomTeclat, idTeclat);
        }
        );
        GridPane.setColumnSpan(botoCancelar, 2);
        GridPane.setConstraints(botoCancelar, 3, 1);

        layout.getChildren().addAll(
                cboxAlgorismes, botoConfirmar, botoCancelar
        );
        Scene escena = new Scene(layout, 400, 250);
        window.setScene(escena);
        window.showAndWait();
    }
}
