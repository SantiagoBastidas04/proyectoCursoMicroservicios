package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.port.in.GetProjectUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProjectService implements GetProjectUseCase {

    private final ProjectRepositoryPort projectRepository;

    public GetProjectService(ProjectRepositoryPort projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project obtenerProyecto(Long id) {
        Optional<Project> opt = projectRepository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Proyecto no encontrado con ID " + id);
        }
        return opt.get();
    }
}
