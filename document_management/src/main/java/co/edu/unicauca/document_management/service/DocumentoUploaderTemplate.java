package co.edu.unicauca.document_management.service;

import co.edu.unicauca.document_management.entity.Documento;
import co.edu.unicauca.document_management.exception.CorreosIgualesException;
import co.edu.unicauca.document_management.exception.UsuarioNoRegistradoException;
import co.edu.unicauca.document_management.messaging.SubmissionPublisher;
import co.edu.unicauca.document_management.repository.DocumentoRepository;
import co.edu.unicauca.document_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public abstract class DocumentoUploaderTemplate {
    protected final DocumentoRepository repository;
    protected final UserRepository userRepository;
    protected final SubmissionPublisher publisher;


    private final Path root = Paths.get("uploads");

    // === MÉTODO PLANTILLA ===
    public final Documento subirDocumento(MultipartFile archivo, Documento documento) {
        validarCorreos(documento);
        validarUsuarios(documento);
        prepararDocumento(documento);
        guardarArchivo(archivo, documento);
        Documento guardado = repository.save(documento);
        publisher.enviarDocumento(guardado);
        return guardado;
    }
    // --- Métodos comunes ---
    protected void validarCorreos(Documento doc) {
        if (doc.getDirectorId() != null &&
                doc.getEstudiante1Id() != null &&
                doc.getDirectorId().equalsIgnoreCase(doc.getEstudiante1Id())) {
            throw new CorreosIgualesException("El email del director y estudiante son iguales");
        }

        if (doc.getDirectorId() != null &&
                doc.getCodirectorId() != null &&
                doc.getDirectorId().equalsIgnoreCase(doc.getCodirectorId())) {
            throw new CorreosIgualesException("El email del director y codirector son iguales");
        }

        if (doc.getEstudiante1Id() != null &&
                doc.getEstudiante2Id() != null &&
                doc.getEstudiante1Id().equalsIgnoreCase(doc.getEstudiante2Id())) {
            throw new CorreosIgualesException("Los estudiantes no pueden tener el mismo email");
        }
    }

    protected void validarUsuarios(Documento doc) {
        validarUsuarioExistente(doc.getDirectorId());
        validarUsuarioExistente(doc.getEstudiante1Id());
        if (doc.getEstudiante2Id() != null) validarUsuarioExistente(doc.getEstudiante2Id());
        if (doc.getCodirectorId() != null) validarUsuarioExistente(doc.getCodirectorId());
    }

    protected void validarUsuarioExistente(String email) {
        if (email == null || email.trim().isEmpty()) return;
        if (!userRepository.existsById(email)) {
            throw new UsuarioNoRegistradoException("El usuario con correo " + email + " no existe.");
        }
    }
    protected void guardarArchivo(MultipartFile archivo, Documento documento) {
        try {
            if (!Files.exists(root)) Files.createDirectories(root);

            String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path ruta = root.resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);

            documento.setRutaArchivo(ruta.toString());
            documento.setNombreArchivo(nombreArchivo);
            documento.setFechaSubida(LocalDate.now());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }
    // --- Método abstracto (cada subclase lo define) ---
    protected abstract void prepararDocumento(Documento documento);
}
