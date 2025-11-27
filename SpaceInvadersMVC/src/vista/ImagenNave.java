package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class ImagenNave extends ImagenObjetoJuego {

    private Image imagen;
    private int vida = 100;

    public ImagenNave() {
        this.setSize(50, 30);
        this.setOpaque(false); // Transparente

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Navecita2.png"));
            imagen = icon.getImage();
        } catch (Exception e) {
            System.err.println("Error cargando Navecita2.png: " + e.getMessage());
            this.setBackground(Color.GREEN);
            this.setOpaque(true);
        }
    }

    @Override
    public void actualizarEstado(int vida, boolean destruido) {
        this.vida = vida;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        } else {
            super.paintComponent(g);
        }

        if (vida < 100) {
            int barWidth = getWidth();
            int barHeight = 4;
            int barY = -6; 

            g.setColor(Color.BLACK);
            g.fillRect(0, barY, barWidth, barHeight);

            int vidaWidth = (int) ((vida / 100.0) * barWidth);
            if (vida > 60) {
                g.setColor(Color.GREEN);
            } else if (vida > 30) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.RED);
            }
            g.fillRect(0, barY, vidaWidth, barHeight);
        }
    }
}
