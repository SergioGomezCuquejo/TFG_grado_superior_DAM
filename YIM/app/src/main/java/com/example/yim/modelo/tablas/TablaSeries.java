package com.example.yim.modelo.tablas;

import java.io.Serializable;

public class TablaSeries implements Serializable {
    private String coste;
    private int peso;
    private int repeticiones;
    private int serie;

    public TablaSeries() {
    }

    public TablaSeries(String coste, int peso, int repeticiones, int serie) {
        this.coste = coste;
        this.peso = peso;
        this.repeticiones = repeticiones;
        this.serie = serie;
    }

    public String getCoste() {
        return coste;
    }

    public void setCoste(String coste) {
        this.coste = coste;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }
}
