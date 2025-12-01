package view;

import data_access.LearningGoalDataAccess;
import interface_adapter.learning_goal.LearningGoalController;
import interface_adapter.learning_goal.LearningGoalViewModel;

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
    private final LearningGoalViewModel viewModel;
    private final LearningGoalController controller;

    public SetLearningGoalView(LearningGoalViewModel viewModel, LearningGoalController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

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
                // work on this bit
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // work on this bit
            }
        });

        learningGoalInputField.getDocument().addDocumentListener(new DocumentListener() {
            public void documentListenerHelper() {
                // work on this bit
            }

            @Override
            public void insertUpdate(DocumentEvent e) {documentListenerHelper();}

            @Override
            public void removeUpdate(DocumentEvent e) {documentListenerHelper();}

            @Override
            public void changedUpdate(DocumentEvent e) {documentListenerHelper();}

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
