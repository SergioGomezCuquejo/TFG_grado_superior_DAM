package com.example.yim.modelo.tablas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TablaUsuario {
    private TablaPerfil perfil;
    private ArrayList<TablaEjerciciosUsuario> ejercicios;
    private ArrayList<TablaLogrosUsuario> logros;
    private ArrayList<TablaMusculosUsuario> musculos;

    public TablaUsuario() {
        perfil = new TablaPerfil();
        musculos = new ArrayList<TablaMusculosUsuario>();
        ejercicios = new ArrayList<TablaEjerciciosUsuario>();
    }

    public TablaUsuario(TablaPerfil perfil, ArrayList<TablaEjerciciosUsuario> ejercicios, ArrayList<TablaLogrosUsuario> logros,
                        ArrayList<TablaMusculosUsuario> musculos) {
        this.perfil = perfil;
        this.ejercicios = ejercicios;
        this.logros = logros;
        this.musculos = musculos;
    }

    public TablaPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(TablaPerfil perfil) {
        this.perfil = perfil;
    }

    public ArrayList<TablaEjerciciosUsuario> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<TablaEjerciciosUsuario> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public ArrayList<TablaLogrosUsuario> getLogros() {
        return logros;
    }

    public void setLogros(ArrayList<TablaLogrosUsuario> logros) {
        this.logros = logros;
    }

    public  ArrayList<TablaMusculosUsuario> getMusculos() {
        return musculos;
    }

    public void setMusculos( ArrayList<TablaMusculosUsuario> musculos) {
        this.musculos = musculos;
    }
}
