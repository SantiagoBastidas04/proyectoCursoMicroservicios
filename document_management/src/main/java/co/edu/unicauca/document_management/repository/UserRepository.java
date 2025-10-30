package co.edu.unicauca.document_management.repository;

import co.edu.unicauca.document_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

