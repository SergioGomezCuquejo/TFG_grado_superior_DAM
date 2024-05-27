package com.example.yim.modelo.tablas;

import java.io.Serializable;
import java.util.ArrayList;

public class TablaEjercicioCreado implements Serializable {
    private ArrayList<TablaHistorial> historial;
    private String ID;
    private String imagen;
    private ArrayList<String> musculos;
    private String nombre;
    private String notas;
    private int peso_recomendado;
    private int repeticiones_recomendadas;
    private int series_recomendadas;
    private int tiempo_descanso;

    public TablaEjercicioCreado() {
    }

    public TablaEjercicioCreado(TablaEjercicio ejercicio) {
        imagen = ejercicio.getImagen();
        musculos = ejercicio.getMusculos();
        nombre = ejercicio.getNombre();
    }

    public TablaEjercicioCreado(ArrayList<TablaHistorial> historial, String ID, String imagen, ArrayList<String> musculos,
                                String nombre, String notas, int peso_recomendado, int repeticiones_recomendadas, int series_recomendadas, int tiempo_descanso) {
        this.historial = historial;
        this.ID = ID;
        this.imagen = imagen;
        this.musculos = musculos;
        this.nombre = nombre;
        this.notas = notas;
        this.peso_recomendado = peso_recomendado;
        this.repeticiones_recomendadas = repeticiones_recomendadas;
        this.series_recomendadas = series_recomendadas;
        this.tiempo_descanso = tiempo_descanso;
    }

    public TablaEjercicioCreado(ArrayList<String> musculos, String nombre, String notas,
                                int repeticiones_recomendadas, int series_recomendadas, int tiempo_descanso) {
        this.musculos = musculos;
        this.nombre = nombre;
        this.notas = notas;
        this.repeticiones_recomendadas = repeticiones_recomendadas;
        this.series_recomendadas = series_recomendadas;
        this.tiempo_descanso = tiempo_descanso;
    }


    public ArrayList<TablaHistorial> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<TablaHistorial> historial) {
        this.historial = historial;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getPeso_recomendado() {
        return peso_recomendado;
    }

    public void setPeso_recomendado(int peso_recomendado) {
        this.peso_recomendado = peso_recomendado;
    }

    public int getRepeticiones_recomendadas() {
        return repeticiones_recomendadas;
    }

    public void setRepeticiones_recomendadas(int repeticiones_recomendadas) {
        this.repeticiones_recomendadas = repeticiones_recomendadas;
    }

    public int getSeries_recomendadas() {
        return series_recomendadas;
    }

    public void setSeries_recomendadas(int series_recomendadas) {
        this.series_recomendadas = series_recomendadas;
    }

    public int getTiempo_descanso() {
        return tiempo_descanso;
    }

    public void setTiempo_descanso(int tiempo_descanso) {
        this.tiempo_descanso = tiempo_descanso;
    }
}
