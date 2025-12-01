package view;

import interface_adapter.ViewManagerModel;
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
    private final JButton cancelButton = new JButton("Home");
    private final JProgressBar goalprogressBar = new JProgressBar(0,100);
    private final JProgressBar totalprogressBar = new JProgressBar(0,100);

    private final ProgressTrackerViewModel progressTrackerViewModel;
    private final ProgressTrackerController progressTrackerController;
    private final ViewManager viewManager;

    public ProgressTrackerView(ProgressTrackerViewModel viewModel,
                               ProgressTrackerController controller,
                               ViewManager viewManager) {
        this.progressTrackerViewModel = viewModel;
        this.progressTrackerController = controller;
        this.viewManager = viewManager;

        this.changeLearningGoalButton.addActionListener(this);
        this.cancelButton.addActionListener(this);

        final ProgressTrackerState currentState = progressTrackerViewModel.getState();

        this.goalprogressBar.setValue(currentState.getWordsMastered()/currentState.getDailyTarget());
        this.totalprogressBar.setValue(currentState.getWordsMastered()/currentState.getWordsStudied());
        this.goalprogressBar.setStringPainted(true);
        this.totalprogressBar.setStringPainted(true);

        final JLabel title = new JLabel("VocabVault Learning Goal");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel goalProgressPanel = new JPanel();
        goalProgressPanel.setLayout(new BoxLayout(goalProgressPanel, BoxLayout.X_AXIS));
        goalProgressPanel.add(new JLabel("Your Progress on Your Goal: "));
        goalProgressPanel.add(goalprogressBar);

        final JPanel totalProgressPanel = new JPanel();
        totalProgressPanel.setLayout(new BoxLayout(goalProgressPanel, BoxLayout.X_AXIS));
        totalProgressPanel.add(new JLabel("Percentage of Total Words Learned: "));
        totalProgressPanel.add(totalprogressBar);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
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
        cancelButton.addActionListener(e -> {viewManager.show("LoggedIn");
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(goalProgressPanel);
        this.add(totalProgressPanel);
        this.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
