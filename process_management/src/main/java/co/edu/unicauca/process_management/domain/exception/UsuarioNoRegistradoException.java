package co.edu.unicauca.process_management.domain.exception;

;


public class UsuarioNoRegistradoException extends RuntimeException {
    public UsuarioNoRegistradoException(String mensaje) {
        super(mensaje);
    }
}
