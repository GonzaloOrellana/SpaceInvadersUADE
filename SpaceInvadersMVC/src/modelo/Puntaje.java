package modelo;

import java.io.Serializable;

public class Puntaje implements Comparable<Puntaje>, Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private int puntos;

    public Puntaje(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    @Override
    public int compareTo(Puntaje o) {
        //Mayor puntaje primero
        return Integer.compare(o.puntos, this.puntos);
    }
}
