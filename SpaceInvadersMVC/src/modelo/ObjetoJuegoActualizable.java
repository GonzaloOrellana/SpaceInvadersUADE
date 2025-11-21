package modelo;

public abstract class ObjetoJuegoActualizable extends ObjetoJuego {

    public ObjetoJuegoActualizable(int x, int y, int velocidad) {
        super(x, y, velocidad);
    }

    public abstract void actualizarPosicion();
}
