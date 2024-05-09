package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaRutinasUsuario;
import com.example.yim.vista.controlador.MostratToast;

public class CrearRutinas extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    TablaRutinasUsuario rutinaUsuario;
    ImageView atras, preguntas;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);

        intent = getIntent();
        rutinaUsuario = (TablaRutinasUsuario) intent.getSerializableExtra("rutinaUsuario");

        //Referencias de las vistas
        atras = findViewById(R.id.atras);
        preguntas = findViewById(R.id.preguntas);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        atras.setOnClickListener(this);
        preguntas.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.atras){
            finish();

        } else if (id == R.id.preguntas){
            cambiarActivity(Cuestionario.class);

        } else if (id == R.id.dia1){
            cambiarActivity(EjerciciosRutinas.class);

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        }  else if (id == R.id.imagen_calendario) {
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