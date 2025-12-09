package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.User;
import co.edu.unicauca.process_management.port.in.RegisterUserUseCase;
import co.edu.unicauca.process_management.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;

    public RegisterUserService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registrarUsuario(User user) {
        return userRepository.save(user);
    }
}
