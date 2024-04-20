package edu.upc.prop.cluster33.presentacio;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import java.util.Arrays;

/**
 * Aquesta classe representa la vista per a la gestió d'usuaris administradors.
 * Proporciona una interfície d'usuari per promocionar usuaris a administradors i per eliminar usuaris.
 */
public class VistaGestioUsuaris {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;
    /**
     * Constructor de la classe VistaGestioUsuaris.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaGestioUsuaris(ControladorCapaPresentacio c){
        cPresentacio = c;
    }
    /**
     * Mostra la interfície d'usuari per a la gestió d'usuaris administradors.
     * Inclou opcions per promocionar usuaris a administradors i per eliminar usuaris.
     */
    public void mostra(){

        //Declarem el Stage
        Stage window = new Stage();
        window.setResizable(true);
        window.setTitle("Keyboard Distribution Generator");


        //Declarem el grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(10);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(80);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(10);
        grid.getColumnConstraints().addAll(c1, c2, c3);

        //Llista de textos possibles
        ListView<String> llistaUsuaris = new ListView<String>();
        String[] usuaris;
        try {
            usuaris = cPresentacio.getUsuaris();
            ObservableList<String> itemsUsuaris = FXCollections.observableArrayList();
            itemsUsuaris.addAll(Arrays.asList(usuaris));

            llistaUsuaris.setItems(itemsUsuaris);

            llistaUsuaris.setPrefHeight(100);
            llistaUsuaris.setPrefWidth(100);
            GridPane.setConstraints(llistaUsuaris, 1, 0);
        }
        catch (Exception e1){
            cPresentacio.mostraError(e1.getMessage());
        }


        //Primera opcio fer admin
        Button botoFerAdmin = new Button("Promocionar a administrador");
        botoFerAdmin.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoFerAdmin.setPrefWidth(623);
        GridPane.setConstraints(botoFerAdmin, 1, 2);
        botoFerAdmin.setOnAction(e -> {
            mostraPopUpPromocionar(llistaUsuaris.getSelectionModel().getSelectedItem());
            window.close();
            cPresentacio.mostraGestioUsuaris();
        });


        //Segona opcio eliminar usuari
        Button botoEliminarUsuari = new Button("Eliminar Usuari");
        botoEliminarUsuari.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        botoEliminarUsuari.setPrefWidth(623);
        GridPane.setConstraints(botoEliminarUsuari, 1, 3);
        botoEliminarUsuari.setOnAction(e -> {
            try{

                cPresentacio.eliminaAdminUsuari(llistaUsuaris.getSelectionModel().getSelectedItem());

                Platform.runLater(() -> {
                    cPresentacio.mostraInfo("S'ha esborrat correctament");
                    window.close();
                    if (cPresentacio.getUsername().equals(llistaUsuaris.getSelectionModel().getSelectedItem()) && !cPresentacio.getUsername().equals("admin")) cPresentacio.Inicialitza();
                    else cPresentacio.mostraGestioUsuaris();
                });

            }
            catch (Exception e1){
                cPresentacio.mostraError(e1.getMessage());
            }
        });

        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 4);
        GridPane.setColumnSpan(botoEnrere, 2);
        botoEnrere.setOnAction(e -> {
            cPresentacio.mostraMenuPrincipal();
            window.close();
        });

        grid.getChildren().addAll(llistaUsuaris, botoEliminarUsuari, botoFerAdmin, botoEnrere);
        Scene escena = new Scene(grid, 800, 500);
        window.setScene(escena);
        window.show();
    }
    /**
     * Mostra una finestra emergent per a confirmar la promoció d'un usuari a administrador.
     *
     * @param username El nom d'usuari de l'usuari a promocionar.
     */
    private void mostraPopUpPromocionar(String username) {
        //Declarem el Stage
        Stage window = new Stage();
        window.setResizable(true);
        window.setTitle("Keyboard Distribution Generator");


        //Declarem el grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(10);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(80);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(10);
        grid.getColumnConstraints().addAll(c1, c2, c3);

        Label labelAvis = new Label("Atenció: aquesta acció no es podrà desfer");
        labelAvis.setStyle("-fx-font-size: 16; -fx-text-fill: red");
        Label labelAvis2 = new Label("Segur que vol continuar?");
        labelAvis2.setStyle("-fx-font-size: 16; -fx-text-fill: red");
        Button botoConfirmar = new Button("Confirmar");
        botoConfirmar.setPrefWidth(300);
        botoConfirmar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoConfirmar.setOnAction(e -> {
            try{
                cPresentacio.setAdmin(username);
                cPresentacio.mostraInfo("S'ha fet administrador al usuari seleccionat");
                window.close();
            }
            catch (Exception ex){
                cPresentacio.mostraError(ex.getMessage());
            }
        });

        GridPane.setConstraints(labelAvis, 1, 0);
        GridPane.setConstraints(labelAvis2, 1, 1);
        GridPane.setConstraints(botoConfirmar, 1, 2);

        grid.getChildren().addAll(labelAvis, labelAvis2, botoConfirmar);
        Scene escena = new Scene(grid, 500, 450);
        window.setScene(escena);
        window.showAndWait();
    }
}
