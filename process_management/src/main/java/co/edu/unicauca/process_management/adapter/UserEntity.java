package co.edu.unicauca.process_management.adapter;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios_recibidos")
@Data
    public class UserEntity {

    @Id
    private String atrEmail;
    private String atrNombre;
    private String atrApellidos;
    private String atrRol;
    private String atrCelular;

}

