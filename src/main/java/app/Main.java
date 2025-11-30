package app;

import javax.swing.SwingUtilities;

import view.ViewManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // build the whole app (DAOs + use cases + views)
                ViewManager viewManager = null;
                try {
                    viewManager = AppBuilder.build();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // set initial page
                viewManager.show("DeckMenu"); // TODO: change to login view later
            }
        });
    }
}
