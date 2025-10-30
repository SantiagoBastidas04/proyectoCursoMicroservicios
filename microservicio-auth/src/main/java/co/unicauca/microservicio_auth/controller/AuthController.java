package co.unicauca.microservicio_auth.controller;

import co.unicauca.microservicio_auth.entity.User;
import co.unicauca.microservicio_auth.service.ServiceUsers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/auth")

public class AuthController {
    /*@Authowired inyeccion de dependedincias*/
    private final ServiceUsers serviceUsers;

    public AuthController(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    /**
     * Registro de usuario.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody User newUser) {
        boolean registrado = serviceUsers.registrarUsuario(newUser);
        if (!registrado) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "No se pudo registrar el usuario. Verifique los datos."));
        }
        return ResponseEntity.ok(Map.of("message", "Usuario registrado correctamente"));
    }

    /**
     * Inicio de sesión.
     *
     */
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestParam String email, @RequestParam String contrasenia) {
        User usuario = serviceUsers.iniciarSesion(email, contrasenia);
        if (usuario == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Credenciales incorrectas o usuario no encontrado"));
        }
        return ResponseEntity.ok(Map.of(
                "message", "Inicio de sesión exitoso",
                "usuario", usuario
        ));
    }
}
