package vista;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonNeon extends JButton {

    private Color colorNeon;
    private boolean hover;

    public BotonNeon(String texto, Color colorNeon) {
        super(texto);
        this.colorNeon = colorNeon;
        this.hover = false;

        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setFont(new Font("Consolas", Font.BOLD, 24));
        this.setForeground(Color.WHITE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fondo
        if (hover) {
            g2.setColor(new Color(colorNeon.getRed(), colorNeon.getGreen(), colorNeon.getBlue(), 50));
            g2.fillRoundRect(0, 0, width, height, 20, 20);
        }

        // Borde 
        g2.setColor(colorNeon);
        g2.setStroke(new java.awt.BasicStroke(3f));
        g2.drawRoundRect(2, 2, width - 5, height - 5, 20, 20);

        // Texto con Glow effect
        if (hover) {
            g2.setColor(colorNeon);

            int textWidth = g2.getFontMetrics().stringWidth(getText());
            int textX = (width - textWidth) / 2;

            int shipHeight = 16;
            int shipWidth = 16;
            int shipX = textX - shipWidth - 10;
            int shipY = height / 2;

            int[] xPoints = { shipX, shipX + shipWidth, shipX };
            int[] yPoints = { shipY - shipHeight / 2, shipY, shipY + shipHeight / 2 };
            g2.fillPolygon(xPoints, yPoints, 3);

            // Texto Glow
            g2.drawString(getText(), textX, height / 2 + 8);
        }
        
        g2.setColor(Color.WHITE);
        int textWidth = g2.getFontMetrics().stringWidth(getText());
        int textX = (width - textWidth) / 2;
        int textY = height / 2 + 8;
        g2.drawString(getText(), textX, textY);
    }
}
