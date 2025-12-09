package co.edu.unicauca.process_management.domain.service;

public class NotificationDomainService {

    public void notificar(String mensaje) {
        // dominio NO envía correos → solo genera evento
        System.out.println("[EVENTO DOMINIO] " + mensaje);
    }
}

