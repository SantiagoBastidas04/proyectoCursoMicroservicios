package co.edu.unicauca.document_management.service;

import co.edu.unicauca.document_management.entity.Anteproyecto;
import co.edu.unicauca.document_management.entity.Documento;
import co.edu.unicauca.document_management.messaging.SubmissionPublisher;
import co.edu.unicauca.document_management.repository.DocumentoRepository;
import co.edu.unicauca.document_management.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AnteproyectoUploader extends DocumentoUploaderTemplate{

    public AnteproyectoUploader(DocumentoRepository repository, UserRepository userRepository, SubmissionPublisher publisher) {
        super(repository, userRepository, publisher);
    }

    @Override
    protected void prepararDocumento(Documento documento) {
        documento.setTipo("ANTEPROYECTO");
        // Puedes inicializar campos específicos del anteproyecto, por ejemplo:
        if (documento instanceof Anteproyecto anteproyecto && anteproyecto.getFechaAprobacionFormatoA() == null) {
            anteproyecto.setFechaAprobacionFormatoA(java.time.LocalDate.now());
        }
    }
}
