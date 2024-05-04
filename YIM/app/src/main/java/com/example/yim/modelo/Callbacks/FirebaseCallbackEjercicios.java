package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaEjercicios;

import java.util.ArrayList;

public interface FirebaseCallbackEjercicios {
    void onCallback(ArrayList<TablaEjercicios> ejercicios);
}

