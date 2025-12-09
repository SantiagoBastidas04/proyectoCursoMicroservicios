package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoCorreccionesComite implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        context.setCurrentState(new EstadoEnEvaluacionComite());
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoEnEvaluacionComite());
    }

    @Override
    public String getEstadoNombre() {
        return "Correcciones Comité";
    }

    @Override
    public Status getStatusEnum() {
        return Status.CORRECCIONES_COMITE;
    }
}
