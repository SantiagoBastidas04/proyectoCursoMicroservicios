package co.unicauca.frontendapp.observer;

public interface AuthSubject {
    void addObserver(AuthObserver o);
    void removeObserver(AuthObserver o);
}
