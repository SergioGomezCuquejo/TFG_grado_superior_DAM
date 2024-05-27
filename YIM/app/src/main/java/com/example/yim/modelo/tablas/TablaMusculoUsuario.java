package com.example.yim.modelo.tablas;

import java.io.Serializable;

public class TablaMusculoUsuario implements Serializable {
    private String ID;
    private String color_fondo;
    private String color_fuente;
    private String nombre;

    public TablaMusculoUsuario() {
    }


    public TablaMusculoUsuario(String ID, String color_fondo, String color_fuente, String nombre) {
        this.ID = ID;
        this.color_fondo = color_fondo;
        this.color_fuente = color_fuente;
        this.nombre = nombre;
    }

    public TablaMusculoUsuario(TablaMusculo musculo) {
        this.color_fondo = musculo.getColor_fondo();
        this.color_fuente = musculo.getColor_letras();
        this.nombre = musculo.getNombre();
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
