package controlador;

import modelo.GestorRanking;
import modelo.Puntaje;
import java.util.ArrayList;
import java.util.List;

public class RankingController {

    /** Obtiene la lista de puntajes formateada para la vista */
    public List<String> obtenerTop10Formateado() {
        List<Puntaje> top10 = GestorRanking.getInstancia().obtenerTop10();
        List<String> resultado = new ArrayList<>();

        int pos = 1;
        for (Puntaje p : top10) {
            String linea = String.format("%d. %s - %d pts", pos++, p.getNombre(), p.getPuntos());
            resultado.add(linea);
        }

        return resultado;
    }

    /* Verifica si hay puntajes registrados.*/
    public boolean hayPuntajes() {
        return !GestorRanking.getInstancia().obtenerTop10().isEmpty();
    }
}
