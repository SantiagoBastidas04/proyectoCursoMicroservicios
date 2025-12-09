package co.edu.unicauca.process_management.adapter;

import co.edu.unicauca.process_management.domain.model.Modality;
import co.edu.unicauca.process_management.domain.model.Project;

import lombok.Data;

@Data
public class ProjectRequestDTO {

    private String title;
    private String directorEmail;
    private String student1Email;
    private String student2Email;
    private String codirectorEmail;
    private String generalObjective;
    private String specificObjectives;
    private String modality;

    public Project toDomain() {
        Project p = new Project();

        // Métodos de dominio (sin setters)
        p.definirTitulo(title);
        p.asignarDirector(directorEmail);
        p.registrarEstudiantePrincipal(student1Email);

        if (student2Email != null)
            p.registrarEstudianteSecundario(student2Email);

        if (codirectorEmail != null)
            p.asignarCoDirector(codirectorEmail);

        p.cargarObjetivoGeneralDesdePersistencia(generalObjective);
        p.cargarObjetivosEspecificosDesdePersistencia(specificObjectives);


        if (modality != null)
            p.definirModalidad(Modality.valueOf(modality));


        return p;
    }
}
