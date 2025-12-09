package co.edu.unicauca.process_management.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class User {

    private String atrEmail;
    private String atrNombre;
    private String atrApellidos;
    private String atrRol;
    private String atrCelular;

    public User(String atrEmail, String atrNombre, String atrApellidos, String atrRol, String atrCelular) {
        this.atrEmail = atrEmail;
        this.atrNombre = atrNombre;
        this.atrApellidos = atrApellidos;
        this.atrRol = atrRol;
        this.atrCelular = atrCelular;
    }

    public String getAtrEmail() {
        return atrEmail;
    }

    public String getAtrCelular() {
        return atrCelular;
    }

    public String getAtrApellidos() {
        return atrApellidos;
    }

    public String getAtrRol() {
        return atrRol;
    }

    public String getAtrNombre() {
        return atrNombre;
    }
}
