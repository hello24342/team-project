package app;

import javax.swing.JFrame;

import app.factory.DeckManageUseCaseFactory;
import app.factory.DeckManageUseCaseFactory.DeckMenuBundle;
import app.factory.StudyDeckUseCaseFactory;

import data_access.FileDeckDataAccessObject;
import data_access.FileFlashcardDataAccessObject;

import usecase.deck.DeckDataAccessInterface;
import view.ViewManager;
import view.StudyDeckView;
import view.deck.DeckDetailView;
import view.deck.DeckMenuView;

public class AppBuilder {

    /**
     * Build DAOs, use cases, views and register them into ViewManager.
     */
    public static ViewManager build() {

        // 1) create the main frame and ViewManager
        JFrame frame = new JFrame("VocabVault");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ViewManager viewManager = new ViewManager(frame);

        // 2) create Data Access instances
        // TODO: other DAOs & can be replaced by DB implementations later if needed
        // Deck DAO (uses CSV file for storage)
        DeckDataAccessInterface deckDAO =
                new FileDeckDataAccessObject("decks.csv");

        FileFlashcardDataAccessObject cardDAO = new FileFlashcardDataAccessObject("flashcards.csv");

        // assume currently only one user, id = 1
        int currentUserId = 1;

        // 3) construct use case components
        // TODO: other use case components
        // deck UC5 & 10 & 11
        DeckMenuBundle deckBundle =
                DeckManageUseCaseFactory.build(deckDAO, cardDAO, currentUserId);

        // study deck UC2
        StudyDeckUseCaseFactory.StudyDeckBundle studyBundle =
                StudyDeckUseCaseFactory.build(deckDAO, cardDAO);

        // 4) construct Views
        // TODO: other views
        // DeckMenuView
        DeckMenuView deckMenuView = new DeckMenuView(
                deckBundle.vm,
                deckBundle.createController,
                deckBundle.listController,
                deckBundle.openController,
                viewManager
        );

        // DeckDetailView
        DeckDetailView deckDetailView = new DeckDetailView(
                deckBundle.detailVM,
                deckBundle.openController,
                studyBundle.controller,
                currentUserId,
                viewManager
        ); // TODO: adjust if DeckDetailView constructor changes

        // StudyDeckView
        StudyDeckView studyView = new StudyDeckView(studyBundle.vm);
        studyView.setController(studyBundle.controller);

        // 5) register views to ViewManager
        // notice that the name should be the same as the one
        // used in viewManager.show(name)
        // TODO: register other views
        viewManager.add("DeckMenu", deckMenuView);
        viewManager.add("DeckDetail", deckDetailView);
        viewManager.add("Study", studyView);

        return viewManager;
    }
}
