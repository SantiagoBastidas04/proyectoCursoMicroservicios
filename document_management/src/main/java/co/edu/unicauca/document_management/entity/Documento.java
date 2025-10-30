package co.edu.unicauca.document_management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;
    private String tipo; // FORMATO_A o ANTEPROYECTO
    private String rutaArchivo;
    private LocalDate fechaSubida;

    @Column(nullable = false)
    private String directorId;
    private String codirectorId;
    private String estudiante1Id;
    private String estudiante2Id;
}
