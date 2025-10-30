package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */


import lombok.Data;

/**
 * Contexto que mantiene una referencia al estado actual
 */
@Data
public class ProjectContext {
    // Getters y Setters
    private ProjectState currentState;
    private ProjectModel project;
    private int intentosCorreccion;

    public ProjectContext(ProjectModel project) {
        this.project = project;

        this.intentosCorreccion = project.getAtrNumberOfAttempts() != null
                ? project.getAtrNumberOfAttempts() : 0;

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
        if (project.getAtrStatus() == StatusEnum.RECHAZADO_COMITE) {
            System.out.println("El proyecto ya fue rechazado por exceso de correcciones.");
            return;
        }

        intentosCorreccion = project.getAtrNumberOfAttempts() != null
                ? project.getAtrNumberOfAttempts() + 1
                : 1;

        project.setAtrNumberOfAttempts(intentosCorreccion);

        if (intentosCorreccion >= 3) {
            System.out.println(" Se superó el número máximo de correcciones. Proyecto rechazado.");
            setCurrentState(new EstadoRechazadoComite());
        } else {
            System.out.println("Proyecto devuelto para corrección. Intento " + intentosCorreccion);
            setCurrentState(new EstadoCorreccionesComite());
        }
    }

    public void setCurrentState(ProjectState state) {
        this.currentState = state; 
        this.project.setStatus(state.getStatusEnum());
    }

}
