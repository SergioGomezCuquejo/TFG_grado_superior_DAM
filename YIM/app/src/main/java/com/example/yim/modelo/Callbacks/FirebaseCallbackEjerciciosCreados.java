package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaEjercicioCreado;

import java.util.ArrayList;

public interface FirebaseCallbackEjerciciosCreados {
    void onCallback(ArrayList<TablaEjercicioCreado> ejerciciosCreadosUsuario);
}

