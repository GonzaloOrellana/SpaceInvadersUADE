package vista;

import controlador.JuegoController;
import controlador.RankingController;

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

        PanelMenu panelMenu = new PanelMenu(
                e -> mostrarJuego(),
                e -> mostrarRanking());
        mainPanel.add(panelMenu, "MENU");

        RankingController rankingController = new RankingController();
        PanelRanking panelRanking = new PanelRanking(rankingController, e -> mostrarMenu());
        mainPanel.add(panelRanking, "RANKING");

        this.add(mainPanel);
        this.setVisible(true);
    }

    private void mostrarMenu() {
        cardLayout.show(mainPanel, "MENU");
    }

    private void mostrarRanking() {
        // actualizar lista
        for (java.awt.Component comp : mainPanel.getComponents()) {
            if (comp instanceof PanelRanking) {
                mainPanel.remove(comp);
                break;
            }
        }
        RankingController rankingController = new RankingController();
        PanelRanking panelRanking = new PanelRanking(rankingController, e -> mostrarMenu());
        mainPanel.add(panelRanking, "RANKING");

        cardLayout.show(mainPanel, "RANKING");
    }

    private void mostrarJuego() {
        String dificultadStr = PanelDificultad.mostrarDialogo(this);
        if (dificultadStr == null) {
            return;
        }

        // Usar el controlador para crear el juego con la dificultad seleccionada
        JuegoController controller = new JuegoController(dificultadStr);

        // Inicializar la nave a través del controlador
        controller.inicializarNave(375, 500, 5, 800, 600);

        PanelPrincipal panelJuego = new PanelPrincipal(
                controller,
                () -> mostrarRanking(),
                () -> mostrarMenu());

        mainPanel.add(panelJuego, "JUEGO");
        cardLayout.show(mainPanel, "JUEGO");

        panelJuego.requestFocusInWindow();
    }
}
