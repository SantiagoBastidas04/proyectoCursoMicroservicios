package co.edu.unicauca.document_management.repository;

import co.edu.unicauca.document_management.entity.EvaluacionAsignada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluacionAsignadaRepository extends JpaRepository<EvaluacionAsignada, Long> {
    boolean existsByAnteproyectoIdAndDocenteEmail(Long anteproyectoId, String docenteEmail);
    List<EvaluacionAsignada> findByAnteproyectoId(Long anteproyectoId);
}