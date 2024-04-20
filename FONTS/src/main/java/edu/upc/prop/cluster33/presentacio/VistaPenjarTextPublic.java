package edu.upc.prop.cluster33.presentacio;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * VistaPenjarTextPublic gestiona la interfície per a penjar textos públics a l'aplicació.
 */
public class VistaPenjarTextPublic {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;

    /**
     * Constructor de VistaPenjarTextPublic.
     *
     * @param c Controlador de la capa de presentació associat a aquesta vista.
     */
    public VistaPenjarTextPublic(ControladorCapaPresentacio c){
        cPresentacio = c;
    }

    /**
     * Llegeix el contingut d'un fitxer de text i el retorna com a String.
     *
     * @param file El fitxer a llegir.
     * @return String amb el contingut del fitxer.
     * @throws IOException Si es produeix un error de lectura.
     */
    private String readfile(File file) throws IOException {
        StringBuilder contingut = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String line;

        while((line = reader.readLine()) != null){
            contingut.append(line);
            contingut.append("\n");
        }
        reader.close();
        return contingut.toString();
    }

    /**
     * Mostra la vista per penjar textos públics.
     * Aquest mètode configura i mostra els elements de la interfície d'usuari per permetre a l'usuari seleccionar
     * i penjar un fitxer de text públic.
     */
    public void mostra(){
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

        //Titol
        Label titol = new Label("Segueixi el format indicat a la següent capsa de text seguint el format indicat.");
        titol.setPrefHeight(40);
        titol.setPrefWidth(480);
        GridPane.setValignment(titol, VPos.CENTER);
        GridPane.setHalignment(titol, HPos.CENTER);
        GridPane.setConstraints(titol, 1, 0);

        //FileChooser
        AtomicReference<String> contingut = new AtomicReference<>("");
        Button selectFileButton = new Button("Selecciona fitxer");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona fitxer");
        selectFileButton.setOnAction(e ->{
            File selectedFile =  fileChooser.showOpenDialog(window);
            if(selectedFile != null){
                try{
                    contingut.set(readfile(selectedFile));
                }
                catch (Exception ex){
                    cPresentacio.mostraError(ex.getMessage());
                }
            }
        });

        //Quadre Text Posar Nom
        TextField tFieldNom = new TextField();
        tFieldNom.setPrefWidth(460);
        tFieldNom.setPrefHeight(40);
        tFieldNom.setPromptText("Introdueix el nom del text aquí");
        GridPane.setConstraints(tFieldNom, 1, 2);

        //Boto confirmar
        Button botoConfirmar = new Button("Confirmar");
        botoConfirmar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoConfirmar.setMinWidth(623);
        botoConfirmar.setMinHeight(40);
        botoConfirmar.setOnAction(e -> {
            try {
                cPresentacio.afegirTextPublic(tFieldNom.getText(), contingut.get());
                cPresentacio.mostraInfo("El text amb nom " + tFieldNom.getText() +" s'ha penjat correctament!");
                window.close();
                cPresentacio.mostraMenuPrincipal();
            } catch (Exception ex) {
                cPresentacio.mostraError(ex.getMessage());
            }
        });
        GridPane.setConstraints(botoConfirmar, 1, 3);

        //Boto Enrere
        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 5);
        GridPane.setColumnSpan(botoEnrere, 2);
        botoEnrere.setOnAction(e -> {
            cPresentacio.mostraMenuPrincipal();
            window.close();
        });

        layout.getChildren().addAll(
                titol, selectFileButton, tFieldNom, botoConfirmar, botoEnrere
        );
        Scene escena = new Scene(layout, 800, 600);
        window.setScene(escena);
        window.show();
    }

}
