package com.example.yim.modelo.tablas;


import java.io.Serializable;
import java.util.ArrayList;

public class TablaHistorial implements Serializable, Comparable<TablaHistorial>  {
    private String dia;
    private ArrayList<TablaSerie> series;

    public TablaHistorial() {
    }

    public TablaHistorial(String dia, ArrayList<TablaSerie> series) {
        this.dia = dia;
        this.series = series;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public ArrayList<TablaSerie> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<TablaSerie> series) {
        this.series = series;
    }

    @Override
    public int compareTo(TablaHistorial o) {
        return o.getDia().compareTo(dia);
    }
}
