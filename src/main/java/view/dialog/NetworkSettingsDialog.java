package view.dialog;

import javax.swing.*;

import model.network.NetworkConfig;
import model.network.User;
import view.dialog.callbacks.NetworkChangedCallback;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkSettingsDialog extends JDialog {
    private static final long serialVersionUID = -5756829885607010864L;
    private JTextField hostField;
    private JTextField userField;
    private JTextField passwordField;
    private JTextField apiLimitField;
    private NetworkChangedCallback callback;

    public NetworkSettingsDialog(Frame parent, NetworkConfig existingConfig, NetworkChangedCallback callback) {
        super(parent, "Network Settings", true);
        this.callback = callback;
        
        setResizable(false);
        setPreferredSize(new Dimension(450, 250));
        
        // Initialize UI components
        hostField = new JTextField(20);
        userField = new JTextField(20);
        passwordField = new JPasswordField(20);
        apiLimitField = new JTextField(5);

        // Populate fields with existing config if available
        if (existingConfig != null) {
            hostField.setText(existingConfig.getHost() != null ? existingConfig.getHost() : "");
            userField.setText(existingConfig.getUser() != null && existingConfig.getUser().username() != null ? existingConfig.getUser().username() : "");
            passwordField.setText(existingConfig.getUser() != null && existingConfig.getUser().password() != null ? existingConfig.getUser().password() : "");
            apiLimitField.setText(existingConfig.getMaxRequestsPerMinute() > 0 ? String.valueOf(existingConfig.getMaxRequestsPerMinute()) : "");
        }

        // Setup layout with padding
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); 

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add Host URL field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Host URL:"), gbc);
        gbc.gridx = 1;
        panel.add(hostField, gbc);

        // Add User field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        // Add Password field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Add API Limit field
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("API Limit (per minute):"), gbc);
        gbc.gridx = 1;
        panel.add(apiLimitField, gbc);

        // Add buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        
        getRootPane().setDefaultButton(addButton);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private void onAdd() {
        String host = hostField.getText().trim();
        String username = userField.getText().trim();
        String password = passwordField.getText().trim();
        String apiLimitText = apiLimitField.getText().trim();

        // Validate inputs
        if (host.isEmpty() || username.isEmpty() || password.isEmpty() || !isValidApiLimit(apiLimitText)) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out correctly and API limit must be a positive integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int apiLimit = Integer.parseInt(apiLimitText);

        // Create or update NetworkConfig
        User user = new User(username, password);
        NetworkConfig newConfig = new NetworkConfig(user, host, apiLimit);

        // Trigger callback
        if (callback != null) {
            callback.onNetworkChanged(newConfig);
        }

        // Close dialog
        dispose();
    }

    private boolean isValidApiLimit(String apiLimitText) {
        try {
            int apiLimit = Integer.parseInt(apiLimitText);
            return apiLimit > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


