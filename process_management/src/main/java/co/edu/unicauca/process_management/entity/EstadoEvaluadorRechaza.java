package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */
public class EstadoEvaluadorRechaza implements ProjectState{
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
    public StatusEnum getStatusEnum() {
        return StatusEnum.EVALUADOR_RECHAZA;
    }
    
}
