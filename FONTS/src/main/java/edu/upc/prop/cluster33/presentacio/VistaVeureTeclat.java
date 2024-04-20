package edu.upc.prop.cluster33.presentacio;

import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.util.Vector;

/**
 * VistaVeureTeclat gestiona la interfície per a visualitzar la informació detallada d'un teclat específic.
 */
public class VistaVeureTeclat {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cpresentacio;

    /**
     * Constructor de VistaVeureTeclat.
     *
     * @param c Controlador de la capa de presentació associat a aquesta vista.
     */
    public VistaVeureTeclat(ControladorCapaPresentacio c){
        cpresentacio = c;
    }

    /**
     * Mostra la vista amb la informació detallada d'un teclat específic.
     *
     * @param idTeclat Identificador del teclat a visualitzar.
     */
    public void mostra(int idTeclat){
        Stage window = new Stage();
        window.setResizable(true);
        window.setTitle("Key-Layout Generator");
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));

        //Obtenim info del teclat
        Vector<String> infoTeclat;
        try{
            infoTeclat = cpresentacio.getTeclat(idTeclat);
            //Nom del teclat
            Label labelNom = new Label("Nom: " + infoTeclat.get(0));
            labelNom.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            GridPane.setConstraints(labelNom, 0, 0);
            GridPane.setColumnSpan(labelNom, 2);

            //Nom Algorisme
            Label labelAlgorisme = new Label("Algorisme: " + infoTeclat.get(1));
            labelAlgorisme.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            GridPane.setConstraints(labelAlgorisme, 0, 1);
            GridPane.setColumnSpan(labelAlgorisme, 2);

            //Nom Alfabet
            Label labelAlfabet = new Label("Alfabet: " + infoTeclat.get(3));
            labelAlfabet.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            GridPane.setConstraints(labelAlfabet, 0, 2);
            GridPane.setColumnSpan(labelAlfabet, 2);

            //Data Creacio
            Label labelData = new Label("Data Creació: " + infoTeclat.get(2));
            labelData.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            GridPane.setConstraints(labelData, 0, 3);
            GridPane.setColumnSpan(labelData, 2);

            String maBona = new String("");
            try{
                maBona = cpresentacio.getMaBona();
            }
            catch (Exception e){
                cpresentacio.mostraError(e.getMessage());
            }

            for(int i = 4; i < infoTeclat.size(); ++i){
                boolean displayLine = false;
                for (int j = 0; j < infoTeclat.get(i).length(); ++j)
                    if (!(infoTeclat.get(i).charAt(j) == '\u0000')) displayLine = true;

                if(displayLine) {
                    if (maBona.equals("Esquerra")) {
                        for (int j = 0; j < infoTeclat.get(i).length(); ++j) {
                            Button botoTeclat = new Button("" + infoTeclat.get(i).charAt(j));
                            botoTeclat.setPrefWidth(40);
                            botoTeclat.setPrefHeight(40);
                            GridPane.setConstraints(botoTeclat, j + 2, i + 4);
                            layout.getChildren().add(botoTeclat);
                        }
                    } else {
                        int aux = 0;
                        for (int j = infoTeclat.get(i).length() - 1; j >= 0; --j) {
                            Button botoTeclat = new Button("" + infoTeclat.get(i).charAt(j));
                            botoTeclat.setPrefWidth(40);
                            botoTeclat.setPrefHeight(40);
                            GridPane.setConstraints(botoTeclat, aux + 2, i + 4);
                            layout.getChildren().add(botoTeclat);
                            ++aux;
                        }
                    }
                }

            }
            layout.getChildren().addAll(labelNom, labelAlgorisme, labelAlfabet, labelData);



        }
        catch(Exception e1){
            cpresentacio.mostraError(e1.getMessage());
        }

        //Boto Enrere
        Button botoEnrere = new Button("Enrere");
        GridPane.setColumnSpan(botoEnrere,2);
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 5);
        botoEnrere.setOnAction(e -> {
            cpresentacio.mostraGestionarTeclats();
            window.close();
        });

        layout.getChildren().add(botoEnrere);
        Scene escena = new Scene(layout, 600, 600);
        window.setScene(escena);
        window.show();
    }
}
