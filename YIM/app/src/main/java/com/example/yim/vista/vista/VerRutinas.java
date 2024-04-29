package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.yim.R;
import com.google.android.material.imageview.ShapeableImageView;

public class VerRutinas extends AppCompatActivity implements View.OnClickListener {
    ShapeableImageView rutina1;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_rutinas);

        //Referencias de las vistas
        rutina1 = findViewById(R.id.rutina1);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        rutina1.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rutina1){
            cambiarActivity(PopupRutinas.class);

        } else if (id == R.id.imagen_casa) {
            cambiarActivity(Inicio.class);

        }else if (id == R.id.imagen_calendario) {
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