/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Santiago Bastidas
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends Persona{
    
    private String email;
    private String contrasenia;
    private enumRol rol;
    private enumPrograma programa;
    
    public User(){
        
    }
    public User(String nombre, String apellidos, String celular, enumPrograma programa,enumRol rol,String email,String contrasenia){
        super(nombre,apellidos,validacionCelular(celular));
        this.email = email; 
        this.contrasenia = contrasenia;
        this.rol = rol;
         this.programa = programa;
        
    }
    public User(String email, String contraseña,enumRol rol, enumPrograma programa) {
        this.email = email;
        this.contrasenia = contraseña;
        this.rol = rol;
        this.programa = programa;

    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contrasenia;
    }

    public void setContraseña(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public enumRol getRol() {
        return rol;
    }
    public void setRol(enumRol rol) {
        this.rol = rol;
    }
    public enumPrograma getPrograma(){
        return programa;
    }
    public void setPrograma(enumPrograma programa) {
        this.programa = programa;
    }
      @Override
    public String toString() {
        return "User{" + "email=" + email + ", rol=" + rol + ", programa=" + programa + '}';
    }
}
