package view;

import interface_adapter.flashcard.CreateFlashcardController;
import use_case.flashcard_create.CreateFlashcardInputData;
import use_case.flashcard_create.CreateFlashcardOutputData;

import javax.swing.*;
import java.awt.*;

public class CreateFlashcardView extends JFrame {

    private final CreateFlashcardController controller;

    private final JTextField sourceWordField;
    private final JTextField sourceLangField;
    private final JTextField targetLangField;
    private final JTextField deckIdField;
    private final JTextArea outputArea;

    public CreateFlashcardView(CreateFlashcardController controller) {
        this.controller = controller;

        setTitle("Create Flashcard");
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Source word:"));
        sourceWordField = new JTextField();
        inputPanel.add(sourceWordField);

        inputPanel.add(new JLabel("Source language code:"));
        sourceLangField = new JTextField();
        inputPanel.add(sourceLangField);

        inputPanel.add(new JLabel("Target language code:"));
        targetLangField = new JTextField();
        inputPanel.add(targetLangField);

        inputPanel.add(new JLabel("Deck ID (optional):"));
        deckIdField = new JTextField();
        inputPanel.add(deckIdField);

        JButton createButton = new JButton("Create Flashcard");
        inputPanel.add(createButton);

        add(inputPanel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Button action
        createButton.addActionListener(e -> createFlashcard());
    }

    private void createFlashcard() {
        String sourceWord = sourceWordField.getText();
        String sourceLang = sourceLangField.getText();
        String targetLang = targetLangField.getText();
        String deckId = deckIdField.getText();

        CreateFlashcardInputData request = new CreateFlashcardInputData(
                sourceWord,
                sourceLang,
                targetLang,
                deckId.isEmpty() ? null : deckId
        );

        try {
            CreateFlashcardOutputData response = controller.createFlashcard(request);
            outputArea.setText("Flashcard created!\n"
                    + "Source word: " + response.getSourceWord() + "\n"
                    + "Translated word: " + response.getTargetWord() + "\n"
                    + "Deck IDs: " + response.getDeckIds());
        } catch (Exception ex) {
            outputArea.setText("Error creating flashcard: " + ex.getMessage());
        }
    }
}
