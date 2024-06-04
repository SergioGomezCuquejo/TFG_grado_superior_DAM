package com.example.yim.modelo.tablas;

import java.io.Serializable;

public class TablaInfoRutinaUsuario implements Serializable {
    private boolean activo;
    private String imagen;
    private String nombre;

    public TablaInfoRutinaUsuario() {
    }

    public TablaInfoRutinaUsuario(boolean activo, String imagen, String nombre) {
        this.activo = activo;
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public TablaInfoRutinaUsuario(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}