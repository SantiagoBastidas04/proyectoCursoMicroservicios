package co.edu.unicauca.process_management.entity;
 /*
 * @author edwin
 */

/**
 * Factory para crear estados basados en StatusEnum
 */
public class StateFactory {
    public static ProjectState createState(StatusEnum status) {
        if (status == null) {
            return new EstadoInicio();
        }
        
        switch(status) {
            case INICIO: return new EstadoInicio();
            case FORMATO_A_DILIGENCIADO: return new EstadoFormatoADiligenciado();
            case PRESENTADO_A_COORDINADOR: return new EstadoPresentadoACoordinador();
            case EN_EVALUACION_COMITE: return new EstadoEnEvaluacionComite();
            case CORRECCIONES_COMITE: return new EstadoCorreccionesComite();
            case ACEPTADO_COMITE: return new EstadoAceptadoComite();
            case RECHAZADO_COMITE: return new EstadoRechazadoComite();
            case ESCRIBIENDO_ANTEPROYECTO: return new EstadoEscribiendoAnteproyecto();
            case PRESENTADO_JEFATURA: return new EstadoPresentadoJefatura();
            case EVALUACION_DEPARTAMENTO: return new EstadoEvaluacionDepartamento();
            case EVALUADOR_PIDE_CORRECCIONES: return new EstadoEvaluadorPideCorrecciones();
            case EVALUADOR_ACEPTA: return new EstadoEvaluadorAcepta();
            case EVALUADOR_RECHAZA: return new EstadoEvaluadorRechaza();

            
            // Estados legacy para compatibilidad
            case FIRST: 
            case SECOND: 
            case THIRD: return new EstadoCorreccionesComite();
            case ACCEPTED: return new EstadoAceptadoComite();
            case REJECTED: return new EstadoRechazadoComite();
                
            default: return new EstadoInicio();
        }
    }
}
