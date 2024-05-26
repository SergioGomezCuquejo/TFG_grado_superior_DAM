package com.example.yim.modelo.tablas;

import java.util.ArrayList;

public class TablaEjercicio {
    private ArrayList<String> consejos_clave;
    private ArrayList<String> ejecucion;
    private String imagen;
    private String imagen_realizar;
    private ArrayList<String> musculos;
    private String nombre;

    public TablaEjercicio() {
    }

    public TablaEjercicio(ArrayList<String> consejos_clave, ArrayList<String> ejecucion, String imagen,
                          String imagenRealizar, ArrayList<String> musculos, String nombre) {
        this.consejos_clave = consejos_clave;
        this.ejecucion = ejecucion;
        this.imagen = imagen;
        this.imagen_realizar = imagenRealizar;
        this.musculos = musculos;
        this.nombre = nombre;
    }

    public ArrayList<String> getConsejos_clave() {
        return consejos_clave;
    }

    public void setConsejos_clave(ArrayList<String> consejos_clave) {
        this.consejos_clave = consejos_clave;
    }

    public ArrayList<String> getEjecucion() {
        return ejecucion;
    }

    public void setEjecucion(ArrayList<String> ejecucion) {
        this.ejecucion = ejecucion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen_realizar() {
        return imagen_realizar;
    }

    public void setImagen_realizar(String imagen_realizar) {
        this.imagen_realizar = imagen_realizar;
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

    @Override
    public String toString() {
        return "TablaEjercicios{" +
                "consejosClave=" + consejos_clave +
                ", ejecucion=" + ejecucion +
                ", imagen='" + imagen + '\'' +
                ", musculos=" + musculos +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
