package com.example.yim.modelo.tablas;

import java.util.ArrayList;

public class TablaUsuario {
    private TablaEjerciciosUsuario ejercicios;
    private ArrayList<TablaLogroUsuario> logros;
    private ArrayList<TablaMusculoUsuario> musculos;
    private TablaPerfil perfil;

    public TablaUsuario() {
    }

    public TablaUsuario(TablaEjerciciosUsuario ejercicios, ArrayList<TablaLogroUsuario> logros,
                        ArrayList<TablaMusculoUsuario> musculos, TablaPerfil perfil) {
        this.ejercicios = ejercicios;
        this.logros = logros;
        this.musculos = musculos;
        this.perfil = perfil;
    }

    public TablaUsuario(TablaPerfil perfil) {
        this.perfil = perfil;
    }

    public TablaEjerciciosUsuario getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(TablaEjerciciosUsuario ejercicios) {
        this.ejercicios = ejercicios;
    }

    public ArrayList<TablaLogroUsuario> getLogros() {
        return logros;
    }

    public void setLogros(ArrayList<TablaLogroUsuario> logros) {
        this.logros = logros;
    }

    public ArrayList<TablaMusculoUsuario> getMusculos() {
        return musculos;
    }

    public void setMusculos(ArrayList<TablaMusculoUsuario> musculos) {
        this.musculos = musculos;
    }

    public TablaPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(TablaPerfil perfil) {
        this.perfil = perfil;
    }
}
