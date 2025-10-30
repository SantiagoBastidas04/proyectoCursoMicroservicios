package co.unicauca.microservicio_auth.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Persona {
    public String nombre;
    public String apellidos;
    public String celular;

}
