package com.example.yim.modelo.tablas;

import java.util.ArrayList;

public class TablaRutinaActiva {
    private String idRutina;
    private ArrayList<TablaDiaRutinaActiva> semana;

    public TablaRutinaActiva() {
    }

    public TablaRutinaActiva(String idRutina, ArrayList<TablaDiaRutinaActiva> semana) {
        this.idRutina = idRutina;
        this.semana = semana;
    }

    public String getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(String idRutina) {
        this.idRutina = idRutina;
    }

    public ArrayList<TablaDiaRutinaActiva> getSemana() {
        return semana;
    }

    public void setSemana(ArrayList<TablaDiaRutinaActiva> semana) {
        this.semana = semana;
    }
}
