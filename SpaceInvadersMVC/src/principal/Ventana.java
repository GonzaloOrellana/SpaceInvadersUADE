package principal;

import controlador.JuegoController;
import modelo.Espacio;
import modelo.NaveJugador;
import vista.PanelMenu;
import vista.PanelPrincipal;
import vista.PanelRanking;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class Ventana extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Ventana() {
        this.setTitle("Space Invaders MVC");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Inicializar Menu
        PanelMenu panelMenu = new PanelMenu(
                e -> mostrarJuego(),
                e -> mostrarRanking());
        mainPanel.add(panelMenu, "MENU");

        // Inicializar Ranking
        controlador.RankingController rankingController = new controlador.RankingController();
        PanelRanking panelRanking = new PanelRanking(rankingController, e -> mostrarMenu());
        mainPanel.add(panelRanking, "RANKING");

        this.add(mainPanel);
        this.setVisible(true);
    }

    private void mostrarMenu() {
        cardLayout.show(mainPanel, "MENU");
    }

    private void mostrarRanking() {
        // Recrear panel ranking para actualizar lista
        for (java.awt.Component comp : mainPanel.getComponents()) {
            if (comp instanceof PanelRanking) {
                mainPanel.remove(comp);
                break;
            }
        }
        controlador.RankingController rankingController = new controlador.RankingController();
        PanelRanking panelRanking = new PanelRanking(rankingController, e -> mostrarMenu());
        mainPanel.add(panelRanking, "RANKING");

        cardLayout.show(mainPanel, "RANKING");
    }

    private void mostrarJuego() {
        // Mostrar diálogo de selección de dificultad con estética del juego
        String dificultadStr = vista.PanelDificultad.mostrarDialogo(this);

        // Si el usuario cancela, volver al menú
        if (dificultadStr == null) {
            return;
        }

        // Convertir String a Enum
        modelo.Dificultad dificultad = modelo.Dificultad.valueOf(dificultadStr);

        // Inicializar Modelo con dificultad seleccionada
        Espacio espacio = new Espacio(dificultad);
        NaveJugador nave = new NaveJugador(375, 500, 5); // Centro abajo, velocidad 5
        nave.setLimites(800, 600); // Establecer límites de la ventana
        espacio.setNave(nave);

        // Inicializar Controlador
        JuegoController controller = new JuegoController(espacio);

        // Inicializar Vista (Juego)
        PanelPrincipal panelJuego = new PanelPrincipal(
                controller,
                () -> mostrarRanking(),
                () -> mostrarMenu());

        mainPanel.add(panelJuego, "JUEGO");
        cardLayout.show(mainPanel, "JUEGO");

        panelJuego.requestFocusInWindow();
    }
}
