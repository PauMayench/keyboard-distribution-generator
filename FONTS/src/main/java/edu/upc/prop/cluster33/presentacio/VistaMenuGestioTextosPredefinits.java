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
 * Aquesta classe representa la vista per a la gestió de textos predefinits.
 * Permet visualitzar i eliminar textos predefinits de l'aplicació.
 */
public class VistaMenuGestioTextosPredefinits {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;

    /**
     * Constructor de la classe VistaMenuGestioTextosPredefinits.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaMenuGestioTextosPredefinits(ControladorCapaPresentacio c){
        cPresentacio = c;
    }

    /**
     * Mostra la interfície d'usuari per a la gestió de textos predefinits.
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
        ListView<String> llistaTextos = new ListView<String>();
        HashMap<Integer, String> textosPredefinits = new HashMap<>();
        try {
            textosPredefinits = cPresentacio.getTextsPredefinits();
        }
        catch (Exception e1){
            cPresentacio.mostraError(e1.getMessage());
        }
        ObservableList<String> itemsTextosPredefinits = FXCollections.observableArrayList();
        for(Map.Entry<Integer, String> entry : textosPredefinits.entrySet()) {
            itemsTextosPredefinits.add(entry.getValue());
        }
        llistaTextos.setItems(itemsTextosPredefinits);

        llistaTextos.setPrefHeight(100);
        llistaTextos.setPrefWidth(100);
        GridPane.setConstraints(llistaTextos, 1, 0);

        //Primera opcio veure text predefinit
        Button botoVeureTextPre = new Button("Veure text predefinit");
        botoVeureTextPre.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        GridPane.setConstraints(botoVeureTextPre, 1, 2);
        botoVeureTextPre.setPrefWidth(623);
        HashMap<Integer, String> finalTextosPredefinits = textosPredefinits;

        botoVeureTextPre.setOnAction(e -> {
            int idTextSeleccionat = 0;
            String textSeleccionat = llistaTextos.getSelectionModel().getSelectedItem();
            for(Map.Entry<Integer, String> entry : finalTextosPredefinits.entrySet()){
                if(entry.getValue().equals(textSeleccionat)){
                    idTextSeleccionat = entry.getKey();
                }
            }

            try{
                String textPre = cPresentacio.getTextPredefinit(idTextSeleccionat);
               mostraPopUpText(textPre);
            }
            catch (Exception e1){
                cPresentacio.mostraError(e1.getMessage());
            }
        });


        //Segona opcio eliminar text predefinit
        Button botoEliminarTextPre = new Button("Eliminar text públic");
        botoEliminarTextPre.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEliminarTextPre, 1, 3);
        botoEliminarTextPre.setPrefWidth(623);
        botoEliminarTextPre.setOnAction(e -> {
            int idTextSeleccionat = 0;
            String textSeleccionat = llistaTextos.getSelectionModel().getSelectedItem();
            for(Map.Entry<Integer, String> entry : finalTextosPredefinits.entrySet()){
                if(entry.getValue().equals(textSeleccionat)){
                    idTextSeleccionat = entry.getKey();
                }
            }
            try{
                cPresentacio.eliminarTextPredefinit(idTextSeleccionat);
                cPresentacio.mostraInfo("S'ha esborrat correctament");
                window.close();
                cPresentacio.mostraGestioTextPredefinit();
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

        grid.getChildren().addAll(llistaTextos, botoVeureTextPre, botoEliminarTextPre, botoEnrere);
        Scene escena = new Scene(grid, 800, 500);
        window.setScene(escena);
        window.show();
    }

    /**
     * Mostra una finestra emergent amb el contingut d'un text predefinit.
     *
     * @param textP El text predefinit a visualitzar.
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
