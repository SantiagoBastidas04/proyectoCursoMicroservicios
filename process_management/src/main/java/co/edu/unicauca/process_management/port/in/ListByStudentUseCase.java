package co.edu.unicauca.process_management.port.in;

import co.edu.unicauca.process_management.domain.model.Project;

import java.util.List;

public interface ListByStudentUseCase {
    List<Project> obtenerProyectosPorEstudiante(String emailEstudiante);
}
