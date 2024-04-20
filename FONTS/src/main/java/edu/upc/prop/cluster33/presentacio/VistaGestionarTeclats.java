package edu.upc.prop.cluster33.presentacio;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;

/**
 * Aquesta classe representa la vista per a la gestió de teclats.
 * Proporciona una interfície d'usuari per visualitzar, modificar i eliminar teclats.
 */
public class VistaGestionarTeclats {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;
    /**
     * Constructor de la classe VistaGestionarTeclats.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */

    public VistaGestionarTeclats(ControladorCapaPresentacio c) {cPresentacio = c;}
    /**
     * Mostra la interfície d'usuari per a la gestió de teclats.
     * Presenta una llista de teclats amb opcions per veure, modificar i eliminar cadascun.
     */
    public void mostra() {
            Stage window = new Stage();
            window.setResizable(true);
            window.setTitle("Key-Layout Generator");
            GridPane layout = new GridPane();
            layout.setPadding(new Insets(10,10,10,10));

            // 3 Columnes: 10% 80% 10%
            ColumnConstraints c1 = new ColumnConstraints();
            ColumnConstraints c2 = new ColumnConstraints();
            ColumnConstraints c3 = new ColumnConstraints();
            c1.setPercentWidth(10);
            c2.setPercentWidth(80);
            c3.setPercentWidth(10);
            layout.getColumnConstraints().addAll(c1, c2, c3);

            //Titol
            Label titol = new Label("Els teus teclats...");
            titol.setStyle("-fx-font-size: 16;");
            GridPane.setConstraints(titol, 1, 0);

            //Acordió de teclats

            Accordion llistaTeclats = new Accordion();
            try {
                int fila = 1;
                HashMap<Integer, String> teclats = cPresentacio.getTeclats();
                for (int i : teclats.keySet()) {
                    Button veureButton = new Button("Veure Teclat");
                    veureButton.setPrefWidth(460);
                    Button modificarButton = new Button("Modificar Teclat");
                    modificarButton.setPrefWidth(460);
                    Button eliminarButton = new Button("Eliminar Teclat");
                    eliminarButton.setPrefWidth(460);
                    VBox packBotons = new VBox(veureButton, modificarButton, eliminarButton);
                    TitledPane tPane = new TitledPane(teclats.get(i), packBotons);
                    veureButton.setOnAction(e -> {
                        cPresentacio.mostraVeureTeclat(i);
                        window.close();
                    });

                    modificarButton.setOnAction(e -> {
                        cPresentacio.mostraModificarTeclat(teclats.get(i), i);
                        window.close();
                    });

                    eliminarButton.setOnAction(e -> {
                        cPresentacio.mostraEliminarTeclat(teclats.get(i), i);
                        window.close();
                    });
                    GridPane.setConstraints(tPane, 1, fila);
                    llistaTeclats.getPanes().add(tPane);
                }
            } catch (Exception e) {
                cPresentacio.mostraError(e.getMessage());
            }
            llistaTeclats.setPrefWidth(460);
            GridPane.setConstraints(llistaTeclats, 1, 1);

            //Enrere
            Button botoEnrere = new Button("Enrere");
            botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
            GridPane.setConstraints(botoEnrere, 0, 3);
            GridPane.setColumnSpan(botoEnrere, 2);
            botoEnrere.setOnAction(e -> {
                cPresentacio.mostraMenuPrincipal();
                window.close();
            });

            layout.getChildren().addAll(
                    titol, llistaTeclats, botoEnrere
            );
            Scene escena = new Scene(layout, 800, 600);
            window.setScene(escena);
            window.show();
        }
}
