package com.example.yim.modelo.tablas;

import java.io.Serializable;

public class TablaInfoRutinasUsuario implements Serializable {
    private boolean activo;
    private int dias_completados;
    private int dias_descansados;
    private int dias_descanso;
    private int dias_totales;
    private int ejercicios_realizados;
    private int ejercicios_sin_realizar;
    private int ejercicios_totales;
    private String imagen;
    private int musculos_activos;
    private int musculos_totales;
    private String nombre;
    private int veces_activada;
    private int veces_completada;

    public TablaInfoRutinasUsuario() {
    }

    public TablaInfoRutinasUsuario(boolean activo, int dias_completados, int dias_descansados, int dias_descanso, int dias_totales,
                                   int ejercicios_realizados, int ejercicios_sin_realizar, int ejercicios_totales, String imagen,
                                   int musculos_activos, int musculos_totales, String nombre, int veces_activada, int veces_completada) {
        this.activo = activo;
        this.dias_completados = dias_completados;
        this.dias_descansados = dias_descansados;
        this.dias_descanso = dias_descanso;
        this.dias_totales = dias_totales;
        this.imagen = imagen;
        this.ejercicios_realizados = ejercicios_realizados;
        this.ejercicios_sin_realizar = ejercicios_sin_realizar;
        this.ejercicios_totales = ejercicios_totales;
        this.musculos_activos = musculos_activos;
        this.musculos_totales = musculos_totales;
        this.nombre = nombre;
        this.veces_activada = veces_activada;
        this.veces_completada = veces_completada;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getDias_completados() {
        return dias_completados;
    }

    public void setDias_completados(int dias_completados) {
        this.dias_completados = dias_completados;
    }

    public int getDias_descansados() {
        return dias_descansados;
    }

    public void setDias_descansados(int dias_descansados) {
        this.dias_descansados = dias_descansados;
    }

    public int getDias_descanso() {
        return dias_descanso;
    }

    public void setDias_descanso(int dias_descanso) {
        this.dias_descanso = dias_descanso;
    }

    public int getDias_totales() {
        return dias_totales;
    }

    public void setDias_totales(int dias_totales) {
        this.dias_totales = dias_totales;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getEjercicios_realizados() {
        return ejercicios_realizados;
    }

    public void setEjercicios_realizados(int ejercicios_realizados) {
        this.ejercicios_realizados = ejercicios_realizados;
    }

    public int getEjercicios_sin_realizar() {
        return ejercicios_sin_realizar;
    }

    public void setEjercicios_sin_realizar(int ejercicios_sin_realizar) {
        this.ejercicios_sin_realizar = ejercicios_sin_realizar;
    }

    public int getEjercicios_totales() {
        return ejercicios_totales;
    }

    public void setEjercicios_totales(int ejercicios_totales) {
        this.ejercicios_totales = ejercicios_totales;
    }

    public int getMusculos_activos() {
        return musculos_activos;
    }

    public void setMusculos_activos(int musculos_activos) {
        this.musculos_activos = musculos_activos;
    }

    public int getMusculos_totales() {
        return musculos_totales;
    }

    public void setMusculos_totales(int musculos_totales) {
        this.musculos_totales = musculos_totales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVeces_activada() {
        return veces_activada;
    }

    public void setVeces_activada(int veces_activada) {
        this.veces_activada = veces_activada;
    }

    public int getVeces_completada() {
        return veces_completada;
    }

    public void setVeces_completada(int veces_completada) {
        this.veces_completada = veces_completada;
    }
}