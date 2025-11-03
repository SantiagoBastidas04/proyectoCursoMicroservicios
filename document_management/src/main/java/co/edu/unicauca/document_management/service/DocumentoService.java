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
public class DocumentoService implements IDocumentService  {

    private final DocumentoRepository repository;

    private final UserRepository userRepository;
    private final SubmissionPublisher submissionPublisher;


    private final Path root = Paths.get("uploads");



    @Override
    public Documento subirDocumento(MultipartFile archivo, Documento documento) {

        //Validar que estudiante y estudiante no tenga el mismo correo
        if(documento.getDirectorId()!=null &&
                documento.getEstudiante1Id()!=null &&
                documento.getDirectorId().equalsIgnoreCase( documento.getEstudiante1Id())){
            throw new CorreosIgualesException("El email del director y estudiante son iguales");
        }

        if(documento.getDirectorId()!=null &&
                documento.getCodirectorId()!=null &&
                documento.getDirectorId().equalsIgnoreCase( documento.getCodirectorId())){
            throw new CorreosIgualesException("El email del director y Codirector son iguales");
        }
        if(documento.getEstudiante1Id()!=null &&
                documento.getEstudiante2Id()!=null &&
                documento.getEstudiante1Id().equalsIgnoreCase(documento.getEstudiante2Id())){
            throw new CorreosIgualesException("Los estudiantes no pueden tener el mismo email");
        }

        validarUsuarioExistente(documento.getDirectorId());
        validarUsuarioExistente(documento.getEstudiante1Id());
        if(documento.getEstudiante2Id() != null  ){
            validarUsuarioExistente(documento.getEstudiante2Id());
        }
        if (documento.getCodirectorId() != null  ){
            validarUsuarioExistente(documento.getCodirectorId());
        }
        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            if (documento instanceof FormatoA formatoA){
                List<FormatoA> versionesPrevias = repository.findByTituloProyectoAndEstudiante1Id(formatoA.getTituloProyecto(),formatoA.getEstudiante1Id());

                if(!versionesPrevias.isEmpty()){
                    int nuevaVersion = versionesPrevias.size() + 1;
                    formatoA.setNumeroVersion(nuevaVersion);
                    if (nuevaVersion > 3) {
                        throw new RuntimeException("Se superó el número máximo de 3 correcciones del Formato A.");
                    }
                }else {
                    formatoA.setNumeroVersion(1);
                    formatoA.setTipo("FORMATO_A");
                }
            }


            String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path ruta = root.resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), ruta);

            documento.setRutaArchivo(ruta.toString());
            documento.setNombreArchivo(nombreArchivo);
            documento.setFechaSubida(LocalDate.now());

            Documento guardado = repository.save(documento);

            submissionPublisher.enviarDocumento(guardado);
            return guardado;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }

    @Override
    public Documento obtenerDocumento(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }

    @Override
    public List<Documento> listarPorDirector(String directorId) {
        return repository.findByDirectorId(directorId);
    }

    private void validarUsuarioExistente(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Correo no especificado, se omite validación");
            return;
        }

        if (!userRepository.existsById(email)) {
            throw new UsuarioNoRegistradoException("El usuario con correo " + email + " no existe.");
        }
    }
}
