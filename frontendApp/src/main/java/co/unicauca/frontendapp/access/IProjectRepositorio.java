/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.unicauca.frontendapp.access;

import co.unicauca.frontendapp.entities.ProjectModel;
import co.unicauca.frontendapp.entities.StatusEnum;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IProjectRepositorio {
    boolean registrarProyecto(ProjectModel proyecto);
    public List<ProjectModel> listarTodos();
    public List<ProjectModel> listarPorEstado(StatusEnum estado);
    public ProjectModel buscarPorId(Long id);
    public List<ProjectModel> listarPorEmailProfesor(String emailProfesor);
    public List<ProjectModel> listarPendientes();
    public List<ProjectModel> listarPorEmailEstudiante(String emailEstudiante);
    public boolean avanzarEstado(ProjectModel proyecto);
    public boolean retrocederEstado(ProjectModel proyecto);
    public boolean corregirEstado(ProjectModel proyecto);
    public boolean actualizarProyecto(ProjectModel proyecto);
    public List<ProjectModel> listarPorEstadoYCorreo(StatusEnum estado, String Correo);
}
