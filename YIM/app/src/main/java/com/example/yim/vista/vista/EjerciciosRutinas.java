package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yim.R;

public class EjerciciosRutinas extends AppCompatActivity implements View.OnClickListener {
    ImageView atras;
    LinearLayout musculos, editar, opciones;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    TextView espalda, biceps;
    ImageView agregar_ejercicio;
    Button borrar;

    RelativeLayout ejercicio1;
    boolean visible;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_rutinas);

        //Referencias de las vistas
        atras = findViewById(R.id.atras);

        musculos = findViewById(R.id.musculos);
        espalda = findViewById(R.id.espalda);
        biceps = findViewById(R.id.biceps);

        ejercicio1 = findViewById(R.id.ejercicio1);
        editar = findViewById(R.id.editar);
        opciones = findViewById(R.id.opciones);
        borrar = findViewById(R.id.borrar);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        agregar_ejercicio = findViewById(R.id.agregar_ejercicio);

        //Listeners
        atras.setOnClickListener(this);

        musculos.setOnClickListener(this);

        ejercicio1.setOnClickListener(this);
        borrar.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        agregar_ejercicio.setOnClickListener(this);




        //Poner color al fondo y letras de los músculos
        cambiarColores(espalda, "#233284", "white");
        cambiarColores(biceps, "#00c143", "black");

        visible = false;

    }

    private void cambiarVisibilidad() {
        Animation animation;
        if (visible) {
            animation = AnimationUtils.loadAnimation(this, R.anim.deslizar_abajo);
            editar.setVisibility(View.GONE);
            opciones.setVisibility(View.GONE);
        } else {
            animation = AnimationUtils.loadAnimation(this, R.anim.deslizar_arriba);
            editar.setVisibility(View.VISIBLE);
            opciones.setVisibility(View.VISIBLE);
        }
        editar.startAnimation(animation);
        opciones.startAnimation(animation);
        visible = !visible;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.atras) {
            finish();

        } else if (id == R.id.musculos) {
            cambiarActivity(PopupMusculosRutina.class);

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        } else if (id == R.id.ejercicio1) {
            cambiarVisibilidad();

        } else if (id == R.id.borrar) {
            cambiarAlerta(this, "Eliminar ejercicio", "Se eleminará el ejercicio 'Ejercicio' de este día.", "ir_a_ejercicios_rutina");

        } else if (id == R.id.agregar_ejercicio) {
            cambiarActivity(PopupAgregarEjercicio.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    public void cambiarColores(View view, String colorFondo, String colorLetras){
        TextView textView = (TextView) view;
        Drawable shape = (Drawable) textView.getBackground();

        shape.setColorFilter(Color.parseColor(colorFondo), android.graphics.PorterDuff.Mode.SRC);
        textView.setTextColor(Color.parseColor(colorLetras));

        if (textView.getText().equals("_DESCANSO_")) {
            textView.setTypeface(null, Typeface.ITALIC);
        }
    }
}