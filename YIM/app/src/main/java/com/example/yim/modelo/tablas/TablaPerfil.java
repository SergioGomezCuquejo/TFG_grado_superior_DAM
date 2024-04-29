package com.example.yim.modelo.tablas;

public class TablaPerfil {
    private int altura, edad, peso;
    private String contrasena, correo, genero, imagen, nombre, unidades;

    public TablaPerfil() {
        altura = 0;
        contrasena = "";
        correo = "";
        edad = 0;
        genero = "";
        imagen = "";
        nombre = "";
        peso = 0;
        unidades = "";
    }

    public TablaPerfil(int altura, String contrasena, String correo, int edad, String genero, String imagen, String nombre, int peso, String unidades) {
        this.altura = altura;
        this.contrasena = contrasena;
        this.correo = correo;
        this.edad = edad;
        this.genero = genero;
        this.imagen = imagen;
        this.nombre = nombre;
        this.peso = peso;
        this.unidades = unidades;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
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

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }
}
