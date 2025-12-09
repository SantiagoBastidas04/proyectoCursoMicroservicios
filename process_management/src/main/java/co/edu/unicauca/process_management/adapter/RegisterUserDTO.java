package co.edu.unicauca.process_management.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {
    private String email;
    private String nombre;
    private String apellidos;
    private String celular;
    private String rol;
}
