/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.access;

/**
 *
 * @author juan
 */
public class Factory {

    // Instancia única (Singleton)
    private static Factory instance;

    // Repositorios gestionados por la fábrica
    private final IProjectRepositorio projectRepositorio;
    private final IUserRepositorio userRepositorio;

    // Constructor privado: evita creación externa
    private Factory() {
        this.userRepositorio = new UserRepositorio();
        this.projectRepositorio = new ProjectRepositorio();
    }

    // Método público para obtener la instancia única
    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    // Métodos de instancia (no estáticos)
    public IProjectRepositorio getProjectRepository() {
        return projectRepositorio;
    }

    public IUserRepositorio getUserRepository() {
        return userRepositorio;
    }
}
