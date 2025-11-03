/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.service;
import co.unicauca.frontendapp.access.IUserRepositorio;
import co.unicauca.frontendapp.entities.User;
import co.unicauca.frontendapp.observer.AuthObserver;
import co.unicauca.frontendapp.utility.EmailValidator;
import co.unicauca.frontendapp.utility.PasswordUtils;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private IUserRepositorio repositorio;
     private final List<AuthObserver> observers = new ArrayList<>();
 
    public Service() {
    }

    public Service(IUserRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
     // ===== Observer =====
    public void addObserver(AuthObserver o) {
        if (o != null && !observers.contains(o)) observers.add(o);
    }
    public void removeObserver(AuthObserver o) {
        observers.remove(o);
    }
    private void notifyRegistered(User u) {
        for (AuthObserver o : List.copyOf(observers)) o.onUserRegistered(u);
    }
    private void notifyRegisterFailed(String msg) {
        for (AuthObserver o : List.copyOf(observers)) o.onUserRegistrationFailed(msg);
    }
    
    
    
    public boolean registrarUsuario(User newUser){
        
        //Validar Usuario
        if((newUser == null) || (newUser.getEmail().isBlank()) || (newUser.getContraseña().isBlank())){
            System.out.println("No se puede registrar");
            notifyRegisterFailed("Datos incompletos");
            return false;
            
        }
        // Validar correo institucional
        if (!EmailValidator.esCorreoInstitucional(newUser.getEmail())) {
      
        return false;
        }
        // Validar contraseña
        
         if(!PasswordUtils.validarContrasenia(newUser.getContraseña())) {
        return false;
         }
        notifyRegistered(newUser);
        repositorio.guardar(newUser);
        return true;
    }

    public User iniciarSesion(String email, String contrasenia){
        
         if (email == null || email.isBlank() || contrasenia == null || contrasenia.isBlank()) {
            return null;
        }
        return repositorio.iniciarSesion(email, contrasenia);
    }
    
    

}
