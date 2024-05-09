package com.example.yim.modelo.tablas;

import java.io.Serializable;

public class TablaEjercicioRutinaUsuario implements Serializable {

    private String nombre;
    private int posicion;
    private int repeticiones;
    private int series;
    private int tiempo_descanso;

    public TablaEjercicioRutinaUsuario() {
    }

    public TablaEjercicioRutinaUsuario(String nombre, int posicion, int repeticiones, int series, int tiempo_descanso) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.repeticiones = repeticiones;
        this.series = series;
        this.tiempo_descanso = tiempo_descanso;
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
}
