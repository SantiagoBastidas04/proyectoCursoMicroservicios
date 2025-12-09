package co.edu.unicauca.process_management.adapter;

import co.edu.unicauca.process_management.domain.model.*;

import java.util.Optional;

public class ProjectMapper {
    private ProjectMapper() {}

    public static ProjectEntity toEntity(Project domain) {
        if (domain == null) return null;
        ProjectEntity e = new ProjectEntity();

        e.setAtrId(domain.getAtrId());
        e.setAtrTitle(domain.getAtrTitle());
        e.setAtrDirectorEmail(domain.getAtrDirectorEmail());
        e.setAtrCodirectorEmail(domain.getAtrCodirectorEmail());
        e.setAtrStudent1Email(domain.getAtrStudent1Email());
        e.setAtrStudent2Email(domain.getAtrStudent2Email());

        if (domain.getAtrModality() != null) {
            e.setAtrModality(mapModalityToEntity(domain.getAtrModality()));
        }
        e.setAtrCreationDate(domain.getAtrCreationDate());
        e.setAtrGeneralObjective(domain.getAtrGeneralObjective());
        e.setAtrSpecificObjectives(domain.getAtrSpecificObjectives());

        if (domain.getStatus() != null) {
            e.setAtrStatus(mapStatusToEntity(domain.getAtrStatus()));
        }
        e.setAtrNumberOfAttempts(domain.getAtrNumberOfAttempts());
        e.setAtrObservations(domain.getAtrObservations());
        e.setRutaFormatoA(domain.getRutaFormatoA());
        e.setRutaAnteproyecto(domain.getRutaAnteproyecto());
        e.setRutaCartaAceptacion(domain.getRutaCartaAceptacion());

        return e;
    }

    public static Project toDomain(ProjectEntity e) {
        if (e == null) return null;
        Project d = new Project();

        d.asignarIdDesdePersistencia(e.getAtrId());
        d.definirTitulo(e.getAtrTitle());
        d.asignarDirector(e.getAtrDirectorEmail());
        d.asignarCoDirector(e.getAtrCodirectorEmail());
        d.registrarEstudiantePrincipal(e.getAtrStudent1Email());
        d.registrarEstudianteSecundario(e.getAtrStudent2Email());

        if (e.getAtrModality() != null) {
            d.definirModalidad(mapModalityToDomain(e.getAtrModality()));
        }
        d.iniciarFechaCreacion(e.getAtrCreationDate());
        d.cargarObjetivoGeneralDesdePersistencia(e.getAtrGeneralObjective());
        d.cargarObjetivosEspecificosDesdePersistencia(e.getAtrSpecificObjectives());

        if (e.getAtrStatus() != null) {
            d.setStatus(mapStatusToDomain(e.getAtrStatus()));
        }
        d.asignarIntentosDesdePersistencia(Optional.ofNullable(e.getAtrNumberOfAttempts()).orElse(0));
        d.agregarObservacion(e.getAtrObservations());
        d.registrarFormatoA(e.getRutaFormatoA());
        d.registrarAnteproyecto(e.getRutaAnteproyecto());
        d.registrarCartaAceptacion(e.getRutaCartaAceptacion());

        return d;
    }

    // ---------------- mapping helpers ----------------
    private static ModalityEnum mapModalityToEntity(Modality m) {
        return ModalityEnum.valueOf(m.name());
    }

    private static Modality mapModalityToDomain(ModalityEnum e) {
        return Modality.valueOf(e.name());
    }

    private static StatusEnum mapStatusToEntity(Status s) {
        return StatusEnum.valueOf(s.name());
    }

    private static Status mapStatusToDomain(StatusEnum e) {
        return Status.valueOf(e.name());
    }

}
