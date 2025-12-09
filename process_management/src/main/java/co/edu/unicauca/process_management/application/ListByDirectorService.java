package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.port.in.ListByDirectorUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListByDirectorService implements ListByDirectorUseCase {
    private final ProjectRepositoryPort repository;

    public ListByDirectorService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> obtenerProyectosPorDirector(String emailDirector) {
        return repository.findByDirectorEmail(emailDirector);
    }

}
