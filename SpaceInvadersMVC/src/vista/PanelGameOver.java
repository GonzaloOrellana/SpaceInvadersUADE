package vista;

import javax.swing.*;
import java.awt.*;

public class PanelGameOver extends JPanel {

    private FondoEstrellado fondo;
    private JTextField txtNombre;
    private String nombreJugador;
    private boolean verRanking;
    private JDialog dialog;

    public PanelGameOver(JDialog dialog, int puntajeFinal) {
        this.dialog = dialog;
        this.nombreJugador = null;
        this.verRanking = false;
        this.setLayout(new GridBagLayout());
        this.fondo = new FondoEstrellado(600, 550);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título GAME OVER
        JLabel lblGameOver = new JLabel("GAME", SwingConstants.CENTER);
        lblGameOver.setFont(new Font("Monospaced", Font.BOLD, 72));
        lblGameOver.setForeground(new Color(255, 50, 80)); 

        JLabel lblOver = new JLabel("OVER", SwingConstants.CENTER);
        lblOver.setFont(new Font("Monospaced", Font.BOLD, 72));
        lblOver.setForeground(new Color(255, 50, 80));

        gbc.gridy = 0;
        this.add(lblGameOver, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 30, 0);
        this.add(lblOver, gbc);

        // Puntaje Final
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblPuntaje = new JLabel("PUNTAJE FINAL: " + puntajeFinal, SwingConstants.CENTER);
        lblPuntaje.setFont(new Font("Monospaced", Font.BOLD, 28));
        lblPuntaje.setForeground(Color.CYAN);
        this.add(lblPuntaje, gbc);

        // Campo de texto para nombre
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 40, 5, 40);
        JLabel lblNombre = new JLabel("INGRESE SU NOMBRE:", SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);
        this.add(lblNombre, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(5, 40, 20, 40);
        txtNombre = new JTextField(15);
        txtNombre.setFont(new Font("Consolas", Font.PLAIN, 20));
        txtNombre.setHorizontalAlignment(JTextField.CENTER);
        txtNombre.setBackground(new Color(20, 20, 40));
        txtNombre.setForeground(Color.CYAN);
        txtNombre.setCaretColor(Color.CYAN);
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.CYAN, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        txtNombre.addActionListener(e -> guardarYContinuar(true));

        this.add(txtNombre, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.ipadx = 50;
        gbc.ipady = 15;

        BotonNeon btnRanking = new BotonNeon("VER RANKING", Color.CYAN);
        btnRanking.addActionListener(e -> guardarYContinuar(true));
        gbc.gridy = 5;
        this.add(btnRanking, gbc);

        BotonNeon btnMenu = new BotonNeon("MENU PRINCIPAL", new Color(100, 100, 255));
        btnMenu.addActionListener(e -> guardarYContinuar(false));
        gbc.gridy = 6;
        this.add(btnMenu, gbc);

        BotonNeon btnSalir = new BotonNeon("SALIR", new Color(255, 50, 150));
        btnSalir.addActionListener(e -> {
            guardarPuntaje();
            System.exit(0);
        });
        gbc.gridy = 7;
        this.add(btnSalir, gbc);

        // Dar foco al campo de texto
        SwingUtilities.invokeLater(() -> txtNombre.requestFocusInWindow());
    }

    private void guardarYContinuar(boolean mostrarRanking) {
        guardarPuntaje();
        this.verRanking = mostrarRanking;
        dialog.dispose();
    }

    private void guardarPuntaje() {
        String nombre = txtNombre.getText().trim();
        if (!nombre.isEmpty()) {
            this.nombreJugador = nombre;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            fondo.paint(g, getWidth(), getHeight());
        }
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public boolean isVerRanking() {
        return verRanking;
    }

    public static class ResultadoGameOver {
        public final String nombreJugador;
        public final boolean verRanking;

        public ResultadoGameOver(String nombreJugador, boolean verRanking) {
            this.nombreJugador = nombreJugador;
            this.verRanking = verRanking;
        }
    }

    public static ResultadoGameOver mostrarDialogo(Component parent, int puntajeFinal) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent),
                "Game Over", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(false);

        PanelGameOver panel = new PanelGameOver(dialog, puntajeFinal);
        dialog.add(panel);
        dialog.setSize(600, 650);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return new ResultadoGameOver(panel.getNombreJugador(), panel.isVerRanking());
    }
}
