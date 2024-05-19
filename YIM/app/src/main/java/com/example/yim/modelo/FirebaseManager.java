package com.example.yim.modelo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicioUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicios;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogros;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogrosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculos;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaActiva;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinasUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaEjercicios;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.modelo.tablas.TablaHistorial;
import com.example.yim.modelo.tablas.TablaLogros;
import com.example.yim.modelo.tablas.TablaLogrosUsuario;
import com.example.yim.modelo.tablas.TablaMusculos;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaRutinasUsuario;
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
    private DatabaseReference ejerciciosReference, logrosReference, musculosReference, usuariosReference;
    private String idUsuario;

    public FirebaseManager() {
        //Inicializar instancias.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Referencias de Firebase Realtime Database
        ejerciciosReference = firebaseDatabase.getReference("ejercicios");
        logrosReference = firebaseDatabase.getReference("logros");
        musculosReference = firebaseDatabase.getReference("musculos");
        usuariosReference = firebaseDatabase.getReference("usuarios");

        //Comprobar que no se ha cerrado sesión.
        if(auth.getCurrentUser() != null){
            idUsuario = auth.getCurrentUser().getUid();
        }
    }

    public void obtenerEjercicios(Context context, FirebaseCallbackEjercicios callback){
        try{
            ejerciciosReference.addValueEventListener(new ValueEventListener() {
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

    public void obtenerLogros(Context context, FirebaseCallbackLogros callback){
        try{
            logrosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaLogros> logros = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot logrosSnapshot : dataSnapshot.getChildren()) {
                            logros.add(logrosSnapshot.getValue(TablaLogros.class));
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

            obtenerEjercicios(context, new FirebaseCallbackEjercicios() {
                @Override
                public void onCallback(ArrayList<TablaEjercicios> ejercicios) {

                    obtenerLogros(context, new FirebaseCallbackLogros() {
                        @Override
                        public void onCallback(ArrayList<TablaLogros> logros) {


                                obtenerMusculos(context, new FirebaseCallbackMusculos() {
                                    @Override
                                    public void onCallback(ArrayList<TablaMusculos> musculos) {

                                        agregarUsuario(context, idAuth, contrasena, email, nombre, ejercicios, logros, musculos);

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

    public void agregarUsuario(Context context, String idAuth, String contrasena, String email, String nombre,
                               ArrayList<TablaEjercicios> ejercicios, ArrayList<TablaLogros> logros, ArrayList<TablaMusculos> musculos){
        try{
            TablaUsuario nuevoUsuario = new TablaUsuario();

            //Perfil
            nuevoUsuario.getPerfil().setContrasena(contrasena);
            nuevoUsuario.getPerfil().setEmail(email);
            nuevoUsuario.getPerfil().setNombre(nombre);

            //Ejercicios
            ArrayList<TablaEjerciciosUsuario> ejerciciosUsuario = new ArrayList<TablaEjerciciosUsuario>();
            TablaEjerciciosUsuario ejercicioUsuario;
            for (TablaEjercicios ejercicio : ejercicios) {
                ejercicioUsuario = new TablaEjerciciosUsuario(ejercicio);
                ejerciciosUsuario.add(ejercicioUsuario);
            }
            nuevoUsuario.setEjercicios(ejerciciosUsuario);

            //Logros
            ArrayList<TablaLogrosUsuario> logrosUsuario = new ArrayList<TablaLogrosUsuario>();
            TablaLogrosUsuario logroUsuario;
            for (TablaLogros logro : logros) {
                logroUsuario = new TablaLogrosUsuario(logro);
                logrosUsuario.add(logroUsuario);
            }
            nuevoUsuario.setLogros(logrosUsuario);

            //Músculos
            ArrayList<TablaMusculosUsuario> musculosUsuario = new ArrayList<TablaMusculosUsuario>();
            TablaMusculosUsuario musculoUsuario;
            for (TablaMusculos musculo : musculos) {
                musculoUsuario = new TablaMusculosUsuario(musculo.getColor_fondo(), musculo.getColor_letras(), musculo.getNombre());
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

    public void obtenerPerfil(Context context, FirebaseCallbackPerfil callback) {
        try {
            usuariosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaPerfil perfil = new TablaPerfil();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot perfilSnapshot = usuarioSnapshot.child("perfil");

                            if (perfilSnapshot.exists()) {
                                perfil = perfilSnapshot.getValue(TablaPerfil.class);
                            }
                        }
                    } else {
                        MostratToast.mostrarToast(context, "Usuario no encontrado.");
                    }

                    callback.onCallback(perfil);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    MostratToast.mostrarToast(context, "Error al obtener el perfil del usuario");
                }
            });
        } catch (Exception ex) {
            MostratToast.mostrarToast(context, "Error al obtener el perfil del usuario.");
            ex.printStackTrace();
        }
    }



    public void obtenerMusculosUsuario(Context context, FirebaseCallbackMusculosUsuario callback) {
        try {
            usuariosReference.addValueEventListener(new ValueEventListener() {
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

    public void obtenerEjerciciosUsuario(Context context, FirebaseCallbackEjerciciosUsuario callback) {
        try {
            usuariosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TablaEjerciciosUsuario> ejerciciosUsuarios = new ArrayList<TablaEjerciciosUsuario>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot ejerciciosSnapshot = usuarioSnapshot.child("ejercicios");

                            for (DataSnapshot ejercicioSnapshot : ejerciciosSnapshot.getChildren()) {
                                TablaEjerciciosUsuario ejercicio = ejercicioSnapshot.getValue(TablaEjerciciosUsuario.class);
                                ejercicio.setID(ejercicioSnapshot.getKey());
                                ejerciciosUsuarios.add(ejercicio);
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

    public void obtenerEjercicioUsuario(Context context, String idEjercicio, FirebaseCallbackEjercicioUsuario callback) {
        try {
            usuariosReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaEjerciciosUsuario ejercicio = new TablaEjerciciosUsuario();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot ejerciciosSnapshot = usuarioSnapshot.child("ejercicios").child(idEjercicio);

                            if (ejerciciosSnapshot.exists()) {
                                ejercicio = ejerciciosSnapshot.getValue(TablaEjerciciosUsuario.class);
                            }
                        }
                    } else {
                        MostratToast.mostrarToast(context, "Usuario no encontrado.");
                    }

                    callback.onCallback(ejercicio);
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

    public boolean agregarEjercicio(Context context, TablaEjerciciosUsuario nuevoEjercicio){
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

    public boolean actualizarEjercicio(Context context, TablaEjerciciosUsuario ejercicio){
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
                    ArrayList<TablaLogrosUsuario> logrosUsuario = new ArrayList<TablaLogrosUsuario>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot logrosSnapshot = usuarioSnapshot.child("logros");

                            for (DataSnapshot logroSnapshot : logrosSnapshot.getChildren()) {
                                TablaLogrosUsuario logro = logroSnapshot.getValue(TablaLogrosUsuario.class);
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
                    ArrayList<TablaLogrosUsuario> logrosUsuario = new ArrayList<TablaLogrosUsuario>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot logrosSnapshot = usuarioSnapshot.child("logros");

                            Iterator<DataSnapshot> iterator = logrosSnapshot.getChildren().iterator();
                            while (iterator.hasNext() && logrosUsuario.size() < titulos.size()) {
                                DataSnapshot logroSnapshot = iterator.next();
                                TablaLogrosUsuario logro = logroSnapshot.getValue(TablaLogrosUsuario.class);
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

    public boolean actualizarLogros(Context context, ArrayList<TablaLogrosUsuario> logros){
        boolean actualizados = false;
        try{
            DatabaseReference logrosUsuarioReference = usuariosReference.child(idUsuario).child("logros");

            for (TablaLogrosUsuario logro : logros) {
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
                    ArrayList<TablaRutinasUsuario> rutinasUsuario = new ArrayList<TablaRutinasUsuario>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot rutinasSnapshot = usuarioSnapshot.child("rutinas");

                            for (DataSnapshot rutinaSnapshot : rutinasSnapshot.getChildren()) {
                                TablaRutinasUsuario rutina = rutinaSnapshot.getValue(TablaRutinasUsuario.class);
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

    public boolean agregarRutina(Context context, TablaRutinasUsuario nuevaRutina){
        boolean agregada = false;
        try{
            DatabaseReference rutinasUsuarioReference = usuariosReference.child(idUsuario).child("rutinas");
            String idRutina = rutinasUsuarioReference.push().getKey();

            rutinasUsuarioReference.child(idRutina).setValue(nuevaRutina);

            agregada = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar la rutina.");
            e.printStackTrace();
        }
        return agregada;
    }

    public void desactivarRutinas(Context context){
        try {
            usuariosReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TablaRutinasUsuario rutinaUsuario = new TablaRutinasUsuario();
                    boolean desactivadas = false;

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot rutinasSnapshot = usuarioSnapshot.child("rutinas");

                            Iterator<DataSnapshot> iterator = rutinasSnapshot.getChildren().iterator();
                            while (iterator.hasNext() && !desactivadas) {
                                DataSnapshot rutinaSnapshot = iterator.next();
                                rutinaUsuario = rutinaSnapshot.getValue(TablaRutinasUsuario.class);

                                if(rutinaUsuario.getInformacion().isActivo()){
                                    rutinaUsuario.setID(rutinaSnapshot.getKey());
                                    rutinaUsuario.getInformacion().setActivo(false);
                                    if(actualizarRutina(context, rutinaUsuario)){
                                        desactivadas = true;
                                        MostratToast.mostrarToast(context, "Desactivada la rutina " + rutinaUsuario.getInformacion().getNombre());
                                    }else{
                                        MostratToast.mostrarToast(context, "Error al desactivar rutinas");
                                    }
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

    public boolean actualizarRutina(Context context, TablaRutinasUsuario rutina){
        boolean actualizado = false;
        try{
            DatabaseReference rutinasUsuarioReference = usuariosReference.child(idUsuario).child("rutinas");

            rutinasUsuarioReference.child(rutina.getID()).setValue(rutina);
            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar la rutina.");
            e.printStackTrace();
        }
        return actualizado;
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
                    ArrayList<TablaDiaRutinaActiva> rutinaActiva = new ArrayList<>();

                    if (dataSnapshot.exists()) {
                        DataSnapshot usuarioSnapshot = dataSnapshot.child(idUsuario);

                        if (usuarioSnapshot.exists()) {
                            DataSnapshot rutinaActivaSnapshot = usuarioSnapshot.child("rutina_activa");

                            for (DataSnapshot rutinaSnapshot : rutinaActivaSnapshot.getChildren()) {
                                TablaDiaRutinaActiva rutina = rutinaSnapshot.getValue(TablaDiaRutinaActiva.class);
                                rutinaActiva.add(rutina);
                            }
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

    public boolean modificarEjercicioRutinaActiva(Context context, int numDia, int numEjercicio, ArrayList<TablaHistorial> historial){
        boolean actualizado = false;
        try{
            DatabaseReference rutinaUsuarioReference = usuariosReference.child(idUsuario).child("rutina_activa").
                    child(String.valueOf(numDia-1)).child("ejercicios").child(String.valueOf(numEjercicio-1));

            rutinaUsuarioReference.child("historial").setValue(historial);
            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar la rutina.");
            e.printStackTrace();
        }
        return actualizado;
    }
    public boolean modificarEjercicioRutinaActiva(Context context, int numDia, int numEjercicio, int seriesRealizadas){
        boolean actualizado = false;
        try{
            DatabaseReference rutinaUsuarioReference = usuariosReference.child(idUsuario).child("rutina_activa").
                    child(String.valueOf(numDia-1)).child("ejercicios").child(String.valueOf(numEjercicio-1));

            rutinaUsuarioReference.child("series_realizadas").setValue(seriesRealizadas+1);
            actualizado = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al actualizar la rutina.");
            e.printStackTrace();
        }
        return actualizado;
    }


    public boolean agregarRutinaActiva(Context context, ArrayList<TablaDiaRutinaActiva> nuevaRutinaActiva){
        boolean agregada = false;
        try{
            DatabaseReference rutinasUsuarioReference = usuariosReference.child(idUsuario).child("rutina_activa");

            for (int i=0; i < nuevaRutinaActiva.size(); i++){
                rutinasUsuarioReference.child(String.valueOf(i)).setValue(nuevaRutinaActiva.get(i));
            }


            agregada = true;
        } catch (Exception e) {
            MostratToast.mostrarToast(context, "Error al agregar la rutina.");
            e.printStackTrace();
        }
        return agregada;
    }
}
