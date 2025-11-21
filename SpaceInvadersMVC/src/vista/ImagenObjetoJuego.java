package vista;

import modelo.Observador;
import javax.swing.JLabel;

public abstract class ImagenObjetoJuego extends JLabel implements Observador {

    @Override
    public void mover(int x, int y) {
        this.setLocation(x, y);
        this.repaint();
    }

    @Override
    public int getAncho() {
        return this.getWidth();
    }

    @Override
    public int getAlto() {
        return this.getHeight();
    }
}
