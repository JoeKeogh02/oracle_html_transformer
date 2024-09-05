package controller;

import data.repositories.IOracleRepository;
import model.network.NetworkConfig;
import model.network.User;
import view.dialog.NetworkSettingsDialog;

/**
 * The NetworkConfigController class manages the network configuration for the application.
 * It interacts with the IOracleRepository to get and set network related settings such as the host,
 * user, and API rate limits.
 */
public class NetworkConfigController {
    private IOracleRepository repository;

    /**
     * Constructs a new NetworkConfigController with the provided repository.
     *
     * @param repository The repository where network configuration settings are stored and retrieved.
     */
    public NetworkConfigController(IOracleRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves the current NetworkConfig from the repository.
     *
     * @return The current NetworkConfig object.
     */
    public NetworkConfig getNetworkConfig() {
        return repository.getNetworkConfig();
    }

    /**
     * Sets a new NetworkConfig in the repository.
     *
     * @param config The new network configuration to set.
     */
    public void setNetworkConfig(NetworkConfig config) {
        repository.setNetworkConfig(config);
    }

    /**
     * Retrieves the current host URL from the network configuration.
     *
     * @return The host URL as a String.
     */
    public String getHost() {
        return repository.getNetworkConfig().getHost();
    }

    /**
     * Updates the host URL in the network configuration.
     *
     * @param host The new host URL to set.
     */
    public void setHost(String host) {
        NetworkConfig config = repository.getNetworkConfig();
        repository.setNetworkConfig(new NetworkConfig(config.getUser(), host, config.getMaxRequestsPerMinute()));
    }

    /**
     * Retrieves the current User from the network configuration.
     *
     * @return The User object containing the current user credentials.
     */
    public User getUser() {
        return repository.getNetworkConfig().getUser();
    }

    /**
     * Updates the user credentials in the network configuration.
     *
     * @param user The new user credentials to set.
     */
    public void setUser(User user) {
        NetworkConfig config = repository.getNetworkConfig();
        repository.setNetworkConfig(new NetworkConfig(user, config.getHost(), config.getMaxRequestsPerMinute()));
    }

    /**
     * Retrieves the current maximum number of API requests allowed per minute.
     *
     * @return The maximum number of requests per minute as an int.
     */
    public int getMaxRequestsPerMinute() {
        return repository.getNetworkConfig().getMaxRequestsPerMinute();
    }

    /**
     * Updates the maximum number of API requests allowed per minute in the network configuration.
     *
     * @param maxRequestsPerMinute The new maximum number of requests per minute to set.
     */
    public void setMaxRequestsPerMinute(int maxRequestsPerMinute) {
        NetworkConfig config = repository.getNetworkConfig();
        repository.setNetworkConfig(new NetworkConfig(config.getUser(), config.getHost(), maxRequestsPerMinute));
    }

    /**
     * Opens a dialog window to allow the user to edit the network configuration settings.
     * The current settings are passed to the dialog, and any changes made by the user
     * are updated in the repository.
     */
    public void openNetworkDialog() {
        NetworkConfig config = repository.getNetworkConfig();

        // Open the network settings dialog and update the configuration 
        NetworkSettingsDialog dialog = new NetworkSettingsDialog(null, config, (c) -> repository.setNetworkConfig(c));
        dialog.setVisible(true);
    }
}

