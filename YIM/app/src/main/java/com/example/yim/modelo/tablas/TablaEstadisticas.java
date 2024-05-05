package com.example.yim.modelo.tablas;

public class TablaEstadisticas {
    private int peso;
    private int repeticiones;
    private int semana;

    public TablaEstadisticas() {
    }

    public TablaEstadisticas(int peso, int repeticiones, int semana) {
        this.peso = peso;
        this.repeticiones = repeticiones;
        this.semana = semana;
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

    public int getSemana() {
        return semana;
    }

    public void setSemana(int semana) {
        this.semana = semana;
    }
}
