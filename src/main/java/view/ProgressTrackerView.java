package view;

import interface_adapter.progress_tracker.ProgressTrackerViewModel;
import interface_adapter.progress_tracker.ProgressTrackerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class ProgressTrackerView extends JPanel implements PropertyChangeListener,ActionListener {
    private final String viewName = "progress tracker";

    private final JButton changeLearningGoalButton = new JButton("Change Learning Goal");
    private final JButton cancelButton = new JButton("Cancel");
    private final JProgressBar progressBar = new JProgressBar();

    private final ProgressTrackerViewModel viewModel;
    private final ProgressTrackerController controller;

    public ProgressTrackerView(ProgressTrackerViewModel viewModel, ProgressTrackerController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        this.changeLearningGoalButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
        this.progressBar.setStringPainted(true);

        final JLabel title = new JLabel("VocabVault Learning Goal");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(changeLearningGoalButton);
        buttonPanel.add(cancelButton);

        changeLearningGoalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // work on this bit
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // work on this bit
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(progressBar);
        this.add(buttonPanel);
    }
}
