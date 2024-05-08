package com.example.yim.modelo.tablas;

import java.io.Serializable;

public class TablaLogrosUsuario implements Serializable {
    private String ID;
    private String descripcion;
    private String dificultad;
    private String imagen;
    private int progreso;
    private String titulo;
    private int total;

    public TablaLogrosUsuario() {
    }

    public TablaLogrosUsuario(String descripcion, String dificultad, String imagen, int progreso, String titulo, int total) {
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.imagen = imagen;
        this.progreso = progreso;
        this.titulo = titulo;
        this.total = total;
    }

    public TablaLogrosUsuario(TablaLogros logro) {
        descripcion = logro.getDescripcion();
        dificultad = logro.getDificultad();
        imagen = logro.getImagen();
        progreso = 0;
        titulo = logro.getTitulo();
        total = logro.getTotal();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
