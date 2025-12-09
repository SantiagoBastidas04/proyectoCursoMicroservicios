package co.edu.unicauca.process_management.port.out;

import co.edu.unicauca.process_management.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    boolean existsById(String email);

     User save(User user);

    Optional<User> findById(String email);
}
