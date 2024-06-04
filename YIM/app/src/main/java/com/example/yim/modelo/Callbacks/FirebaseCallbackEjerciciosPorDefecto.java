package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;

import java.util.ArrayList;

public interface FirebaseCallbackEjerciciosPorDefecto {
    void onCallback(ArrayList<TablaEjercicioPorDefecto> ejerciciosPorDefectoUsuario);
}

