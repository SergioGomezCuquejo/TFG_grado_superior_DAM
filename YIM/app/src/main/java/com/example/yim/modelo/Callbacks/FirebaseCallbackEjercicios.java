package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaEjercicio;

import java.util.ArrayList;

public interface FirebaseCallbackEjercicios {
    void onCallback(ArrayList<TablaEjercicio> ejercicios);
}

