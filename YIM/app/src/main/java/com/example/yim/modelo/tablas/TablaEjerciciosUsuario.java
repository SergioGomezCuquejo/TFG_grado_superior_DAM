package com.example.yim.modelo.tablas;

import java.util.ArrayList;

public class TablaEjerciciosUsuario {
    private String ID;
    private ArrayList<String> consejos_clave;
    private ArrayList<String> ejecucion;
    private String imagen;
    private ArrayList<String> musculos;
    private String nombre;
    private String notas;
    private int peso_maximo;
    private int repeticiones_maximas;
    private int repeticiones_recomendadas;
    private int series_maximas;
    private int series_recomendadas;
    private int tiempo_descanso;
    private int veces_no_realizado;
    private int veces_realizado;
    private int veces_usado_en_rutian_activa;
    private int veces_usado_en_rutinas;

    public TablaEjerciciosUsuario() {
    }

    public TablaEjerciciosUsuario(ArrayList<String> consejos_clave, ArrayList<String> ejecucion, String imagen, ArrayList<String> musculos, String nombre, String notas, int peso_maximo,
                                  int repeticiones_maximas, int repeticiones_recomendadas, int series_maximas, int series_recomendadas,
                                  int tiempo_descanso, int veces_no_realizado, int veces_realizado, int veces_usado_en_rutian_activa, int veces_usado_en_rutinas) {
        this.consejos_clave = consejos_clave;
        this.ejecucion = ejecucion;
        this.imagen = imagen;
        this.musculos = musculos;
        this.nombre = nombre;
        this.notas = notas;
        this.peso_maximo = peso_maximo;
        this.repeticiones_maximas = repeticiones_maximas;
        this.repeticiones_recomendadas = repeticiones_recomendadas;
        this.series_maximas = series_maximas;
        this.series_recomendadas = series_recomendadas;
        this.tiempo_descanso = tiempo_descanso;
        this.veces_no_realizado = veces_no_realizado;
        this.veces_realizado = veces_realizado;
        this.veces_usado_en_rutian_activa = veces_usado_en_rutian_activa;
        this.veces_usado_en_rutinas = veces_usado_en_rutinas;
    }

    public TablaEjerciciosUsuario(TablaEjercicios ejercicio) {
        consejos_clave = ejercicio.getConsejos_clave();
        ejecucion = ejercicio.getEjecucion();
        imagen = ejercicio.getImagen();
        musculos = ejercicio.getMusculos();
        nombre = ejercicio.getNombre();
        notas = "";
        peso_maximo = 0;
        repeticiones_maximas = 0;
        repeticiones_recomendadas = 0;
        series_maximas = 0;
        series_recomendadas = 0;
        tiempo_descanso = 0;
        veces_no_realizado = 0;
        veces_realizado = 0;
        veces_usado_en_rutian_activa = 0;
        veces_usado_en_rutinas = 0;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<String> getConsejos_clave() {
        return consejos_clave;
    }

    public void setConsejos_clave(ArrayList<String> consejos_clave) {
        this.consejos_clave = consejos_clave;
    }

    public ArrayList<String> getEjecucion() {
        return ejecucion;
    }

    public void setEjecucion(ArrayList<String> ejecucion) {
        this.ejecucion = ejecucion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<String> getMusculos() {
        return musculos;
    }

    public void setMusculos(ArrayList<String> musculos) {
        this.musculos = musculos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getPeso_maximo() {
        return peso_maximo;
    }

    public void setPeso_maximo(int peso_maximo) {
        this.peso_maximo = peso_maximo;
    }

    public int getRepeticiones_maximas() {
        return repeticiones_maximas;
    }

    public void setRepeticiones_maximas(int repeticiones_maximas) {
        this.repeticiones_maximas = repeticiones_maximas;
    }

    public int getRepeticiones_recomendadas() {
        return repeticiones_recomendadas;
    }

    public void setRepeticiones_recomendadas(int repeticiones_recomendadas) {
        this.repeticiones_recomendadas = repeticiones_recomendadas;
    }

    public int getSeries_maximas() {
        return series_maximas;
    }

    public void setSeries_maximas(int series_maximas) {
        this.series_maximas = series_maximas;
    }

    public int getSeries_recomendadas() {
        return series_recomendadas;
    }

    public void setSeries_recomendadas(int series_recomendadas) {
        this.series_recomendadas = series_recomendadas;
    }

    public int getTiempo_descanso() {
        return tiempo_descanso;
    }

    public void setTiempo_descanso(int tiempo_descanso) {
        this.tiempo_descanso = tiempo_descanso;
    }

    public int getVeces_no_realizado() {
        return veces_no_realizado;
    }

    public void setVeces_no_realizado(int veces_no_realizado) {
        this.veces_no_realizado = veces_no_realizado;
    }

    public int getVeces_realizado() {
        return veces_realizado;
    }

    public void setVeces_realizado(int veces_realizado) {
        this.veces_realizado = veces_realizado;
    }

    public int getVeces_usado_en_rutian_activa() {
        return veces_usado_en_rutian_activa;
    }

    public void setVeces_usado_en_rutian_activa(int veces_usado_en_rutian_activa) {
        this.veces_usado_en_rutian_activa = veces_usado_en_rutian_activa;
    }

    public int getVeces_usado_en_rutinas() {
        return veces_usado_en_rutinas;
    }

    public void setVeces_usado_en_rutinas(int veces_usado_en_rutinas) {
        this.veces_usado_en_rutinas = veces_usado_en_rutinas;
    }

    @Override
    public String toString() {
        return "TablaEjerciciosUsuario{" +
                "consejos_clave=" + consejos_clave +
                ", ejecucion=" + ejecucion +
                ", imagen='" + imagen + '\'' +
                ", musculos=" + musculos +
                ", nombre='" + nombre + '\'' +
                ", notas='" + notas + '\'' +
                ", peso_maximo=" + peso_maximo +
                ", repeticiones_maximas=" + repeticiones_maximas +
                ", repeticiones_recomendadas=" + repeticiones_recomendadas +
                ", series_maximas=" + series_maximas +
                ", series_recomendadas=" + series_recomendadas +
                ", tiempo_descanso=" + tiempo_descanso +
                ", veces_no_realizado=" + veces_no_realizado +
                ", veces_realizado=" + veces_realizado +
                ", veces_usado_en_rutian_activa=" + veces_usado_en_rutian_activa +
                ", veces_usado_en_rutinas=" + veces_usado_en_rutinas +
                '}';
    }
}
