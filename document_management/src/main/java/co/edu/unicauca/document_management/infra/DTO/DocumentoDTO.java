package co.edu.unicauca.document_management.infra.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDTO {
    private String documentId;
    private String projectId;
    private String documentType;   // Ej: "FormatoA", "Anteproyecto"
    private String action;         // Ej: "CREATED", "UPDATED"
    private String authorEmail;
    private String timestamp;
}
