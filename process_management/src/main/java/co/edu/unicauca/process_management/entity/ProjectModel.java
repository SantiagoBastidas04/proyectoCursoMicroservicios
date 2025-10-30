package co.edu.unicauca.process_management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * @author edwin
 **/

@Entity
@Table(name = "proyectos")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectModel  {
    
    // Campos de la entidad en la DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atrId;

    private String atrTitle;
    @Column(name = "ATR_DIRECTOR")
    private String atrDirectorEmail;
    @Column(name = "ATR_CODIRECTOR",nullable = true)
    private String atrCodirectorEmail;
    @Column(name = "ATR_STUDENT1")
    private String atrStudent1Email;
    @Column(name = "ATR_STUDENT2",nullable = true)
    private String atrStudent2Email;
    @Enumerated(EnumType.STRING)
    private ModalityEnum atrModality;
    private LocalDate atrCreationDate;
    private String atrGeneralObjective;
    private String atrSpecificObjectives;
    @Enumerated(EnumType.STRING)
    private StatusEnum atrStatus;
    private Integer atrNumberOfAttempts;
    private String atrObservations;
    private String rutaFormatoA;
    private String rutaAnteproyecto;


    public StatusEnum getStatus()
    {
        return this.atrStatus;
    }

    public void setStatus(StatusEnum prmStatus)
    {
        this.atrStatus = prmStatus;
    }
}
