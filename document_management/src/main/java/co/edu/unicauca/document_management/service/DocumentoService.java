package co.edu.unicauca.document_management.service;

import co.edu.unicauca.document_management.entity.Documento;
import co.edu.unicauca.document_management.entity.FormatoA;
import co.edu.unicauca.document_management.exception.CorreosIgualesException;
import co.edu.unicauca.document_management.exception.UsuarioNoRegistradoException;
import co.edu.unicauca.document_management.messaging.SubmissionPublisher;
import co.edu.unicauca.document_management.repository.DocumentoRepository;
import co.edu.unicauca.document_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentoService   {

    private final DocumentoRepository repository;


    public Documento obtenerDocumento(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }

    public List<Documento> listarPorDirector(String directorId) {
        return repository.findByDirectorId(directorId);
    }

}
