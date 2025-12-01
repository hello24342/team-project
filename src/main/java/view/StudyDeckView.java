package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.study_deck.StudyDeckController;
import interface_adapter.study_deck.StudyDeckState;
import interface_adapter.study_deck.StudyDeckViewModel;

public class StudyDeckView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "study deck";

    private final JLabel cardLabel;
    private final JLabel deckTitleLabel;

    private final JButton flipButton;
    private final JButton nextButton;
    private final JButton previousButton;
    private final JButton knowButton;
    private final JButton dontKnowButton;

    private final StudyDeckViewModel viewModel;
    private StudyDeckController controller;

    public StudyDeckView(StudyDeckViewModel viewModel) {

        cardLabel = new JLabel();
        deckTitleLabel = new JLabel();

        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        deckTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cardLabel.setOpaque(true);
        cardLabel.setBackground(Color.WHITE);
        cardLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)));

        flipButton = new JButton("Flip Card");
        nextButton = new JButton("→");
        previousButton = new JButton("←");
        knowButton = new JButton("Know it");
        dontKnowButton = new JButton("Don't know it");

        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        updateView(this.viewModel.getState());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(previousButton);
        buttonPanel.add(flipButton);
        buttonPanel.add(nextButton);

        flipButton.addActionListener(this);
        nextButton.addActionListener(this);
        previousButton.addActionListener(this);
        knowButton.addActionListener(this);
        dontKnowButton.addActionListener(this);

        this.setLayout(new BorderLayout());
        this.add(deckTitleLabel, BorderLayout.NORTH);
        this.add(cardLabel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(knowButton, BorderLayout.WEST);
        this.add(dontKnowButton, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller == null) {
            return;
        }

        StudyDeckState currentState = viewModel.getState();
        if (e.getSource() == flipButton) {
            controller.flipCard(currentState.getDeckId(), currentState.getCardIndex(),
                    currentState.isShowingFront(), currentState.startsWithSource());
        }
        else if (e.getSource() == nextButton) {
            controller.nextCard(currentState.getDeckId(), currentState.getCardIndex());
        }
        else if (e.getSource() == previousButton) {
            controller.previousCard(currentState.getDeckId(), currentState.getCardIndex());
        } else if (e.getSource() == knowButton) {
            controller.markKnown(currentState.getUserId(), currentState.getDeckId(), currentState.getCardIndex(), currentState.getUsername());
        } else if (e.getSource() == dontKnowButton) {
            controller.markUnknown(currentState.getUserId(), currentState.getDeckId(), currentState.getCardIndex());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (viewModel.ERROR_PROPERTY.equals(evt.getPropertyName())) {
            String error = viewModel.getErrorMessage();
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        final StudyDeckState state = (StudyDeckState) evt.getNewValue();
        updateView(state);
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
