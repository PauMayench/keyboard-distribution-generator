package edu.upc.prop.cluster33.presentacio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * VistaRegistre gestiona la interfície per al registre d'usuaris nous a l'aplicació.
 */
public class VistaRegistre {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cpresentacio;

    /**
     * Constructor de VistaRegistre.
     *
     * @param cp Controlador de la capa de presentació associat a aquesta vista.
     * @throws Exception Si es produeix un error durant la creació de la vista.
     */
    public VistaRegistre(ControladorCapaPresentacio cp) throws Exception{
        cpresentacio = cp;
    }

    /**
     * Mostra la vista per al registre d'usuaris.
     * Aquest mètode configura i mostra els elements de la interfície d'usuari per permetre el registre d'usuaris nous.
     */
    public void mostra(){

        //Declarem el Stage
        Stage window = new Stage();
        window.setResizable(false);
        window.setTitle("Key-Layout Generator");

        //Declarem el Grid
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(10);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        c1.setPercentWidth(40);
        c2.setPercentWidth(50);
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);

        //Part Username
        Label labelUsername = new Label("Username:");
        labelUsername.setStyle("-fx-font-size: 14;-fx-text-fill: white;");
        GridPane.setConstraints(labelUsername, 0, 0);
        TextField Fusername = new TextField();
        GridPane.setConstraints(Fusername, 1, 0);

        //Label recordatori
        Label recordatori = new Label("Advertència: La contrasenya ha d'incloure mínim 5 caràcters i ha d'haver lletres i números");
        recordatori.setStyle("-fx-text-fill: #3498db; -fx-underline: true; -fx-font-size: 11;");
        GridPane.setConstraints(recordatori, 0, 2);
        GridPane.setColumnSpan(recordatori, 2);

        //Part Password
        Label labelPassword = new Label("Contrasenya:");
        labelPassword.setStyle("-fx-font-size: 14;-fx-text-fill: white;");
        GridPane.setConstraints(labelPassword,0, 1 );
        PasswordField Fpassword = new PasswordField();
        GridPane.setConstraints(Fpassword, 1, 1);

        //Part ma bona
        Label labelMa = new Label("Ma Bona: ");
        labelMa.setStyle("-fx-font-size: 14;-fx-text-fill: white;");
        GridPane.setConstraints(labelMa, 0, 3);
        ObservableList<String> opcionsMa =
                FXCollections.observableArrayList("Esquerra", "Dreta");
        final ComboBox<String> comboBox = new ComboBox(opcionsMa);
        GridPane.setConstraints(comboBox, 1, 3);

        //Boto Confirmar
        Button botoConfirmar = new Button("Confirmar Registre");
        botoConfirmar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        GridPane.setConstraints(botoConfirmar, 0, 4);
        GridPane.setColumnSpan(botoConfirmar, 2);
        botoConfirmar.setOnAction(e -> {
            String username = Fusername.getText();
            String password = Fpassword.getText();
            String maBona = comboBox.getValue();
            try {
                cpresentacio.enregistraUsuari(username, password, maBona, false);
                cpresentacio.mostraInfo("S'ha registrat al usuari correctament");
                cpresentacio.mostraMenuPrincipal();
                window.close();
            }
            catch (Exception e1){
                cpresentacio.mostraError(e1.getMessage());
            }
        });

        //Boto Enrere
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

        layout.getChildren().addAll(labelUsername, labelPassword, Fusername, Fpassword, botoConfirmar, labelMa, comboBox, botoEnrere,
                recordatori);
        Scene escena = new Scene(layout, 600, 608);


        window.setScene(escena);

        window.show();

    }
}
