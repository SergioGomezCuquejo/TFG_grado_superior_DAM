package com.example.yim.modelo.tablas;

public class TablaPerfil {
    private int altura, edad, peso;
    private String contrasena, email, genero, imagen, nombre;

    public TablaPerfil() {
        altura = 0;
        contrasena = "";
        edad = 0;
        email = "";
        genero = "";
        imagen = "";
        nombre = "";
        peso = 0;
    }

    public TablaPerfil(int altura, String contrasena, int edad, String email, String genero,
                       String imagen, String nombre, int peso) {
        this.altura = altura;
        this.contrasena = contrasena;
        this.edad = edad;
        this.email = email;
        this.genero = genero;
        this.imagen = imagen;
        this.nombre = nombre;
        this.peso = peso;
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

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
