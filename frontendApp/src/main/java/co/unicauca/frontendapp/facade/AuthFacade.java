package co.unicauca.frontendapp.facade;

import co.unicauca.frontendapp.access.UserRepositorio;
import co.unicauca.frontendapp.entities.User;
import co.unicauca.frontendapp.observer.AuthObserver;
import co.unicauca.frontendapp.service.Service;

public class AuthFacade {
    private final Service service;

    public AuthFacade() {
        var repo = new UserRepositorio(); // 
        this.service = new Service(repo); //
    }

    // API simple para la UI
    public boolean register(User u) { return service.registrarUsuario(u); }
    public User login(String email, String pass) { return service.iniciarSesion(email, pass); }

    public void addObserver(AuthObserver o) { service.addObserver(o); }
    public void removeObserver(AuthObserver o) { service.removeObserver(o); }
}
