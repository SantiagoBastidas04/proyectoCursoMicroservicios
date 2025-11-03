package co.edu.unicauca.process_management.repository;

import co.edu.unicauca.process_management.entity.ModalityEnum;
import co.edu.unicauca.process_management.entity.ProjectModel;
import co.edu.unicauca.process_management.entity.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, Long>{

    List<ProjectModel> findByAtrModality(ModalityEnum modality);
    List<ProjectModel> findByAtrDirectorEmail(String atrDirectorEmail);
    List<ProjectModel> findByAtrStudent1Email(String atrStudent1Email);
    List<ProjectModel> findByAtrStatus(StatusEnum atrStatus);
    List<ProjectModel>  findByAtrStatusAndAtrDirectorEmail(StatusEnum atrStatus, String atrDirectorEmail);
    Optional<ProjectModel> findByAtrDirectorEmailAndAtrStudent1Email(String atrDirectorEmail, String atrStudent1Email);
}
