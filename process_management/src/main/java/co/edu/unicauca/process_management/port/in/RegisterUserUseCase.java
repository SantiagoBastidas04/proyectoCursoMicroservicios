package co.edu.unicauca.process_management.port.in;

import co.edu.unicauca.process_management.domain.model.User;

public interface RegisterUserUseCase {
    User registrarUsuario(User user);
}
