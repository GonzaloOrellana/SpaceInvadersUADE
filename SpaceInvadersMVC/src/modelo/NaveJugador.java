package modelo;

public class NaveJugador extends ObjetoJuego {

    private int vida;
    private static final int VIDA_MAXIMA = 100;

    public NaveJugador(int x, int y, int velocidad) {
        super(x, y, velocidad);
        this.vida = VIDA_MAXIMA;
    }

    public void moverDerecha() {
        if (x + velocidad + ancho <= limiteX) {
            x += velocidad;
            notificarCambio();
        }
    }

    public void moverIzquierda() {
        if (x - velocidad >= 0) {
            x -= velocidad;
            notificarCambio();
        }
    }

    public Rayo disparar() {
        // El rayo sale del centro de la nave
        int rayoX = x + (ancho / 2) - 2; // Ajuste fino para centrar
        int rayoY = y;
        return new Rayo(rayoX, rayoY, 10, false); // Velocidad del rayo, no es enemigo
    }

    public void recibirDano(int cantidad) {
        this.vida -= cantidad;
        if (this.vida < 0) {
            this.vida = 0;
        }
        notificarCambioVida();
    }

    public int getVida() {
        return vida;
    }

    public boolean isDestruido() {
        return vida <= 0;
    }

    public void reiniciarVida() {
        this.vida = VIDA_MAXIMA;
        notificarCambioVida();
    }

    private void notificarCambioVida() {
        if (observador != null) {
            observador.actualizarEstado(vida, isDestruido());
        }
    }
}
