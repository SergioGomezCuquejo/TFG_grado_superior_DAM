package com.example.yim.modelo.tablas;

public class TablaUsuario {
    private TablaPerfil perfil;

    public TablaUsuario() {
        perfil = new TablaPerfil();
    }

    public TablaUsuario(TablaPerfil perfil) {
        this.perfil = perfil;
    }

    public TablaPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(TablaPerfil perfil) {
        this.perfil = perfil;
    }
}
