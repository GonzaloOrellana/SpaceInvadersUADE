package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Espacio {

    private NaveJugador nave;
    private List<Rayo> rayos;
    private List<Invasor> invasores;
    private List<Muro> muros;

    private int direccionInvasores = 1; // 1 derecha, -1 izquierda
    private int velocidadInvasores = 2;

    private int puntaje = 0;
    private int nivel = 1;
    private int creditos = 1;

    private boolean gameOver = false;
    private boolean esperandoRepoblacion = false;

    // Callback para notificar nuevos rayos enemigos
    private NuevoRayoCallback nuevoRayoCallback;

    public interface NuevoRayoCallback {
        void onNuevoRayoEnemigo(Rayo rayo);
    }

    public void setNuevoRayoCallback(NuevoRayoCallback callback) {
        this.nuevoRayoCallback = callback;
    }
    
    //Constructor. Inicializa el espacio con dificultad GUERRERO.
    public Espacio() {
        this(Dificultad.GUERRERO);
    }

    // Constructor que permite especificar la dificultad inicial.
    public Espacio(Dificultad dificultad) {
        this.rayos = new ArrayList<>();
        this.invasores = new ArrayList<>();
        this.muros = new ArrayList<>();
        this.velocidadInvasores = dificultad.getVelocidadBase();
        inicializarMuros();
    }

    private void inicializarMuros() {
        muros.clear();
        // 4 muros con espacios
        // Ancho pantalla ~800. Muro ancho ~60.
        // Posiciones aprox: 100, 300, 500, 700
        int yMuro = 400;
        int[] xMuros = { 100, 280, 460, 640 };

        for (int x : xMuros) {
            Muro muro = new Muro(x, yMuro);
            muros.add(muro);
        }
    }

    public void setNave(NaveJugador nave) {
        this.nave = nave;
    }

    public NaveJugador getNave() {
        return nave;
    }

    public void agregarInvasor(Invasor invasor) {
        this.invasores.add(invasor);
    }

    public void agregarRayo(Rayo rayo) {
        this.rayos.add(rayo);
    }

    public void agregarMuro(Muro muro) {
        this.muros.add(muro);
    }

    public List<Muro> getMuros() {
        return muros;
    }

    public void actualizarPosiciones() {
        if (gameOver)
            return;

        // Actualizar rayos
        Iterator<Rayo> iterRayos = rayos.iterator();
        while (iterRayos.hasNext()) {
            Rayo rayo = iterRayos.next();
            rayo.actualizarPosicion();
            if (!rayo.isActivo()) {
                iterRayos.remove();
            }
        }

        // Lógica de movimiento de invasores
        moverInvasores();

        // Enemigos disparan aleatoriamente
        dispararEnemigos();

        // Colisiones
        verificarColisiones();

        // Verificar si nave fue destruida
        if (nave != null && nave.isDestruido()) {
            perderCredito();
        }

        // Verificar Nivel Completado
        if (invasores.isEmpty() && !esperandoRepoblacion) {
            avanzarNivel();
            esperandoRepoblacion = true;
        }
    }

    private void dispararEnemigos() {
        // probabilidad de disparar
        for (Invasor inv : invasores) {
            if (Math.random() < 0.005) { // 0.5% de probabilidad por frame
                Rayo rayoEnemigo = inv.disparar();
                rayoEnemigo.setLimites(800, 600);
                agregarRayo(rayoEnemigo);

                // Notificar a la vista que se creó un nuevo rayo enemigo
                if (nuevoRayoCallback != null) {
                    nuevoRayoCallback.onNuevoRayoEnemigo(rayoEnemigo);
                }
            }
        }
    }

    private void perderCredito() {
        creditos--;
        if (creditos > 0) {
            nave.reiniciarVida();
        } else {
            setGameOver(true);
        }
    }

    public boolean isInvasoresEmpty() {
        return invasores.isEmpty();
    }

    public void setEsperandoRepoblacion(boolean esperando) {
        this.esperandoRepoblacion = esperando;
    }

    private void moverInvasores() {
        boolean tocarBorde = false;
        for (Invasor inv : invasores) {
            if ((direccionInvasores == 1 && inv.getX() + inv.getAncho() >= inv.limiteX) ||
                    (direccionInvasores == -1 && inv.getX() <= 0)) {
                tocarBorde = true;
                break;
            }
        }

        if (tocarBorde) {
            direccionInvasores *= -1;
            for (Invasor inv : invasores) {
                inv.mover(0, 20); // Bajar
                // Verificar si llegan muy abajo (Game Over)
                if (inv.getY() + inv.getAlto() >= 500) { // Altura nave
                    setGameOver(true);
                }
            }
        } else {
            for (Invasor inv : invasores) {
                inv.mover(velocidadInvasores * direccionInvasores, 0);
            }
        }
    }

    private void verificarColisiones() {
        Iterator<Rayo> iterRayos = rayos.iterator();
        while (iterRayos.hasNext()) {
            Rayo rayo = iterRayos.next();
            boolean rayoEliminado = false;

            if (rayo.isEnemigo()) {
                // Rayo enemigo vs Nave
                if (nave != null && hayColision(rayo, nave)) {
                    nave.recibirDano(20); // 20 de daño por impacto
                    rayoEliminado = true;
                }

                // Rayo enemigo vs Muro
                if (!rayoEliminado) {
                    for (Muro muro : muros) {
                        if (!muro.isDestruido() && hayColision(rayo, muro)) {
                            muro.recibirDano(10);
                            rayoEliminado = true;
                            break;
                        }
                    }
                }
            } else {
                // Rayo jugador vs Invasor
                Iterator<Invasor> iterInvasores = invasores.iterator();
                while (iterInvasores.hasNext()) {
                    Invasor inv = iterInvasores.next();
                    if (hayColision(rayo, inv)) {
                        inv.destruir(); // Notificar vista para que desaparezca
                        iterInvasores.remove();
                        puntaje += 10;
                        verificarCreditosExtra();
                        rayoEliminado = true;
                        break;
                    }
                }

                // Rayo jugador vs Muro
                if (!rayoEliminado) {
                    for (Muro muro : muros) {
                        if (!muro.isDestruido() && hayColision(rayo, muro)) {
                            muro.recibirDano(10);
                            rayoEliminado = true;
                            break;
                        }
                    }
                }
            }

            if (rayoEliminado) {
                rayo.destruir(); // Notificar vista
                iterRayos.remove();
            }
        }

        // Invasor vs Muro y Invasor vs Nave
        for (Invasor inv : invasores) {
            // Vs Muro
            for (Muro muro : muros) {
                if (!muro.isDestruido() && hayColision(inv, muro)) {
                    muro.recibirDano(5);
                }
            }

            // Vs Nave
            if (nave != null && hayColision(inv, nave)) {
                setGameOver(true);
            }
        }
    }

    private boolean hayColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        return obj1.getX() < obj2.getX() + obj2.getAncho() &&
                obj1.getX() + obj1.getAncho() > obj2.getX() &&
                obj1.getY() < obj2.getY() + obj2.getAlto() &&
                obj1.getY() + obj1.getAlto() > obj2.getY();
    }

    private void avanzarNivel() {
        nivel++;
        puntaje += 200;
        velocidadInvasores++; // Aumentar dificultad

        // Restaurar muros
        for (Muro muro : muros) {
            muro.reiniciar();
        }
    }

    private void verificarCreditosExtra() {
        if (puntaje % 500 == 0 && puntaje > 0) {
            creditos++;
        }
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getNivel() {
        return nivel;
    }

    public int getCreditos() {
        return creditos;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getVelocidadInvasores() {
        return velocidadInvasores;
    }
}
