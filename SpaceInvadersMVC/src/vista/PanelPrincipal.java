package vista;

import controlador.JuegoController;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelPrincipal extends JPanel {

    private JuegoController controller;
    private Timer timer;
    private JLabel lblPuntaje;
    private JLabel lblNivel;
    private JLabel lblCreditos;
    private JLabel lblVida;
    private FondoEstrellado fondo;
    private Runnable onShowRanking;
    private Runnable onShowMenu;

    public PanelPrincipal(JuegoController controller, Runnable onShowRanking, Runnable onShowMenu) {
        this.controller = controller;
        this.onShowRanking = onShowRanking;
        this.onShowMenu = onShowMenu;
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.fondo = new FondoEstrellado(800, 600);

        inicializarUI();
        inicializarJuego();
        inicializarEventos();

        controller.setCallbackRayosEnemigos(rayo -> {
            ImagenRayoEnemigo imagenRayo = new ImagenRayoEnemigo();
            PanelPrincipal.this.add(imagenRayo);
            PanelPrincipal.this.setComponentZOrder(imagenRayo, 0);
            rayo.setObservador(imagenRayo);
        });

        timer = new Timer(16, new ActionListener() { // ~60 FPS
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarJuego();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            fondo.paint(g, getWidth(), getHeight());
        }
    }

    private void inicializarUI() {
        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setFont(new Font("Arial", Font.BOLD, 14));
        lblPuntaje.setBounds(10, 10, 150, 20);
        this.add(lblPuntaje);

        lblNivel = new JLabel("Nivel: 1");
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setFont(new Font("Arial", Font.BOLD, 14));
        lblNivel.setBounds(170, 10, 100, 20);
        this.add(lblNivel);

        lblCreditos = new JLabel("Creditos: 1");
        lblCreditos.setForeground(Color.WHITE);
        lblCreditos.setFont(new Font("Arial", Font.BOLD, 14));
        lblCreditos.setBounds(280, 10, 100, 20);
        this.add(lblCreditos);

        lblVida = new JLabel("Vida: 100");
        lblVida.setForeground(Color.GREEN);
        lblVida.setFont(new Font("Arial", Font.BOLD, 14));
        lblVida.setBounds(400, 10, 100, 20);
        this.add(lblVida);
    }

    private void inicializarJuego() {
        // Crear Nave Vista
        ImagenNave imagenNave = new ImagenNave();
        imagenNave.setLocation(375, 500);
        this.add(imagenNave);
        controller.setObservadorNave(imagenNave);

        // Crear Invasores
        crearInvasores();

        // Crear Muros
        controller.inicializarMurosConObservadores((x, y) -> {
            ImagenMuro imagenMuro = new ImagenMuro();
            imagenMuro.setLocation(x, y);
            this.add(imagenMuro);
            return imagenMuro;
        });
    }

    private void crearInvasores() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 5; col++) {
                ImagenInvasor imagenInvasor = new ImagenInvasor();
                int x = 50 + col * 60;
                int y = 50 + fila * 50;
                imagenInvasor.setLocation(x, y);
                this.add(imagenInvasor);

                controller.crearInvasor(x, y, imagenInvasor);
            }
        }
    }

    private void inicializarEventos() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (controller.isGameOver())
                    return;

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    controller.moverNaveIzquierda();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    controller.moverNaveDerecha();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    ImagenRayo imagenRayo = new ImagenRayo();
                    PanelPrincipal.this.add(imagenRayo);
                    PanelPrincipal.this.setComponentZOrder(imagenRayo, 0);
                    controller.solicitarDisparo(imagenRayo);
                }
            }
        });
    }

    private void actualizarJuego() {
        if (controller.isGameOver()) {
            timer.stop();
            manejarGameOver();
            return;
        }

        controller.actualizarJuego();

        // Actualizar 
        lblPuntaje.setText("Puntaje: " + controller.getPuntaje());
        lblNivel.setText("Nivel: " + controller.getNivel());
        lblCreditos.setText("Creditos: " + controller.getCreditos());

        int vida = controller.getVidaNave();
        lblVida.setText("Vida: " + vida);
        if (vida > 60) {
            lblVida.setForeground(Color.GREEN);
        } else if (vida > 30) {
            lblVida.setForeground(Color.YELLOW);
        } else {
            lblVida.setForeground(Color.RED);
        }

        if (controller.verificarNivelCompletado()) {
            crearInvasores();
        }
    }

    private void manejarGameOver() {
        // Mostrar panel de Game Over
        PanelGameOver.ResultadoGameOver resultado = PanelGameOver.mostrarDialogo(this, controller.getPuntaje());

        // Guardar puntaje si ingresó nombre
        if (resultado.nombreJugador != null && !resultado.nombreJugador.isEmpty()) {
            System.out.println("Guardando puntaje de " + resultado.nombreJugador + ": " + controller.getPuntaje());
            modelo.GestorRanking.getInstancia().guardarPuntaje(
                    new modelo.Puntaje(resultado.nombreJugador, controller.getPuntaje()));
        }

        if (resultado.verRanking) {
            if (onShowRanking != null)
                onShowRanking.run();
        } else {
            if (onShowMenu != null)
                onShowMenu.run();
        }
    }
}
