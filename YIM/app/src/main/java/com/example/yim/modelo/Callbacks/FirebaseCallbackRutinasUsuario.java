package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaRutinasUsuario;

import java.util.ArrayList;

public interface FirebaseCallbackRutinasUsuario {
    void onCallback(ArrayList<TablaRutinasUsuario> rutinas);
}

