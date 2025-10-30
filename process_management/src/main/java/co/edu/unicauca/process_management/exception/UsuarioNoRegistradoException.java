package co.edu.unicauca.process_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsuarioNoRegistradoException extends RuntimeException {
    public UsuarioNoRegistradoException(String mensaje) {
        super(mensaje);
    }
}
