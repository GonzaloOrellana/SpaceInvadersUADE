package modelo;

public abstract class ObjetoJuego {
    protected int x;
    protected int y;
    protected int ancho;
    protected int alto;
    protected int velocidad;
    protected int limiteX;
    protected int limiteY;
    protected Observador observador;

    public ObjetoJuego(int x, int y, int velocidad) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
    }

    public void setObservador(Observador observador) {
        this.observador = observador;
        if (observador != null) {
            this.ancho = observador.getAncho();
            this.alto = observador.getAlto();
            notificarCambio();
        }
    }

    public void setLimites(int limiteX, int limiteY) {
        this.limiteX = limiteX;
        this.limiteY = limiteY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    protected void notificarCambio() {
        if (observador != null) {
            observador.mover(x, y);
        }
    }
}
