package com.example.yim.modelo.tablas;

public class ColoresMusculoUsuario {
    private String color_fondo;
    private String color_fuente;

    public ColoresMusculoUsuario() {
    }

    public ColoresMusculoUsuario(String color_fondo, String color_fuente) {
        this.color_fondo = color_fondo;
        this.color_fuente = color_fuente;
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
}
