/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.entities;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Juan Medina
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectModel {
    private Long atrId;

    private String atrTitle;
    private String atrDirectorEmail;
    private String atrCodirectorEmail;
    private String atrStudent1Email;
    private String atrStudent2Email;
    private ModalityEnum atrModality;
    private LocalDate atrCreationDate;
    private String atrGeneralObjective;
    private String atrSpecificObjectives;
    private StatusEnum atrStatus;
    private Integer atrNumberOfAttempts;
    private String atrObservations;
    private String rutaFormatoA;
    private String atrCartaAceptacion;
    private String rutaAnteproyecto;

    public ProjectModel(Long atrId, String atrTitle, String atrDirectorEmail, String atrCodirectorEmail, String atrStudent1Email, String atrStudent2Email, ModalityEnum atrModality, LocalDate atrCreationDate, String atrGeneralObjective, String atrSpecificObjectives, StatusEnum atrStatus, Integer atrNumberOfAttempts, String atrObservations, String atrFileName, String atrCartaAceptacion, String rutaAnteproyecto) {
        this.atrId = atrId;
        this.atrTitle = atrTitle;
        this.atrDirectorEmail = atrDirectorEmail;
        this.atrCodirectorEmail = atrCodirectorEmail;
        this.atrStudent1Email = atrStudent1Email;
        this.atrStudent2Email = atrStudent2Email;
        this.atrModality = atrModality;
        this.atrCreationDate = atrCreationDate;
        this.atrGeneralObjective = atrGeneralObjective;
        this.atrSpecificObjectives = atrSpecificObjectives;
        this.atrStatus = atrStatus;
        this.atrNumberOfAttempts = atrNumberOfAttempts;
        this.atrObservations = atrObservations;
        this.rutaFormatoA = atrFileName;
        this.atrCartaAceptacion = atrCartaAceptacion;
        this.rutaAnteproyecto = rutaAnteproyecto;
    }

    public String getRutaFormatoA() {
        return rutaFormatoA;
    }

    public void setRutaFormatoA(String rutaFormatoA) {
        this.rutaFormatoA = rutaFormatoA;
    }

    public String getRutaAnteproyecto() {
        return rutaAnteproyecto;
    }

    public void setRutaAnteproyecto(String rutaAnteproyecto) {
        this.rutaAnteproyecto = rutaAnteproyecto;
    }


    public String getAtrCartaAceptacion() {
        return atrCartaAceptacion;
    }

    public void setAtrCartaAceptacion(String atrCartaAceptacion) {
        this.atrCartaAceptacion = atrCartaAceptacion;
    }

    public Long getAtrId() {
        return atrId;
    }

    public void setAtrId(Long atrId) {
        this.atrId = atrId;
    }

    public String getAtrTitle() {
        return atrTitle;
    }

    public void setAtrTitle(String atrTitle) {
        this.atrTitle = atrTitle;
    }

    public String getAtrDirectorEmail() {
        return atrDirectorEmail;
    }

    public void setAtrDirectorEmail(String atrDirectorEmail) {
        this.atrDirectorEmail = atrDirectorEmail;
    }

    public String getAtrCodirectorEmail() {
        return atrCodirectorEmail;
    }

    public void setAtrCodirectorEmail(String atrCodirectorEmail) {
        this.atrCodirectorEmail = atrCodirectorEmail;
    }

    public String getAtrStudent1Email() {
        return atrStudent1Email;
    }

    public void setAtrStudent1Email(String atrStudent1Email) {
        this.atrStudent1Email = atrStudent1Email;
    }

    public String getAtrStudent2Email() {
        return atrStudent2Email;
    }

    public void setAtrStudent2Email(String atrStudent2Email) {
        this.atrStudent2Email = atrStudent2Email;
    }

    public ModalityEnum getAtrModality() {
        return atrModality;
    }

    public void setAtrModality(ModalityEnum atrModality) {
        this.atrModality = atrModality;
    }

    public String getAtrGeneralObjective() {
        return atrGeneralObjective;
    }

    public void setAtrGeneralObjective(String atrGeneralObjective) {
        this.atrGeneralObjective = atrGeneralObjective;
    }

    public String getAtrSpecificObjectives() {
        return atrSpecificObjectives;
    }

    public void setAtrSpecificObjectives(String atrSpecificObjectives) {
        this.atrSpecificObjectives = atrSpecificObjectives;
    }

    public StatusEnum getAtrStatus() {
        return atrStatus;
    }

    public void setAtrStatus(StatusEnum atrStatus) {
        this.atrStatus = atrStatus;
    }

    public Integer getAtrNumberOfAttempts() {
        return atrNumberOfAttempts;
    }

    public void setAtrNumberOfAttempts(Integer atrNumberOfAttempts) {
        this.atrNumberOfAttempts = atrNumberOfAttempts;
    }

    public String getAtrObservations() {
        return atrObservations;
    }

    public void setAtrObservations(String atrObservations) {
        this.atrObservations = atrObservations;
    }

    public LocalDate getAtrCreationDate() {
        return atrCreationDate;
    }

    public void setAtrCreationDate(LocalDate atrCreationDate) {
        this.atrCreationDate = atrCreationDate;
    }
    
    
}
