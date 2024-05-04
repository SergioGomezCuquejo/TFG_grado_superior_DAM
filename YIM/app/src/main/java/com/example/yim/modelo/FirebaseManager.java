package com.example.yim.modelo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.yim.controlador.Adaptadores.MusculosAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicios;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculos;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackUsuario;
import com.example.yim.modelo.tablas.TablaEjercicios;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.modelo.tablas.TablaMusculos;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaUsuario;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.vista.Musculos;
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
    private DatabaseReference ejercicioReference, musculosReference, usuarioReference;
    private String idUsuario;

    public FirebaseManager() {
        //Inicializar instancias.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Referencias de Firebase Realtime Database
        ejercicioReference = firebaseDatabase.getReference("ejercicios");
        musculosReference = firebaseDatabase.getReference("musculos");
        usuarioReference = firebaseDatabase.getReference("usuarios");

        //Comprobar que no se ha cerrado sesión.
        if(auth.getCurrentUser() != null){
            idUsuario = auth.getCurrentUser().getUid();
        }
    }

    public void obtenerEjercicios(Context context, FirebaseCallbackEjercicios callback){
        try{
            ejercicioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicios> ejercicios = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ejerciciosSnapshot : dataSnapshot.getChildren()) {
                            ejercicios.add(ejerciciosSnapshot.getValue(TablaEjercicios.class));
                        }

                    } else {
                        Toast.makeText(context, "No hay ejercicios disponibles", Toast.LENGTH_SHORT).show();
                    }

                    callback.onCallback(ejercicios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los ejercicios");
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al obtener los ejercicios.");
            e.printStackTrace();
        }
    }

    public void obtenerMusculos(Context context, FirebaseCallbackMusculos callback){
        try{
            musculosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaMusculos> musculos = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot musculosSnapshot : dataSnapshot.getChildren()) {
                            musculos.add(musculosSnapshot.getValue(TablaMusculos.class));
                        }

                    } else {
                        Toast.makeText(context, "No hay músculos disponibles", Toast.LENGTH_SHORT).show();
                    }

                    callback.onCallback(musculos);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los músculos");
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al obtener los músculos.");
            e.printStackTrace();
        }
    }


    public void agregarUsuario(Context context, String idAuth, String contrasena, String email, String nombre) {
        try{
            obtenerMusculos(context, new FirebaseCallbackMusculos() {
                @Override
                public void onCallback(ArrayList<TablaMusculos> musculos) {
                    obtenerEjercicios(context, new FirebaseCallbackEjercicios() {
                        @Override
                        public void onCallback(ArrayList<TablaEjercicios> ejercicios) {

                            agregarUsuario(context, idAuth, contrasena, email, nombre, musculos, ejercicios);

                        }
                    });
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar el usuario.");
            e.printStackTrace();
        }
    }

    public void agregarUsuario(Context context, String idAuth, String contrasena, String email, String nombre,
                               ArrayList<TablaMusculos> musculos, ArrayList<TablaEjercicios> ejercicios){
        try{
            TablaUsuario nuevoUsuario = new TablaUsuario(new TablaPerfil(), new ArrayList<TablaMusculosUsuario>(), new ArrayList<TablaEjerciciosUsuario>());

            //Perfil
            nuevoUsuario.getPerfil().setContrasena(contrasena);
            nuevoUsuario.getPerfil().setEmail(email);
            nuevoUsuario.getPerfil().setNombre(nombre);

            //Músculos
            ArrayList<TablaMusculosUsuario> musculosUsuario = new ArrayList<TablaMusculosUsuario>();
            TablaMusculosUsuario musculoUsuario;
            for (TablaMusculos musculo : musculos) {
                musculoUsuario = new TablaMusculosUsuario(musculo.getColor_fondo(), musculo.getColor_letras(), musculo.getNombre());
                musculosUsuario.add(musculoUsuario);
            }
            nuevoUsuario.setMusculos(musculosUsuario);

            //Ejercicios
            ArrayList<TablaEjerciciosUsuario> ejerciciosUsuario = new ArrayList<TablaEjerciciosUsuario>();
            TablaEjerciciosUsuario ejercicioUsuario;
            for (TablaEjercicios ejercicio : ejercicios) {
                ejercicioUsuario = new TablaEjerciciosUsuario(ejercicio);
                ejerciciosUsuario.add(ejercicioUsuario);
            }
            nuevoUsuario.setEjercicios(ejerciciosUsuario);

            //Enviar
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
                                musculo.setID(musculoSnapshot.getKey());
                                musculosUsuarios.add(musculo);
                            }
                        }
                    } else {
                        MostratToast.mostrarToast(context, "Usuario no encontrado.");
                    }

                    callback.onCallback(musculosUsuarios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los músculos del usuario");
                }
            });
        } catch (Exception ex) {
            MostratToast.mostrarToast(context, "Error al obtener los músculos del usuario.");
            ex.printStackTrace();
        }
    }

    public boolean actualizarColoresMusculosUsuario(Context context, String idMusculo, String colorFondo, String colorFuente){
        boolean actualizado = false;
        try{
            DatabaseReference musculoReference = usuarioReference.child(idUsuario).child("musculos").child(idMusculo);

            musculoReference.child("color_fondo").setValue(colorFondo);
            musculoReference.child("color_fuente").setValue(colorFuente);

            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar los colores del músculo.");
            e.printStackTrace();
        }
        return actualizado;
    }




}
