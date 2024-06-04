package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaMusculoUsuario;

import java.util.ArrayList;

public interface FirebaseCallbackMusculosUsuario {
    void onCallback(ArrayList<TablaMusculoUsuario> musculosUsuario);
}

