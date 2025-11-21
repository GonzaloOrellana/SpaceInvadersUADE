package modelo;

public interface Observador {
    void mover(int x, int y);

    int getAncho();

    int getAlto();

    // Método para notificar cambios de estado (vida, destrucción, etc.)
    default void actualizarEstado(int vida, boolean destruido) {
        // Implementación por defecto vacía para clases que no lo necesiten
    }
}
