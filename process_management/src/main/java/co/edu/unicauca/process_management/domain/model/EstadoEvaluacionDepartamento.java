package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoEvaluacionDepartamento implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        // Simular decisión del departamento
        if (Math.random() > 0.4) {
            context.setCurrentState(new EstadoEvaluadorAcepta());
        } else {
            context.setCurrentState(new EstadoEvaluadorPideCorrecciones());
        }
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoPresentadoJefatura());
    }

    @Override
    public String getEstadoNombre() {
        return "Evaluación Departamento";
    }

    @Override
    public Status getStatusEnum() {
        return Status.EVALUACION_DEPARTAMENTO;
    }
}
