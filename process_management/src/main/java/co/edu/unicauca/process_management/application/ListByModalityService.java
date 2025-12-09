package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Modality;
import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.port.in.ListByModalityUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListByModalityService implements ListByModalityUseCase {

    private final ProjectRepositoryPort repository;

    public ListByModalityService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> listarPorModality(Modality modality) {
        return repository.findByModality(modality);
    }
}
