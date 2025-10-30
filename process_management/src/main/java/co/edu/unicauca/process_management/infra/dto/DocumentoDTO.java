package co.edu.unicauca.process_management.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoDTO {
    private String tipo;
    private String rutaArchivo;
    private String directorId;
    private String estudiante1Id;
    private String estudiante2Id;
}
