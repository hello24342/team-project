package view;

import data_access.LearningGoalDataAccess;
import interface_adapter.ViewManagerModel;
import interface_adapter.learning_goal.LearningGoalController;
import interface_adapter.learning_goal.LearningGoalViewModel;
import interface_adapter.learning_goal.LearningGoalState;
import view.ViewManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is setting their learning goal
 */
public class SetLearningGoalView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "set learning goal";
    private final JTextField learningGoalInputField = new JTextField();

    private final JButton setLearningGoalButton = new JButton("Set Learning Goal");
    private final JButton cancelButton = new JButton("Cancel");
    private final LearningGoalViewModel learningGoalViewModel;
    private final LearningGoalController learningGoalController;
    private final ViewManager viewManager;

    public SetLearningGoalView(LearningGoalViewModel viewModel,
                               LearningGoalController controller,
                               ViewManager viewManager) {
        this.learningGoalViewModel = viewModel;
        this.learningGoalController = controller;
        this.viewManager = viewManager;

        this.setLearningGoalButton.addActionListener(this);
        this.cancelButton.addActionListener(this);

        final JLabel title = new JLabel("VocabVault Learning Goal");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel learningGoalLabel = new JLabel("Every week I want to master:");
        learningGoalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        final LabelTextPanel learningGoal =  new LabelTextPanel(
                new JLabel("Learning Goal"), learningGoalInputField);
        final JLabel learningGoalLabel2 = new JLabel("words!");
        learningGoalLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(setLearningGoalButton);
        buttonPanel.add(cancelButton);

        setLearningGoalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(cancelButton)) {
                    final LearningGoalState currrentState = learningGoalViewModel.getState();

                    learningGoalController.execute(currrentState.getUserId() ,currrentState.getLearningGoal());
                }
            }
        });
        cancelButton.addActionListener(e -> {viewManager.show("Progress"); // change this if needed
        });

        learningGoalInputField.getDocument().addDocumentListener(new DocumentListener() {
            public void documentListenerHelper() {
                final LearningGoalState currentState = learningGoalViewModel.getState();
                int learningGoalInt = Integer.parseInt(learningGoalInputField.getText());
                currentState.setLearningGoal(learningGoalInt);
                learningGoalViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(learningGoalLabel);
        this.add(learningGoal);
        this.add(learningGoalLabel2);
        this.add(buttonPanel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }

}
