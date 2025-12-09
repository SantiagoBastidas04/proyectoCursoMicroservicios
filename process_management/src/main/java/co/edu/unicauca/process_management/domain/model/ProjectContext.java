package co.edu.unicauca.process_management.domain.model;
/**
 *
 * @author edwin
 */



import co.edu.unicauca.process_management.domain.exception.MaximoIntentosException;
import lombok.Data;

/**
 * Contexto que mantiene una referencia al estado actual
 */
@Data
public class ProjectContext {
    // Getters y Setters
    private ProjectState currentState;
    private Project project;


    public ProjectContext(Project project) {
        this.project = project;
        // Inicializar con el estado correspondiente al StatusEnum del proyecto
        this.currentState = StateFactory.createState(project.getStatus());
    }

    public void siguienteEstado() {
        currentState.siguienteEstado(this);
    }

    public void estadoAnterior() {
        currentState.estadoAnterior(this);
    }

    public void corregir() {
       project.incrementNumberOfAttempts();
       if(project.maxNumberOfAttempts()){
           project.actualizarEstadoDesdeStatePattern(Status.RECHAZADO_COMITE);
           throw new MaximoIntentosException("Máximo de correciones alcanzado");
       }
       project.actualizarEstadoDesdeStatePattern(Status.CORRECCIONES_COMITE);
    }

    public void setCurrentState(ProjectState state) {
        this.currentState = state; 
        this.project.actualizarEstadoDesdeStatePattern(state.getStatusEnum());
    }

    public Project getProject(){
        return project;
    }

}
