package vista;

import controlador.ObservadorController;
import javax.swing.JLabel;

public abstract class ImagenObjetoJuego extends JLabel implements ObservadorController {

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
