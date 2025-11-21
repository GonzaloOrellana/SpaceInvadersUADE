package controlador;

import modelo.GestorRanking;
import modelo.Puntaje;
import java.util.ArrayList;
import java.util.List;

public class RankingController {

    private GestorRanking gestorRanking;

    public RankingController() {
        this.gestorRanking = GestorRanking.getInstancia();
    }

    /**
     * Obtiene la lista de puntajes formateada para la vista.
     * Evita que la vista tenga que conocer la clase Puntaje.
     * 
     * @return Lista de cadenas con el formato "Pos. Nombre - Puntos pts"
     */
    public List<String> obtenerTop10Formateado() {
        List<Puntaje> top10 = gestorRanking.obtenerTop10();
        List<String> resultado = new ArrayList<>();

        int pos = 1;
        for (Puntaje p : top10) {
            String linea = String.format("%d. %s - %d pts", pos++, p.getNombre(), p.getPuntos());
            resultado.add(linea);
        }

        return resultado;
    }

    /**
     * Verifica si hay puntajes registrados.
     * 
     * @return true si hay puntajes, false si esta vacio
     */
    public boolean hayPuntajes() {
        return !gestorRanking.obtenerTop10().isEmpty();
    }
}
