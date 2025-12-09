package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.exception.MaximoIntentosException;
import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.ProjectContext;
import co.edu.unicauca.process_management.domain.model.Status;
import co.edu.unicauca.process_management.port.in.CorrectProjectUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
/*
* Correguir proyecto
* */
@Service
public class CorrectProjectService implements CorrectProjectUseCase {

    private final ProjectRepositoryPort repository;

    public CorrectProjectService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Project corregirProyecto(Long id) {
        Optional<Project> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Proyecto no encontrado con ID " + id);
        }
        Project model = opt.get();
        ProjectContext context = new ProjectContext(model);
        try {
            context.corregir();
        } catch (MaximoIntentosException ex) {
            // marcar como rechazado (ya hace el contexto, pero dejamos lógica por si acaso)
            model.actualizarEstadoDesdeStatePattern(Status.RECHAZADO_COMITE);
            repository.save(model);
            throw ex;
        }
        Project actualizado = context.getProject();
        return repository.save(actualizado);
    }
}
