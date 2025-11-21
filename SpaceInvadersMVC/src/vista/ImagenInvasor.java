package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class ImagenInvasor extends ImagenObjetoJuego {

    private Image imagen;

    public ImagenInvasor() {
        this.setSize(50, 40);
        this.setOpaque(false);

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/naveAliens.png"));
            imagen = icon.getImage();
        } catch (Exception e) {
            System.err.println("Error cargando naveAliens.png: " + e.getMessage());
            this.setBackground(Color.RED);
            this.setOpaque(true);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        } else {
            super.paintComponent(g);
        }
    }
}
