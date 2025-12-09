package co.edu.unicauca.process_management.adapter;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name = "proyectos")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {
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
    private String rutaCartaAceptacion;



    public StatusEnum getAtrStatus() {
        return atrStatus;
    }



    public ModalityEnum getAtrModality() {
        return atrModality;
    }

    public void setAtrId(Long atrId) {
        this.atrId = atrId;
    }

    public void setAtrTitle(String atrTitle) {
        this.atrTitle = atrTitle;
    }

    public void setAtrDirectorEmail(String atrDirectorEmail) {
        this.atrDirectorEmail = atrDirectorEmail;
    }

    public void setAtrCodirectorEmail(String atrCodirectorEmail) {
        this.atrCodirectorEmail = atrCodirectorEmail;
    }

    public void setAtrStudent1Email(String atrStudent1Email) {
        this.atrStudent1Email = atrStudent1Email;
    }

    public void setAtrStudent2Email(String atrStudent2Email) {
        this.atrStudent2Email = atrStudent2Email;
    }

    public void setAtrModality(ModalityEnum atrModality) {
        this.atrModality = atrModality;
    }

    public void setAtrCreationDate(LocalDate atrCreationDate) {
        this.atrCreationDate = atrCreationDate;
    }

    public void setAtrGeneralObjective(String atrGeneralObjective) {
        this.atrGeneralObjective = atrGeneralObjective;
    }

    public void setAtrSpecificObjectives(String atrSpecificObjectives) {
        this.atrSpecificObjectives = atrSpecificObjectives;
    }

    public void setAtrStatus(StatusEnum atrStatus) {
        this.atrStatus = atrStatus;
    }

    public void setAtrNumberOfAttempts(Integer atrNumberOfAttempts) {
        this.atrNumberOfAttempts = atrNumberOfAttempts;
    }

    public void setAtrObservations(String atrObservations) {
        this.atrObservations = atrObservations;
    }

    public void setRutaFormatoA(String rutaFormatoA) {
        this.rutaFormatoA = rutaFormatoA;
    }
}
