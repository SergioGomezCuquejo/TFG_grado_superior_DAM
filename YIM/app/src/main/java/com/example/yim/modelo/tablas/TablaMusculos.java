package com.example.yim.modelo.tablas;

public class TablaMusculos {
    private String color_fondo;
    private String color_letras;
    private String nombre;

    public TablaMusculos() {
    }
    public TablaMusculos(String color_fondo, String color_letras, String nombre) {
        this.color_fondo = color_fondo;
        this.color_letras = color_letras;
        this.nombre = nombre;
    }

    public String getColor_fondo() {
        return color_fondo;
    }

    public void setColor_fondo(String color_fondo) {
        this.color_fondo = color_fondo;
    }

    public String getColor_letras() {
        return color_letras;
    }

    public void setColor_letras(String color_letras) {
        this.color_letras = color_letras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
