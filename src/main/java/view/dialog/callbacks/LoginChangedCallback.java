package view.dialog.callbacks;

import model.network.User;

public interface LoginChangedCallback {
    void onCompleted(User user);
}
