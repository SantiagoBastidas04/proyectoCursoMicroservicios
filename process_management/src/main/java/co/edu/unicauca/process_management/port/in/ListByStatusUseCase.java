package co.edu.unicauca.process_management.port.in;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.Status;

import java.util.List;

public interface ListByStatusUseCase {
    List<Project> obtenerProyectosPorEstado(Status estado);
}
