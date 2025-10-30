package co.edu.unicauca.document_management.infra.DTO;

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
