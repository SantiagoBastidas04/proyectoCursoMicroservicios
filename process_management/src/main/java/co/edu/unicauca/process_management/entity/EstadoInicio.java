package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */
public class EstadoInicio implements ProjectState{
    @Override
    public void siguienteEstado(ProjectContext context) {
        context.setCurrentState(new EstadoFormatoADiligenciado());
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        // No hay estado anterior al inicio
    }

    @Override
    public String getEstadoNombre() {
        return "Inicio";    
    }

    @Override
    public StatusEnum getStatusEnum() {
        return StatusEnum.INICIO;
    }
}
