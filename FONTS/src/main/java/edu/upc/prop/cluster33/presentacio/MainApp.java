package edu.upc.prop.cluster33.presentacio;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Classe principal de l'aplicació JavaFX.
 * Aquesta classe hereta de l'aplicació JavaFX i gestiona l'inici de la interfície gràfica d'usuari.
 */
public class MainApp extends Application {
    /**
     * Mètode principal que llança l'aplicació.
     *
     * @param args Arguments de la línia de comandaments no utilitzats directament per aquesta aplicació.
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Inicialitza l'escenari principal de l'aplicació i configura la visualització inicial.
     *
     * @param primaryStage L'escenari principal sobre el qual es carregarà la interfície gràfica d'usuari.
     * @throws Exception Si ocorre qualsevol tipus d'error durant l'inicialització.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ControladorCapaPresentacio ctrlPresentacio = new ControladorCapaPresentacio();
        ctrlPresentacio.Inicialitza();
    }
}
