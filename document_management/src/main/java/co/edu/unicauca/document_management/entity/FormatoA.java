package co.edu.unicauca.document_management.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormatoA extends Documento{
    private String tituloProyecto;
    private String modalidad; // Investigación o Práctica profesional
    private String objetivoGeneral;
    private String objetivosEspecificos;
    private  int numeroVersion = 1;
}
