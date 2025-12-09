package co.edu.unicauca.process_management.port.in;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.Status;

import java.util.List;

public interface ListByStatusAndDirectorUseCase {
    List<Project> obtenerProyectosPorEstadoYCorreo(Status estado, String correoDirector);
}
