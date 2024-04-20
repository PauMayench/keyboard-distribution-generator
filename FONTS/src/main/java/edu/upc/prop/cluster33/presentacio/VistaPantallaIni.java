package edu.upc.prop.cluster33.presentacio;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * VistaPantallaIni és la classe que gestiona la pantalla inicial de l'aplicació Keyboard Distribution Generator.
 */
public class VistaPantallaIni {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cpresentacio;

    /**
     * Constructor de VistaPantallaIni.
     *
     * @param c Controlador de la capa de presentació associat a aquesta vista.
     */
    public VistaPantallaIni(ControladorCapaPresentacio c) {
        cpresentacio = c;
    }
    /**
     * Mostra la pantalla inicial amb opcions per iniciar sessió o registrar-se.
     * Aquest mètode configura i mostra els elements de la interfície d'usuari.
     */
    public void mostra() {

        //Declarem el Stage
        Stage window = new Stage();
        window.setResizable(false);
        window.setTitle("Keyboard Distribution Generator");
        Label titol = new Label("KEYBOARD DISTRIBUTION GENERATOR");
        titol.setStyle("-fx-font-weight: bold; -fx-font-size: 20;-fx-text-fill: white;");
        //Declarem el grid
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        GridPane.setConstraints(titol, 0, 0);
        GridPane.setColumnSpan(titol, 3);

        //Subtitol
        Label subtitol = new Label("Projectes de programacio: Grup 33.3 - QTardor 23-24");
        subtitol.setStyle("-fx-font-style: italic; -fx-font-size: 14;-fx-text-fill: white;");
        GridPane.setColumnSpan(subtitol, 3);
        GridPane.setConstraints(subtitol, 0, 1);

        //Label: Ja tens un compte?
        Label jatenscompte = new Label("Ja tens un compte?");
        jatenscompte.setStyle("-fx-font-size: 14;-fx-text-fill: white;");
        GridPane.setConstraints(jatenscompte, 0, 3);

        //Label: Ets un usuari nou?
        Label notenscompte = new Label("Ets un nou usuari?");
        notenscompte.setStyle("-fx-font-size: 14;-fx-text-fill: white;");
        GridPane.setConstraints(notenscompte, 0, 4);

        //Botó Inicia Sessió
        Button botoIniciaSessio = new Button("Iniciar Sessio");
        botoIniciaSessio.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoIniciaSessio.setPrefWidth(300);
        GridPane.setConstraints(botoIniciaSessio, 1, 3);
        botoIniciaSessio.setOnAction(e -> {
            cpresentacio.mostraLogin();
            window.close();
        });

        //Botó Registra't
        Button botoRegistrat = new Button("Crea un compte");
        botoRegistrat.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        GridPane.setConstraints(botoRegistrat, 1, 4);
        botoRegistrat.setPrefWidth(300);
        botoRegistrat.setOnAction(e -> {
            cpresentacio.mostraRegistre();
            window.close();
        });


        Image image = new Image(getClass().getResourceAsStream("/backgroundLogo.png"));

        BackgroundImage bg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        // Set the background of grid
        grid.setBackground(new Background(bg));

        for (int i = 0; i < 6; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(26); // Minimum height for each row
            grid.getRowConstraints().add(rowConstraints);
        }

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(550); // Set the minimum height for spacing
        grid.getRowConstraints().add(rowConstraints);

        //Label: Developed By
        Label desenvolupatPer = new Label("DESENVOLUPAT PER:\n\n" +
                "\tPAU MAYENCH CARO\n" +
                "\tJOSEP DIAZ SOSA\n" +
                "\tMARC EXPOSITO FRANCISCO\n" +
                "\tVICTOR HERNANDEZ BARRAGAN");
        desenvolupatPer.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-text-fill: white;");
        GridPane.setConstraints(desenvolupatPer, 0, 6);
        GridPane.setColumnSpan(desenvolupatPer, 2);

        grid.getChildren().addAll(
                titol, subtitol, jatenscompte, notenscompte, botoIniciaSessio, botoRegistrat, desenvolupatPer
        );
        Scene escena = new Scene(grid, 600, 608);

        window.setScene(escena);
        window.show();
    }
}
