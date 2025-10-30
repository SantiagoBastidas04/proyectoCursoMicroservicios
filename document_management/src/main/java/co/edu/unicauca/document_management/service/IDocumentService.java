package co.edu.unicauca.document_management.service;

import co.edu.unicauca.document_management.entity.Documento;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDocumentService {
    Documento subirDocumento(MultipartFile archivo, Documento documento);
    Documento obtenerDocumento(Long id);
    List<Documento> listarPorDirector(String directorId);
}
