package modelo;

/* Enum que define los niveles de dificultad del juego. */
public enum Dificultad {
    CADETE(1), 
    GUERRERO(2),
    MASTER(3); 

    private final int velocidadBase;

    Dificultad(int velocidadBase) {
        this.velocidadBase = velocidadBase;
    }

    public int getVelocidadBase() {
        return velocidadBase;
    }

    public String getNombreCapitalizado() {
        String nombre = this.name();
        return nombre.charAt(0) + nombre.substring(1).toLowerCase();
    }
}
