package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.yim.R;

public class EjerciciosRutinas extends AppCompatActivity implements View.OnClickListener {
    LinearLayout musculos;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;

    private RelativeLayout ejercicio1, ejercicio12;
    private Button buttonToggle;
    private boolean isListVisible = false;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_rutinas);

        //Referencias de las vistas
        musculos = findViewById(R.id.musculos);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        musculos.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);


        ejercicio12 = findViewById(R.id.ejercicio12);
        ejercicio1 = findViewById(R.id.ejercicio1);
        ejercicio1.setOnClickListener(this);

    }

    private void toggleListVisibility() {
        Animation animation;
        if (isListVisible) {
            animation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
            ejercicio12.setVisibility(View.GONE);
        } else {
            animation = AnimationUtils.loadAnimation(this, R.anim.slide_out);
            ejercicio12.setVisibility(View.VISIBLE);
        }
        ejercicio12.startAnimation(animation);
        isListVisible = !isListVisible;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.musculos) {
            cambiarActivity(PopupMusculosRutina.class);

        }else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        } else if (id == R.id.ejercicio1) {
            toggleListVisibility();

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}