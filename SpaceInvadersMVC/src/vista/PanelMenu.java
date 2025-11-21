package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelMenu extends JPanel {

    private FondoEstrellado fondo;

    public PanelMenu(ActionListener actionJugar, ActionListener actionRanking) {
        this.setLayout(new GridBagLayout());
        this.fondo = new FondoEstrellado(800, 600);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titulo
        JLabel lblTitulo = new JLabel("SPACE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 80));
        lblTitulo.setForeground(Color.CYAN);

        JLabel lblSubtitulo = new JLabel("INVADERS", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Monospaced", Font.BOLD, 80));
        lblSubtitulo.setForeground(Color.CYAN);

        gbc.gridy = 0;
        this.add(lblTitulo, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 50, 0); // Separacion grande
        this.add(lblSubtitulo, gbc);

        // Botones
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.ipadx = 50; // Ancho botones
        gbc.ipady = 15; // Alto botones

        BotonNeon btnJugar = new BotonNeon("JUGAR", new Color(100, 100, 255)); // Azul/Violeta
        btnJugar.addActionListener(actionJugar);
        gbc.gridy = 2;
        this.add(btnJugar, gbc);

        BotonNeon btnRanking = new BotonNeon("RANKING", Color.CYAN);
        btnRanking.addActionListener(actionRanking);
        gbc.gridy = 3;
        this.add(btnRanking, gbc);

        BotonNeon btnSalir = new BotonNeon("SALIR", new Color(255, 50, 150)); // Rosa
        btnSalir.addActionListener(e -> System.exit(0));
        gbc.gridy = 4;
        this.add(btnSalir, gbc);

        // Footer
        gbc.gridy = 5;
        gbc.weighty = 1.0; // Empujar al fondo
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel lblVer = new JLabel("VER 1.0.0");
        lblVer.setForeground(Color.GRAY);
        lblVer.setFont(new Font("Arial", Font.PLAIN, 12));
        this.add(lblVer, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            fondo.paint(g, getWidth(), getHeight());
        }
    }
}
