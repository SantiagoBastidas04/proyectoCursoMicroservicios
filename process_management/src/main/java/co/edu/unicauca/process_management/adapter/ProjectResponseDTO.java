package co.edu.unicauca.process_management.adapter;

import co.edu.unicauca.process_management.domain.model.Project;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponseDTO {

    private Long id;
    private String title;
    private String directorEmail;
    private String student1Email;
    private String student2Email;
    private String codirectorEmail;
    private String generalObjective;
    private String specificObjectives;
    private String modality;
    private String status;
    private int numberOfAttempts;
    private String creationDate;

    public static ProjectResponseDTO fromDomain(Project p) {
        return ProjectResponseDTO.builder()
                .id(p.getAtrId())
                .title(p.getAtrTitle())
                .directorEmail(p.getAtrDirectorEmail())
                .student1Email(p.getAtrStudent1Email())
                .student2Email(p.getAtrStudent2Email())
                .codirectorEmail(p.getAtrCodirectorEmail())
                .generalObjective(p.getAtrGeneralObjective())
                .specificObjectives(p.getAtrSpecificObjectives())
                .modality(p.getAtrModality().name())
                .status(p.getStatus().name())
                .numberOfAttempts(p.getAtrNumberOfAttempts())
                .creationDate(p.getAtrCreationDate().toString())
                .build();
    }
}
