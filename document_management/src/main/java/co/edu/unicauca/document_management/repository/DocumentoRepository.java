package co.edu.unicauca.document_management.repository;

import co.edu.unicauca.document_management.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByDirectorId(String directorId);

}
