package com.example.yim.modelo.tablas;

public class TablaPerfil {
    private double altura, peso;
    private int edad;
    private String email, genero, imagen, nombre;

    public TablaPerfil() {
        altura = 0;
        edad = 0;
        email = "";
        genero = "";
        imagen = "";
        nombre = "";
        peso = 0;
    }

    public TablaPerfil(double altura, int edad, String email, String genero,
                       String imagen, String nombre, double peso) {
        this.altura = altura;
        this.edad = edad;
        this.email = email;
        this.genero = genero;
        this.imagen = imagen;
        this.nombre = nombre;
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getEmail() {
        return email;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
}
