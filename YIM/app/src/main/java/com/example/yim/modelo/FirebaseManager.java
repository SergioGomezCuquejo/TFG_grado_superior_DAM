package com.example.yim.modelo;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackUsuario;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaUsuario;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseManager {
    private DatabaseReference usuarioReference;
    private String idUsuario;

    public FirebaseManager() {
        //Inicializar instancias.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Referencias de Firebase Realtime Database
        usuarioReference = firebaseDatabase.getReference("usuarios");

        //Comprobar que no se ha cerrado sesión.
        if(auth.getCurrentUser() != null){
            idUsuario = auth.getCurrentUser().getUid();
        }
    }


    public void agregarUsuario(Context context, String idAuth, String contrasena, String email, String nombre) {
        try{
            TablaUsuario nuevoUsuario = new TablaUsuario(new TablaPerfil(), new ArrayList<TablaMusculosUsuario>());

            nuevoUsuario.getPerfil().setContrasena(contrasena);
            nuevoUsuario.getPerfil().setEmail(email);
            nuevoUsuario.getPerfil().setNombre(nombre);

            usuarioReference.child(idAuth).setValue(nuevoUsuario);
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar el usuario.");
            e.printStackTrace();
        }
    }

    public void obtenerUsuario(Context context, FirebaseCallbackUsuario callback){
        try{
            usuarioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaUsuario usuario = null;

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            usuario = usuarioSnapshot.getValue(TablaUsuario.class);
                        }
                    } else {
                        MostratToast.mostrarToast(context, "No hay usuarios.");
                    }

                    callback.onCallback(usuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los usuarios");
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al obtener el usuario.");
            e.printStackTrace();
        }
    }



    public void obtenerPerfil(Context context, FirebaseCallbackPerfil callback){
        try{
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
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al obtener el perfil del usuario.");
            e.printStackTrace();
        }
    }

    public void obtenerMusculosUsuario(Context context, FirebaseCallbackMusculosUsuario callback) {
        try {
            usuarioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaMusculosUsuario> musculosUsuarios = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot musculosSnapshot = usuarioSnapshot.child("musculos");

                            for (DataSnapshot musculoSnapshot : musculosSnapshot.getChildren()) {
                                TablaMusculosUsuario musculo = musculoSnapshot.getValue(TablaMusculosUsuario.class);
                                musculosUsuarios.add(musculo);
                            }
                        }
                    } else {
                        MostratToast.mostrarToast(context, "No se encontró el usuario.");
                    }

                    callback.onCallback(musculosUsuarios);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los músculos del usuario");
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al obtener los músculos del usuario.");
            e.printStackTrace();
        }
    }




}
