package edu.upc.prop.cluster33.presentacio;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Aquesta classe representa la vista per a la creació de teclats.
 * Proporciona una interfície d'usuari per seleccionar un algorisme de creació de teclats
 * i determinar la forma de proporcionar les dades per a la generació del teclat.
 */
public class VistaCreacioTeclat {
    /**
     * Controlador de la capa de presentació associat amb aquesta vista.
     */
    ControladorCapaPresentacio cPresentacio;

    /**
     * Constructor de la classe VistaCreacioTeclat.
     *
     * @param c El controlador de la capa de presentació associat amb aquesta vista.
     */
    public VistaCreacioTeclat(ControladorCapaPresentacio c) {cPresentacio = c;}

    /**
     * Mostra la interfície d'usuari per a la creació de teclats.
     * Permet a l'usuari escollir un algorisme de creació i el mètode de
     * introducció de dades (manual, fitxer de text, etc.).
     */
    public void mostra() {
        Stage windowSeleccio = new Stage();
        windowSeleccio.setResizable(true);
        windowSeleccio.setTitle("Key-Layout Generator");
        GridPane layoutSeleccio = new GridPane();
        layoutSeleccio.setVgap(10);

        //4 Columnes: 10%, 40%, 40%, 10%
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        c1.setPercentWidth(10);
        c2.setPercentWidth(40);
        c3.setPercentWidth(40);
        c4.setPercentWidth(10);

        layoutSeleccio.getColumnConstraints().addAll(c1, c2, c3, c4);

        /*for (int i = 0; i < 10; ++i) {
            RowConstraints fila = new RowConstraints();
            fila.setPercentHeight(100.0/10.0);
            layoutSeleccio.getRowConstraints().add(fila);
        }*/

        //Separadors (No restricció de files...)
        Separator separador1 = new Separator();
        Separator separador2 = new Separator();
        Separator separador3 = new Separator();
        GridPane.setColumnSpan(separador1, 4);
        GridPane.setColumnSpan(separador2, 4);
        GridPane.setColumnSpan(separador3, 4);
        GridPane.setConstraints(separador1, 0, 3);
        GridPane.setConstraints(separador2, 0, 6);
        GridPane.setConstraints(separador3, 0, 12);

        //Titol:

        Label titol = new Label("Anem a crear un teclat, però abans...\n" +
                "Necessitem que ens proporcionis més detalls!");
        titol.setStyle("-fx-font-size: 18;");
        GridPane.setColumnSpan(titol, 2);
        GridPane.setRowSpan(titol, 2);
        GridPane.setConstraints(titol, 1, 0);

        //Descripció comboBox Algorismes
        Label descAlg = new Label("Abans que res, necessitem saber com donarem lloc a la teva distribució ideal");
        descAlg.setStyle("-fx-font-size: 14;");
        GridPane.setColumnSpan(descAlg, 3);
        GridPane.setConstraints(descAlg, 1, 4);

        //Combobox Algorisme:
        ObservableList<String> optAlgorismes = FXCollections.observableArrayList();
        ComboBox<String> cboxAlgorismes = new ComboBox<>();
        HashMap<Integer, String> alg2 = new HashMap<>();
        try{

            alg2 = cPresentacio.getAlgorismes();
            for (int i : alg2.keySet()) {
                String a = alg2.get(i);
                optAlgorismes.add(a);
            }

            String avisInicial = "Selecciona l'algorisme amb què vols crear el teclat";
            cboxAlgorismes.setItems(optAlgorismes);
            cboxAlgorismes.setPromptText(avisInicial);
            GridPane.setColumnSpan(cboxAlgorismes, 2);
            GridPane.setConstraints(cboxAlgorismes, 1, 5);
        }
        catch (Exception e){
            cPresentacio.mostraError(e.getMessage());
        }

        final HashMap<Integer, String> alg = alg2;

        //Text Elecció mètode
        Label tEleccio = new Label("De quina manera prefereixes proporcionar la informació per a crear el teclat?");
        GridPane.setColumnSpan(tEleccio, 3);
        GridPane.setConstraints(tEleccio, 1, 7);

        //Botó entrar Frequencies Manualment
        Button botoFreqMan = new Button("Entrar freqüències Manualment");
        botoFreqMan.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoFreqMan.setMinWidth(480);
        botoFreqMan.setMinHeight(40);
        botoFreqMan.setTooltip(new Tooltip("Introduïr de forma manual la llista de freqüències sobre\n" +
                "la que treballarà el programa."));
        botoFreqMan.setOnAction(e -> {
            if (cboxAlgorismes.getSelectionModel().isEmpty()) {
                cPresentacio.mostraError("No s'ha seleccionat cap algorisme. Assegura't que has emplenat correctament tots els camps.");
            }
            else {
                String algorismeSeleccionat = cboxAlgorismes.getValue();
                boolean gotit = false;
                for (int i : alg.keySet()) {
                    if (alg.get(i).equals(algorismeSeleccionat) && !gotit) {

                        gotit = true;
                        cPresentacio.mostraVistaCreacioFreqManual(i, -1);
                    }
                }
                windowSeleccio.close();
            }
        });
        GridPane.setColumnSpan(botoFreqMan, 2);
        GridPane.setConstraints(botoFreqMan, 1, 8);

        //Botó entrar Frequencies per fitxer
        Button botoFreqf = new Button("Entrar Fitxer amb Freqüències");
        botoFreqf.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoFreqf.setMinWidth(480);
        botoFreqf.setMinHeight(40);
        botoFreqf.setTooltip(new Tooltip("Introdueix les freqüències sobre les que treballarà el programa " +
                "a partir d'un fitxer extern."));
        GridPane.setColumnSpan(botoFreqf, 2);
        GridPane.setConstraints(botoFreqf, 1, 9);
        botoFreqf.setOnAction(e ->{
            if (cboxAlgorismes.getSelectionModel().isEmpty()) {
                cPresentacio.mostraError("No s'ha seleccionat cap algorisme. Assegura't que has emplenat correctament tots els camps.");
            }
            else {
                String algorismeSeleccionat = cboxAlgorismes.getValue();
                boolean gotit = false;
                for (int i : alg.keySet()) {
                    if (alg.get(i).equals(algorismeSeleccionat) && !gotit) {                        
                        gotit = true;
                        try {
                            cPresentacio.mostraVistaCreacioFitxer(i, 1, -1);
                        } catch (Exception ex) {
                            cPresentacio.mostraError(ex.getMessage());
                        }
                    }
                }
                windowSeleccio.close();
            }
        });


        //Botó seleccionar fitxer de text
        Button botoFitxerTxt = new Button("Crear a partir d'un fitxer de text");
        botoFitxerTxt.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        botoFitxerTxt.setMinWidth(480);
        botoFitxerTxt.setMinHeight(40);
        botoFitxerTxt.setTooltip(new Tooltip("El programa generarà el teclat més adient a partir del " +
                "fitxer de text local en llenguatge natural que proporcionis."));
        GridPane.setColumnSpan(botoFitxerTxt, 2);
        GridPane.setConstraints(botoFitxerTxt, 1, 10);
        botoFitxerTxt.setOnAction(e -> {
            if (cboxAlgorismes.getSelectionModel().isEmpty()) {
                cPresentacio.mostraError("No s'ha seleccionat cap algorisme. Assegura't que has emplenat correctament tots els camps.");
            }
            else {
                String algorismeSeleccionat = cboxAlgorismes.getSelectionModel().getSelectedItem();
                int idAlgorisme = -1;
                boolean gotit = false;
                for (int i : alg.keySet()) {
                    if (alg.get(i).equals(algorismeSeleccionat) && !gotit) {
                        gotit = true;
                        idAlgorisme = i;
                    }
                }
                try {
                    cPresentacio.mostraVistaCreacioFitxer(idAlgorisme, 2, -1);
                } catch (Exception ex) {
                    cPresentacio.mostraError(ex.getMessage());
                }
                windowSeleccio.close();
            }
        });


        //Botó seleccionar fitxer de text públic
        Button botoFitxerPub = new Button("Usar text públic");
        botoFitxerPub.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoFitxerPub.setMinWidth(240);
        botoFitxerPub.setMinHeight(40);
        botoFitxerPub.setTooltip(new Tooltip("Crear el teclat a partir d'un text que ja ha fet servir un " +
                "altre usuari i que posteriorment ha publicat."));
        GridPane.setColumnSpan(botoFitxerPub, 1);
        GridPane.setConstraints(botoFitxerPub, 1, 11);

        botoFitxerPub.setOnAction(e ->{
            if (cboxAlgorismes.getSelectionModel().isEmpty()) {
                cPresentacio.mostraError("No s'ha seleccionat cap algorisme. Assegura't que has emplenat correctament tots els camps.");
            }
            else{
                String algorismeSeleccionat = cboxAlgorismes.getSelectionModel().getSelectedItem();
                int idAlgorisme = -1;
                boolean gotit = false;
                for (int i : alg.keySet()) {
                    if (alg.get(i).equals(algorismeSeleccionat) && !gotit) {
                        gotit = true;
                        idAlgorisme = i;
                    }
                }
                cPresentacio.mostraVistaCreacioTextos(idAlgorisme, 1, -1);
                windowSeleccio.close();
            }
        });

        //Botó seleccionar fitxer de text predefinit
        Button botoFitxerPre = new Button("Usar text predefinit");
        botoFitxerPre.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 6 14; -fx-border-radius: 4; -fx-background-radius: 4;");
        botoFitxerPre.setMinWidth(240);
        botoFitxerPre.setMinHeight(40);
        botoFitxerPre.setTooltip(new Tooltip("Crear el teclat a partir d'un text predefinit al sistema."));
        GridPane.setColumnSpan(botoFitxerPre, 1);
        GridPane.setConstraints(botoFitxerPre, 2, 11);

        botoFitxerPre.setOnAction(e ->{
            if (cboxAlgorismes.getSelectionModel().isEmpty()) {
                cPresentacio.mostraError("No s'ha seleccionat cap algorisme. Assegura't que has emplenat correctament tots els camps.");
            }
            else{
                String algorismeSeleccionat = cboxAlgorismes.getSelectionModel().getSelectedItem();
                int idAlgorisme = -1;
                boolean gotit = false;
                for (int i : alg.keySet()) {
                    if (alg.get(i).equals(algorismeSeleccionat) && !gotit) {
                        gotit = true;
                        idAlgorisme = i;
                    }
                }
                cPresentacio.mostraVistaCreacioTextos(idAlgorisme, 2, -1);
                windowSeleccio.close();
            }
        });

        Button botoEnrere = new Button("Enrere");
        botoEnrere.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16; -fx-border-radius: 4.5; -fx-background-radius: 4.5;");
        GridPane.setConstraints(botoEnrere, 0, 20);
        GridPane.setColumnSpan(botoEnrere, 2);
        botoEnrere.setOnAction(e -> {
            cPresentacio.mostraMenuPrincipal();
            windowSeleccio.close();
        });


        layoutSeleccio.getChildren().addAll(
                titol, descAlg, cboxAlgorismes, separador1, separador2, separador3,
                botoFreqMan, botoFreqf, botoFitxerTxt, botoFitxerPub, botoFitxerPre, botoEnrere
        );
        Scene escenaSeleccio = new Scene(layoutSeleccio, 600, 600);
        windowSeleccio.setScene(escenaSeleccio);
        windowSeleccio.show();
    }
}
