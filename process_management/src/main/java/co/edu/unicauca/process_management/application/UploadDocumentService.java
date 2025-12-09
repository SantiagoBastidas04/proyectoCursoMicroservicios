package co.edu.unicauca.process_management.application;

import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.port.in.UploadDocumentUseCase;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UploadDocumentService implements UploadDocumentUseCase {

    private final ProjectRepositoryPort projectRepository;

    public UploadDocumentService(ProjectRepositoryPort projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project procesarDocumento(String tipoDocumento, String directorEmail, String estudianteEmail, String rutaArchivo) {

        Optional<Project> projectOpt = projectRepository.findByDirectorEmailAndStudent1Email(directorEmail, estudianteEmail);
        if (projectOpt.isEmpty()) {
            throw new RuntimeException("No se encontró trabajo de grado para director=" + directorEmail + " y estudiante=" + estudianteEmail);
        }

        Project objProject = projectOpt.get();

        if ("FORMATO_A".equalsIgnoreCase(tipoDocumento)) {
            objProject.registrarFormatoA(rutaArchivo);
        } else if ("ANTEPROYECTO".equalsIgnoreCase(tipoDocumento)) {
            objProject.registrarAnteproyecto(rutaArchivo);
        } else if ("CARTA_ACEPTACION".equalsIgnoreCase(tipoDocumento)) {
            objProject.registrarCartaAceptacion(rutaArchivo);
        } else {
            // tipo desconocido, guardar en observaciones
            objProject.agregarObservacion("Documento recibido: " + tipoDocumento);
        }

        Project saved = projectRepository.save(objProject);
        return saved;
    }
}
