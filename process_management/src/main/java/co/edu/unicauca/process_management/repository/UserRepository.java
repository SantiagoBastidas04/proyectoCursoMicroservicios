package co.edu.unicauca.process_management.repository;

import co.edu.unicauca.process_management.entity.ProjectModel;
import co.edu.unicauca.process_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
