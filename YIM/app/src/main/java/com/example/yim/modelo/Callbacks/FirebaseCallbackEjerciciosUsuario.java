package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaEjercicios;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;

import java.util.ArrayList;

public interface FirebaseCallbackEjerciciosUsuario {
    void onCallback(ArrayList<TablaEjerciciosUsuario> ejerciciosUsuarios);
}

