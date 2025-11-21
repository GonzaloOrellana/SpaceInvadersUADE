package vista;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FondoEstrellado {

    private List<Point> estrellas;
    private Random random;

    public FondoEstrellado(int width, int height) {
        estrellas = new ArrayList<>();
        random = new Random();
        generarEstrellas(width, height);
    }

    public void generarEstrellas(int width, int height) {
        estrellas.clear();
        for (int i = 0; i < 100; i++) {
            estrellas.add(new Point(random.nextInt(width), random.nextInt(height)));
        }
    }

    public void paint(Graphics g, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;

        // Fondo Gradiente
        GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 30), 0, height, Color.BLACK);
        g2.setPaint(gp);
        g2.fillRect(0, 0, width, height);

        // Estrellas
        g2.setColor(Color.WHITE);
        for (Point p : estrellas) {
            int size = random.nextInt(3) + 1;
            g2.fillOval(p.x, p.y, size, size);
        }
    }
}
