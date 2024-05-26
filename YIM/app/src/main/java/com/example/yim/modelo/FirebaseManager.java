package com.example.yim.modelo;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicioCreado;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicioPorDefecto;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicios;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosCreados;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosPorDefecto;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogros;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogrosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculos;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaActiva;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinasUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaEjercicio;
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;
import com.example.yim.modelo.tablas.TablaHistorial;
import com.example.yim.modelo.tablas.TablaLogro;
import com.example.yim.modelo.tablas.TablaLogroUsuario;
import com.example.yim.modelo.tablas.TablaMusculo;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaRutinaActiva;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaUsuario;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class FirebaseManager {

    // Variables de instancias.
    private final DatabaseReference ejerciciosReference, logrosReference, musculosReference, usuariosReference, ejerciciosUsuarioReference,
            logrosUsuarioReference, perfilUsuarioReference, musculosUsuarioReference, rutinaActivaUsuarioReference, rutinasUsuarioReference;
    private String idUsuario;


    public FirebaseManager() {
        // Inicializar instancias.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String IDUsuario;

        // Comprobar que no se ha cerrado sesión.
        if(auth.getCurrentUser() != null){
            IDUsuario = auth.getCurrentUser().getUid();
            idUsuario = auth.getCurrentUser().getUid(); //todo quitar
        } else {
            IDUsuario = "";
        }

        //Referencias de Firebase Realtime Database
        ejerciciosReference = firebaseDatabase.getReference("ejercicios"); //Nodo EJERCICIOS.
        logrosReference = firebaseDatabase.getReference("logros"); //Nodo LOGROS.
        musculosReference = firebaseDatabase.getReference("musculos"); //Nodo MUSCULOS.
        usuariosReference = firebaseDatabase.getReference("usuarios"); //Nodo USUARIOS.

        ejerciciosUsuarioReference = usuariosReference.child(IDUsuario).child("ejercicios"); //Nodo EJERCICIOS del nodo USUARIOS.
        logrosUsuarioReference = usuariosReference.child(IDUsuario).child("logros"); //Nodo LOGROS del nodo USUARIOS.
        perfilUsuarioReference = usuariosReference.child(IDUsuario).child("perfil"); //Nodo PERFIL del nodo USUARIOS.
        musculosUsuarioReference = usuariosReference.child(IDUsuario).child("musculos"); //Nodo MUSCULOS del nodo USUARIOS.
        rutinaActivaUsuarioReference = usuariosReference.child(IDUsuario).child("rutina_activa"); //Nodo RUTINA ACTIVA del nodo USUARIOS.
        rutinasUsuarioReference = usuariosReference.child(IDUsuario).child("rutinas"); //Nodo RUTINAS del nodo USUARIOS.
    }

    // Nodo EJERCICIOS.
    // Obtener ejercicios.
    public void obtenerEjercicios(Context context, FirebaseCallbackEjercicios callback){
        try {
            ejerciciosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicio> ejercicios = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ejerciciosSnapshot : dataSnapshot.getChildren()) {
                            ejercicios.add(ejerciciosSnapshot.getValue(TablaEjercicio.class));
                        }

                    } else {
                        mostrarToast(context, "No hay ejercicios disponibles");
                    }

                    callback.onCallback(ejercicios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los ejercicios.");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los ejercicios.");
            ex.printStackTrace();
        }
    }

    // Nodo LOGROS.
    // Obtener logros.
    public void obtenerLogros(Context context, FirebaseCallbackLogros callback){
        try {
            logrosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaLogro> logros = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot logrosSnapshot : dataSnapshot.getChildren()) {
                            logros.add(logrosSnapshot.getValue(TablaLogro.class));
                        }

                    } else {
                        mostrarToast(context, "No hay logros disponibles");
                    }

                    callback.onCallback(logros);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los logros.");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los logros.");
            ex.printStackTrace();
        }
    }

    // Nodo MUSCULOS.
    // Obtener músculos.
    public void obtenerMusculos(Context context, FirebaseCallbackMusculos callback){
        try {
            musculosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaMusculo> musculos = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot musculosSnapshot : dataSnapshot.getChildren()) {
                            musculos.add(musculosSnapshot.getValue(TablaMusculo.class));
                        }

                    } else {
                        mostrarToast(context, "No hay músculos disponibles");
                    }

                    callback.onCallback(musculos);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los músculos.");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los músculos.");
            ex.printStackTrace();
        }
    }

    // Nodo USUARIOS.

    // Crear usuario. todo
    public void agregarUsuario(Context context, String IDUsuario, TablaUsuario usuario, FirebaseCallbackBoolean callback) {
        try{
            usuariosReference.child(IDUsuario).setValue(usuario, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean agregado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al agregar el usuario: " + databaseError.getMessage());
                        agregado = false;
                    } else {
                        agregado = true;
                    }
                    callback.onCallback(agregado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los usuarios.");
            ex.printStackTrace();
        }
    }

    // Obtener usuario. todo

    // Actualizar usurio. todo

    // Eliminar usuario. todo


    // todo Nodo EJERCICIOS del nodo USUARIOS.
    // Crear ejercicio.
    public void agregarEjercicio(Context context, TablaEjercicioCreado nuevoEjercicio, FirebaseCallbackBoolean callback){

        try{
            String IDEjercicio = ejerciciosUsuarioReference.child("creados").push().getKey();
            ejerciciosUsuarioReference.child("creados").child(IDEjercicio).setValue(nuevoEjercicio, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean agregado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al agregar el ejercicio: " + databaseError.getMessage());
                        agregado = false;
                    } else {
                        agregado = true;
                    }
                    callback.onCallback(agregado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los ejercicios del usuario.");
            ex.printStackTrace();
        }
    }

    // Obtener ejercicios.
    public void obtenerEjerciciosCreados(Context context, FirebaseCallbackEjerciciosCreados callback) {
        try {
            ejerciciosUsuarioReference.child("creados").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicioCreado> ejerciciosUsuarios = new ArrayList<TablaEjercicioCreado>();
                    TablaEjercicioCreado ejercicio;

                    for (DataSnapshot ejercicioSnapshot : dataSnapshot.getChildren()) {
                        ejercicio = ejercicioSnapshot.getValue(TablaEjercicioCreado.class);
                        ejercicio.setID(ejercicioSnapshot.getKey());
                        ejerciciosUsuarios.add(ejercicio);
                    }

                    callback.onCallback(ejerciciosUsuarios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los ejercicios creados por el usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los ejercicios creados por el usuario.");
            ex.printStackTrace();
        }
    }
    public void obtenerEjerciciosPorDefecto(Context context, FirebaseCallbackEjerciciosPorDefecto callback) {
        try {
            ejerciciosUsuarioReference.child("por_defecto").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicioPorDefecto> ejerciciosUsuarios = new ArrayList<TablaEjercicioPorDefecto>();

                    for (DataSnapshot ejercicioSnapshot : dataSnapshot.getChildren()) {
                        TablaEjercicioPorDefecto ejercicio = ejercicioSnapshot.getValue(TablaEjercicioPorDefecto.class);
                        ejercicio.setID(ejercicioSnapshot.getKey());
                        ejerciciosUsuarios.add(ejercicio);
                    }

                    callback.onCallback(ejerciciosUsuarios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los ejercicios por defecto del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los ejercicios por defecto del usuario.");
            ex.printStackTrace();
        }
    }
    public void obtenerEjercicioCreado(Context context, String IDEjercicio, FirebaseCallbackEjercicioCreado callback) {
        try {
            ejerciciosUsuarioReference.child("creados").child(IDEjercicio).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaEjercicioCreado ejercicio = new TablaEjercicioCreado();

                    if (dataSnapshot.exists()) {
                        ejercicio = dataSnapshot.getValue(TablaEjercicioCreado.class);

                    } else {
                        mostrarToast(context, "Ejercicio no encontrado.");
                    }

                    callback.onCallback(ejercicio);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los ejercicios del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los ejercicios del usuario.");
            ex.printStackTrace();
        }
    }
    public void obtenerEjercicioPorDefecto(Context context, String IDEjercicio, FirebaseCallbackEjercicioPorDefecto callback) {
        try {
            ejerciciosUsuarioReference.child("por_defecto").child(IDEjercicio).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaEjercicioPorDefecto ejercicio = new TablaEjercicioPorDefecto();

                    if (dataSnapshot.exists()) {
                        ejercicio = dataSnapshot.getValue(TablaEjercicioPorDefecto.class);

                    } else {
                        mostrarToast(context, "Ejercicio no encontrado.");
                    }

                    callback.onCallback(ejercicio);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los ejercicios del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los ejercicios del usuario.");
            ex.printStackTrace();
        }
    }
    public void obtenerEjerciciosCreadosConEstadisticas(Context context, FirebaseCallbackEjerciciosCreados callback) {
        try {
            ejerciciosUsuarioReference.child("creados").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicioCreado> ejerciciosUsuarios = new ArrayList<TablaEjercicioCreado>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ejercicioSnapshot : dataSnapshot.getChildren()) {
                            TablaEjercicioCreado ejercicio = ejercicioSnapshot.getValue(TablaEjercicioCreado.class);
                            if(ejercicio.getEstadisticas() != null){
                                ejercicio.setID(ejercicioSnapshot.getKey());
                                ejerciciosUsuarios.add(ejercicio);
                            }
                        }

                    } else {
                        mostrarToast(context, "Ejercicios no encontrados.");
                    }

                    callback.onCallback(ejerciciosUsuarios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los ejercicios del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los ejercicios del usuario.");
            ex.printStackTrace();
        }
    }
    public void obtenerEjerciciosPorDefectoConEstadisticas(Context context, FirebaseCallbackEjerciciosPorDefecto callback) {
        try {
            ejerciciosUsuarioReference.child("por_defecto").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicioPorDefecto> ejerciciosUsuarios = new ArrayList<TablaEjercicioPorDefecto>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ejercicioSnapshot : dataSnapshot.getChildren()) {
                            TablaEjercicioPorDefecto ejercicio = ejercicioSnapshot.getValue(TablaEjercicioPorDefecto.class);
                            if(ejercicio.getEstadisticas() != null){
                                ejercicio.setID(ejercicioSnapshot.getKey());
                                ejerciciosUsuarios.add(ejercicio);
                            }
                        }

                    } else {
                        mostrarToast(context, "Ejercicios no encontrados.");
                    }

                    callback.onCallback(ejerciciosUsuarios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los ejercicios del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los ejercicios del usuario.");
            ex.printStackTrace();
        }
    }

    // Actualizar ejercicio.
    public void actualizarEjercicioCreado(Context context, TablaEjercicioCreado ejercicio, FirebaseCallbackBoolean callback){
        try{
            ejerciciosUsuarioReference.child("creados").child(ejercicio.getID()).setValue(ejercicio, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean actualizado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al actualizar el ejercicio: " + databaseError.getMessage());
                        actualizado = false;
                    } else {
                        actualizado = true;
                    }
                    callback.onCallback(actualizado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener el perfil del usuario.");
            ex.printStackTrace();
        }
    }
    public void actualizarEjercicioCreado(Context context, String IDEjercicio, String urlImagen, FirebaseCallbackBoolean callback){

        try{
            ejerciciosUsuarioReference.child("creados").child(IDEjercicio).child("imagen").setValue(urlImagen, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean actualizado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al actualizar la imagen del ejercicio: " + databaseError.getMessage());
                        actualizado = false;
                    } else {
                        actualizado = true;
                    }
                    callback.onCallback(actualizado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener el ejercicio del usuario.");
            ex.printStackTrace();
        }
    }


    // Eliminar ejercicio.
    public void eliminarEjercicio(Context context, String IDEjercicio, FirebaseCallbackBoolean callback){

        try{
            ejerciciosUsuarioReference.child("creados").child(IDEjercicio).removeValue( new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean eliminado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al eliminar el ejercicio: " + databaseError.getMessage());
                        eliminado = false;
                    }else{
                        eliminado = true;
                    }
                    callback.onCallback(eliminado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al eliminar el ejercicio.");
            ex.printStackTrace();
        }
    }


    //todo Nodo LOGROS del nodo USUARIOS.
    // Crear logro. todo
    // Obtener logros.
    public void obtenerLogrosUsuario(Context context, FirebaseCallbackLogrosUsuario callback) {
        try {
            logrosUsuarioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaLogroUsuario> logrosUsuario = new ArrayList<TablaLogroUsuario>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot logroSnapshot : dataSnapshot.getChildren()) {
                            TablaLogroUsuario logro = logroSnapshot.getValue(TablaLogroUsuario.class);
                            logro.setID(logroSnapshot.getKey());
                            logrosUsuario.add(logro);
                        }

                    } else {
                        mostrarToast(context, "Logros no encontrado.");
                    }

                    callback.onCallback(logrosUsuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener los logros del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los logros del usuario.");
            ex.printStackTrace();
        }
    }

    // Actualizar logro. todo
    // Eliminar logro. todo


    // todo Nodo PERFIL del nodo USUARIOS.
    // Crear perfil. todo

    // Obtener perfil.
    public void obtenerPerfil(Context context, FirebaseCallbackPerfil callback) {
        try {
            perfilUsuarioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaPerfil perfil = new TablaPerfil();

                    if (dataSnapshot.exists()) {
                        perfil = dataSnapshot.getValue(TablaPerfil.class);

                    } else {
                        mostrarToast(context, "Perfil no encontrado.");
                    }

                    callback.onCallback(perfil);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener el perfil del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener el perfil del usuario.");
            ex.printStackTrace();
        }
    }

    // Actualizar perfil.
    public void actualizarPerfil(Context context, TablaPerfil perfil, FirebaseCallbackBoolean callback){
        try{
            perfilUsuarioReference.setValue(perfil, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean actualizado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al actualizar el perfil: " + databaseError.getMessage());
                        actualizado = false;
                    } else {
                        actualizado = true;
                    }
                    callback.onCallback(actualizado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener el perfil del usuario.");
            ex.printStackTrace();
        }
    }
    public void actualizarPerfil(Context context, String urlImagen, FirebaseCallbackBoolean callback){
        try{
            perfilUsuarioReference.child("imagen").setValue(urlImagen, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean actualizado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al actualizar la imagen del perfil: " + databaseError.getMessage());
                        actualizado = false;
                    } else {
                        actualizado = true;
                    }
                    callback.onCallback(actualizado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener el perfil del usuario.");
            ex.printStackTrace();
        }
    }

    // Eliminar perfil. todo


    // todo Nodo MUSCULOS del nodo USUARIOS.
    // Crear músculo. todo

    // Obtener músculos.
    public void obtenerMusculosUsuario(Context context, FirebaseCallbackMusculosUsuario callback) {
        try {
            musculosUsuarioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaMusculoUsuario> musculosUsuario = new ArrayList<>();

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot musculoSnapshot : dataSnapshot.getChildren()) {
                            TablaMusculoUsuario musculo = musculoSnapshot.getValue(TablaMusculoUsuario.class);
                            musculo.setID(musculoSnapshot.getKey());
                            musculosUsuario.add(musculo);
                        }

                    } else {
                        mostrarToast(context, "Músculos no encontrados.");
                    }

                    callback.onCallback(musculosUsuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los músculos del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los músculos del usuario.");
            ex.printStackTrace();
        }
    }

    // Actualizar músculo.
    public void actualizarMusculoUsuario(Context context, TablaMusculoUsuario musculo, FirebaseCallbackBoolean callback){
        try{
            musculosUsuarioReference.child(musculo.getID()).setValue(musculo, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean actualizado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al actualizar el músculo: " + databaseError.getMessage());
                        actualizado = false;
                    } else {
                        actualizado = true;
                    }
                    callback.onCallback(actualizado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener los músculos del usuario.");
            ex.printStackTrace();
        }
    }

    // Eliminar músculo. todo


    // todo Nodo RUTINA ACTIVA del nodo USUARIOS.

    // Crear rutina activa.
    public void agregarRutinaActiva(Context context, TablaRutinaActiva nuevaRutinaActiva){
        try{
            rutinaActivaUsuarioReference.setValue(nuevaRutinaActiva, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        mostrarToast(context, "Error al agregar la rutina semanal: " + databaseError.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar la rutina semanal.");
            e.printStackTrace();
        }
    }

    // Obtener rutina activa.
    public void obtenerRutinaActiva(Context context, FirebaseCallbackRutinaActiva callback) {
        try {
            rutinaActivaUsuarioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaRutinaActiva rutinaActiva = null;

                    if (dataSnapshot.exists()) {
                        rutinaActiva = dataSnapshot.getValue(TablaRutinaActiva.class);

                    }

                    callback.onCallback(rutinaActiva);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener la rutina activa del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener la rutina activa del usuario.");
            ex.printStackTrace();
        }
    }


    // Actualizar rutina activa.
    public void actualizarRutinaActiva(Context context, int numDia, int numEjercicio, ArrayList<TablaHistorial> historial, FirebaseCallbackBoolean callback){
        try{
            DatabaseReference historialRutinaActivaUsuarioReference = rutinaActivaUsuarioReference.child("semana").child(String.valueOf(numDia-1)).
                    child("ejercicios").child(String.valueOf(numEjercicio)).child("historial");
            historialRutinaActivaUsuarioReference.setValue(historial, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean actualizada;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al actualizar el ejercicio: " + databaseError.getMessage());
                        actualizada = false;
                    } else {
                        actualizada = true;
                    }
                    callback.onCallback(actualizada);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener las rutinas del usuario.");
            ex.printStackTrace();
        }
    }
    public void actualizarRutinaActiva(Context context, int numDia, int numEjercicio, int seriesRealizadas, FirebaseCallbackBoolean callback){
        try{
            DatabaseReference seriesRutinaActivaUsuarioReference = rutinaActivaUsuarioReference.child("semana").child(String.valueOf(numDia-1)).
                    child("ejercicios").child(String.valueOf(numEjercicio)).child("series_realizadas");
            seriesRutinaActivaUsuarioReference.setValue((seriesRealizadas+1), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean actualizada;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al actualizar el ejercicio: " + databaseError.getMessage());
                        actualizada = false;
                    } else {
                        actualizada = true;
                    }
                    callback.onCallback(actualizada);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener la rutina activa del usuario.");
            ex.printStackTrace();
        }
    }
    public void actualizarRutinaActiva(Context context, TablaRutinaUsuario rutina){
        try{
            rutinaActivaUsuarioReference.child("semana").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long id = dataSnapshot.getChildrenCount();

                    for(TablaDiaRutinaUsuario diaRutinaUsuario : rutina.getSemana()){
                        diaRutinaUsuario.setDia((int) id+1);
                        rutinaActivaUsuarioReference.child("semana").child(String.valueOf(id)).setValue(diaRutinaUsuario);
                        id++;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener la rutina semanal del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener la rutina semanal del usuario.");
            ex.printStackTrace();
        }
    }

    // Eliminar rutina activa.

    public void eliminarRutinaActiva(Context context){
        try{
            rutinaActivaUsuarioReference.removeValue( new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                    if (databaseError != null) {
                        mostrarToast(context, "Error al eliminar la rutina semanal: " + databaseError.getMessage());

                    }
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al eliminar la rutina semanal del usuario.");
            ex.printStackTrace();
        }
    }



    // todo Nodo RUTINAS del nodo USUARIOS.

    // Crear rutina.
    public void agregarRutina(Context context, TablaRutinaUsuario rutina, FirebaseCallbackBoolean callback){
        try{
            String IDRutina = rutinasUsuarioReference.push().getKey();
            rutinasUsuarioReference.child(IDRutina).setValue(rutina, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean agregada;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al agregar la rutina: " + databaseError.getMessage());
                        agregada = false;
                    } else {
                        agregada = true;
                    }
                    callback.onCallback(agregada);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener las rutinas del usuario.");
            ex.printStackTrace();
        }
    }

    // Obtener rutinas.
    public void obtenerRutinasUsuario(Context context, FirebaseCallbackRutinasUsuario callback){
        try {
            rutinasUsuarioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaRutinaUsuario> rutinasUsuario = new ArrayList<TablaRutinaUsuario>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot rutinaSnapshot : dataSnapshot.getChildren()) {
                            TablaRutinaUsuario rutina = rutinaSnapshot.getValue(TablaRutinaUsuario.class);
                            rutina.setID(rutinaSnapshot.getKey());
                            rutinasUsuario.add(rutina);
                        }

                    }

                    callback.onCallback(rutinasUsuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener las rutinas del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener las rutinas del usuario.");
            ex.printStackTrace();
        }
    }
    public void obtenerRutinaUsuario(Context context, String IDRutina, FirebaseCallbackRutinaUsuario callback){
        try {
            rutinasUsuarioReference.child(IDRutina).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaRutinaUsuario rutinaUsuario = new TablaRutinaUsuario();

                    if (dataSnapshot.exists()) {
                        rutinaUsuario = dataSnapshot.getValue(TablaRutinaUsuario.class);

                    } else {
                        mostrarToast(context, "Rutina no encontrada.");
                    }

                    callback.onCallback(rutinaUsuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener la rutina del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener las rutinas del usuario.");
            ex.printStackTrace();
        }
    }

    // Actualizar rutina.
    public void desactivarRutinas(Context context, FirebaseCallbackRutinaUsuario callback){
        try {
            rutinasUsuarioReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaRutinaUsuario rutina;
                    TablaRutinaUsuario rutinaActivaUsuario = null;

                    if (dataSnapshot.exists()) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        while (iterator.hasNext() && rutinaActivaUsuario == null) {
                            DataSnapshot rutinaSnapshot = iterator.next();
                            rutina = rutinaSnapshot.getValue(TablaRutinaUsuario.class);
                            if (rutina.getInformacion().isActivo()) {
                                rutina.setID(rutinaSnapshot.getKey());
                                rutinaActivaUsuario = rutina;
                            }
                        }

                    } else {
                        mostrarToast(context, "Rutina no encontrada.");
                    }

                    callback.onCallback(rutinaActivaUsuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarToast(context, "Error al obtener la rutina del usuario");
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener las rutinas del usuario.");
            ex.printStackTrace();
        }
    }
    public void actualizarRutina(Context context, TablaRutinaUsuario rutina, FirebaseCallbackBoolean callback){
        rutinasUsuarioReference.child(rutina.getID()).setValue(rutina, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                boolean actualizada;
                if (databaseError != null) {
                    mostrarToast(context, "Error al actualizar la rutina: " + databaseError.getMessage());
                    actualizada = false;
                } else {
                    actualizada = true;
                }
                callback.onCallback(actualizada);
            }
        });
    }
    public void actualizarRutina(Context context, String IDRutina, boolean activo){
        rutinasUsuarioReference.child(IDRutina).child("informacion").child("activo").setValue(activo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    mostrarToast(context, "Error al actualizar la rutina: " + databaseError.getMessage());
                }
            }
        });
    }
    public void actualizarRutina(Context context, String IDRutina, String urlImagen, FirebaseCallbackBoolean callback){

        try{
            rutinasUsuarioReference.child(IDRutina).child("informacion").child("imagen").setValue(urlImagen, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    boolean actualizado;
                    if (databaseError != null) {
                        mostrarToast(context, "Error al actualizar la imagen de la rutina: " + databaseError.getMessage());
                        actualizado = false;
                    } else {
                        actualizado = true;
                    }
                    callback.onCallback(actualizado);
                }
            });
        } catch (Exception ex) {
            mostrarToast(context, "Error al obtener la rutina del usuario.");
            ex.printStackTrace();
        }
    }


    // Eliminar rutina.
    public void eliminarRutina(Context context, String ID, FirebaseCallbackBoolean callback){
        rutinasUsuarioReference.child(ID).removeValue( new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                boolean eliminada;
                if (databaseError != null) {
                    mostrarToast(context, "Error al eliminar la rutina: " + databaseError.getMessage());
                    eliminada = false;
                } else {
                    eliminada = true;
                }
                callback.onCallback(eliminada);
            }
        });
    }



    // Método para mostrar un mensaje por pantalla.
    private void mostrarToast(Context context, String mensaje){
        MostratToast.mostrarToast(context, mensaje);
    }










    ////////////////////////////////////////////////////////////////////////////////////////////////
























    public void obtenerLogrosUsuario(Context context, ArrayList<String> titulos, FirebaseCallbackLogrosUsuario callback) {
        try {
            usuariosReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaLogroUsuario> logrosUsuario = new ArrayList<TablaLogroUsuario>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot logrosSnapshot = usuarioSnapshot.child("logros");

                            Iterator<DataSnapshot> iterator = logrosSnapshot.getChildren().iterator();
                            while (iterator.hasNext() && logrosUsuario.size() < titulos.size()) {
                                DataSnapshot logroSnapshot = iterator.next();
                                TablaLogroUsuario logro = logroSnapshot.getValue(TablaLogroUsuario.class);
                                if(titulos.contains(logro.getTitulo())){
                                    logro.setID(logroSnapshot.getKey());
                                    logrosUsuario.add(logro);
                                }
                            }
                        }
                    } else {
                        MostratToast.mostrarToast(context, "Usuario no encontrado.");
                    }

                    callback.onCallback(logrosUsuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los logros del usuario");
                }
            });
        } catch (Exception ex) {
            MostratToast.mostrarToast(context, "Error al obtener los logros del usuario.");
            ex.printStackTrace();
        }
    }

    public boolean actualizarLogros(Context context, ArrayList<TablaLogroUsuario> logros){
        boolean actualizados = false;
        try{
            DatabaseReference logrosUsuarioReference = usuariosReference.child(idUsuario).child("logros");

            for (TablaLogroUsuario logro : logros) {
                logrosUsuarioReference.child(logro.getID()).setValue(logro);
            }
            actualizados = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar el logro.");
            e.printStackTrace();
        }
        return actualizados;
    }






















}
