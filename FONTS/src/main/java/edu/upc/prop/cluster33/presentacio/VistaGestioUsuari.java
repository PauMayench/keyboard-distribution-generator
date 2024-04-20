package edu.upc.prop.cluster33.presentacio;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * Aquesta classe representa la vista per a la gestió d'usuaris.
 * Proporciona una interfície d'usuari per realitzar accions relacionades amb el compte d'usuari.
 */
public class VistaGestioUsuari {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;
    /**
     * Constructor de la classe VistaGestioUsuari.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaGestioUsuari(ControladorCapaPresentacio c) {cPresentacio = c;}

    /**
     * Mostra la interfície d'usuari per a la gestió del compte d'usuari.
     * Permet canviar la contrasenya, canviar la mà bona, i eliminar el compte d'usuari.
     */
    public void mostra() {
        Stage window = new Stage();
        window.setResizable(true);
        window.setTitle("Key-Layout Generator");
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(80);
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);

        for (int i = 0; i < 12; ++i) {
            RowConstraints fila = new RowConstraints();
            fila.setPercentHeight(10);
            layout.getRowConstraints().add(fila);
        }

        //Separadors
        Separator s1 = new Separator();
        Separator s2 = new Separator();
        Separator s3 = new Separator();
        Separator s4 = new Separator();
        GridPane.setColumnSpan(s1, 3);
        GridPane.setColumnSpan(s2, 3);
        GridPane.setColumnSpan(s3, 3);
        GridPane.setColumnSpan(s4, 3);
        GridPane.setConstraints(s1, 0, 2);
        GridPane.setConstraints(s2, 0, 4);
        GridPane.setConstraints(s3, 0, 6);
        GridPane.setConstraints(s4, 0, 8);

        //Titol
        Label titol = new Label("Hola! "+ cPresentacio.getUsername() +" Esperem que la teva experiència d'usuari estigui sent bona!\n" +
                "Què desitges fer amb el teu compte?");
        titol.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        GridPane.setColumnSpan(titol, 2);
        GridPane.setRowSpan(titol, 2);
        GridPane.setConstraints(titol, 0, 0);


        //Boto Canviar Contrasenya
        Button botoCanviContrasenya = new Button("Canviar Contrasenya");
        botoCanviContrasenya.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoCanviContrasenya.setPrefWidth(460);
        botoCanviContrasenya.setPrefHeight(40);
        botoCanviContrasenya.setOnAction(e -> {
            popUpCanviPassword();
        });
        GridPane.setConstraints(botoCanviContrasenya, 1, 3);

        //Boto Canviar Ma Bona
        Button botoCanviarMaBona = new Button("Canviar la meva mà bona");
        botoCanviarMaBona.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoCanviarMaBona.setPrefHeight(40);
        botoCanviarMaBona.setPrefWidth(460);
        botoCanviarMaBona.setOnAction(e -> {
            popUpCanviMa();
        });
        GridPane.setConstraints(botoCanviarMaBona, 1, 5);

        //Boto Eliminar Usuari
        Button botoEliminarUsuari = new Button("ELIMINAR USUARI");
        botoEliminarUsuari.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        botoEliminarUsuari.setPrefHeight(40);
        botoEliminarUsuari.setPrefWidth(460);
        botoEliminarUsuari.setTextFill(Color.RED);
        botoEliminarUsuari.setOnAction(e -> {
            popUpEliminarUsuari();
            window.close();
        });
        GridPane.setConstraints(botoEliminarUsuari, 1, 7);

        //Boto Enrere
        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 9);
        GridPane.setColumnSpan(botoEnrere, 2);
        botoEnrere.setOnAction(e -> {
            cPresentacio.mostraMenuPrincipal();
            window.close();
        });

        layout.getChildren().addAll(
                titol, botoCanviarMaBona, botoCanviContrasenya, botoEliminarUsuari, s1, s2, s3, s4, botoEnrere
        );
        Scene escena = new Scene(layout, 600, 600);
        window.setScene(escena);
        window.show();
    }

    /**
     * Mostra una finestra emergent per a canviar la contrasenya de l'usuari.
     */
    private void popUpCanviPassword() {
        Stage window = new Stage();
        window.setTitle("Canvi de contrasenya");
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(40);
        c3.setPercentWidth(40);
        c4.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3, c4);

        //Label contrasenya antiga
        Label titolPAntiga = new Label("Contrasenya actual: ");
        GridPane.setConstraints(titolPAntiga, 1, 0);

        //TextField Contrasenya antiga
        PasswordField oldPassTF = new PasswordField();
        oldPassTF.setPromptText("Introdueix la teva contrasenya actual");
        GridPane.setColumnSpan(oldPassTF, 2);
        GridPane.setConstraints(oldPassTF, 2, 0);

        //Label nova contrasenya
        Label titolPNova = new Label("Nova contrasenya: ");
        GridPane.setConstraints(titolPNova, 1, 1);

        //TextField Contrasenya nova
        PasswordField newPassTF = new PasswordField();
        newPassTF.setPromptText("Introdueix la nova contrasenya que vols");
        GridPane.setColumnSpan(newPassTF, 2);
        GridPane.setConstraints(newPassTF, 2, 1);

        //Label recordatori
        Label recordatori = new Label("Advertència: La contrasenya ha d'incloure mínim 5 caràcters i ha d'haver lletres i números");
        recordatori.setStyle("-fx-text-fill: #3498db; -fx-underline: true; -fx-font-size: 11;");
        GridPane.setConstraints(recordatori, 0, 2);
        GridPane.setColumnSpan(recordatori, 4);

        //Boto confirmar nova contrasenya
        Button botoConf = new Button("Confirmar");
        botoConf.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoConf.setOnAction(e -> {
            String oldPass = oldPassTF.getText();
            String newPass = newPassTF.getText();
            try {
                cPresentacio.canviaPassword(oldPass, newPass);
                cPresentacio.mostraInfo("La contrasenya s'ha canviat correctament!");
                window.close();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
            }
        });
        GridPane.setColumnSpan(botoConf, 2);
        GridPane.setConstraints(botoConf, 1, 3);

        layout.getChildren().addAll(
                titolPNova, titolPAntiga, newPassTF, oldPassTF, botoConf, recordatori
        );
        Scene escena = new Scene(layout, 600,250);
        window.setScene(escena);
        window.showAndWait();
    }

    /**
     * Mostra una finestra emergent per a canviar la mà bona de l'usuari.
     */
    private void popUpCanviMa() {
        Stage window = new Stage();
        window.setTitle("Canvi de Mà Bona");
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(80);
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);

        //Label Mà Bona
        Label avis1 = new Label();
        String text = "La seva mà bona actual és: ";
        String maBonaAct = cPresentacio.getMaBona();
        text += maBonaAct;
        avis1.setText(text);
        GridPane.setConstraints(avis1, 1, 0);

        //Label vol canviar
        Label avis2 = new Label("Desitja canviar la seva mà bona?");
        GridPane.setConstraints(avis2, 1,1);

        //Botó de confirmació
        Button botoConf = new Button("Confirmar");
        botoConf.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoConf.setPrefWidth(350);
        botoConf.setOnAction(e -> {
            try {
                cPresentacio.canviaMaBona();
                cPresentacio.mostraInfo("La seva mà bona ha sigut canviada correctament");
                window.close();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
            }
        });
        GridPane.setConstraints(botoConf, 1,2);

        layout.getChildren().addAll(
                avis1, avis2, botoConf
        );
        Scene escena = new Scene(layout, 500,250);
        window.setScene(escena);
        window.showAndWait();
    }

    /**
     * Mostra una finestra emergent per a confirmar l'eliminació del compte d'usuari.
     */
    private void popUpEliminarUsuari() {
        Stage window = new Stage();
        window.setTitle("Eliminar Usuari");
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(80);
        c3.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3);

        //Titol
        Label estasSegur = new Label("ESTAS SEGUR QUE VOLS DONAR-TE DE BAIXA?");
        estasSegur.setTextFill(Color.RED);
        GridPane.setConstraints(estasSegur, 1,0);

        //BotoDefintiu
        Button botoDef = new Button("SÍ, ELIMINAR");
        botoDef.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        //botoDef.setTextFill(Color.DARKRED);
        botoDef.setPrefWidth(250);
        botoDef.setPrefHeight(50);
        botoDef.setOnAction(e -> {
            popUpCredencialsEliminar();
            window.close();
        });
        GridPane.setConstraints(botoDef, 1,1);
        window.setOnCloseRequest((WindowEvent event)-> {
                    window.close();
                    cPresentacio.mostraGestioUsuari();
                }
        );
        layout.getChildren().addAll(
                estasSegur, botoDef
        );
        Scene escena = new Scene(layout, 350,250);
        window.setScene(escena);
        window.showAndWait();
    }

    /**
     * Mostra una finestra emergent per a confirmar l'eliminació del compte d'usuari.
     * Demana a l'usuari que introdueixi les seves credencials per a confirmar l'acció.
     */
    private void popUpCredencialsEliminar() {
        Stage window = new Stage();
        window.setTitle("Eliminar Usuari");
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c1.setPercentWidth(25);
        c2.setPercentWidth(75);
        layout.getColumnConstraints().addAll(c1, c2);

        //Titol
        Label titol = new Label("Introdueix els teus credencials per a confirmar l'eliminació");
        GridPane.setConstraints(titol, 1, 0);

        //Text
        Label lUsername = new Label("El teu username: ");
        GridPane.setConstraints(lUsername, 0, 1);

        //textField Username
        TextField tFieldUsername = new TextField();
        GridPane.setConstraints(tFieldUsername, 1, 1);

        //Label Password
        Label lPassword = new Label("La teva contrasenya: ");
        GridPane.setConstraints(lPassword, 0,2);

        //Text Field Password
        TextField tFieldPassw = new PasswordField();
        GridPane.setConstraints(tFieldPassw, 1,2);

        //Botó confirmació
        Button botoConf = new Button("Confirmar eliminació");
        botoConf.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");

        //botoConf.setTextFill(Color.DARKRED);
        botoConf.setPrefWidth(350);
        botoConf.setPrefHeight(50);
        botoConf.setOnAction(e -> {
            try {
                cPresentacio.esborraUsuari(tFieldUsername.getText(), tFieldPassw.getText());
                cPresentacio.mostraInfo("Usuari eliminat correctament del sistema");
                window.close();
                cPresentacio.Inicialitza();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
            }
        });
        GridPane.setConstraints(botoConf, 1,3);
        window.setOnCloseRequest((WindowEvent event)-> {
                    window.close();
                    cPresentacio.mostraGestioUsuari();
                }
        );
        layout.getChildren().addAll(
                titol, lUsername, tFieldUsername, lPassword, tFieldPassw, botoConf
        );
        Scene escena = new Scene(layout, 500,300);
        window.setScene(escena);
        window.showAndWait();
    }
}
