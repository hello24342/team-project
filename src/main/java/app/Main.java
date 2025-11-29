package app;
import javax.swing.*;

import app.factory.DeckManageUseCaseFactory;
import app.factory.DeckManageUseCaseFactory.DeckMenuBundle;
import app.factory.StudyDeckUseCaseFactory;

import data_access.DeckDataAccess;
import data_access.FlashcardDataAccessObject;

import entity.Flashcard;
import entity.FlashcardDeck;
import entity.Language;
import usecase.FlashcardDataAccessInterface;
import usecase.deck.DeckDataAccessInterface;

import view.LoginView;
import view.StudyDeckView;
import view.ViewManager;
import view.deck.DeckDetailView;
import view.deck.DeckMenuView;


public class Main {

    public static void main(String[] args) {

        // 1) create the main page and ViewManager
        JFrame frame = new JFrame("VocabVault");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ViewManager viewManager = new ViewManager(frame);

        // 2) create Data Access instance
        //TODO: other DAOs & can be replaced by DB implementation later
        DeckDataAccess deckDAO = new DeckDataAccess();
        FlashcardDataAccessObject cardDAO = new FlashcardDataAccessObject();

        // assume currently only one user，id = 1
        int currentUserId = 1;

        // 3) Construct usecase components
        // TODO: other usecase components
        // deck uc5 & 10 & 11
        // 用 DeckManageUseCaseFactory
        DeckMenuBundle deckBundle =
                DeckManageUseCaseFactory.build(deckDAO, cardDAO, currentUserId);

        // study deck uc2
        StudyDeckUseCaseFactory.StudyDeckBundle studyBundle = StudyDeckUseCaseFactory.build(deckDAO, cardDAO);

        // 4) construct Views
        // TODO: other views
        // LoginView
        // LoginView loginView = new LoginView(viewManager);// TODO: implement appropriate constructor here just a placeholder
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
        ); // TODO: Jane pls check and change if the DeckDetailView constructor is changed

        StudyDeckView studyView = new StudyDeckView(studyBundle.vm);
        studyView.setController(studyBundle.controller);

        // 5) register view to ViewManager
        // TODO: register other views
        // notice that the name should be the same as the one used in viewManager.show(name)
        // viewManager.add("Login", loginView);
        viewManager.add("DeckMenu", deckMenuView);
        viewManager.add("DeckDetail", deckDetailView);
        viewManager.add("Study", studyView);

        // 6) set initial page, I think it's LoginView?
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                viewManager.show("DeckMenu");
            }
        });
    }
}