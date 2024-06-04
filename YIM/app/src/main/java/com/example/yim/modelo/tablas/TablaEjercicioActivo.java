package com.example.yim.modelo.tablas;

import java.io.Serializable;
import java.util.ArrayList;

public class TablaEjercicioActivo implements Serializable {
    private ArrayList<TablaHistorial> historial;
    private String id_ejercicio;
    private String imagen;
    private ArrayList<String> musculos;
    private String nombre;
    private int posicion;
    private int repeticiones;
    private int series_necesarias;
    private int series_realizadas;
    private int tiempo_descanso;

    public TablaEjercicioActivo() {
    }

    public TablaEjercicioActivo(ArrayList<TablaHistorial> historial, String id_ejercicio, ArrayList<String> musculos, String nombre,
                                String imagen, int posicion, int repeticiones, int series_necesarias, int series_realizadas, int tiempo_descanso) {
        this.historial = historial;
        this.id_ejercicio = id_ejercicio;
        this.imagen = imagen;
        this.musculos = musculos;
        this.nombre = nombre;
        this.posicion = posicion;
        this.repeticiones = repeticiones;
        this.series_necesarias = series_necesarias;
        this.series_realizadas = series_realizadas;
        this.tiempo_descanso = tiempo_descanso;
    }

    public TablaEjercicioActivo(TablaEjercicioRutinaUsuario ejercicio) {
        //this.historial = ejercicio.getHistorial(); TODO hacer
        this.id_ejercicio = ejercicio.getID();
        this.musculos = ejercicio.getMusculos();
        this.nombre = ejercicio.getNombre();
        this.posicion = ejercicio.getPosicion();
        this.imagen = ejercicio.getImagen();
        this.repeticiones = ejercicio.getRepeticiones();
        this.series_necesarias = ejercicio.getSeries();
        this.series_realizadas = 0;
        this.tiempo_descanso = ejercicio.getTiempo_descanso();
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<TablaHistorial> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<TablaHistorial> historial) {
        this.historial = historial;
    }

    public String getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(String id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
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

    public int getSeries_necesarias() {
        return series_necesarias;
    }

    public void setSeries_necesarias(int series_necesarias) {
        this.series_necesarias = series_necesarias;
    }

    public int getSeries_realizadas() {
        return series_realizadas;
    }

    public void setSeries_realizadas(int series_realizadas) {
        this.series_realizadas = series_realizadas;
    }

    public int getTiempo_descanso() {
        return tiempo_descanso;
    }

    public void setTiempo_descanso(int tiempo_descanso) {
        this.tiempo_descanso = tiempo_descanso;
    }
}

