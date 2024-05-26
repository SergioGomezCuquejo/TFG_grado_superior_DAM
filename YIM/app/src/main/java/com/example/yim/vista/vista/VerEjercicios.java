package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.VerEjerciciosAdaptador;
import com.example.yim.controlador.Adaptadores.VerEjerciciosCreadosAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosCreados;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosPorDefecto;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class VerEjercicios extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    ImageView imagenPerfilMenu, agregarEjercicio;
    RecyclerView recyclerViewCreados, recyclerViewPorDefecto;
    ProgressBar cargando;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicios);

        //Inicializar instancias.
        firebaseManager = new FirebaseManager();


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        recyclerViewCreados = findViewById(R.id.ejercicios_creados);
        recyclerViewPorDefecto = findViewById(R.id.ejercicios_por_defecto);

        agregarEjercicio = findViewById(R.id.agregar_ejercicio);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners.
        agregarEjercicio.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);


        //Mostrar datos.
        try{
            mostrarImagenPerfil();
            mostrarEjerciciosCreados();
            mostrarEjerciciosPorDefecto();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar los ejercicios.");
            ex.printStackTrace();
        }


        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "agregar_ejercicio":
                cambiarActivity(PopupCrearEjercicios.class);
                break;

            case "imagen_casa_menu":
                cambiarActivity(Inicio.class);
                break;
            case "imagen_calendario_menu":
                cambiarActivity(RutinaSemanal.class);
                break;

            case "imagen_estadisticas_menu":
                cambiarActivity(Estadisticas.class);
                break;
            case "imagen_usuario_menu":
                cambiarActivity(Perfil.class);
                break;
        }
    }

    // Método para mostrar los ejercicios y su información desde un adaptador.
    public void mostrarEjerciciosCreados(){
        try{
            firebaseManager.obtenerEjerciciosCreados(this, new FirebaseCallbackEjerciciosCreados() {
                @Override
                public void onCallback(ArrayList<TablaEjercicioCreado> ejerciciosUsuarios) {
                    recyclerViewCreados.setLayoutManager(new LinearLayoutManager(VerEjercicios.this));
                    VerEjerciciosCreadosAdaptador adaptador = new VerEjerciciosCreadosAdaptador(VerEjercicios.this, ejerciciosUsuarios);
                    recyclerViewCreados.setAdapter(adaptador);
                }
            });

        }catch (Exception ex){
            mostrarToast("Error al obtener los musculos del usuario.");
            ex.printStackTrace();
        }
    }
    public void mostrarEjerciciosPorDefecto(){
        try{
            firebaseManager.obtenerEjerciciosPorDefecto(this, new FirebaseCallbackEjerciciosPorDefecto() {
                @Override
                public void onCallback(ArrayList<TablaEjercicioPorDefecto> ejerciciosUsuarios) {
                    recyclerViewPorDefecto.setLayoutManager(new LinearLayoutManager(VerEjercicios.this));
                    VerEjerciciosAdaptador adaptador = new VerEjerciciosAdaptador(VerEjercicios.this, ejerciciosUsuarios);
                    recyclerViewPorDefecto.setAdapter(adaptador);
                }
            });

        }catch (Exception ex){
            mostrarToast("Error al obtener los musculos del usuario.");
            ex.printStackTrace();
        }
    }


    // Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    private void mostrarImagenPerfil(){
        try{
            firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
                @Override
                public void onCallback(TablaPerfil perfil) {
                    if(perfil.getImagen() != null && !perfil.getImagen().equals("")){
                        Imagenes.urlImagenPerfil = perfil.getImagen();
                        Imagenes.mostrarImagenPerfil(VerEjercicios.this, imagenPerfilMenu);
                    }

                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al mostrar la imagen de perfil.");
            ex.printStackTrace();
        }

    }


    // Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        CambiarActivity.cambiar(this, activity);
    }


    // Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}