package co.edu.unicauca.process_management.port.out;

import co.edu.unicauca.process_management.domain.model.Modality;
import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.Status;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryPort {
    Project save(Project project);

    Optional<Project> findById(Long id);

    List<Project> findAll();

    List<Project> findByDirectorEmail(String email);

    List<Project> findByStudent1Email(String email);

    List<Project> findByStatus(Status status);

    List<Project> findByStatusAndDirectorEmail(Status status, String directorEmail);

    Optional<Project> findByDirectorEmailAndStudent1Email(String directorEmail, String student1Email);

    List<Project> findByModality(Modality modality);
}
