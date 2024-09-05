package view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    private static final long serialVersionUID = 8572143124845836804L;
    private JButton runStopButton, uploadButton, addTransformationButton, userButton, networkButton;

    public ControlPanel() {
        runStopButton = new JButton("Run");
        uploadButton = new JButton("Upload");
        addTransformationButton = new JButton("Add Transformation");
        userButton = new JButton("User");
        networkButton = new JButton("Network");

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(runStopButton);
        add(uploadButton);
        add(addTransformationButton);
        // add(userButton);
        add(networkButton);

        runStopButton.addActionListener(this::toggleRunStop);
        uploadButton.addActionListener(this::handleUpload);
        addTransformationButton.addActionListener(this::handleAddTransformation);
        userButton.addActionListener(this::handleUserSettings);
        networkButton.addActionListener(this::handleNetworkSettings);
    }

    private void toggleRunStop(ActionEvent e) {
        boolean newRunningState = !isRunning();
        firePropertyChange("runStopToggle", isRunning(), newRunningState);
        setRunning(newRunningState);
    }

    private void handleUpload(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            firePropertyChange("fileUploaded", null, fileChooser.getSelectedFile());
        }
    }

    private void handleAddTransformation(ActionEvent e) {
        firePropertyChange("addTransformation", null, null);
    }

    private void handleUserSettings(ActionEvent e) {
        firePropertyChange("userSettings", null, null);
    }

    private void handleNetworkSettings(ActionEvent e) {
        firePropertyChange("networkSettings", null, null);
    }

    public void setRunning(boolean running) {
        runStopButton.setText(running ? "Running..." : "Run");
        setRunStopButtonEnabled(!running); // Disable button when running
    }

    public boolean isRunning() {
        return runStopButton.getText().equals("Running...");
    }

    public void setRunStopButtonEnabled(boolean enabled) {
        runStopButton.setEnabled(enabled); // Enable or disable the button
    }
}


