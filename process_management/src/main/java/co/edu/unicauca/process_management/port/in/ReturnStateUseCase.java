package co.edu.unicauca.process_management.port.in;

import co.edu.unicauca.process_management.domain.model.Project;

public interface ReturnStateUseCase {
    Project retrocederEstado(Long id);
}
