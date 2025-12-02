package co.unicauca.microservicio_auth.service;

import co.unicauca.microservicio_auth.entity.Rol;
import co.unicauca.microservicio_auth.entity.User;
import co.unicauca.microservicio_auth.messaging.SubmissionPublisher;
import co.unicauca.microservicio_auth.repository.UserRepository;
import co.unicauca.microservicio_auth.utility.EmailValidator;
import co.unicauca.microservicio_auth.utility.PasswordUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceUsers {
    private UserRepository userRepository;
    private SubmissionPublisher submissionPublisher;

    public ServiceUsers(UserRepository userRepository, SubmissionPublisher submissionPublisher) {
        this.userRepository = userRepository;
        this.submissionPublisher = submissionPublisher;
    }

    public boolean registrarUsuario(User newUser){
        //Validar Usuario
        if((newUser == null) || (newUser.getEmail().isBlank()) || (newUser.getContrasenia().isBlank())){
            System.out.println("No se puede registrar");
            return false;

        }
        // Validar correo institucional
        if (!EmailValidator.esCorreoInstitucional(newUser.getEmail())) {
            return false;
        }
        // Validar contraseña
        if(!PasswordUtils.validarContrasenia(newUser.getContrasenia())) {

            return false;
        }
        String contraseniaCifrada = PasswordUtils.cifrarSHA256(newUser.getContrasenia());
        newUser.setContrasenia(contraseniaCifrada);
        submissionPublisher.enviarUsuario(newUser);
        userRepository.save(newUser);
        return true;
    }

    public User iniciarSesion(String email, String contrasenia){

        if (email == null || email.isBlank() || contrasenia == null || contrasenia.isBlank()) {
            return null;
        }
        // Cifrar la contraseña ingresada antes de enviar al repositorio
        String contraseniaCifrada = PasswordUtils.cifrarSHA256(contrasenia);

        return userRepository.findByEmailAndContrasenia(email, contraseniaCifrada).orElse(null);
    }
    public List<User> listarUsuariosPorRol(Rol rol){
        return userRepository.findByRol(rol);
    }
}
