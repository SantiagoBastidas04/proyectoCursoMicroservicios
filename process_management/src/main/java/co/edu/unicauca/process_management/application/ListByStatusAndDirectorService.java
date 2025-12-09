package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.Status;
import co.edu.unicauca.process_management.port.in.ListByStatusAndDirectorUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListByStatusAndDirectorService implements ListByStatusAndDirectorUseCase {

    private final ProjectRepositoryPort repository;

    public ListByStatusAndDirectorService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> obtenerProyectosPorEstadoYCorreo(Status estado, String correoDirector) {
        return repository.findByStatusAndDirectorEmail(estado, correoDirector);
    }
}
