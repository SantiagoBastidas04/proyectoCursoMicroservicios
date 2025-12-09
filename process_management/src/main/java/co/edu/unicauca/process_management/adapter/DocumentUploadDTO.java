package co.edu.unicauca.process_management.adapter;

import lombok.Data;

@Data
public class DocumentUploadDTO {
    private String tipoDocumento;        // FORMATO_A, ANTEPROYECTO, CARTA_ACEPTACION
    private String directorEmail;
    private String estudianteEmail;
    private String rutaArchivo;
    private String estudiante2Id;
}


