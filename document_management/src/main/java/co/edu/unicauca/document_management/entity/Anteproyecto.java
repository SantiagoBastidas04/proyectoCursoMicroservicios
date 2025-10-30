package co.edu.unicauca.document_management.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anteproyecto extends Documento {
    private LocalDate fechaAprobacionFormatoA;
    private String resumen;
}
