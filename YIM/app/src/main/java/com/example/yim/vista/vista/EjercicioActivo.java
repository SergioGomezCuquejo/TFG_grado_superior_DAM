package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yim.R;

public class EjercicioActivo extends AppCompatActivity implements View.OnClickListener {
    ImageView atras;
    ImageButton boton_info;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_activo);

        //Referencias de las vistas
        atras = findViewById(R.id.atras);
        boton_info = findViewById(R.id.boton_info);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        atras.setOnClickListener(this);
        boton_info.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.boton_info){
            cambiarActivity(PopupVerEjercicios.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        } else if (id == R.id.atras) {
            finish();

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}