package controlador;

import modelo.Espacio;
import modelo.Invasor;
import modelo.Muro;
import modelo.Rayo;
import modelo.Observador;

import java.util.List;

public class JuegoController {

    private Espacio espacio;

    public JuegoController(Espacio espacio) {
        this.espacio = espacio;
    }

    /* Constructor que crea un nuevo Espacio con la dificultad especificada. */
    public JuegoController(modelo.Dificultad dificultad) {
        this.espacio = new Espacio(dificultad);
    }

    /*
     * Constructor que crea un nuevo Espacio con la dificultad especificada como
     * String.
     */
    public JuegoController(String dificultadStr) {
        modelo.Dificultad dificultad = modelo.Dificultad.valueOf(dificultadStr);
        this.espacio = new Espacio(dificultad);
    }

    /* Método para inicializar la nave del jugador. */
    public void inicializarNave(int x, int y, int velocidad, int limiteX, int limiteY) {
        modelo.NaveJugador nave = new modelo.NaveJugador(x, y, velocidad);
        nave.setLimites(limiteX, limiteY);
        espacio.setNave(nave);
    }

    public void moverNaveDerecha() {
        espacio.getNave().moverDerecha();
    }

    public void moverNaveIzquierda() {
        espacio.getNave().moverIzquierda();
    }

    public void solicitarDisparo(Observador observadorRayo) {
        Rayo rayo = espacio.getNave().disparar();
        rayo.setObservador(observadorRayo);
        espacio.agregarRayo(rayo);
    }

    public void actualizarJuego() {
        espacio.actualizarPosiciones();
    }

    public void crearInvasor(int x, int y, Observador observador) {
        Invasor invasor = new Invasor(x, y, getVelocidadInvasores());
        invasor.setLimites(800, 600);
        invasor.setObservador(observador);
        espacio.agregarInvasor(invasor);
    }

    public void setObservadorNave(Observador observador) {
        espacio.getNave().setObservador(observador);
    }

    public void inicializarMurosConObservadores(ObservadorMuroCallback callback) {
        List<Muro> muros = espacio.getMuros();
        for (Muro muro : muros) {
            Observador observador = callback.crearObservador(muro.getX(), muro.getY());
            muro.setObservador(observador);
        }
    }

    // Interfaz funcional para callback
    public interface ObservadorMuroCallback {
        Observador crearObservador(int x, int y);
    }

    public int getPuntaje() {
        return espacio.getPuntaje();
    }

    public int getNivel() {
        return espacio.getNivel();
    }

    public int getCreditos() {
        return espacio.getCreditos();
    }

    public boolean isGameOver() {
        return espacio.isGameOver();
    }

    public int getVelocidadInvasores() {
        return espacio.getVelocidadInvasores();
    }

    public int getVidaNave() {
        return espacio.getNave().getVida();
    }

    public void setCallbackRayosEnemigos(Espacio.NuevoRayoCallback callback) {
        espacio.setNuevoRayoCallback(callback);
    }

    public boolean verificarNivelCompletado() {
        if (espacio.isInvasoresEmpty()) {
            espacio.setEsperandoRepoblacion(false);
            return true;
        }
        return false;
    }
}
