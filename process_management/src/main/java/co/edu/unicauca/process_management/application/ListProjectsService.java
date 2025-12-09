package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.port.in.ListProjectsUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListProjectsService implements ListProjectsUseCase {

    private final ProjectRepositoryPort repository;

    public ListProjectsService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> listarProyectos() {
        return repository.findAll();
    }

}
