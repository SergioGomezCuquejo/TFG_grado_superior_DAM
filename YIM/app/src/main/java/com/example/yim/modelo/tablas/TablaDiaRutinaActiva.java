package com.example.yim.modelo.tablas;

import java.io.Serializable;
import java.util.ArrayList;

public class TablaDiaRutinaActiva implements Serializable {
    private int dia;
    private ArrayList<TablaEjercicioActivo> ejercicios;
    private ArrayList<String> musculos;

    public TablaDiaRutinaActiva() {
    }

    public TablaDiaRutinaActiva(int dia, ArrayList<TablaEjercicioActivo> ejercicios, ArrayList<String> musculos) {
        this.dia = dia;
        this.ejercicios = ejercicios;
        this.musculos = musculos;
    }

    public TablaDiaRutinaActiva(TablaDiaRutinaUsuario diaRutinaUsuario) {
        this.dia = diaRutinaUsuario.getDia();

        if(diaRutinaUsuario.getEjercicios() != null){
            this.ejercicios = new ArrayList<>();
            for(TablaEjercicioRutinaUsuario ejercicio : diaRutinaUsuario.getEjercicios()){
                this.ejercicios.add(new TablaEjercicioActivo(ejercicio));
            }
        }

        this.musculos = diaRutinaUsuario.getMusculos();
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public ArrayList<TablaEjercicioActivo> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<TablaEjercicioActivo> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public ArrayList<String> getMusculos() {
        return musculos;
    }

    public void setMusculos(ArrayList<String> musculos) {
        this.musculos = musculos;
    }
}
