package vista;

import modelo.Observador;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.AlphaComposite;

public class ImagenMuro extends JPanel implements Observador {

    private Image imagen;
    private int vida = 100;
    private boolean destruido = false;

    public ImagenMuro() {
        this.setSize(60, 40);
        this.setOpaque(false); // Transparente para que se vea el fondo

        try {
            // Cargar imagen desde el classpath (src)
            ImageIcon icon = new ImageIcon(getClass().getResource("/Muro.png"));
            imagen = icon.getImage();
        } catch (Exception e) {
            System.err.println("Error cargando Muro.png: " + e.getMessage());
            this.setBackground(Color.GREEN);
            this.setOpaque(true);
        }
    }

    @Override
    public void mover(int x, int y) {
        this.setLocation(x, y);
    }

    @Override
    public int getAncho() {
        return getWidth();
    }

    @Override
    public int getAlto() {
        return getHeight();
    }

    @Override
    public void actualizarEstado(int vida, boolean destruido) {
        this.vida = vida;
        this.destruido = destruido;

        if (destruido) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (destruido) {
            return;
        }

        if (imagen != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Aplicar transparencia según daño
            float porcentajeVida = vida / 100.0f;
            float alpha = Math.max(0.3f, porcentajeVida); // Mínimo 30% opacidad
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            // Dibujar imagen
            g2d.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

            // Aplicar tinte de color según vida
            if (porcentajeVida <= 0.75) {
                Color tintColor;
                if (porcentajeVida > 0.5) {
                    tintColor = new Color(255, 255, 0, 50); // Amarillo semi-transparente
                } else if (porcentajeVida > 0.25) {
                    tintColor = new Color(255, 165, 0, 80); // Naranja semi-transparente
                } else {
                    tintColor = new Color(255, 0, 0, 100); // Rojo semi-transparente
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                g2d.setColor(tintColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            g2d.dispose();
        } else {
            // Fallback si no se cargó la imagen
            float porcentajeVida = vida / 100.0f;
            if (porcentajeVida > 0.75) {
                g.setColor(Color.GREEN);
            } else if (porcentajeVida > 0.5) {
                g.setColor(Color.YELLOW);
            } else if (porcentajeVida > 0.25) {
                g.setColor(Color.ORANGE);
            } else {
                g.setColor(Color.RED);
            }
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
