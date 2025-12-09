package co.edu.unicauca.process_management.port.in;

import co.edu.unicauca.process_management.domain.model.Modality;
import co.edu.unicauca.process_management.domain.model.Project;

import java.util.List;

public interface ListByModalityUseCase {
    List<Project> listarPorModality(Modality modality);
}
