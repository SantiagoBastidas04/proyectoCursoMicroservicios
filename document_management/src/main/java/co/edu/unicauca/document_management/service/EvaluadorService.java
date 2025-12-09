package co.edu.unicauca.document_management.service;

import co.edu.unicauca.document_management.entity.EvaluacionAsignada;
import co.edu.unicauca.document_management.repository.EvaluacionAsignadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluadorService {

    private final EvaluacionAsignadaRepository repository;

    public void asignarEvaluador(Long anteproyectoId, String docenteEmail) {

        if (repository.existsByAnteproyectoIdAndDocenteEmail(anteproyectoId, docenteEmail)) {
            throw new RuntimeException(
                    "El docente " + docenteEmail +
                            " ya está asignado como evaluador del anteproyecto " + anteproyectoId
            );
        }

        EvaluacionAsignada asignacion = new EvaluacionAsignada();
        asignacion.setAnteproyectoId(anteproyectoId);
        asignacion.setDocenteEmail(docenteEmail);

        repository.save(asignacion);
    }
    public List<EvaluacionAsignada> listarEvaluadores(Long anteproyectoId) {
        return repository.findByAnteproyectoId(anteproyectoId);
    }
}
