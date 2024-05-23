package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaEjercicioUsuario;

import java.util.ArrayList;

public interface FirebaseCallbackEjerciciosUsuario {
    void onCallback(ArrayList<TablaEjercicioUsuario> ejerciciosUsuarios);
}

