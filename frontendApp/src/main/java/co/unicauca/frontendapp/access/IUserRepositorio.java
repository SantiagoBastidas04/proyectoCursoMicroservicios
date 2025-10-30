/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.unicauca.frontendapp.access;
import co.unicauca.frontendapp.entities.User;


/**
 *
 * @author Santiago Bastidas
 */
public interface IUserRepositorio {
    boolean guardar(User usuario);
    User iniciarSesion(String email,String contrasenia);
}
