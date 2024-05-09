package com.example.yim.modelo.tablas;

import java.io.Serializable;
import java.util.ArrayList;

public class TablaRutinasUsuario implements Serializable {
    private String ID;
    private TablaInfoRutinasUsuario informacion;
    private ArrayList<TablaDiaRutinaUsuario> semana;

    public TablaRutinasUsuario() {
    }

    public TablaRutinasUsuario(String ID, TablaInfoRutinasUsuario informacion, ArrayList<TablaDiaRutinaUsuario> semana) {
        this.ID = ID;
        this.informacion = informacion;
        this.semana = semana;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public TablaInfoRutinasUsuario getInformacion() {
        return informacion;
    }

    public void setInformacion(TablaInfoRutinasUsuario informacion) {
        this.informacion = informacion;
    }

    public ArrayList<TablaDiaRutinaUsuario> getSemana() {
        return semana;
    }

    public void setSemana(ArrayList<TablaDiaRutinaUsuario> semana) {
        this.semana = semana;
    }
}
