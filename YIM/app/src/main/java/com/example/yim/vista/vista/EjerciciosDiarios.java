package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yim.R;

public class EjerciciosDiarios extends AppCompatActivity  implements View.OnClickListener {
    RelativeLayout ejercicio1;
    ImageView atras, agregar_ejercicio;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_diarios);

        //Referencias de las vistas
        agregar_ejercicio = findViewById(R.id.agregar_ejercicio);
        atras = findViewById(R.id.atras);

        ejercicio1 = findViewById(R.id.ejercicio1);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        agregar_ejercicio.setOnClickListener(this);
        atras.setOnClickListener(this);

        ejercicio1.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.musculos) {
            cambiarActivity(PopupMusculosRutina.class);

        } else if (id == R.id.ejercicio1){
            cambiarActivity(EjercicioActivo.class);

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        } else if (id == R.id.agregar_ejercicio) {
            cambiarActivity(PopupAgregarEjercicio.class);

        } else if (id == R.id.atras) {
            finish();

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}