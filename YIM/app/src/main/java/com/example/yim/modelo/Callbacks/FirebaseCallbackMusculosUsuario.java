package com.example.yim.modelo.Callbacks;

import com.example.yim.modelo.tablas.TablaMusculosUsuario;

import java.util.ArrayList;

public interface FirebaseCallbackMusculosUsuario {
    void onCallback(ArrayList<TablaMusculosUsuario> musculosUsuario);
}

