package view.panels;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5068131147200209223L;
	
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton setButton;
    private JButton editButton;

    public LoginPanel() {
        initializeUI();
        layoutComponents();
    }

    private void initializeUI() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        setButton = new JButton("Set");
        editButton = new JButton("Edit");
        editButton.setVisible(false);  // Initially hidden
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(setButton);
        formPanel.add(editButton);
        add(formPanel, BorderLayout.CENTER);
    }

    public void updateViewForSet() {
        setButton.setVisible(false);
        editButton.setVisible(true);
    }

    public void showEditOptions() {
        setButton.setVisible(true);
        editButton.setVisible(false);
    }

    // Methods to expose user details to the controller
    public String getUsername() {
        return usernameField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }
    
    public JButton setButton() { return setButton; }
    public JButton editButton() { return editButton; }
}

