package co.edu.unicauca.process_management.domain.model;


import co.edu.unicauca.process_management.domain.exception.CorreosIgualesException;

import java.time.LocalDate;


public class Project {

    private Long atrId;
    private String atrTitle;
    private String atrDirectorEmail;
    private String atrCodirectorEmail;
    private String atrStudent1Email;
    private String atrStudent2Email;
    private Modality atrModality;
    private LocalDate atrCreationDate;
    private String atrGeneralObjective;
    private String atrSpecificObjectives;
    private Status atrStatus;
    private Integer atrNumberOfAttempts;
    private String atrObservations;
    private String rutaFormatoA;
    private String rutaAnteproyecto;
    private String rutaCartaAceptacion;


    public Status getStatus()
    {
        return this.atrStatus;
    }

    public void setStatus(Status prmStatus)
    {
        this.atrStatus = prmStatus;
    }

    public int getAtrNumberOfAttempts() {
        return atrNumberOfAttempts;
    }

    public void incrementNumberOfAttempts(){
        this.atrNumberOfAttempts++;
    }

    public void resetNumberOfAttempts(){
        this.atrNumberOfAttempts = 0;
    }

    public boolean maxNumberOfAttempts(){
        return atrNumberOfAttempts >=3;
    }

    public boolean directorEqualsStudent(){
        return atrDirectorEmail != null && atrDirectorEmail.equalsIgnoreCase(atrStudent1Email);
    }

    public boolean duplicatedStudents(){
        return atrStudent1Email != null &&
                atrStudent1Email.equalsIgnoreCase(atrStudent2Email);
    }

    public String getAtrStudent1Email() {
        return atrStudent1Email;
    }

    public String getAtrDirectorEmail() {
        return atrDirectorEmail;
    }

    public String getAtrCodirectorEmail() {
        return atrCodirectorEmail;
    }

    public String getAtrStudent2Email() {
        return atrStudent2Email;
    }

    public Long getAtrId() {
        return atrId;
    }

    public String getAtrTitle() {
        return atrTitle;
    }

    public Modality getAtrModality() {
        return atrModality;
    }

    public LocalDate getAtrCreationDate() {
        return atrCreationDate;
    }

    public String getAtrGeneralObjective() {
        return atrGeneralObjective;
    }

    public String getAtrSpecificObjectives() {
        return atrSpecificObjectives;
    }

    public Status getAtrStatus() {
        return atrStatus;
    }

    public String getAtrObservations() {
        return atrObservations;
    }

    public String getRutaFormatoA() {
        return rutaFormatoA;
    }

    public String getRutaAnteproyecto() {
        return rutaAnteproyecto;
    }

    public String getRutaCartaAceptacion() {
        return rutaCartaAceptacion;
    }

    // ----------------------------
    //   MÉTODOS "SET" CORRECTOS PERO RENOMBRADOS + LÓGICA
    // ----------------------------
    public void asignarIdDesdePersistencia(Long id) {
        this.atrId = id;
    }

    // NOMBRE NUEVO: definirTitulo
    public void definirTitulo(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.atrTitle = title.trim();
    }


    // NOMBRE NUEVO: asignarDirector
    public void asignarDirector(String emailDirector) {
        validarCorreoDiferente(emailDirector, this.atrStudent1Email);
        validarCorreoDiferente(emailDirector, this.atrStudent2Email);

        // Regla: no se puede cambiar director si ya existía
        if (this.atrDirectorEmail != null && !this.atrDirectorEmail.equals(emailDirector)) {
            throw new IllegalStateException("No se puede cambiar el director después de creado el proyecto.");
        }

        this.atrDirectorEmail = emailDirector;
    }


    // NOMBRE NUEVO: asignarCoDirector
    public void asignarCoDirector(String emailCodirector) {
        validarCorreoDiferente(emailCodirector, this.atrDirectorEmail);
        validarCorreoDiferente(emailCodirector, this.atrStudent1Email);
        validarCorreoDiferente(emailCodirector, this.atrStudent2Email);

        this.atrCodirectorEmail = emailCodirector;
    }


    // NOMBRE NUEVO: registrarEstudiantePrincipal
    public void registrarEstudiantePrincipal(String email) {
        validarCorreoDiferente(email, this.atrDirectorEmail);
        validarCorreoDiferente(email, this.atrStudent2Email);

        this.atrStudent1Email = email;
    }


    // NOMBRE NUEVO: registrarEstudianteSecundario
    public void registrarEstudianteSecundario(String email) {
        validarCorreoDiferente(email, this.atrDirectorEmail);
        validarCorreoDiferente(email, this.atrStudent1Email);

        this.atrStudent2Email = email;
    }


    // NOMBRE NUEVO: definirModalidad
    public void definirModalidad(Modality modality) {
        if (modality == null) {
            throw new IllegalArgumentException("La modalidad no puede ser nula.");
        }

        // Regla: no se puede cambiar modalidad luego de creado
        if (this.atrModality != null && this.atrCreationDate != null) {
            throw new IllegalStateException("No se puede cambiar la modalidad después de creado el proyecto.");
        }

        this.atrModality = modality;
    }


    // NOMBRE NUEVO: establecerEstadoInicial
    public void establecerEstadoInicial(Status status) {
        if (this.atrStatus != null) {
            throw new IllegalStateException("El estado inicial solo puede establecerse una vez.");
        }
        this.atrStatus = status;
    }


    // NOMBRE NUEVO: actualizarEstadoDesdeStatePattern
    public void actualizarEstadoDesdeStatePattern(Status nuevoEstado) {
        this.atrStatus = nuevoEstado;
    }


    // NOMBRE NUEVO: iniciarFechaCreacion
    public void iniciarFechaCreacion(LocalDate fecha) {
        if (this.atrCreationDate != null) {
            throw new IllegalStateException("La fecha de creación ya fue definida.");
        }
        this.atrCreationDate = fecha;
    }


    public void cargarObjetivoGeneralDesdePersistencia(String objGeneral) {
        this.atrGeneralObjective = objGeneral;
    }


    public void cargarObjetivosEspecificosDesdePersistencia(String objEspecificos) {
        this.atrSpecificObjectives = objEspecificos;
    }

    // ----------------------------
    //  DOCUMENTOS (Formato A, Carta, Anteproyecto)
    // ----------------------------

    // NOMBRE NUEVO: registrarFormatoA
    public void registrarFormatoA(String ruta) {
        if (this.rutaFormatoA != null) {
            agregarObservacion("Se reemplazó el Formato A el " + LocalDate.now());
        }
        this.rutaFormatoA = ruta;
    }

    // NOMBRE NUEVO: registrarAnteproyecto
    public void registrarAnteproyecto(String ruta) {
        if (this.rutaAnteproyecto != null) {
            agregarObservacion("Se reemplazó el Anteproyecto el " + LocalDate.now());
        }
        this.rutaAnteproyecto = ruta;
    }

    // NOMBRE NUEVO: registrarCartaAceptacion
    public void registrarCartaAceptacion(String ruta) {
        if (this.rutaCartaAceptacion != null) {
            agregarObservacion("Se reemplazó la Carta de Aceptación el " + LocalDate.now());
        }
        this.rutaCartaAceptacion = ruta;
    }


    // ----------------------------
    //   INTENTOS PARA CORRECCIONES
    // ----------------------------

    // NOMBRE NUEVO: reiniciarIntentos
    public void reiniciarIntentos() {
        this.atrNumberOfAttempts = 0;
    }

    // NOMBRE NUEVO: aumentarIntentos
    public void aumentarIntentos() {
        this.atrNumberOfAttempts++;
    }
    public void asignarIntentosDesdePersistencia(int intentos) {
        this.atrNumberOfAttempts = intentos;
    }

    // ----------------------------
    //      OBSERVACIONES
    // ----------------------------
    public void agregarObservacion(String texto) {
        if (this.atrObservations == null) {
            this.atrObservations = texto;
        } else {
            this.atrObservations += "\n" + texto;
        }
    }


    // ----------------------------
    //         LÓGICA COMÚN
    // ----------------------------
    private void validarCorreoDiferente(String c1, String c2) {
        if (c1 != null && c2 != null && c1.equalsIgnoreCase(c2)) {
            throw new CorreosIgualesException("Dos correos no pueden ser iguales: " + c1);
        }
    }
}
