package co.edu.unicauca.process_management.service;

import co.edu.unicauca.process_management.entity.ModalityEnum;
import co.edu.unicauca.process_management.entity.ProjectContext;
import co.edu.unicauca.process_management.entity.ProjectModel;
import co.edu.unicauca.process_management.entity.StatusEnum;
import co.edu.unicauca.process_management.exception.CorreosIgualesException;
import co.edu.unicauca.process_management.exception.MaximoIntentosException;
import co.edu.unicauca.process_management.exception.UsuarioNoRegistradoException;
import co.edu.unicauca.process_management.repository.ProjectRepository;

import co.edu.unicauca.process_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repository;
    @Autowired
    private UserRepository userRepository;


    /**
     * Crea un nuevo proyecto de grado en estado INICIO
     */
    public ProjectModel crearProyecto(ProjectModel project) {
        //Validar que estudiante y estudiante no tenga el mismo correo
        if(project.getAtrDirectorEmail()!=null &&
        project.getAtrStudent1Email()!=null &&
        project.getAtrDirectorEmail().equalsIgnoreCase(project.getAtrStudent1Email())){
            throw new CorreosIgualesException("El email del director y estudiante son iguales");
        }

        if(project.getAtrDirectorEmail()!=null &&
                project.getAtrCodirectorEmail()!=null &&
                project.getAtrDirectorEmail().equalsIgnoreCase(project.getAtrCodirectorEmail())){
            throw new CorreosIgualesException("El email del director y Codirector son iguales");
        }
        if(project.getAtrStudent1Email()!=null &&
                project.getAtrStudent2Email()!=null &&
                project.getAtrStudent1Email().equalsIgnoreCase(project.getAtrStudent2Email())){
            throw new CorreosIgualesException("Los estudiantes no pueden tener el mismo email");
        }

        validarUsuarioExistente(project.getAtrDirectorEmail());
        validarUsuarioExistente(project.getAtrStudent1Email());
        if(project.getAtrStudent2Email() != null){
            validarUsuarioExistente(project.getAtrStudent2Email());
        }
        project.setAtrStatus(StatusEnum.INICIO);
        project.setAtrNumberOfAttempts(0);
        ProjectModel saved = repository.save(project);
        System.out.println("Proyecto creado en estado: " + saved.getAtrStatus());
        return saved;
    }

    /**
     * Pasa el proyecto al siguiente estado, usando el patrón State
     */
    public ProjectModel avanzarEstado(Long id) {
        ProjectModel model = obtenerProyecto(id);
        ProjectContext context = new ProjectContext(model);

        context.siguienteEstado(); // Avanza según la lógica de cada estado
        ProjectModel actualizado = context.getProject();

        return repository.save(actualizado);
    }

    /**
     * Devuelve el proyecto a un estado anterior (si aplica)
     */
    public ProjectModel retrocederEstado(Long id) {
        ProjectModel model = obtenerProyecto(id);
        ProjectContext context = new ProjectContext(model);

        context.estadoAnterior();
        ProjectModel actualizado = context.getProject();

        return repository.save(actualizado);
    }

    /**
     * Aplica corrección (usa la lógica del contexto: máximo 3 intentos)
     *
     */
    public ProjectModel corregirProyecto(Long id) {

        ProjectModel model = obtenerProyecto(id);

        ProjectContext context = new ProjectContext(model);
        try{
            context.corregir();
        } catch (MaximoIntentosException ex){
            model.setAtrStatus(StatusEnum.RECHAZADO_COMITE);
            repository.save(model);
            throw  ex;
        }

        ProjectModel actualizado = context.getProject();

        return repository.save(actualizado);
    }

    /**
     * Obtiene un proyecto por su ID
     */
    public ProjectModel obtenerProyecto(Long id) {
        Optional<ProjectModel> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Proyecto no encontrado con ID " + id);
        }
        return opt.get();
    }

    /**
     * Lista todos los proyectos
     */
    public List<ProjectModel> listarProyectos() {
        return repository.findAll();
    }

    /**
    *Lista los proyectos por modalidad
    * */

    public List<ProjectModel> listarPorModality(ModalityEnum modality) {
        return repository.findByAtrModality(modality);
    }
    /**
     * Lista los proyectos por Director
     * **/
    public List<ProjectModel> obtenerProyectosPorDirector(String emailDirector) {
        return repository.findByAtrDirectorEmail(emailDirector);
    }
    /**
     * Lista los proyectos por Estudiante
     * **/
    public List<ProjectModel> obtenerProyectosPorEstudiante(String emailEstudiante) {
        return repository.findByAtrStudent1Email(emailEstudiante);
    }

    /**
     * Valida que el Usuario este creado
     * **/
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
