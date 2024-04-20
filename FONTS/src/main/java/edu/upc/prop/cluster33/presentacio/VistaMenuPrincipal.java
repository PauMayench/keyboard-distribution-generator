package edu.upc.prop.cluster33.presentacio;

import edu.upc.prop.cluster33.excepcions.ExcepcioErrorGuardantDades;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
/**
 * Aquesta classe representa la vista del menú principal de l'aplicació.
 * Proporciona una interfície d'usuari amb diverses opcions de gestió.
 */
public class VistaMenuPrincipal {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;
    /**
     * Constructor de la classe VistaMenuPrincipal.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaMenuPrincipal(ControladorCapaPresentacio c) {cPresentacio = c;}

    /**
     * Mostra la finestra del menú principal amb les diferents opcions de gestió.
     */
    public void mostra() {
        Stage window = new Stage();
        window.setResizable(true);
        window.setTitle("Key-Layout Generator");

        //Configuració GridPane:
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setVgap(15);
        layout.setAlignment(Pos.TOP_CENTER);
        // Tres columnes, 10%, 80% i 10%
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(10);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(80);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);

        for (int i = 0; i < 10; ++i) {
            RowConstraints fila = new RowConstraints();
            fila.setPercentHeight(100.0/10.0);
            layout.getRowConstraints().add(fila);
        }

        //Separadors:
        Separator separador1 = new Separator();
        GridPane.setColumnSpan(separador1, 3);
        GridPane.setConstraints(separador1, 0, 2);

        //Label titol:
        javafx.scene.control.Label titol = new javafx.scene.control.Label("KEY-LAYOUT GENERATOR");
        titol.setStyle("-fx-font-weight: bold; -fx-font-size: 30;");
        titol.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(titol, 2);
        GridPane.setConstraints(titol, 1, 0);

        //Label Subtitol:
        javafx.scene.control.Label subtitol = new javafx.scene.control.Label("Benvingut al generador de distribucions de teclat definitiu. Seleccioni l'opció desitjada.");
        titol.setStyle("-fx-font-weight: bold; -fx-font-size: 25;");
        GridPane.setColumnSpan(subtitol, 2);
        GridPane.setConstraints(subtitol, 1, 1);

        //Combobox Usuari:
        ObservableList<String> opcionsUserConfig = FXCollections.observableArrayList(
                "Perfil", "Desconnectar-se"
        );
        ComboBox<String> userConfig = new ComboBox<>();
        userConfig.setItems(opcionsUserConfig);
        userConfig.setPromptText(cPresentacio.getUsername());
        userConfig.setOnAction(e -> {
            String opt = userConfig.getValue();
            if (opt.equals("Perfil")) {
                cPresentacio.mostraGestioUsuari();
                window.close();
            }
            else if (opt.equals("Desconnectar-se")) {
                try {
                    cPresentacio.logout();
                    cPresentacio.Inicialitza();
                    window.close();
                } catch (ExcepcioErrorGuardantDades ex) {
                    cPresentacio.mostraError(ex.getMessage());
                }
            }
        });
        GridPane.setConstraints(userConfig, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        //GridPane.setConstraints(userConfig, 2, 0);

        //Boto Crear Teclat
        Button botoCrearTeclat = new Button("Crear Nou Teclat");
        botoCrearTeclat.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoCrearTeclat.setMinWidth(480);
        botoCrearTeclat.setMinHeight(40);
        botoCrearTeclat.setOnAction(e -> {
            cPresentacio.mostraVistaCreacioTeclat();
            window.close();
        });
        GridPane.setConstraints(botoCrearTeclat, 1, 3);

        //Boto Gestionar Teclat
        Button botoGestioTeclat = new Button("Gestionar Teclats");
        botoGestioTeclat.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoGestioTeclat.setMinWidth(480);
        botoGestioTeclat.setMinHeight(40);
        botoGestioTeclat.setOnAction(e -> {
            try {
                cPresentacio.mostraGestionarTeclats();
                window.close();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
                window.close();
            }

        });
        GridPane.setConstraints(botoGestioTeclat, 1, 4);

        //Boto Penjar Text Públic
        Button botoPenjarTextPublic = new Button("Publicar Text Public");
        botoPenjarTextPublic.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoPenjarTextPublic.setMinWidth(480);
        botoPenjarTextPublic.setMinHeight(40);
        botoPenjarTextPublic.setOnAction(e -> {
            try {
                cPresentacio.mostraPenjarTextPublic();
                window.close();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
                window.close();
            }
        });
        GridPane.setConstraints(botoPenjarTextPublic, 1, 5);

        //Boto Gestionar Texts Publics
        Button botoGestionarTextPublic = new Button("Gestionar Texts Publics");
        botoGestionarTextPublic.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoGestionarTextPublic.setMinWidth(480);
        botoGestionarTextPublic.setMinHeight(40);
        botoGestionarTextPublic.setOnAction(e -> {
            try {
                cPresentacio.mostraGestioTextPublic();
                window.close();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
                window.close();
            }
        });
        GridPane.setConstraints(botoGestionarTextPublic, 1, 6);

        layout.getChildren().addAll(
                titol, subtitol, userConfig, separador1,
                botoCrearTeclat, botoGestioTeclat, botoPenjarTextPublic, botoGestionarTextPublic
        );


        //COSES ADMIN
        if(cPresentacio.isAdmin()){
            //Boto Penjar Text Predefinits
            Button botoPenjarTextPredefinit = new Button("Publicar Text Predefinit");
            botoPenjarTextPredefinit.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
            botoPenjarTextPredefinit.setMinWidth(480);
            botoPenjarTextPredefinit.setMinHeight(40);
            botoPenjarTextPredefinit.setOnAction(e -> {
                try {
                    cPresentacio.mostraPenjarTextPredefinit();
                    window.close();
                } catch (Exception ex) {
                    cPresentacio.mostraError(ex.getMessage());
                    window.close();
                }
            });
            GridPane.setConstraints(botoPenjarTextPredefinit, 1, 7);

            //Boto Gestionar Texts Predefinits
            Button botoGestionarTextPredefinit = new Button("Gestionar Texts Predefinits");
            botoGestionarTextPredefinit.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
            botoGestionarTextPredefinit.setMinWidth(480);
            botoGestionarTextPredefinit.setMinHeight(40);
            botoGestionarTextPredefinit.setOnAction(e -> {
                try {
                    cPresentacio.mostraGestioTextPredefinit();
                    window.close();
                } catch (Exception ex) {
                    cPresentacio.mostraError(ex.getMessage());
                    window.close();
                }
            });
            GridPane.setConstraints(botoGestionarTextPredefinit, 1, 8);


            Button botoGestionarUsuaris = new Button("Gestionar Usuaris");
            botoGestionarUsuaris.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
            botoGestionarUsuaris.setMinWidth(480);
            botoGestionarUsuaris.setMinHeight(40);
            botoGestionarUsuaris.setOnAction(e -> {
                try {
                    cPresentacio.mostraGestioUsuaris();
                    window.close();
                } catch (Exception ex) {
                    cPresentacio.mostraError(ex.getMessage());
                    window.close();
                }
            });
            GridPane.setConstraints(botoGestionarUsuaris, 1, 9);

            layout.getChildren().addAll(
                   botoPenjarTextPredefinit, botoGestionarTextPredefinit, botoGestionarUsuaris
            );
        }



        Scene escena = new Scene(layout, 800, 600);
        window.setScene(escena);
        window.show();
    }
}
