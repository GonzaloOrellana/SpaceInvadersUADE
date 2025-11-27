package modelo;

public class Rayo extends ObjetoJuegoActualizable {

    private boolean activo;
    private boolean esEnemigo;

    public Rayo(int x, int y, int velocidad, boolean esEnemigo) {
        super(x, y, velocidad);
        this.activo = true;
        this.esEnemigo = esEnemigo;
    }

    @Override
    public void actualizarPosicion() {
        if (activo) {
            if (esEnemigo) {
                y += velocidad; 
                if (y > limiteY) {
                    activo = false;
                    x = -100;
                    y = -100;
                }
            } else {
                y -= velocidad;
                if (y < 0) {
                    activo = false;
                    x = -100;
                    y = -100;
                }
            }
            notificarCambio();
        }
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isEnemigo() {
        return esEnemigo;
    }

    public void destruir() {
        this.activo = false;
        this.x = -1000;
        this.y = -1000;
        notificarCambio();
    }
}
