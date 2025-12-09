package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.port.in.UpdateProjectUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

    private final ProjectRepositoryPort repository;

    public UpdateProjectService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Project actualizarProyecto(Long id, Project proyectoActualizado) {
        Optional<Project> optional = repository.findById(id);
        if (optional.isPresent()) {
            Project existente = optional.get();

            existente.definirTitulo(proyectoActualizado.getAtrTitle());
            existente.asignarDirector(proyectoActualizado.getAtrDirectorEmail());
            existente.asignarCoDirector(proyectoActualizado.getAtrCodirectorEmail());
            existente.registrarEstudiantePrincipal(proyectoActualizado.getAtrStudent1Email());
            existente.registrarEstudianteSecundario(proyectoActualizado.getAtrStudent2Email());
            existente.definirModalidad(proyectoActualizado.getAtrModality());
            existente.actualizarEstadoDesdeStatePattern(proyectoActualizado.getStatus());
            existente.aumentarIntentos();
            existente.agregarObservacion(proyectoActualizado. getAtrObservations());
            existente.registrarFormatoA(proyectoActualizado.getRutaFormatoA());
            existente.registrarCartaAceptacion(proyectoActualizado.getRutaCartaAceptacion());
            existente.registrarAnteproyecto(proyectoActualizado.getRutaAnteproyecto());

            return repository.save(existente);
        } else {
            throw new RuntimeException("Proyecto con ID " + id + " no encontrado");
        }
    }
}
