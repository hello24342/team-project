package view;

import interface_adapter.study_deck.StudyDeckController;
import interface_adapter.study_deck.StudyDeckState;
import interface_adapter.study_deck.StudyDeckViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StudyDeckView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "study deck";

    private final JLabel cardLabel;
    private JLabel deckTitleLabel;

    private final JButton flipButton;
    private final JButton nextButton;
    private final JButton previousButton;
    private final JButton knowButton;
    private final JButton dontKnowButton;

    private final StudyDeckViewModel viewModel;
    private StudyDeckController controller = null;

    public StudyDeckView(StudyDeckViewModel vm) {

        cardLabel = new JLabel();
        deckTitleLabel = new JLabel();
        flipButton = new JButton("Flip Card");
        nextButton = new JButton("→");
        previousButton = new JButton("←");
        knowButton = new JButton("Know it");
        dontKnowButton = new JButton("Don't know it");

        this.viewModel = vm;
        this.viewModel.addPropertyChangeListener(this);


        // Add buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.add(previousButton);
        buttonPanel.add(flipButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(knowButton);
        buttonPanel.add(dontKnowButton);

        // Add action listeners
        flipButton.addActionListener(this);
        nextButton.addActionListener(this);
        previousButton.addActionListener(this);
        knowButton.addActionListener(this);
        dontKnowButton.addActionListener(this);

        // Add panels to main view
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(deckTitleLabel);
        this.add(cardLabel);
        this.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == flipButton) {
            controller.flipCard();
        } else if (e.getSource() == nextButton) {
            controller.nextCard();
        } else if (e.getSource() == previousButton) {
            controller.previousCard();
        } else if (e.getSource() == knowButton) {
            controller.markKnown();
        } else if (e.getSource() == dontKnowButton) {
            controller.markUnknown();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final StudyDeckState state = (StudyDeckState) evt.getNewValue();
    }

    private void updateView(StudyDeckState state) {
        cardLabel.setText(state.getCardText());
        deckTitleLabel.setText(state.getDeckName());
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(StudyDeckController controller) {
        this.controller = controller;
    }
}
