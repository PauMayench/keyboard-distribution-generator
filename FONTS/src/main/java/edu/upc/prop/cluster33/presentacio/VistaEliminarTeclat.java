package edu.upc.prop.cluster33.presentacio;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Aquesta classe representa la vista per a l'eliminació de teclats.
 * Proporciona una interfície d'usuari per confirmar l'eliminació d'un teclat específic.
 */
public class VistaEliminarTeclat {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;
    /**
     * Constructor de la classe VistaEliminarTeclat.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaEliminarTeclat(ControladorCapaPresentacio c) {cPresentacio = c;}

    /**
     * Mostra la interfície d'usuari per confirmar l'eliminació d'un teclat.
     * Presenta un missatge d'advertència i proporciona opcions per confirmar o cancel·lar l'acció.
     *
     * @param nomTeclat El nom del teclat a eliminar.
     * @param idTeclat L'identificador únic del teclat a eliminar.
     */
    public void mostra(String nomTeclat, int idTeclat) {
        Stage window = new Stage();
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

        //Avis
        Label avis = new Label("Estas segur de que vols eliminar el teclat " + nomTeclat + "?");
        avis.setStyle("-fx-font-weight: bold; -fx-text-fill: #FF0000; -fx-font-size: 20;");


        GridPane.setConstraints(avis, 1, 0);

        //Boto Confirmar Eliminació
        Button botoConfirmar = new Button("SI, ELIMINAR TECLAT");
        botoConfirmar.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        botoConfirmar.setTextFill(Color.RED);
        botoConfirmar.setPrefWidth(623);
        botoConfirmar.setPrefHeight(40);
        botoConfirmar.setOnAction(e -> {
            try {
                cPresentacio.eliminarTeclat(idTeclat);
                cPresentacio.mostraGestionarTeclats();
                window.close();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
            }
        });
        GridPane.setConstraints(botoConfirmar, 1, 1);

        //Boto Enrere
        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        GridPane.setConstraints(botoEnrere, 0, 2);
        GridPane.setColumnSpan(botoEnrere, 2);
        botoEnrere.setOnAction(e -> {
            cPresentacio.mostraGestionarTeclats();
            window.close();
        });


        layout.getChildren().addAll(
                avis, botoConfirmar, botoEnrere
        );
        Scene escena = new Scene(layout, 800, 600);
        window.setScene(escena);
        window.show();
    }
}
