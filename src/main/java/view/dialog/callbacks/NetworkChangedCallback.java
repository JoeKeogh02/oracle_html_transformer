package view.dialog.callbacks;

import model.network.NetworkConfig;

public interface NetworkChangedCallback {
    /**
     * This method will be called when the network configuration is changed.
     *
     * @param newConfig The updated NetworkConfig object.
     */
    void onNetworkChanged(NetworkConfig newConfig);
}

