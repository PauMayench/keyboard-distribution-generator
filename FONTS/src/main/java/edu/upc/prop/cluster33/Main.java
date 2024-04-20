package edu.upc.prop.cluster33;

import edu.upc.prop.cluster33.presentacio.MainApp;

/**
 * La classe Main actua com a punt d'entrada per a l'aplicació.
 * Aquesta classe conté el mètode main, que és el primer mètode que s'executa en iniciar l'aplicació.
 *
 * En aquest cas, la classe Main delega l'execució de l'aplicació a la classe MainApp,
 * la qual és responsable de configurar i llançar l'interfície d'usuari principal i la lògica associada.
 *
 */
public class Main {
  public static void main(String[] args) throws Exception {
    MainApp.main(args);
  }
}