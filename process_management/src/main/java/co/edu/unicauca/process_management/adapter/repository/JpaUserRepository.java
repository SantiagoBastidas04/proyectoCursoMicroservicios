package co.edu.unicauca.process_management.adapter.repository;

import co.edu.unicauca.process_management.adapter.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
}