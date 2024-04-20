package edu.upc.prop.cluster33.presentacio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Aquesta classe representa la vista per a la gestió de textos públics.
 * Permet visualitzar i eliminar textos públics de l'aplicació.
 */
public class VistaMenuGestioTextosPublics {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;

    /**
     * Constructor de la classe VistaMenuGestioTextosPublics.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaMenuGestioTextosPublics(ControladorCapaPresentacio c){
        cPresentacio = c;
    }

    /**
     * Mostra la interfície d'usuari per a la gestió de textos públics.
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
        // Tres columnes, 10%, 80% i 10%
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(10);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(80);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(10);
        grid.getColumnConstraints().addAll(c1, c2, c3);

        //Llista de textos possibles
        ListView<String> llistaTextos = new ListView<String>();
        HashMap<Integer, String> textosPublics = new HashMap<>();
        try {
            textosPublics = cPresentacio.getTextsPublicsUsuari();
        }
        catch (Exception e1){
            cPresentacio.mostraError(e1.getMessage());
        }
        ObservableList<String> itemsTextosPublics = FXCollections.observableArrayList();
        for(Map.Entry<Integer, String> entry : textosPublics.entrySet()) {
            itemsTextosPublics.add(entry.getValue());
        }
        llistaTextos.setItems(itemsTextosPublics);

        llistaTextos.setPrefHeight(100);
        llistaTextos.setPrefWidth(100);
        GridPane.setConstraints(llistaTextos, 1, 0);

        //Primera opcio veure text public
        Button botoVeureTextP = new Button("Veure text públic");
        botoVeureTextP.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        GridPane.setConstraints(botoVeureTextP, 1, 2);
        botoVeureTextP.setPrefWidth(623);
        HashMap<Integer, String> finalTextosPublics = textosPublics;
        //Text text = new Text();
        //GridPane.setConstraints(text, 1, 2);
        botoVeureTextP.setOnAction(e -> {
            int idTextSeleccionat = 0;
            String textSeleccionat = llistaTextos.getSelectionModel().getSelectedItem();
            for(Map.Entry<Integer, String> entry : finalTextosPublics.entrySet()){
                if(entry.getValue().equals(textSeleccionat)){
                    idTextSeleccionat = entry.getKey();
                }
            }

            try{
                String textPub = cPresentacio.getTextPublic(idTextSeleccionat);
                mostraPopUpText(textPub);

                //text.setText(textPub);
            }
            catch (Exception e1){
                cPresentacio.mostraError(e1.getMessage());
            }
        });


        //Segona opcio eliminar text public
        Button botoEliminarTextP = new Button("Eliminar text públic");
        botoEliminarTextP.setPrefWidth(623);
        botoEliminarTextP.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEliminarTextP, 1, 3);
        botoEliminarTextP.setOnAction(e -> {
            int idTextSeleccionat = 0;
            String textSeleccionat = llistaTextos.getSelectionModel().getSelectedItem();
            for(Map.Entry<Integer, String> entry : finalTextosPublics.entrySet()){
                if(entry.getValue().equals(textSeleccionat)){
                    idTextSeleccionat = entry.getKey();
                }
            }
            try{
                cPresentacio.eliminarTextPublic(idTextSeleccionat);
                cPresentacio.mostraInfo("S'ha esborrat correctament");
                window.close();
                cPresentacio.mostraGestioTextPublic();
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

        grid.getChildren().addAll(llistaTextos, botoVeureTextP, botoEliminarTextP, botoEnrere);
        Scene escena = new Scene(grid, 800, 600);
        window.setScene(escena);
        window.show();
    }

    /**
     * Mostra una finestra emergent amb el contingut d'un text públic.
     *
     * @param textP El text públic a visualitzar.
     */
    private void mostraPopUpText(String textP){
        Stage window = new Stage();
        window.setTitle("Visualització text públic");
        window.initModality(Modality.APPLICATION_MODAL);
        GridPane layout = new GridPane();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(40);
        c3.setPercentWidth(40);
        c4.setPercentWidth(10);
        layout.getColumnConstraints().addAll(c1, c2, c3, c4);

        Text text = new Text();
        GridPane.setConstraints(text, 0, 0);
        text.setText(textP);

        layout.getChildren().addAll(
                text
        );
        Scene escena = new Scene(layout, 400, 250);
        window.setScene(escena);
        window.showAndWait();
    }
}
