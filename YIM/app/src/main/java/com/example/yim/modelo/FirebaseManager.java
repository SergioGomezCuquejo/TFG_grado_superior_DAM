package com.example.yim.modelo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicioUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicios;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogros;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogrosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculos;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaActiva;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinasUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaEjercicio;
import com.example.yim.modelo.tablas.TablaEjercicioUsuario;
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

    // todo Nodo EJERCICIOS del nodo USUARIO
    // Crear ejercicio. todo

    // Obtener ejercicios.
    public void obtenerEjerciciosUsuario(Context context, FirebaseCallbackEjerciciosUsuario callback) {
        try {
            ejerciciosUsuarioReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicioUsuario> ejerciciosUsuarios = new ArrayList<TablaEjercicioUsuario>();

                    for (DataSnapshot ejercicioSnapshot : dataSnapshot.getChildren()) {
                        TablaEjercicioUsuario ejercicio = ejercicioSnapshot.getValue(TablaEjercicioUsuario.class);
                        ejercicio.setID(ejercicioSnapshot.getKey());
                        ejerciciosUsuarios.add(ejercicio);
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
    public void obtenerEjercicioUsuario(Context context, String IDEjercicio, FirebaseCallbackEjercicioUsuario callback) {
        try {
            ejerciciosUsuarioReference.child(IDEjercicio).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaEjercicioUsuario ejercicio = new TablaEjercicioUsuario();

                    if (dataSnapshot.exists()) {
                        ejercicio = dataSnapshot.getValue(TablaEjercicioUsuario.class);

                    } else {
                        mostrarToast(context, "Ejercicios no encontrado.");
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

    // Actualizar ejercicio. todo
    // Eliminar ejercicio. todo


    //todo Nodo LOGROS del nodo USUARIO
    // Crear logro. todo
    // Obtener logros.todo
    // Actualizar logro. todo
    // Eliminar logro. todo


    // todo Nodo PERFIL del nodo USUARIO
    // Crear perfil. todo

    // Obtener perfil.todo
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

    // Actualizar perfil. todo
    // Eliminar perfil. todo


    // todo Nodo MUSCULOS del nodo USUARIO
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

    // Actualizar músculo. todo
    // Eliminar músculo. todo

    // todo Nodo RUTINA ACTIVA del nodo USUARIO
    // Crear rutina activa. todo
    // Obtener rutina activa.todo
    // Actualizar rutina activa. todo
    public void actualizarRutina(Context context, int numDia, int numEjercicio, ArrayList<TablaHistorial> historial, FirebaseCallbackBoolean callback){

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
    public void actualizarRutina(Context context, int numDia, int numEjercicio, int seriesRealizadas, FirebaseCallbackBoolean callback){
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
            mostrarToast(context, "Error al obtener las rutinas del usuario.");
            ex.printStackTrace();
        }
    }

    // Eliminar rutina activa. todo



    // todo Nodo RUTINAS del nodo USUARIO.

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

    // Obtener rutinas. todo

    // Actualizar rutina.
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


    // Eliminar rutina. todo



    // Método para mostrar un mensaje por pantalla.
    private void mostrarToast(Context context, String mensaje){
        MostratToast.mostrarToast(context, mensaje);
    }










    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void obtenerEjercicios(Context context, FirebaseCallbackEjercicios callback){
        try{
            ejerciciosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicio> ejercicios = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ejerciciosSnapshot : dataSnapshot.getChildren()) {
                            ejercicios.add(ejerciciosSnapshot.getValue(TablaEjercicio.class));
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

    public void obtenerLogros(Context context, FirebaseCallbackLogros callback){
        try{
            logrosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaLogro> logros = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot logrosSnapshot : dataSnapshot.getChildren()) {
                            logros.add(logrosSnapshot.getValue(TablaLogro.class));
                        }

                    } else {
                        Toast.makeText(context, "No hay logros disponibles", Toast.LENGTH_SHORT).show();
                    }

                    callback.onCallback(logros);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los logros");
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al obtener los logros.");
            e.printStackTrace();
        }
    }

    public void obtenerMusculos(Context context, FirebaseCallbackMusculos callback){
        try{
            musculosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaMusculo> musculos = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot musculosSnapshot : dataSnapshot.getChildren()) {
                            musculos.add(musculosSnapshot.getValue(TablaMusculo.class));
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


    public void agregarUsuario(Context context, String idAuth, String email, String nombre) {
        try{

            obtenerEjercicios(context, new FirebaseCallbackEjercicios() {
                @Override
                public void onCallback(ArrayList<TablaEjercicio> ejercicios) {

                    obtenerLogros(context, new FirebaseCallbackLogros() {
                        @Override
                        public void onCallback(ArrayList<TablaLogro> logros) {


                                obtenerMusculos(context, new FirebaseCallbackMusculos() {
                                    @Override
                                    public void onCallback(ArrayList<TablaMusculo> musculos) {

                                        agregarUsuario(context, idAuth, email, nombre, ejercicios, logros, musculos);

                                    }
                                });

                        }
                    });
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar el usuario.");
            e.printStackTrace();
        }
    }

    public void agregarUsuario(Context context, String idAuth, String email, String nombre,
                               ArrayList<TablaEjercicio> ejercicios, ArrayList<TablaLogro> logros, ArrayList<TablaMusculo> musculos){
        try{
            TablaUsuario nuevoUsuario = new TablaUsuario();

            //Perfil
            nuevoUsuario.getPerfil().setEmail(email);
            nuevoUsuario.getPerfil().setNombre(nombre);

            //Ejercicios
            ArrayList<TablaEjercicioUsuario> ejerciciosUsuario = new ArrayList<TablaEjercicioUsuario>();
            TablaEjercicioUsuario ejercicioUsuario;
            for (TablaEjercicio ejercicio : ejercicios) {
                ejercicioUsuario = new TablaEjercicioUsuario(ejercicio);
                ejerciciosUsuario.add(ejercicioUsuario);
            }
            nuevoUsuario.setEjercicios(ejerciciosUsuario);

            //Logros
            ArrayList<TablaLogroUsuario> logrosUsuario = new ArrayList<TablaLogroUsuario>();
            TablaLogroUsuario logroUsuario;
            for (TablaLogro logro : logros) {
                logroUsuario = new TablaLogroUsuario(logro);
                logrosUsuario.add(logroUsuario);
            }
            nuevoUsuario.setLogros(logrosUsuario);

            //Músculos
            ArrayList<TablaMusculoUsuario> musculosUsuario = new ArrayList<TablaMusculoUsuario>();
            TablaMusculoUsuario musculoUsuario;
            for (TablaMusculo musculo : musculos) {
                musculoUsuario = new TablaMusculoUsuario(musculo.getColor_fondo(), musculo.getColor_letras(), musculo.getNombre());
                musculosUsuario.add(musculoUsuario);
            }
            nuevoUsuario.setMusculos(musculosUsuario);

            //Enviar
            usuariosReference.child(idAuth).setValue(nuevoUsuario);
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar el usuario.");
            e.printStackTrace();
        }
    }


    public void obtenerUsuario(Context context, FirebaseCallbackUsuario callback){
        try{
            usuariosReference.addValueEventListener(new ValueEventListener() {
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



    public boolean modificarPerfil(Context context, String urlImagen){
        boolean actualizado = false;
        try{
            DatabaseReference rutinaUsuarioReference = usuariosReference.child(idUsuario).child("perfil");

            rutinaUsuarioReference.child("imagen").setValue(urlImagen);
            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar el perfil.");
            e.printStackTrace();
        }
        return actualizado;
    }





    public boolean actualizarColoresMusculosUsuario(Context context, String idMusculo, String colorFondo, String colorFuente){
        boolean actualizado = false;
        try{
            DatabaseReference musculosUsuarioReference = usuariosReference.child(idUsuario).child("musculos").child(idMusculo);

            musculosUsuarioReference.child("color_fondo").setValue(colorFondo);
            musculosUsuarioReference.child("color_fuente").setValue(colorFuente);

            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar los colores del músculo.");
            e.printStackTrace();
        }
        return actualizado;
    }



    public void obtenerEjerciciosUsuarioConEstadisticas(Context context, FirebaseCallbackEjerciciosUsuario callback) {
        try {
            usuariosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjercicioUsuario> ejerciciosUsuarios = new ArrayList<TablaEjercicioUsuario>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot ejerciciosSnapshot = usuarioSnapshot.child("ejercicios");

                            for (DataSnapshot ejercicioSnapshot : ejerciciosSnapshot.getChildren()) {
                                TablaEjercicioUsuario ejercicio = ejercicioSnapshot.getValue(TablaEjercicioUsuario.class);
                                if(ejercicio.getEstadisticas() != null){
                                    ejercicio.setID(ejercicioSnapshot.getKey());
                                    ejerciciosUsuarios.add(ejercicio);
                                }
                            }
                        }
                    } else {
                        MostratToast.mostrarToast(context, "Usuario no encontrado.");
                    }

                    callback.onCallback(ejerciciosUsuarios);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener los ejercicios del usuario");
                }
            });
        } catch (Exception ex) {
            MostratToast.mostrarToast(context, "Error al obtener los ejercicios del usuario.");
            ex.printStackTrace();
        }
    }



    public boolean agregarEjercicio(Context context, TablaEjercicioUsuario nuevoEjercicio){
        boolean actualizado = false;
        try{
            DatabaseReference ejerciciosUsuarioReference = usuariosReference.child(idUsuario).child("ejercicios");
            String idEjercicio = ejerciciosUsuarioReference.push().getKey();

            ejerciciosUsuarioReference.child(idEjercicio).setValue(nuevoEjercicio);

            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar el ejercicio.");
            e.printStackTrace();
        }
        return actualizado;
    }

    public boolean eliminarEjercicio(Context context, String ID){
        boolean eliminado = false;
        try{
            DatabaseReference ejerciciosUsuarioReference = usuariosReference.child(idUsuario).child("ejercicios");

            ejerciciosUsuarioReference.child(ID).removeValue();
            eliminado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al eliminar el ejercicio.");
            e.printStackTrace();
        }
        return eliminado;
    }

    public boolean actualizarEjercicio(Context context, TablaEjercicioUsuario ejercicio){
        boolean actualizado = false;
        try{
            DatabaseReference ejerciciosUsuarioReference = usuariosReference.child(idUsuario).child("ejercicios");

            ejerciciosUsuarioReference.child(ejercicio.getID()).setValue(ejercicio);
            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar el ejercicio.");
            e.printStackTrace();
        }
        return actualizado;
    }
    public void obtenerLogrosUsuario(Context context, FirebaseCallbackLogrosUsuario callback) {
        try {
            usuariosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaLogroUsuario> logrosUsuario = new ArrayList<TablaLogroUsuario>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot logrosSnapshot = usuarioSnapshot.child("logros");

                            for (DataSnapshot logroSnapshot : logrosSnapshot.getChildren()) {
                                TablaLogroUsuario logro = logroSnapshot.getValue(TablaLogroUsuario.class);
                                logro.setID(logroSnapshot.getKey());
                                logrosUsuario.add(logro);
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

    public void obtenerRutinasUsuario(Context context, FirebaseCallbackRutinasUsuario callback){
        try {
            usuariosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaRutinaUsuario> rutinasUsuario = new ArrayList<TablaRutinaUsuario>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot rutinasSnapshot = usuarioSnapshot.child("rutinas");

                            for (DataSnapshot rutinaSnapshot : rutinasSnapshot.getChildren()) {
                                TablaRutinaUsuario rutina = rutinaSnapshot.getValue(TablaRutinaUsuario.class);
                                rutina.setID(rutinaSnapshot.getKey());
                                rutinasUsuario.add(rutina);
                            }
                        }
                    } else {
                        MostratToast.mostrarToast(context, "Usuario no encontrado.");
                    }

                    callback.onCallback(rutinasUsuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener las rutinas del usuario");
                }
            });
        } catch (Exception ex) {
            MostratToast.mostrarToast(context, "Error al obtener las rutinas del usuario.");
            ex.printStackTrace();
        }
    }

    public void obtenerRutinaUsuario(Context context, String idRutina, FirebaseCallbackRutinaUsuario callback){
        try{
            usuariosReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaRutinaUsuario rutinaUsuario = new TablaRutinaUsuario();

                    if (dataSnapshot.exists()) {
                        DataSnapshot rutinaSnapshot = dataSnapshot.child(idUsuario).child("rutinas").child(idRutina);

                        if (rutinaSnapshot.exists()) {
                            rutinaUsuario = rutinaSnapshot.getValue(TablaRutinaUsuario.class);
                        }
                    } else {
                        MostratToast.mostrarToast(context, "No hay rutinas.");
                    }

                    callback.onCallback(rutinaUsuario);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener las rutinas");
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al obtener la rutina.");
            e.printStackTrace();
        }
    }





    public void desactivarRutinas(Context context){
        try {
            usuariosReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaRutinaUsuario rutinaUsuario = new TablaRutinaUsuario();
                    boolean desactivadas = false;

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot rutinasSnapshot = usuarioSnapshot.child("rutinas");

                            Iterator<DataSnapshot> iterator = rutinasSnapshot.getChildren().iterator();
                            while (iterator.hasNext() && !desactivadas) {
                                DataSnapshot rutinaSnapshot = iterator.next();
                                rutinaUsuario = rutinaSnapshot.getValue(TablaRutinaUsuario.class);

                                if(rutinaUsuario.getInformacion().isActivo()){
                                    rutinaUsuario.setID(rutinaSnapshot.getKey());
                                    rutinaUsuario.getInformacion().setActivo(false);
                                    /*
                                    if(actualizarRutina(context, rutinaUsuario)){
                                        desactivadas = true;
                                    }else{
                                        MostratToast.mostrarToast(context, "Error al desactivar rutinas");
                                    }
                                     */
                                }
                            }
                        }
                    } else {
                        MostratToast.mostrarToast(context, "Usuario no encontrado.");
                    }
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



    public boolean modificarActivoRutina(Context context, String ID, boolean activo){
        boolean actualizado = false;
        try{
            DatabaseReference rutinasUsuarioReference = usuariosReference.child(idUsuario).child("rutinas");

            rutinasUsuarioReference.child(ID).child("informacion").child("activo").setValue(activo);
            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar la rutina.");
            e.printStackTrace();
        }
        return actualizado;
    }

    public boolean eliminarRutina(Context context, String ID){
        boolean eliminada = false;
        try{
            DatabaseReference rutinasUsuarioReference = usuariosReference.child(idUsuario).child("rutinas");

            rutinasUsuarioReference.child(ID).removeValue();
            eliminada = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al eliminar la rutina.");
            e.printStackTrace();
        }
        return eliminada;
    }

    public void obtenerRutinaActiva(Context context, FirebaseCallbackRutinaActiva callback) {
        try {
            usuariosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaRutinaActiva rutinaActiva = new TablaRutinaActiva();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot rutinaActivaSnapshot = usuarioSnapshot.child("rutina_activa");

                            rutinaActiva = rutinaActivaSnapshot.getValue(TablaRutinaActiva.class);
                        }
                    } else {
                        MostratToast.mostrarToast(context, "Usuario no encontrado.");
                    }

                    callback.onCallback(rutinaActiva);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener la rutina del usuario");
                }
            });
        } catch (Exception ex) {
            MostratToast.mostrarToast(context, "Error al obtener la rutina del usuario.");
            ex.printStackTrace();
        }
    }





    public boolean agregarRutinaActiva(Context context, TablaRutinaActiva nuevaRutinaActiva){
        boolean agregada = false;
        try{
            DatabaseReference rutinasUsuarioReference = usuariosReference.child(idUsuario).child("rutina_activa");

            rutinasUsuarioReference.setValue(nuevaRutinaActiva);

            agregada = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar la rutina.");
            e.printStackTrace();
        }
        return agregada;
    }

    public void modificarRutinaActiva(Context context, String idRutina){
        try{

            obtenerRutinaUsuario(context, idRutina, new FirebaseCallbackRutinaUsuario() {
                @Override
                public void onCallback(TablaRutinaUsuario rutina) {

                    DatabaseReference rutinaActivaReference = usuariosReference.child(idUsuario).child("rutina_activa").child("semana");
                    rutinaActivaReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long id = dataSnapshot.getChildrenCount();

                            for(TablaDiaRutinaUsuario diaRutinaUsuario : rutina.getSemana()){
                                diaRutinaUsuario.setDia((int) id+1);
                                rutinaActivaReference.child(String.valueOf(id)).setValue(diaRutinaUsuario);
                                id++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            MostratToast.mostrarToast(context, "Error al obtener la rutina activa del usuario");
                        }
                    });
                }
            });
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar el perfil.");
            e.printStackTrace();
        }
    }

    public boolean eliminarRutinaActiva(Context context){
        boolean eliminada = false;
        try{
            DatabaseReference rutinasUsuarioReference = usuariosReference.child(idUsuario).child("rutina_activa");

            rutinasUsuarioReference.removeValue();
            eliminada = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al eliminar la rutina.");
            e.printStackTrace();
        }
        return eliminada;
    }

    public boolean modificarPerfil(Context context, TablaPerfil perfil){
        boolean actualizado = false;
        try{
            DatabaseReference rutinaUsuarioReference = usuariosReference.child(idUsuario).child("perfil");

            rutinaUsuarioReference.setValue(perfil);
            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar el perfil.");
            e.printStackTrace();
        }
        return actualizado;
    }



}
