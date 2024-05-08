package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaLogrosUsuario;

import java.util.ArrayList;

public interface FirebaseCallbackLogrosUsuario {
    void onCallback(ArrayList<TablaLogrosUsuario> logros);
}

