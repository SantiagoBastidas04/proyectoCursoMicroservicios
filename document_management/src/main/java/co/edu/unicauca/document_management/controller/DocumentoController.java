package co.edu.unicauca.document_management.controller;

import co.edu.unicauca.document_management.entity.Anteproyecto;
import co.edu.unicauca.document_management.entity.Documento;
import co.edu.unicauca.document_management.entity.FormatoA;
import co.edu.unicauca.document_management.service.AnteproyectoUploader;
import co.edu.unicauca.document_management.service.DocumentoService;
import co.edu.unicauca.document_management.service.FormatoAUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoController {
    private final FormatoAUploader formatoAUploader;
    private final AnteproyectoUploader anteproyectoUploader;
    private final DocumentoService documentoService;

    // === Subir Formato A ===
    @PostMapping("/formatoA")
    public ResponseEntity<Documento> subirFormatoA(
            @RequestParam("file") MultipartFile archivo,
            @RequestParam String tituloProyecto,
            @RequestParam String modalidad,
            @RequestParam String objetivoGeneral,
            @RequestParam String objetivosEspecificos,
            @RequestParam String directorId,
            @RequestParam(required = false) String codirectorId,
            @RequestParam String estudiante1Id,
            @RequestParam(required = false) String estudiante2Id
    ) {
        FormatoA formatoA = new FormatoA();
        formatoA.setTipo("FORMATO_A");
        formatoA.setTituloProyecto(tituloProyecto);
        formatoA.setModalidad(modalidad);
        formatoA.setObjetivoGeneral(objetivoGeneral);
        formatoA.setObjetivosEspecificos(objetivosEspecificos);
        formatoA.setDirectorId(directorId);
        formatoA.setCodirectorId(codirectorId);
        formatoA.setEstudiante1Id(estudiante1Id);
        formatoA.setEstudiante2Id(estudiante2Id);

        Documento guardado = formatoAUploader.subirDocumento(archivo, formatoA);
        return ResponseEntity.ok(guardado);
    }

    // === Subir Anteproyecto ===
    @PostMapping("/anteproyecto")
    public ResponseEntity<Documento> subirAnteproyecto(
            @RequestParam("file") MultipartFile archivo,
            @RequestParam String directorId,
            @RequestParam(required = false) String codirectorId,
            @RequestParam String estudiante1Id,
            @RequestParam(required = false) String estudiante2Id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaAprobacionFormatoA,
            @RequestParam(required = false) String resumen
    ) {
        Anteproyecto anteproyecto = new Anteproyecto();
        anteproyecto.setTipo("ANTEPROYECTO");
        anteproyecto.setDirectorId(directorId);
        anteproyecto.setCodirectorId(codirectorId);
        anteproyecto.setEstudiante1Id(estudiante1Id);
        anteproyecto.setEstudiante2Id(estudiante2Id);
        anteproyecto.setFechaAprobacionFormatoA(fechaAprobacionFormatoA);
        anteproyecto.setResumen(resumen);

        Documento guardado = anteproyectoUploader.subirDocumento(archivo, anteproyecto);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.obtenerDocumento(id));
    }

    @GetMapping("/director/{idDirector}")
    public ResponseEntity<List<Documento>> listarPorDirector(@PathVariable String idDirector) {
        return ResponseEntity.ok(documentoService.listarPorDirector(idDirector));
    }
}
