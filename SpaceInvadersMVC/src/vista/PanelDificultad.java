package vista;

import javax.swing.*;
import java.awt.*;

public class PanelDificultad extends JPanel {

    private FondoEstrellado fondo;
    private String dificultadSeleccionada;

    public PanelDificultad(JDialog dialog) {
        this.dificultadSeleccionada = null;
        this.setLayout(new GridBagLayout());
        this.fondo = new FondoEstrellado(500, 400);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("SELECCIONE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 48));
        lblTitulo.setForeground(Color.CYAN);

        JLabel lblSubtitulo = new JLabel("DIFICULTAD", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Monospaced", Font.BOLD, 48));
        lblSubtitulo.setForeground(Color.CYAN);

        gbc.gridy = 0;
        this.add(lblTitulo, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0); 
        this.add(lblSubtitulo, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.ipadx = 60; // ancho
        gbc.ipady = 15; 

        // Botón CADETE 
        BotonNeon btnCadete = new BotonNeon("CADETE", new Color(50, 255, 100));
        btnCadete.addActionListener(e -> {
            dificultadSeleccionada = "CADETE";
            dialog.dispose();
        });
        gbc.gridy = 2;
        this.add(btnCadete, gbc);

        // Botón GUERRERO 
        BotonNeon btnGuerrero = new BotonNeon("GUERRERO", new Color(100, 100, 255));
        btnGuerrero.addActionListener(e -> {
            dificultadSeleccionada = "GUERRERO";
            dialog.dispose();
        });
        gbc.gridy = 3;
        this.add(btnGuerrero, gbc);

        // Botón MASTER 
        BotonNeon btnMaster = new BotonNeon("MASTER", new Color(255, 50, 80));
        btnMaster.addActionListener(e -> {
            dificultadSeleccionada = "MASTER";
            dialog.dispose();
        });
        gbc.gridy = 4;
        this.add(btnMaster, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            fondo.paint(g, getWidth(), getHeight());
        }
    }

    public String getDificultadSeleccionada() {
        return dificultadSeleccionada;
    }

    public static String mostrarDialogo(Component parent) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent),
                "Selección de Dificultad", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);

        PanelDificultad panel = new PanelDificultad(dialog);
        dialog.add(panel);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return panel.getDificultadSeleccionada();
    }
}
