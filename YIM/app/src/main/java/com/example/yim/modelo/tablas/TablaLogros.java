package com.example.yim.modelo.tablas;

public class TablaLogros {
    private String descripcion;
    private String dificultad;
    private String imagen;
    private String titulo;
    private int total;

    public TablaLogros() {
    }

    public TablaLogros(String descripcion, String dificultad, String imagen, String titulo, int total) {
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.imagen = imagen;
        this.titulo = titulo;
        this.total = total;
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
