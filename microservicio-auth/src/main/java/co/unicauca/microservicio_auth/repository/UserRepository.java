package co.unicauca.microservicio_auth.repository;

import co.unicauca.microservicio_auth.entity.Rol;
import co.unicauca.microservicio_auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailAndContrasenia(String email, String contraseña);
    List<User> findByRol(Rol rol);
}
