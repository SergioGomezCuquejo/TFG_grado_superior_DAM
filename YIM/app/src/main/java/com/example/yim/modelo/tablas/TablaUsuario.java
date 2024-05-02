package com.example.yim.modelo.tablas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TablaUsuario {
    private TablaPerfil perfil;
    private ArrayList<TablaMusculosUsuario> musculos;

    public TablaUsuario() {
        perfil = new TablaPerfil();
        musculos = new ArrayList<>();
    }

    public TablaUsuario(TablaPerfil perfil, ArrayList<TablaMusculosUsuario> musculos) {
        this.perfil = perfil;
        this.musculos = musculos;
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
}
