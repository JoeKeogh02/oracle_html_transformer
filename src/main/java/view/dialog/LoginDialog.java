package view.dialog;

import javax.swing.*;

import model.network.User;
import view.dialog.callbacks.LoginChangedCallback;

import java.awt.*;

public class LoginDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton saveButton, cancelButton;

    private LoginChangedCallback callback;

    public LoginDialog(Frame owner, User existingUser, LoginChangedCallback callback) {
        super(owner, "User Login", true);
        this.callback = callback;

        setSize(350, 200);
        setLayout(new BorderLayout());

        // Panel with padding for the input components
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the inputPanel

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        if (existingUser != null) {
            usernameField.setText(existingUser.username());
            passwordField.setText(existingUser.password());
        }

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 0, 10)); // Vertical padding between rows
        gridPanel.add(new JLabel("Username:"));
        gridPanel.add(usernameField);
        gridPanel.add(new JLabel("Password:"));
        gridPanel.add(passwordField);

        inputPanel.add(gridPanel, BorderLayout.NORTH);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add vertical padding around the button panel
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Set saveButton as the default button
        getRootPane().setDefaultButton(saveButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setupActions();
    }

    private void setupActions() {
        saveButton.addActionListener(e -> {
            if (validateInputs()) {
                User user = new User(usernameField.getText(), new String(passwordField.getPassword()));
                callback.onCompleted(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Both fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    private boolean validateInputs() {
        return !usernameField.getText().isEmpty() && passwordField.getPassword().length > 0;
    }
}
