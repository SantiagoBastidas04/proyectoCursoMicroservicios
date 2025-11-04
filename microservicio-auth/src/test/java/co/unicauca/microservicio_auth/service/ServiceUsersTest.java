package co.unicauca.microservicio_auth.service;

import co.unicauca.microservicio_auth.entity.User;
import co.unicauca.microservicio_auth.messaging.SubmissionPublisher;
import co.unicauca.microservicio_auth.repository.UserRepository;
import co.unicauca.microservicio_auth.utility.EmailValidator;
import co.unicauca.microservicio_auth.utility.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceUsersTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubmissionPublisher submissionPublisher;

    @InjectMocks
    private ServiceUsers serviceUsers;

    private User validUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validUser = new User();
        validUser.setEmail("juan@unicauca.edu.co");
        validUser.setContrasenia("Aa123@");
    }

    // ---------- registrarUsuario() ----------

    @Test
    void registrarUsuario_DeberiaRetornarFalse_SiUsuarioEsNull() {
        assertFalse(serviceUsers.registrarUsuario(null));
        verifyNoInteractions(userRepository, submissionPublisher);
    }

    @Test
    void registrarUsuario_DeberiaRetornarFalse_SiCamposSonVacios() {
        User user = new User();
        user.setEmail("");
        user.setContrasenia("");
        assertFalse(serviceUsers.registrarUsuario(user));
        verifyNoInteractions(userRepository, submissionPublisher);
    }

    @Test
    void registrarUsuario_DeberiaRetornarFalse_SiCorreoNoInstitucional() {
        User user = new User();
        user.setEmail("juan@gmail.com");
        user.setContrasenia("Aa123@");
        assertFalse(serviceUsers.registrarUsuario(user));
        verifyNoInteractions(userRepository, submissionPublisher);
    }

    @Test
    void registrarUsuario_DeberiaRetornarFalse_SiContraseniaInvalida() {
        // Mock correo válido
        validUser.setContrasenia("abc"); // no cumple la política
        assertFalse(serviceUsers.registrarUsuario(validUser));
        verifyNoInteractions(userRepository, submissionPublisher);
    }

    @Test
    void registrarUsuario_DeberiaGuardarUsuarioYPublicarEvento_SiDatosValidos() {
        // Espía de la utilidad de cifrado
        String cifrada = PasswordUtils.cifrarSHA256(validUser.getContrasenia());
        doNothing().when(submissionPublisher).enviarUsuario(any(User.class));
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        boolean resultado = serviceUsers.registrarUsuario(validUser);

        assertTrue(resultado);
        verify(submissionPublisher, times(1)).enviarUsuario(any(User.class));
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(cifrada, validUser.getContrasenia());
    }

    // ---------- iniciarSesion() ----------

    @Test
    void iniciarSesion_DeberiaRetornarNull_SiParametrosInvalidos() {
        assertNull(serviceUsers.iniciarSesion("", ""));
        assertNull(serviceUsers.iniciarSesion(null, "abc"));
        assertNull(serviceUsers.iniciarSesion("correo@unicauca.edu.co", null));
        verifyNoInteractions(userRepository);
    }

    @Test
    void iniciarSesion_DeberiaRetornarUsuario_SiCredencialesValidas() {
        String email = "juan@unicauca.edu.co";
        String contrasenia = "Aa123@";
        String contraseniaCifrada = PasswordUtils.cifrarSHA256(contrasenia);
        User user = new User();
        user.setEmail(email);
        user.setContrasenia(contraseniaCifrada);

        when(userRepository.findByEmailAndContrasenia(email, contraseniaCifrada))
                .thenReturn(Optional.of(user));

        User result = serviceUsers.iniciarSesion(email, contrasenia);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1))
                .findByEmailAndContrasenia(email, contraseniaCifrada);
    }

    @Test
    void iniciarSesion_DeberiaRetornarNull_SiNoExisteUsuario() {
        String email = "noexiste@unicauca.edu.co";
        String contrasenia = "Aa123@";
        String contraseniaCifrada = PasswordUtils.cifrarSHA256(contrasenia);

        when(userRepository.findByEmailAndContrasenia(email, contraseniaCifrada))
                .thenReturn(Optional.empty());

        User result = serviceUsers.iniciarSesion(email, contrasenia);

        assertNull(result);
    }
}
