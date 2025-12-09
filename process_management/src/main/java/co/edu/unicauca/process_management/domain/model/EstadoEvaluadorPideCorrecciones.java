package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoEvaluadorPideCorrecciones implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        context.setCurrentState(new EstadoEvaluacionDepartamento());
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoEvaluacionDepartamento());
    }

    @Override
    public String getEstadoNombre() {
        return "Evaluador Pide Correcciones";
    }

    @Override
    public Status getStatusEnum() {
        return Status.EVALUADOR_PIDE_CORRECCIONES;
    }
}
