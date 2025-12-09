package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.ProjectContext;
import co.edu.unicauca.process_management.port.in.ReturnStateUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ReturnStateService implements ReturnStateUseCase {

    private final ProjectRepositoryPort repository;

    public ReturnStateService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Project retrocederEstado(Long id) {
        Optional<Project> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Proyecto no encontrado con ID " + id);
        }
        Project model = opt.get();
        ProjectContext context = new ProjectContext(model);
        context.estadoAnterior();
        Project actualizado = context.getProject();
        return repository.save(actualizado);
    }
}
