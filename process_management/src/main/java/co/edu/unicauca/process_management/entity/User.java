package co.edu.unicauca.process_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios_recibidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String atrEmail;
    private String atrNombre;
    private String atrApellidos;
    private String atrRol;
    private String atrCelular;

}
