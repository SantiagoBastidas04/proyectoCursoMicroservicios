package co.edu.unicauca.process_management.controller;



import co.edu.unicauca.process_management.entity.ProjectModel;
import co.edu.unicauca.process_management.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unicauca.process_management.entity.ModalityEnum;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @PostMapping
    public ResponseEntity<ProjectModel> crearProyecto(@RequestBody ProjectModel project) {
        return ResponseEntity.ok(service.crearProyecto(project));
    }

    @GetMapping
    public ResponseEntity<List<ProjectModel>> listarProyectos() {
        return ResponseEntity.ok(service.listarProyectos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectModel> obtenerProyecto(@PathVariable Long id) {
        try {
            ProjectModel project = service.obtenerProyecto(id);
            return ResponseEntity.ok(project);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/student/{correoEstudiante}")
    public ResponseEntity<List<ProjectModel>> obtenerProyectosPorEstudiante(@PathVariable String correoEstudiante) {
        try {
            List<ProjectModel> projects = service.obtenerProyectosPorEstudiante(correoEstudiante);
            return ResponseEntity.ok(projects);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/modality/{modality}")
    public ResponseEntity<List<ProjectModel>> listarPorModality(@PathVariable String modality) {
        ModalityEnum enumValue = ModalityEnum.getModality(modality);
        if (enumValue == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.listarPorModality(enumValue));
    }

    @GetMapping("/director/{email}")
    public List<ProjectModel> obtenerProyectosPorDirector(@PathVariable String email) {
        return service.obtenerProyectosPorDirector(email);
    }
    @PutMapping("/{id}/avanzar")
    public ResponseEntity<ProjectModel> avanzar(@PathVariable Long id) {
        return ResponseEntity.ok(service.avanzarEstado(id));
    }

    @PutMapping("/{id}/retroceder")
    public ResponseEntity<ProjectModel> retroceder(@PathVariable Long id) {
        return ResponseEntity.ok(service.retrocederEstado(id));
    }

    @PutMapping("/{id}/corregir")
    public ResponseEntity<ProjectModel> corregir(@PathVariable Long id) {
        return ResponseEntity.ok(service.corregirProyecto(id));
    }
}

