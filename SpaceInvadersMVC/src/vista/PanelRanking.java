package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelRanking extends JPanel {

    public PanelRanking(controlador.RankingController controller, ActionListener actionVolver) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);

        JLabel lblTitulo = new JLabel("TOP 10 MEJORES PUNTAJES", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.CYAN);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(Color.BLACK);

        if (!controller.hayPuntajes()) {
            JLabel lblVacio = new JLabel("Aun no hay puntajes registrados.");
            lblVacio.setForeground(Color.WHITE);
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 18));
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelLista.add(lblVacio);
        } else {
            List<String> top10 = controller.obtenerTop10Formateado();
            for (String linea : top10) {
                JLabel lblPuntaje = new JLabel(linea);
                lblPuntaje.setForeground(Color.WHITE);
                lblPuntaje.setFont(new Font("Monospaced", Font.PLAIN, 20));
                lblPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);
                panelLista.add(lblPuntaje);
                panelLista.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        this.add(new JScrollPane(panelLista), BorderLayout.CENTER);

        JButton btnVolver = new JButton("VOLVER AL MENU");
        estilizarBoton(btnVolver);
        btnVolver.addActionListener(actionVolver);

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(Color.BLACK);
        panelBoton.add(btnVolver);
        this.add(panelBoton, BorderLayout.SOUTH);
    }

    private void estilizarBoton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
