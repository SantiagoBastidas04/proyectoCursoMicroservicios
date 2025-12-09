package co.edu.unicauca.process_management.adapter;

import co.edu.unicauca.process_management.adapter.config.RabbitConfig;
import co.edu.unicauca.process_management.domain.model.Project;
import co.edu.unicauca.process_management.port.out.ProjectRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RabbitMQDocumentConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQDocumentConsumer.class);


    private final ProjectRepositoryPort projectRepositoryPort;

    public RabbitMQDocumentConsumer(ProjectRepositoryPort projectRepositoryPort) {
        this.projectRepositoryPort = projectRepositoryPort;
    }

    @RabbitListener(queues = RabbitConfig.DOCUMENT_QUEUE)
    public void recibirDocumento(DocumentUploadDTO documento) {
        logger.info("Documento recivido: tipo={}, ruta={}",documento.getTipoDocumento(),documento.getRutaArchivo());

        Optional<Project> projectOptional =
                projectRepositoryPort.findByDirectorEmailAndStudent1Email(documento.getDirectorEmail(),
                        documento.getEstudianteEmail());

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            if ("FORMATO_A".equalsIgnoreCase(documento.getTipoDocumento())) {
                project.registrarFormatoA(documento.getRutaArchivo());
            } else if ("ANTEPROYECTO".equalsIgnoreCase(documento.getTipoDocumento())) {
                project.registrarAnteproyecto(documento.getRutaArchivo());
            }
            projectRepositoryPort.save(project);
            logger.info("Documento asociado  correctamente al proyecto del estudiante {}", documento.getEstudianteEmail());
        }else{
            logger.warn("No se encontró proyecto para director={} y estudiante={}",
                    documento.getDirectorEmail(), documento.getEstudianteEmail());
        }
    }

}
