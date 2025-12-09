package co.edu.unicauca.process_management.adapter.repository;

import co.edu.unicauca.process_management.adapter.UserEntity;
import co.edu.unicauca.process_management.domain.model.User;
import co.edu.unicauca.process_management.port.out.UserRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpa;

    public UserRepositoryAdapter(JpaUserRepository jpa) {
        this.jpa = jpa;
    }


    @Override
    public boolean existsById(String email) {
        return jpa.existsById(email);
    }

    @Override
    public User save(User user) {
        UserEntity e = new UserEntity();
        e.setAtrEmail(user.getAtrEmail());
        e.setAtrNombre(user.getAtrNombre());
        e.setAtrApellidos(user.getAtrApellidos());
        e.setAtrRol(user.getAtrRol());
        e.setAtrCelular(user.getAtrCelular());

        UserEntity saved = jpa.save(e);

        User domain = new User(saved.getAtrEmail(), saved.getAtrNombre(), saved.getAtrApellidos(),
                saved.getAtrRol(), saved.getAtrCelular());
        return domain;
    }

    @Override
    public Optional<User> findById(String email) {
        return jpa.findById(email).map(e -> new User(e.getAtrEmail(), e.getAtrNombre(), e.getAtrApellidos(),
                e.getAtrRol(), e.getAtrCelular()));
    }
}
