package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */
public class EstadoEvaluadorPideCorrecciones implements ProjectState{
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
    public StatusEnum getStatusEnum() {
        return StatusEnum.EVALUADOR_PIDE_CORRECCIONES;
    }
}
