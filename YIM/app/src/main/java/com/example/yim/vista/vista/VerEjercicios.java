package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicios;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculos;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjercicios;
import com.example.yim.modelo.tablas.TablaMusculos;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaUsuario;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class VerEjercicios extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout ejercicio1;
    ImageView agregar_ejercicio;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicios);

        //Referencias de las vistas
        ejercicio1 = findViewById(R.id.ejercicio1);

        agregar_ejercicio = findViewById(R.id.agregar_ejercicio);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        ejercicio1.setOnClickListener(this);

        agregar_ejercicio.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        FirebaseManager firebaseManager = new FirebaseManager();
        firebaseManager.obtenerEjercicios(this, new FirebaseCallbackEjercicios() {
            @Override
            public void onCallback(ArrayList<TablaEjercicios> ejercicios) {
                for (TablaEjercicios ejercicio : ejercicios){
                    MostratToast.mostrarToast(VerEjercicios.this, ejercicio.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ejercicio1) {
            cambiarActivity(PopupVerEjercicios.class);

        } else if (id == R.id.agregar_ejercicio) {
            cambiarActivity(PopupCrearEjercicios.class);

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}