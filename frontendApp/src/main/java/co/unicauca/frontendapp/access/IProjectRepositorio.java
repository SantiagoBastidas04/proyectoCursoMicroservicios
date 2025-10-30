/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.unicauca.frontendapp.access;

import co.unicauca.frontendapp.entities.ProjectModel;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IProjectRepositorio {
    boolean registrarProyecto(ProjectModel proyecto);
    public List<ProjectModel> listarTodos();
    public ProjectModel buscarPorId(Long id);
    public List<ProjectModel> listarPorEmail(String emailProfesor);
    public List<ProjectModel> listarPendientes();
    public List<ProjectModel> listarPorEmailEstudiante(String emailEstudiante);
}
