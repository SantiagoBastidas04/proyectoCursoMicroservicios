package co.edu.unicauca.process_management.port.in;

import co.edu.unicauca.process_management.domain.model.Project;

public interface UploadDocumentUseCase {
    Project procesarDocumento(

            String tipoDocumento,
            String directorEmail,
            String estudianteEmail,
            String rutaArchivo
    );
}
