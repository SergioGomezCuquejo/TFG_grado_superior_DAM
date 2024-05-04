package com.example.yim.modelo.tablas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TablaUsuario {
    private TablaPerfil perfil;
    private ArrayList<TablaMusculosUsuario> musculos;
    private ArrayList<TablaEjerciciosUsuario> ejercicios;

    public TablaUsuario() {
        perfil = new TablaPerfil();
        musculos = new ArrayList<TablaMusculosUsuario>();
        ejercicios = new ArrayList<TablaEjerciciosUsuario>();
    }

    public TablaUsuario(TablaPerfil perfil, ArrayList<TablaMusculosUsuario> musculos, ArrayList<TablaEjerciciosUsuario> ejercicios) {
        this.perfil = perfil;
        this.musculos = musculos;
        this.ejercicios = ejercicios;
    }

    public TablaPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(TablaPerfil perfil) {
        this.perfil = perfil;
    }

    public  ArrayList<TablaMusculosUsuario> getMusculos() {
        return musculos;
    }

    public void setMusculos( ArrayList<TablaMusculosUsuario> musculos) {
        this.musculos = musculos;
    }

    public ArrayList<TablaEjerciciosUsuario> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<TablaEjerciciosUsuario> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
