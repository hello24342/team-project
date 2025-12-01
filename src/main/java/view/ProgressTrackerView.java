package view;

import interface_adapter.progress_tracker.ProgressTrackerState;
import interface_adapter.progress_tracker.ProgressTrackerViewModel;
import interface_adapter.progress_tracker.ProgressTrackerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProgressTrackerView extends JPanel implements PropertyChangeListener, ActionListener {
    private final String viewName = "progress tracker";

    private final JButton changeLearningGoalButton = new JButton("Change Learning Goal");
    private final JButton cancelButton = new JButton("Cancel");
    private final JProgressBar progressBar = new JProgressBar(0,100);

    private final ProgressTrackerViewModel progressTrackerViewModel;
    private final ProgressTrackerController progressTrackerController;

    public ProgressTrackerView(ProgressTrackerViewModel viewModel, ProgressTrackerController controller) {
        this.progressTrackerViewModel = viewModel;
        this.progressTrackerController = controller;

        this.changeLearningGoalButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
        this.progressBar.setValue(0); // set this to be the words mastered/wordsstudied
        this.progressBar.setStringPainted(true);

        final JLabel title = new JLabel("VocabVault Learning Goal");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(changeLearningGoalButton);
        buttonPanel.add(cancelButton);

        changeLearningGoalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(changeLearningGoalButton)) {
                    final ProgressTrackerState currentState = progressTrackerViewModel.getState();

                    progressTrackerController.execute(currentState.getId(),
                            currentState.getUserId(),
                            currentState.getWordsMastered(),
                            currentState.getWordsMastered());
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // switch screen to main view
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(progressBar);
        this.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
