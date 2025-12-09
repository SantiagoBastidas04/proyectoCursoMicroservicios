package co.edu.unicauca.process_management.adapter.repository;

import co.edu.unicauca.process_management.adapter.ModalityEnum;
import co.edu.unicauca.process_management.adapter.ProjectEntity;
import co.edu.unicauca.process_management.adapter.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByAtrModality(ModalityEnum modality);

    List<ProjectEntity> findByAtrDirectorEmail(String atrDirectorEmail);

    List<ProjectEntity> findByAtrStudent1Email(String atrStudent1Email);

    List<ProjectEntity> findByAtrStatus(StatusEnum atrStatus);

    List<ProjectEntity> findByAtrStatusAndAtrDirectorEmail(StatusEnum atrStatus, String atrDirectorEmail);

    Optional<ProjectEntity> findByAtrDirectorEmailAndAtrStudent1Email(String atrDirectorEmail, String atrStudent1Email);
}
