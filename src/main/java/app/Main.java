package app;

import java.io.IOException;

import javax.swing.SwingUtilities;

import view.ViewManager;

public class Main {
    /**
    * The main entry point for the application.
    * This method initializes the program and starts the GUI.
     * @param args the command line arguments
    **/
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // build the whole app (DAOs + use cases + views)
                ViewManager viewManager = null;
                try {
                    viewManager = AppBuilder.build();
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // set initial page
                viewManager.show("Login"); // TODO: change to login view later
            }
        });
    }
}
