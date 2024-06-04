package com.example.yim.modelo.tablas;

import java.util.ArrayList;

public class TablaEjerciciosUsuario {
    private ArrayList<TablaEjercicioCreado> creados;
    private ArrayList<TablaEjercicioPorDefecto> por_defecto;

    public TablaEjerciciosUsuario() {
    }

    public TablaEjerciciosUsuario(ArrayList<TablaEjercicioCreado> creados, ArrayList<TablaEjercicioPorDefecto> por_defecto) {
        this.creados = creados;
        this.por_defecto = por_defecto;
    }

    public TablaEjerciciosUsuario(ArrayList<TablaEjercicioPorDefecto> por_defecto) {
        this.por_defecto = por_defecto;
    }

    public ArrayList<TablaEjercicioCreado> getCreados() {
        return creados;
    }

    public void setCreados(ArrayList<TablaEjercicioCreado> creados) {
        this.creados = creados;
    }

    public ArrayList<TablaEjercicioPorDefecto> getPor_defecto() {
        return por_defecto;
    }

    public void setPor_defecto(ArrayList<TablaEjercicioPorDefecto> por_defecto) {
        this.por_defecto = por_defecto;
    }
}
