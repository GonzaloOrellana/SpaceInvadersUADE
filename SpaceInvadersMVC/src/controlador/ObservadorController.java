package controlador;

import modelo.Observador;

/**
 * Interfaz del controlador que extiende Observador del modelo.
 * Actúa como puente entre la vista y el modelo, permitiendo que la vista
 * implemente interfaces del controlador en lugar de importar directamente del
 * modelo.
 * Esto asegura el cumplimiento del patrón MVC.
 */
public interface ObservadorController extends Observador {
    // Esta interfaz hereda todos los métodos de Observador sin agregar nuevos
    // métodos
    // Su propósito es permitir que la vista dependa del controlador en lugar del
    // modelo
}
