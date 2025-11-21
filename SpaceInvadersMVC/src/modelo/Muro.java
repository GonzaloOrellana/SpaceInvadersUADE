package modelo;

public class Muro extends ObjetoJuego {

    private int vida;
    private boolean destruido;

    public Muro(int x, int y) {
        super(x, y, 0); // Velocidad 0, los muros no se mueven
        this.vida = 100;
        this.destruido = false;
    }

    public void recibirDano(int cantidad) {
        if (!destruido) {
            this.vida -= cantidad;
            if (this.vida <= 0) {
                this.vida = 0;
                this.destruido = true;
            }
            notificarCambio();
        }
    }

    @Override
    protected void notificarCambio() {
        super.notificarCambio();
        if (observador != null) {
            observador.actualizarEstado(vida, destruido);
        }
    }

    public int getVida() {
        return vida;
    }

    public boolean isDestruido() {
        return destruido;
    }

    public void reiniciar() {
        this.vida = 100;
        this.destruido = false;
        notificarCambio();
    }
}
