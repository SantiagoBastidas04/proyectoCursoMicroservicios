package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoEvaluadorRechaza implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        // Estado final - no hay siguiente estado
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoEvaluacionDepartamento());
    }

    @Override
    public String getEstadoNombre() {
        return "Evaluador Rechaza";
    }

    @Override
    public Status getStatusEnum() {
        return Status.EVALUADOR_RECHAZA;
    }
    
}
