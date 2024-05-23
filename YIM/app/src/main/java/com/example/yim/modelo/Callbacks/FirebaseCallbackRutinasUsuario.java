package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaRutinaUsuario;

import java.util.ArrayList;

public interface FirebaseCallbackRutinasUsuario {
    void onCallback(ArrayList<TablaRutinaUsuario> rutinas);
}

