package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.port.in.ListByStudentUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListByStudentService implements ListByStudentUseCase {

    private final ProjectRepositoryPort repository;

    public ListByStudentService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> obtenerProyectosPorEstudiante(String emailEstudiante) {
        return repository.findByStudent1Email(emailEstudiante);
    }
}
