package com.example.yim.modelo.tablas;

import java.io.Serializable;
import java.util.ArrayList;

public class TablaEjercicioRutinaUsuario implements Serializable, Comparable<TablaEjercicioRutinaUsuario> {
    private String ID;
    private ArrayList<String> musculos;
    private String nombre;
    private int posicion;
    private int repeticiones;
    private int series;
    private int tiempo_descanso;

    public TablaEjercicioRutinaUsuario() {
    }

    public TablaEjercicioRutinaUsuario(ArrayList<String> musculos, String nombre, int posicion, int repeticiones, int series, int tiempo_descanso) {
        this.musculos = musculos;
        this.nombre = nombre;
        this.posicion = posicion;
        this.repeticiones = repeticiones;
        this.series = series;
        this.tiempo_descanso = tiempo_descanso;
    }

    public TablaEjercicioRutinaUsuario(TablaEjercicioUsuario ejercicio, int posicion) {
        this.musculos = ejercicio.getMusculos();
        this.nombre = ejercicio.getNombre();
        this.posicion = posicion;
        this.repeticiones = ejercicio.getRepeticiones_recomendadas();
        this.series = ejercicio.getSeries_recomendadas();
        this.tiempo_descanso = ejercicio.getTiempo_descanso();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<String> getMusculos() {
        return musculos;
    }

    public void setMusculos(ArrayList<String> musculos) {
        this.musculos = musculos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getTiempo_descanso() {
        return tiempo_descanso;
    }

    public void setTiempo_descanso(int tiempo_descanso) {
        this.tiempo_descanso = tiempo_descanso;
    }
    @Override
    public int compareTo(TablaEjercicioRutinaUsuario o) {
        int valor = 0;
        if (this.posicion > o.getPosicion()) {
            valor = 1;
        }
        if (this.posicion < o.getPosicion()) {
            valor = -1;
        }
        return valor;
    }
}
