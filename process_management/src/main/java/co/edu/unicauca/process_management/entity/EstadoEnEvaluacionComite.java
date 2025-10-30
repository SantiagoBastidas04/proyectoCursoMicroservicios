package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */
public class EstadoEnEvaluacionComite implements ProjectState{
    @Override
    public void siguienteEstado(ProjectContext context) {
        // Simular decisión del comité (70% aprobado, 30% correcciones)
        if (Math.random() > 0.3) {
            context.setCurrentState(new EstadoAceptadoComite());
        } else {
            context.setCurrentState(new EstadoCorreccionesComite());
        }
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoPresentadoACoordinador());
    }

    @Override
    public String getEstadoNombre() {
        return "En Evaluación Comité";
    }

    @Override
    public StatusEnum getStatusEnum() {
        return StatusEnum.EN_EVALUACION_COMITE;
    }
}
