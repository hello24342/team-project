package app;

import java.io.IOException;

import javax.swing.JFrame;

import app.factory.*;
import app.factory.DeckManageUseCaseFactory.DeckMenuBundle;
import data_access.FileDeckDataAccessObject;
import data_access.FileFlashcardDataAccessObject;
import data_access.FileUserDataAccessObject;
import use_case.flashcard.FlashcardDataAccessInterface;
import use_case.deck.DeckDataAccessInterface;
import use_case.UserDataAccessInterface;
import view.*;
import view.deck.DeckDetailView;
import view.deck.DeckMenuView;

public class AppBuilder {

    /**
     * Build DAOs, use cases, views and register them into ViewManager.
     * @throws IOException when reading from CSV files fails
     */
    public static ViewManager build() throws IOException {

        // 1) create the main frame and ViewManager
        JFrame frame = new JFrame("VocabVault");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ViewManager viewManager = new ViewManager(frame);

        // 2) create Data Access instances
        // TODO: other DAOs & can be replaced by DB implementations later if needed
        // User DAO
        FileUserDataAccessObject fileUserDataAccessObject = new FileUserDataAccessObject("users.csv");
        UserDataAccessInterface userDAO = fileUserDataAccessObject;

        // Deck DAO (uses CSV file for storage)
        DeckDataAccessInterface deckDAO =
                new FileDeckDataAccessObject("decks.csv");

        FlashcardDataAccessInterface cardDAO = new FileFlashcardDataAccessObject("flashcards.csv");

        int currentUserId = userDAO.getCurrentUserId();
        String username = userDAO.getCurrentUsername();

        // 3) construct use case components
        // TODO: other use case components

        CreateFlashcardUseCaseFactory.CreateFlashcardBundle createBundle =
                CreateFlashcardUseCaseFactory.build(cardDAO);

        // Login UC
        LoginUseCaseFactory.LoginBundle loginBundle =
                LoginUseCaseFactory.build(userDAO, viewManager);

        // Sign up UC
        SignupUseCaseFactory.SignupBundle signupBundle =
                SignupUseCaseFactory.build(userDAO, viewManager);

        // Logout UC
        LogoutUseCaseFactory.LogoutBundle logoutBundle =
                LogoutUseCaseFactory.build(viewManager, userDAO);

        // deck UC5 & 10 & 11
        DeckMenuBundle deckBundle =
                DeckManageUseCaseFactory.build(deckDAO, cardDAO, fileUserDataAccessObject);

        // study deck UC2
        StudyDeckUseCaseFactory.StudyDeckBundle studyBundle =
                StudyDeckUseCaseFactory.build(deckDAO, fileUserDataAccessObject, cardDAO);


        EditFlashcardUseCaseFactory.EditFlashcardBundle editBundle =
                EditFlashcardUseCaseFactory.build(cardDAO);

        // 4) construct Views
        // TODO: other views
        // LoginView
        LoginView loginView = new LoginView(loginBundle.loginViewModel, loginBundle.loginController, loginBundle.signupController, loginBundle.viewManager);

        // Signup View
        SignUpView signupView = new SignUpView(signupBundle.signupViewModel, signupBundle.signupController, signupBundle.viewManager);

        // LoggedInView
        LoggedInView loggedInView = new LoggedInView(
                logoutBundle.loggedInViewModel,
                deckBundle.vm,
                logoutBundle.logoutController,
                deckBundle.listController,
                deckBundle.createController,
                deckBundle.openController,
                loginBundle.viewManager
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
                editBundle.controller,
                createBundle.vm,
                userDAO.getCurrentUserId(),
                viewManager,
                username
        );

        // StudyDeckView
        StudyDeckView studyView = new StudyDeckView(studyBundle.vm, viewManager, fileUserDataAccessObject);
        studyView.setController(studyBundle.controller);

        //CreateFlashcardView
        CreateFlashcardView createFlashcardView =
                new CreateFlashcardView(createBundle.controller, createBundle.vm, viewManager);


        EditFlashcardView editFlashcardView =
                new EditFlashcardView(editBundle.vm, editBundle.controller);

        // 5) register views to ViewManager
        // notice that the name should be the same as the one
        // used in viewManager.show(name)
        // TODO: register other views
        viewManager.add("Login", loginView);
        viewManager.add("Signup", signupView);
        viewManager.add("LoggedIn", loggedInView);
        viewManager.add("DeckMenu", deckMenuView);
        viewManager.add("DeckDetail", deckDetailView);
        viewManager.add("CreateFlashcard", createFlashcardView);
        viewManager.add("EditFlashcard", editFlashcardView);
        viewManager.add("Study", studyView);

        return viewManager;
    }
}
