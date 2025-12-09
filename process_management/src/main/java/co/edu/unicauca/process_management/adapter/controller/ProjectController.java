package co.edu.unicauca.process_management.adapter.controller;

import co.edu.unicauca.process_management.adapter.DocumentUploadDTO;
import co.edu.unicauca.process_management.adapter.ProjectRequestDTO;
import co.edu.unicauca.process_management.adapter.ProjectResponseDTO;
import co.edu.unicauca.process_management.domain.model.Modality;
import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.domain.model.Status;
import co.edu.unicauca.process_management.port.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proyectos")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjectUseCase getProjectUseCase;
    private final ListProjectsUseCase listProjectsUseCase;
    private final ListByStudentUseCase listByStudentUseCase;
    private final ListByStatusUseCase listByStatusUseCase;
    private final ListByStatusAndDirectorUseCase listByStatusAndDirectorUseCase;
    private final ListByModalityUseCase listByModalityUseCase;
    private final ListByDirectorUseCase listByDirectorUseCase;
    private final AdvanceStateUseCase advanceStateUseCase;
    private final ReturnStateUseCase returnStateUseCase;
    private final CorrectProjectUseCase correctProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final UploadDocumentUseCase uploadDocumentUseCase;

    // -------------------------------------------------------------------------------------
    // CREAR PROYECTO
    // -------------------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> crearProyecto(@RequestBody ProjectRequestDTO dto) {

        Project created = createProjectUseCase.crearProyecto(dto.toDomain());

        return ResponseEntity.ok(ProjectResponseDTO.fromDomain(created));
    }

    // -------------------------------------------------------------------------------------
    // LISTAR TODOS
    // -------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> listarProyectos() {

        List<Project> list = listProjectsUseCase.listarProyectos();

        return ResponseEntity.ok(
                list.stream().map(ProjectResponseDTO::fromDomain).collect(Collectors.toList())
        );
    }

    // -------------------------------------------------------------------------------------
    // OBTENER POR ID
    // -------------------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> obtenerProyecto(@PathVariable Long id) {

        Project project = getProjectUseCase.obtenerProyecto(id);

        return ResponseEntity.ok(ProjectResponseDTO.fromDomain(project));
    }

    // -------------------------------------------------------------------------------------
    // LISTAR POR ESTUDIANTE
    // -------------------------------------------------------------------------------------
    @GetMapping("/student/{correoEstudiante}")
    public ResponseEntity<List<ProjectResponseDTO>> obtenerPorEstudiante(
            @PathVariable String correoEstudiante
    ) {

        List<Project> list = listByStudentUseCase.obtenerProyectosPorEstudiante(correoEstudiante);

        return ResponseEntity.ok(
                list.stream().map(ProjectResponseDTO::fromDomain).collect(Collectors.toList())
        );
    }

    // -------------------------------------------------------------------------------------
    // LISTAR POR ESTADO
    // -------------------------------------------------------------------------------------
    @GetMapping("/state/{estado}")
    public ResponseEntity<List<ProjectResponseDTO>> listarPorEstado(
            @PathVariable String estado
    ) {

        List<Project> list =
                listByStatusUseCase.obtenerProyectosPorEstado(Status.valueOf(estado));

        return ResponseEntity.ok(
                list.stream().map(ProjectResponseDTO::fromDomain).collect(Collectors.toList())
        );
    }

    // -------------------------------------------------------------------------------------
    // LISTAR POR ESTADO Y DIRECTOR
    // -------------------------------------------------------------------------------------
    @GetMapping("/{correo}/{estado}")
    public ResponseEntity<List<ProjectResponseDTO>> listarPorEstadoYDirector(
            @PathVariable String correo,
            @PathVariable String estado
    ) {

        List<Project> list =
                listByStatusAndDirectorUseCase.obtenerProyectosPorEstadoYCorreo(
                        Status.valueOf(estado),
                        correo
                );

        return ResponseEntity.ok(
                list.stream().map(ProjectResponseDTO::fromDomain).collect(Collectors.toList())
        );
    }

    // -------------------------------------------------------------------------------------
    // LISTAR POR MODALIDAD
    // -------------------------------------------------------------------------------------
    @GetMapping("/modality/{modality}")
    public ResponseEntity<List<ProjectResponseDTO>> listarPorModality(
            @PathVariable String modality
    ) {

        Modality m = Modality.valueOf(modality);

        List<Project> list = listByModalityUseCase.listarPorModality(m);

        return ResponseEntity.ok(
                list.stream().map(ProjectResponseDTO::fromDomain).collect(Collectors.toList())
        );
    }

    // -------------------------------------------------------------------------------------
    // LISTAR POR DIRECTOR
    // -------------------------------------------------------------------------------------
    @GetMapping("/director/{email}")
    public ResponseEntity<List<ProjectResponseDTO>> listarPorDirector(
            @PathVariable String email
    ) {

        List<Project> list = listByDirectorUseCase.obtenerProyectosPorDirector(email);

        return ResponseEntity.ok(
                list.stream().map(ProjectResponseDTO::fromDomain).collect(Collectors.toList())
        );
    }

    // -------------------------------------------------------------------------------------
    // AVANZAR ESTADO
    // -------------------------------------------------------------------------------------
    @PutMapping("/{id}/avanzar")
    public ResponseEntity<ProjectResponseDTO> avanzar(@PathVariable Long id) {

        Project updated = advanceStateUseCase.avanzarEstado(id);

        return ResponseEntity.ok(ProjectResponseDTO.fromDomain(updated));
    }

    // -------------------------------------------------------------------------------------
    // RETROCEDER ESTADO
    // -------------------------------------------------------------------------------------
    @PutMapping("/{id}/retroceder")
    public ResponseEntity<ProjectResponseDTO> retroceder(@PathVariable Long id) {

        Project updated = returnStateUseCase.retrocederEstado(id);

        return ResponseEntity.ok(ProjectResponseDTO.fromDomain(updated));
    }

    // -------------------------------------------------------------------------------------
    // CORREGIR PROYECTO
    // -------------------------------------------------------------------------------------
    @PutMapping("/{id}/corregir")
    public ResponseEntity<ProjectResponseDTO> corregir(@PathVariable Long id) {

        Project updated = correctProjectUseCase.corregirProyecto(id);

        return ResponseEntity.ok(ProjectResponseDTO.fromDomain(updated));
    }

    // -------------------------------------------------------------------------------------
    // ACTUALIZAR INFORMACIÓN DEL PROYECTO
    // -------------------------------------------------------------------------------------
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<ProjectResponseDTO> actualizarProyecto(
            @PathVariable Long id,
            @RequestBody ProjectRequestDTO dto
    ) {

        Project updated = updateProjectUseCase.actualizarProyecto(id, dto.toDomain());

        return ResponseEntity.ok(ProjectResponseDTO.fromDomain(updated));
    }

    // -------------------------------------------------------------------------------------
    // SUBIR DOCUMENTOS (FORMATO A, ANTEPROYECTO, CARTA)
    // -------------------------------------------------------------------------------------
    @PutMapping("/documento")
    public ResponseEntity<ProjectResponseDTO> uploadDocumento(
            @RequestBody DocumentUploadDTO dto
    ) {

        Project updated = uploadDocumentUseCase.procesarDocumento(
                dto.getTipoDocumento(),
                dto.getDirectorEmail(),
                dto.getEstudianteEmail(),
                dto.getRutaArchivo()
        );

        return ResponseEntity.ok(ProjectResponseDTO.fromDomain(updated));
    }
}
