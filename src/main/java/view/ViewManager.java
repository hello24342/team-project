package view;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.text.View;
import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private final JFrame frame;
    // A mapping from view names to their corresponding JPanel instances
    private final Map<String, JPanel> views;
    private String current;
    private final ViewManagerModel viewManagerModel;

    public ViewManager(JFrame frame) {
        this.frame = frame;
        this.views = new HashMap<>();
        this.viewManagerModel = new ViewManagerModel();
        // Set default JFrame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }

    // Add a new view to the manager
    public void add(String name, JPanel panel) {
        views.put(name, panel);
    }

    // Show the view corresponding to the given name
    public void show(String name) {
        JPanel panel = views.get(name);
        // Throw an exception if the view does not exist
        if (panel == null) {
            throw new IllegalArgumentException("No view: " + name);
        }
        // Set the content pane of the JFrame to the selected view
        frame.setContentPane(panel);
        // Refresh the JFrame to display the new view
        frame.revalidate();
        frame.repaint();
        // Update the current view name
        current = name;
    }

    public String current() {
        return current;
    }

    public ViewManagerModel getViewManagerModel() {
        return this.viewManagerModel;
    }
}

