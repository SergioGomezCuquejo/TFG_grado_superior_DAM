package com.example.yim.modelo.tablas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class TablaRutinaUsuario implements Serializable {
    private String ID;
    private TablaInfoRutinaUsuario informacion;
    private ArrayList<TablaDiaRutinaUsuario> semana;

    public TablaRutinaUsuario() {
    }

    public TablaRutinaUsuario(TablaInfoRutinaUsuario informacion, ArrayList<TablaDiaRutinaUsuario> semana) {
        this.informacion = informacion;
        this.semana = semana;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public TablaInfoRutinaUsuario getInformacion() {
        return informacion;
    }

    public void setInformacion(TablaInfoRutinaUsuario informacion) {
        this.informacion = informacion;
    }

    public ArrayList<TablaDiaRutinaUsuario> getSemana() {
        return semana;
    }

    public void setSemana(ArrayList<TablaDiaRutinaUsuario> semana) {
        this.semana = semana;
    }
    public void ordenarSemana(){
        Collections.sort(semana);
    }



}
