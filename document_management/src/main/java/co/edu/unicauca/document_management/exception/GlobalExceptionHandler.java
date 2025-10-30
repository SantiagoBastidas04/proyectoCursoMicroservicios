package co.edu.unicauca.document_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNoRegistradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNoRegistrado(UsuarioNoRegistradoException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("error", "Usuario no registrado");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CorreosIgualesException.class)
    public ResponseEntity<Map<String, Object>> handleCorreosIguales(CorreosIgualesException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("error", "Emails duplicados");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("error", "Error interno del servidor");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(MaximoIntentosException.class)
//    public ResponseEntity<Map<String, Object>> handleMaximoIntentos(MaximoIntentosException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("error", "Máximo de intentos alcanzado");
//        body.put("message", ex.getMessage());
//        body.put("status", HttpStatus.BAD_REQUEST.value());
//        body.put("timestamp", LocalDateTime.now());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
//    }
}
