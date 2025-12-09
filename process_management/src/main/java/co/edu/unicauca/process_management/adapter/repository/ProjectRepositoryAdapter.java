package co.edu.unicauca.process_management.adapter.repository;

import co.edu.unicauca.process_management.adapter.ModalityEnum;
import co.edu.unicauca.process_management.adapter.ProjectEntity;
import co.edu.unicauca.process_management.adapter.ProjectMapper;
import co.edu.unicauca.process_management.adapter.StatusEnum;
import co.edu.unicauca.process_management.domain.model.Modality;
import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.Status;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class ProjectRepositoryAdapter implements ProjectRepositoryPort {
    private final JpaProjectRepository jpa;

    public ProjectRepositoryAdapter(JpaProjectRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Project save(Project project) {
        ProjectEntity entity = ProjectMapper.toEntity(project);
        ProjectEntity saved = jpa.save(entity);
        return ProjectMapper.toDomain(saved);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return jpa.findById(id).map(ProjectMapper::toDomain);
    }

    @Override
    public List<Project> findAll() {
          return jpa.findAll().stream().map(ProjectMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Project> findByDirectorEmail(String email) {
        return jpa.findByAtrDirectorEmail(email).stream().map(ProjectMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Project> findByStudent1Email(String email) {
        return jpa.findByAtrStudent1Email(email).stream().map(ProjectMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Project> findByStatus(Status status) {
        StatusEnum s = StatusEnum.valueOf(status.name());
        return jpa.findByAtrStatus(s).stream().map(ProjectMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Project> findByStatusAndDirectorEmail(Status status, String directorEmail) {
        StatusEnum s = StatusEnum.valueOf(status.name());
        return jpa.findByAtrStatusAndAtrDirectorEmail(s, directorEmail).stream()
                .map(ProjectMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Project> findByDirectorEmailAndStudent1Email(String directorEmail, String student1Email) {
        return jpa.findByAtrDirectorEmailAndAtrStudent1Email(directorEmail, student1Email)
                .map(ProjectMapper::toDomain);
    }

    @Override
    public List<Project> findByModality(Modality modality) {
        ModalityEnum m = ModalityEnum.valueOf(modality.name());
        return jpa.findByAtrModality(m).stream().map(ProjectMapper::toDomain).collect(Collectors.toList());
    }
}
