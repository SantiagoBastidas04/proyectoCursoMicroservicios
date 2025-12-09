package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.exception.UsuarioNoRegistradoException;
import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.Status;
import co.edu.unicauca.process_management.domain.service.ProjectDomainService;
import co.edu.unicauca.process_management.port.in.CreateProjectUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import co.edu.unicauca.process_management.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final UserRepositoryPort userRepository;
    private final ProjectDomainService domainService;

    public CreateProjectService(ProjectRepositoryPort projectRepository, UserRepositoryPort userRepository,
                                ProjectDomainService domainService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.domainService = domainService;
    }

    @Override
    public Project crearProyecto(Project project) {
        // Validaciones de negocio (correos)
        domainService.validarCorreos(project);

        // Validar existencia de usuarios (director, estudiante1, estudiante2 opcional, codirector opcional)
        validarUsuarioExistente(project.getAtrDirectorEmail());
        validarUsuarioExistente(project.getAtrStudent1Email());
        if (project.getAtrStudent2Email() != null) {
            validarUsuarioExistente(project.getAtrStudent2Email());
        }
        if (project.getAtrCodirectorEmail() != null) {
            validarUsuarioExistente(project.getAtrCodirectorEmail());
        }

        project.resetNumberOfAttempts();
        project.iniciarFechaCreacion(LocalDate.now());
        if (project.getStatus() == null) {
            project.establecerEstadoInicial(Status.INICIO);
        }

        Project saved = projectRepository.save(project);
        return saved;
    }
    private void validarUsuarioExistente(String email) {
        if (email == null || email.trim().isEmpty()) {
            // omitir validación si no hay correo (igual que el comportamiento original)
            return;
        }
        if (!userRepository.existsById(email)) {
            throw new UsuarioNoRegistradoException("El usuario con correo " + email + " no existe.");
        }
    }
}
