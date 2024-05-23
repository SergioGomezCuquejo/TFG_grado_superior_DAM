package com.example.yim.modelo.tablas;

import java.util.ArrayList;

public class TablaUsuario {
    private TablaPerfil perfil;
    private ArrayList<TablaEjercicioUsuario> ejercicios;
    private ArrayList<TablaLogroUsuario> logros;
    private ArrayList<TablaMusculoUsuario> musculos;

    public TablaUsuario() {
        perfil = new TablaPerfil();
        musculos = new ArrayList<TablaMusculoUsuario>();
        ejercicios = new ArrayList<TablaEjercicioUsuario>();
    }

    public TablaUsuario(TablaPerfil perfil, ArrayList<TablaEjercicioUsuario> ejercicios, ArrayList<TablaLogroUsuario> logros,
                        ArrayList<TablaMusculoUsuario> musculos) {
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

    public ArrayList<TablaEjercicioUsuario> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<TablaEjercicioUsuario> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public ArrayList<TablaLogroUsuario> getLogros() {
        return logros;
    }

    public void setLogros(ArrayList<TablaLogroUsuario> logros) {
        this.logros = logros;
    }

    public  ArrayList<TablaMusculoUsuario> getMusculos() {
        return musculos;
    }

    public void setMusculos( ArrayList<TablaMusculoUsuario> musculos) {
        this.musculos = musculos;
    }
}
