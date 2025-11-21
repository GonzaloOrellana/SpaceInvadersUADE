package modelo;

/**
 * Enum que define los niveles de dificultad del juego.
 * Cada dificultad tiene una velocidad base diferente para los invasores.
 */
public enum Dificultad {
    CADETE(1), // Dificultad fácil - velocidad base 1
    GUERRERO(2), // Dificultad media - velocidad base 2
    MASTER(3); // Dificultad difícil - velocidad base 3

    private final int velocidadBase;

    /**
     * Constructor del enum.
     * 
     * @param velocidadBase La velocidad base de los invasores para esta dificultad
     */
    Dificultad(int velocidadBase) {
        this.velocidadBase = velocidadBase;
    }

    /**
     * Obtiene la velocidad base para esta dificultad.
     * 
     * @return La velocidad base de los invasores
     */
    public int getVelocidadBase() {
        return velocidadBase;
    }

    /**
     * Obtiene el nombre capitalizado de la dificultad para mostrar en UI.
     * 
     * @return El nombre de la dificultad capitalizado
     */
    public String getNombreCapitalizado() {
        String nombre = this.name();
        return nombre.charAt(0) + nombre.substring(1).toLowerCase();
    }
}
