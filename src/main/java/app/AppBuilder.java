package app;

import javax.swing.JFrame;

import app.factory.*;
import app.factory.DeckManageUseCaseFactory.DeckMenuBundle;

import data_access.FileDeckDataAccessObject;
import data_access.FileFlashcardDataAccessObject;
import data_access.FileUserDataAccessObject;

import use_case.FlashcardDataAccessInterface;
import use_case.deck.DeckDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import view.*;
import view.deck.DeckDetailView;
import view.deck.DeckMenuView;

import java.io.IOException;

public class AppBuilder {

// This helper is just here for testing purposes until create flashcard works. you can run main and study this deck!!
//    private static void seedDemoData(DeckDataAccessInterface deckDAO, FlashcardDataAccessInterface cardDAO) {
//        int demoDeckId = 100;
//        String demoDeckTitle = "Demo Deck";
//
//        deckDAO.save(new FlashcardDeck(100, demoDeckTitle, 1));
//
//        Flashcard card1 = new Flashcard(99, "hello", "bonjour", Language.ENGLISH, Language.FRENCH);
//        Flashcard card2 = new Flashcard(100, "cat", "chat", Language.ENGLISH, Language.FRENCH);
//        Flashcard card3 = new Flashcard(101, "dog", "chien", Language.ENGLISH, Language.FRENCH);
//        card1.addDeck(demoDeckId);
//        card2.addDeck(demoDeckId);
//        card3.addDeck(demoDeckId);
//        cardDAO.save(card1);
//        cardDAO.save(card2);
//        cardDAO.save(card3);
//    }


    /**
     * Build DAOs, use cases, views and register them into ViewManager.
     */
    public static ViewManager build() throws IOException {

        // 1) create the main frame and ViewManager
        JFrame frame = new JFrame("VocabVault");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ViewManager viewManager = new ViewManager(frame);

        // 2) create Data Access instances
        // TODO: other DAOs & can be replaced by DB implementations later if needed
        // User DAO
        FileUserDataAccessObject userDAO = new FileUserDataAccessObject("users.csv");
        LoginUserDataAccessInterface loginDAO = userDAO;
        SignupUserDataAccessInterface signupDAO = userDAO;

        // Deck DAO (uses CSV file for storage)
        DeckDataAccessInterface deckDAO =
                new FileDeckDataAccessObject("decks.csv");

        FlashcardDataAccessInterface cardDAO = new FileFlashcardDataAccessObject("flashcards.csv");

        // line below is for testing, delete once create flashcard works
//        seedDemoData(deckDAO, cardDAO);

        // assume currently only one user, id = 1
        int currentUserId = 1;

        // 3) construct use case components
        // TODO: other use case components

        // Login UC
        LoginUseCaseFactory.LoginBundle loginBundle =
                LoginUseCaseFactory.build(loginDAO, viewManager);

        // Sign up UC
        SignupUseCaseFactory.SignupBundle signupBundle =
                SignupUseCaseFactory.build(signupDAO, viewManager);

        // Logout UC
        LogoutUseCaseFactory.LogoutBundle logoutBundle =
                LogoutUseCaseFactory.build(viewManager, userDAO);

        // deck UC5 & 10 & 11
        DeckMenuBundle deckBundle =
                DeckManageUseCaseFactory.build(deckDAO, cardDAO, currentUserId);

        // study deck UC2
        StudyDeckUseCaseFactory.StudyDeckBundle studyBundle =
                StudyDeckUseCaseFactory.build(deckDAO, cardDAO);

        // 4) construct Views
        // TODO: other views
        // LoginView
        LoginView loginView = new LoginView(loginBundle.loginViewModel, loginBundle.loginController);

        // Signup View
        SignUpView signupView = new SignUpView(signupBundle.signupViewModel, signupBundle.signupController);

        // LoggedInView
        LoggedInView loggedInView = new LoggedInView(
                logoutBundle.loggedInViewModel,
                logoutBundle.logoutController,
                deckBundle.listController,
                deckBundle.createController,
                deckBundle.openController
        );

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
                deckBundle.createController,
                deckBundle.editController,
                currentUserId,
                viewManager
        );

        // StudyDeckView
        StudyDeckView studyView = new StudyDeckView(studyBundle.vm);
        studyView.setController(studyBundle.controller);

        // 5) register views to ViewManager
        // notice that the name should be the same as the one
        // used in viewManager.show(name)
        // TODO: register other views
        viewManager.add("Login", loginView);
        viewManager.add("Signup", signupView);
        viewManager.add("LoggedIn", loggedInView);
        viewManager.add("DeckMenu", deckMenuView);
        viewManager.add("DeckDetail", deckDetailView);
        viewManager.add("EditFlashcard", editFlashcardView);
        viewManager.add("Study", studyView);

        return viewManager;
    }
}
