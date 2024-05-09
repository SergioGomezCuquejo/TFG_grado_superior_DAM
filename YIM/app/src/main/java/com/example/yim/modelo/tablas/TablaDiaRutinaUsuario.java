package com.example.yim.modelo.tablas;

import java.io.Serializable;
import java.util.ArrayList;

public class TablaDiaRutinaUsuario implements Serializable {

        private ArrayList<TablaEjercicioRutinaUsuario> ejercicios;
    private ArrayList<String> musculos;

    public TablaDiaRutinaUsuario() {
    }

    public TablaDiaRutinaUsuario(ArrayList<TablaEjercicioRutinaUsuario> ejercicios, ArrayList<String> musculos) {
        this.ejercicios = ejercicios;
        this.musculos = musculos;
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
}
