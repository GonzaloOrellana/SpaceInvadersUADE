package modelo;

public class Invasor extends ObjetoJuego {

    public Invasor(int x, int y, int velocidad) {
        super(x, y, velocidad);
    }

    public void mover(int dx, int dy) {
        x += dx;
        y += dy;
        notificarCambio();
    }

    public Rayo disparar() {
        // El rayo sale del centro-abajo del invasor
        int rayoX = x + (ancho / 2) - 2;
        int rayoY = y + alto; // Desde la parte inferior
        return new Rayo(rayoX, rayoY, 5, true); // Velocidad 5, es enemigo
    }

    public void destruir() {
        x = -1000;
        y = -1000;
        notificarCambio();
    }
}
