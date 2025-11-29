package app;

import javax.swing.SwingUtilities;

import view.ViewManager;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // build the whole app (DAOs + use cases + views)
                ViewManager viewManager = AppBuilder.build();
                // set initial page
                viewManager.show("DeckMenu"); // TODO: change to login view later
            }
        });
    }
}
