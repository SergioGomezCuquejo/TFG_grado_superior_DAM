package com.example.yim.modelo;

import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaUsuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    private final DatabaseReference chatsReference;

    public FirebaseManager() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        chatsReference = firebaseDatabase.getReference("usuarios");
    }

    public void agregarUsuario(String idAuth, String contrasena, String correo, String nombre) {
        try{
            TablaUsuario nuevoUsuario = new TablaUsuario(new TablaPerfil());

            nuevoUsuario.getPerfil().setContrasena(contrasena);
            nuevoUsuario.getPerfil().setCorreo(correo);
            nuevoUsuario.getPerfil().setNombre(nombre);

            chatsReference.child(idAuth).setValue(nuevoUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
