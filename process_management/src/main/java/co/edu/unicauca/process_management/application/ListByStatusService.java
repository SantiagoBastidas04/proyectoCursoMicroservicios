package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.Status;
import co.edu.unicauca.process_management.port.in.ListByStatusUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListByStatusService implements ListByStatusUseCase {

    private final ProjectRepositoryPort repository;

    public ListByStatusService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> obtenerProyectosPorEstado(Status estado) {
        return repository.findByStatus(estado);
    }
}
