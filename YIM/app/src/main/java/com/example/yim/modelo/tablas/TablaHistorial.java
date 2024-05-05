package com.example.yim.modelo.tablas;

import java.util.ArrayList;

public class TablaHistorial {

    private String dia;
    private ArrayList<Integer> peso;
    private ArrayList<Integer> repeticiones;

    public TablaHistorial() {
    }

    public TablaHistorial(String dia, ArrayList<Integer> peso, ArrayList<Integer> repeticiones) {
        this.dia = dia;
        this.peso = peso;
        this.repeticiones = repeticiones;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public ArrayList<Integer> getPeso() {
        return peso;
    }

    public void setPeso(ArrayList<Integer> peso) {
        this.peso = peso;
    }

    public ArrayList<Integer> getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(ArrayList<Integer> repeticiones) {
        this.repeticiones = repeticiones;
    }
}
