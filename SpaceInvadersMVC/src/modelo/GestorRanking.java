package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorRanking {

    private static final String ARCHIVO_RANKING = "ranking.dat";
    private static GestorRanking instancia;
    private List<Puntaje> puntajes;

    private GestorRanking() {
        puntajes = new ArrayList<>();
        cargarRanking();
    }

    public static GestorRanking getInstancia() {
        if (instancia == null) {
            instancia = new GestorRanking();
        }
        return instancia;
    }

    public void guardarPuntaje(Puntaje nuevoPuntaje) {
        // Verificacion de nombre
        Puntaje puntajeExistente = null;
        for (Puntaje p : puntajes) {
            if (p.getNombre().equalsIgnoreCase(nuevoPuntaje.getNombre())) {
                puntajeExistente = p;
                break;
            }
        }

        if (puntajeExistente != null) {
            // El nombre ya existe
            if (nuevoPuntaje.getPuntos() > puntajeExistente.getPuntos()) {
                // El nuevo puntaje es mayor: reemplazar
                puntajes.remove(puntajeExistente);
                puntajes.add(nuevoPuntaje);
                System.out.println("Puntaje actualizado para " + nuevoPuntaje.getNombre() +
                        ": " + puntajeExistente.getPuntos() + " -> " + nuevoPuntaje.getPuntos());
            } else {
                // El nuevo puntaje es menor o igual: no hacer nada
                System.out.println("Puntaje no actualizado para " + nuevoPuntaje.getNombre() +
                        ": se mantiene " + puntajeExistente.getPuntos() +
                        " (nuevo: " + nuevoPuntaje.getPuntos() + ")");
                return; // No guardar en archivo si no hubo cambios
            }
        } else {
            // Nombre nuevo: agregar al ranking
            puntajes.add(nuevoPuntaje);
            System.out.println("Nuevo puntaje agregado para " + nuevoPuntaje.getNombre() +
                    ": " + nuevoPuntaje.getPuntos());
        }

        // Orden y limite de top 10
        Collections.sort(puntajes);
        if (puntajes.size() > 10) {
            puntajes = new ArrayList<>(puntajes.subList(0, 10));
        }

        guardarEnArchivo();
    }

    public List<Puntaje> obtenerTop10() {
        return new ArrayList<>(puntajes);
    }

    @SuppressWarnings("unchecked")
    private void cargarRanking() {
        File archivo = new File(ARCHIVO_RANKING);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                puntajes = (List<Puntaje>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                puntajes = new ArrayList<>();
            }
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_RANKING))) {
            oos.writeObject(puntajes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
