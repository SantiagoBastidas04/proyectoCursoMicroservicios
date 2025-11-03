package co.edu.unicauca.document_management.service;

import co.edu.unicauca.document_management.entity.Documento;
import co.edu.unicauca.document_management.entity.FormatoA;
import co.edu.unicauca.document_management.messaging.SubmissionPublisher;
import co.edu.unicauca.document_management.repository.DocumentoRepository;
import co.edu.unicauca.document_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormatoAUploader extends DocumentoUploaderTemplate{

    public FormatoAUploader(DocumentoRepository repository,
                            UserRepository userRepository,
                            SubmissionPublisher publisher) {
        super(repository, userRepository, publisher);
    }

    @Override
    protected void prepararDocumento(Documento documento) {

        // === Validación y versionado específico del Formato A ===
        if (documento instanceof FormatoA formatoA) {

            // Buscar versiones previas del mismo título y estudiante
            List<FormatoA> versionesPrevias = repository
                    .findByTituloProyectoAndEstudiante1Id(
                            formatoA.getTituloProyecto(),
                            formatoA.getEstudiante1Id()
                    );

            if (!versionesPrevias.isEmpty()) {
                int nuevaVersion = versionesPrevias.size() + 1;
                formatoA.setNumeroVersion(nuevaVersion);

                if (nuevaVersion > 3) {
                    throw new RuntimeException(
                            "Se superó el número máximo de 3 correcciones del Formato A."
                    );
                }
            } else {
                formatoA.setNumeroVersion(1);
            }

            formatoA.setTipo("FORMATO_A");
        }
    }
}
