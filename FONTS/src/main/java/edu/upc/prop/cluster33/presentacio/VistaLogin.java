package edu.upc.prop.cluster33.presentacio;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Aquesta classe representa la vista de login de l'aplicació.
 * Proporciona una interfície d'usuari per a l'inici de sessió.
 */
public class VistaLogin {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cpresentacio;

    /**
     * Constructor de la classe VistaLogin.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     * @throws Exception Si hi ha algun problema durant la inicialització.
     */
    public VistaLogin(ControladorCapaPresentacio c) throws Exception {
        cpresentacio = c;
    }

    /**
     * Mostra la finestra de login per a l'inici de sessió de l'usuari.
     */
    public void mostra() {

        //Declarem el Stage
        Stage window = new Stage();
        window.setTitle("Key-Layout Generator");
        window.setResizable(false);
        //Declarem el Grid
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));

        //Part Username
        Label labelUsername = new Label("Username:");
        labelUsername.setStyle("-fx-font-size: 14;-fx-text-fill: white;");
        GridPane.setConstraints(labelUsername, 0, 0);
        TextField Fusername = new TextField();
        GridPane.setConstraints(Fusername, 1, 0);

        //Part Password
        Label labelPassword = new Label("Contrasenya:");
        labelPassword.setStyle("-fx-font-size: 14;-fx-text-fill: white;");
        GridPane.setConstraints(labelPassword,0, 1 );
        PasswordField Fpassword = new PasswordField();
        GridPane.setConstraints(Fpassword, 1, 1);

        Button botoConfirmar = new Button("Confirmar Login");
        botoConfirmar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        GridPane.setConstraints(botoConfirmar, 0, 3);

        botoConfirmar.setOnAction(e -> {
            String username = Fusername.getText();
            String password = Fpassword.getText();
            try {
                cpresentacio.iniciaInstancia(username, password);
                Platform.runLater(() -> {
                    cpresentacio.mostraMenuPrincipal();
                    window.close();
                });

            }
            catch (Exception e1){
                cpresentacio.mostraError(e1.getMessage());
            }
        });

        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 5);
        GridPane.setColumnSpan(botoEnrere, 2);
        botoEnrere.setOnAction(e -> {
            cpresentacio.Inicialitza();
            window.close();
        });

        Image image = new Image(getClass().getResourceAsStream("/backgroundLogo.png"));
        BackgroundImage bg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        // Set the background of grid
        layout.setBackground(new Background(bg));

        for (int i = 0; i < 6; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(26); // Minimum height for each row
            layout.getRowConstraints().add(rowConstraints);
        }

        layout.getChildren().addAll(labelUsername, labelPassword, Fusername, Fpassword, botoConfirmar, botoEnrere);

        Scene escena = new Scene(layout, 600, 608);
        

        window.setScene(escena);

        window.show();
    }
}
