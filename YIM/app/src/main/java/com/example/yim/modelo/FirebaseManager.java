package com.example.yim.modelo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseManager {
    private final DatabaseReference usuarioReference;
    private FirebaseAuth auth;
    String idUsuario;

    public FirebaseManager() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        usuarioReference = firebaseDatabase.getReference("usuarios");

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            idUsuario = auth.getCurrentUser().getUid();
        }
    }

    //Tabla Usuario
    public void agregarUsuario(String idAuth, String contrasena, String email, String nombre) {
        try{
            TablaUsuario nuevoUsuario = new TablaUsuario(new TablaPerfil());

            nuevoUsuario.getPerfil().setContrasena(contrasena);
            nuevoUsuario.getPerfil().setEmail(email);
            nuevoUsuario.getPerfil().setNombre(nombre);

            usuarioReference.child(idAuth).setValue(nuevoUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtenerUsuario(Context context, FirebaseCallbackUsuario callback){
        usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TablaUsuario usuario = null;

                if (dataSnapshot.exists()) {
                    DataSnapshot personaSnapshot = dataSnapshot.child(idUsuario);

                    if (personaSnapshot.exists()) {
                        usuario = personaSnapshot.getValue(TablaUsuario.class);
                    }
                } else {
                    Toast.makeText(context, "No hay personas", Toast.LENGTH_SHORT).show();
                }

                callback.onCallback(usuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error
                Toast.makeText(context, "Error al obtener las personas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obternerPerfil(Context context, FirebaseCallbackPerfil callback){
        obtenerUsuario(context, new FirebaseCallbackUsuario() {
            @Override
            public void onCallback(TablaUsuario usuario) {
                TablaPerfil perfil = null;
                if (usuario != null){
                    perfil = usuario.getPerfil();
                }
                callback.onCallback(perfil);
            }
        });
    }






}
