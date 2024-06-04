package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaLogroUsuario;

import java.util.ArrayList;

public interface FirebaseCallbackLogrosUsuario {
    void onCallback(ArrayList<TablaLogroUsuario> logros);
}

