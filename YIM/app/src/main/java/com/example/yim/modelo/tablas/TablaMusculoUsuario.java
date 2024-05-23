package com.example.yim.modelo.tablas;

import java.io.Serializable;

public class TablaMusculoUsuario implements Serializable {
    private String ID;
    private String color_fondo;
    private String color_fuente;
    private int ejercicios_en_rutinaActual;
    private int ejercicios_en_rutinas;
    private int ejercicios_realizados;
    private int ejercicios_sin_realizar;
    private int ejercicios_totales;
    private String nombre;

    public TablaMusculoUsuario() {
    }

    public TablaMusculoUsuario(String color_fondo, String color_fuente, int ejercicios_en_rutinaActual, int ejercicios_en_rutinas,
                               int ejercicios_realizados, int ejercicios_sin_realizar, int ejercicios_totales, String nombre) {
        this.color_fondo = color_fondo;
        this.color_fuente = color_fuente;
        this.ejercicios_en_rutinaActual = ejercicios_en_rutinaActual;
        this.ejercicios_en_rutinas = ejercicios_en_rutinas;
        this.ejercicios_realizados = ejercicios_realizados;
        this.ejercicios_sin_realizar = ejercicios_sin_realizar;
        this.ejercicios_totales = ejercicios_totales;
        this.nombre = nombre;
    }
    public TablaMusculoUsuario(String color_fondo, String color_fuente, String nombre) {
        this.color_fondo = color_fondo;
        this.color_fuente = color_fuente;
        this.nombre = nombre;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getColor_fondo() {
        return color_fondo;
    }

    public void setColor_fondo(String color_fondo) {
        this.color_fondo = color_fondo;
    }

    public String getColor_fuente() {
        return color_fuente;
    }

    public void setColor_fuente(String color_fuente) {
        this.color_fuente = color_fuente;
    }

    public int getEjercicios_en_rutinaActual() {
        return ejercicios_en_rutinaActual;
    }

    public void setEjercicios_en_rutinaActual(int ejercicios_en_rutinaActual) {
        this.ejercicios_en_rutinaActual = ejercicios_en_rutinaActual;
    }

    public int getEjercicios_en_rutinas() {
        return ejercicios_en_rutinas;
    }

    public void setEjercicios_en_rutinas(int ejercicios_en_rutinas) {
        this.ejercicios_en_rutinas = ejercicios_en_rutinas;
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
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
