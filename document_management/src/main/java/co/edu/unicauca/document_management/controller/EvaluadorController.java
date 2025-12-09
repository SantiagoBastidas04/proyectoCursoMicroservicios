package co.edu.unicauca.document_management.controller;

import co.edu.unicauca.document_management.entity.EvaluacionAsignada;
import co.edu.unicauca.document_management.messaging.SubmissionPublisher;
import co.edu.unicauca.document_management.service.EvaluadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluadores")
@RequiredArgsConstructor
public class EvaluadorController {

    private final SubmissionPublisher publisher;
    private final EvaluadorService evaluadorService;

    @PostMapping("/asignar")
    public ResponseEntity<String> asignarEvaluadores(
            @RequestParam Long anteproyectoId,
            @RequestParam String docente1Email,
            @RequestParam String docente2Email) {

        // Validación
        evaluadorService.asignarEvaluador(anteproyectoId, docente1Email);
        evaluadorService.asignarEvaluador(anteproyectoId, docente2Email);

        // Notificación
        publisher.notificarAsignacionEvaluadores(
                anteproyectoId,
                docente1Email,
                docente2Email
        );

        return ResponseEntity.ok("Evaluadores asignados y notificados correctamente.");
    }

    @GetMapping("/{anteproyectoId}")
    public ResponseEntity<List<EvaluacionAsignada>> listar(
            @PathVariable Long anteproyectoId) {

        return ResponseEntity.ok(evaluadorService.listarEvaluadores(anteproyectoId));
    }
}
