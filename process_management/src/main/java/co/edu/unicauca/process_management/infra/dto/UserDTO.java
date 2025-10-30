package co.edu.unicauca.process_management.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String nombre;
    private String apellidos;
    private String celular;
    private String rol;
}
