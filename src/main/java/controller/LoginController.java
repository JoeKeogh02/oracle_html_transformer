package controller;

import data.repositories.IOracleRepository;
import model.network.User;
import view.dialog.LoginDialog;
import view.panels.LoginPanel;

/**
 * The {@code LoginController} class is responsible for handling the interactions
 * between the LoginPanel and the IOracleRepository. It manages the user login
 * process, setting user credentials in the repository, and providing options to edit the user's details.
 */
public class LoginController {
    final private IOracleRepository oracleRepository;
    private LoginPanel loginPanel;

    public LoginController(LoginPanel loginPanel, IOracleRepository oracleRepository) {
        this.loginPanel = loginPanel;
        this.oracleRepository = oracleRepository;
        addViewListeners();
    }

    /**
     * Adds action listeners to the buttons on the {@link LoginPanel} to handle user interactions.
     * This method wires up the view's button events to the controller's methods.
     */
    private void addViewListeners() {
        loginPanel.setButton().addActionListener(e -> handleSetUser());
        loginPanel.editButton().addActionListener(e -> handleEditUser());
    }

    /**
     * Handles the event where the user submits their login credentials.
     * It retrieves the username and password from the LoginPanel, creates a User object,
     * and sets the user in the IOracleRepository.
     */
    private void handleSetUser() {
        String username = loginPanel.getUsername();
        char[] password = loginPanel.getPassword();
        User user = new User(username, new String(password)); // Assuming User constructor takes username and password
        oracleRepository.setUser(user);
        System.out.println("User set: " + username);
        // Optionally update view state
    }

    /**
     * Handles the event where the user chooses to edit their credentials.
     * It updates the view to display options for editing the user information.
     */
    private void handleEditUser() {
        System.out.println("Editing user settings");
        loginPanel.showEditOptions();  // Update view to reflect edit mode
    }

    /**
     * Opens the login dialog to allow the user to input or edit their credentials.
     * The current user information is fetched from the IOracleRepository and passed to the dialog.
     * Once the dialog is completed, the user is updated in the repository.
     */
    public void openLoginDialog() {
        User user = oracleRepository.getUser();
        
        // Open the login dialog with the current user details and update the repository when done
        LoginDialog dialog = new LoginDialog(null, user, (u) -> oracleRepository.setUser(u));
        dialog.setVisible(true);
    }
}

