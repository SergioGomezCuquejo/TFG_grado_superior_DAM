package com.example.yim.modelo.tablas;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class TablaDiaRutinaUsuario implements Serializable, Comparable<TablaDiaRutinaUsuario> {
    private int dia;
    private ArrayList<TablaEjercicioRutinaUsuario> ejercicios;
    private ArrayList<String> musculos;

    public TablaDiaRutinaUsuario() {
    }

    public TablaDiaRutinaUsuario(int dia, ArrayList<TablaEjercicioRutinaUsuario> ejercicios, ArrayList<String> musculos) {
        this.dia = dia;
        //this.ejercicios = ejercicios;
        this.musculos = musculos;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public ArrayList<TablaEjercicioRutinaUsuario> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<TablaEjercicioRutinaUsuario> ejercicios) {
        this.ejercicios = ejercicios;
    }


    public ArrayList<String> getMusculos() {
        return musculos;
    }

    public void setMusculos(ArrayList<String> musculos) {
        this.musculos = musculos;
    }

    @Override
    public int compareTo(TablaDiaRutinaUsuario o) {
        int valor = 0;
        if (this.dia > o.getDia()) {
            valor = 1;
        }
        if (this.dia < o.getDia()) {
            valor = -1;
        }
        return valor;
    }
    public void ordenarSemana(){
        Collections.sort(ejercicios);
    }
}
