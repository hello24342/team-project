package interface_adapter.deck;

import use_case.UserDataAccessInterface;
import use_case.deck.create_deck.CreateDeckInputBoundary;
import use_case.deck.create_deck.CreateDeckInputData;

public class CreateDeckController {
    private final CreateDeckInputBoundary interactor;
    private final UserDataAccessInterface userDAO;  // Add this

    public CreateDeckController(CreateDeckInputBoundary interactor,
                                UserDataAccessInterface userDAO) {  // Change constructor
        this.interactor = interactor;
        this.userDAO = userDAO;
    }

    public void onCreate(String title) {
        int currentUserId = userDAO.getCurrentUserId();  // Get dynamically
        System.out.println("Creating deck for user ID: " + currentUserId);

        CreateDeckInputData in = new CreateDeckInputData(currentUserId, title);
        interactor.createDeck(in);
    }
}