package co.unicauca.frontendapp.observer;

import co.unicauca.frontendapp.entities.User;

public interface AuthObserver {
    void onUserRegistered(User user);
    void onUserRegistrationFailed(String message);
}
