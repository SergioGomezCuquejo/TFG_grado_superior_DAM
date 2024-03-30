package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class CrearRutinas extends AppCompatActivity implements View.OnClickListener {
    ImageView atras, preguntas;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    TextView espalda, biceps, pecho, hombro, triceps, pierna, descanso, espalda2, biceps2, pecho2, hombro2, triceps2, pierna2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);

        //Referencias de las vistas
        atras = findViewById(R.id.atras);
        preguntas = findViewById(R.id.preguntas);

        espalda = findViewById(R.id.espalda);
        biceps = findViewById(R.id.biceps);
        pecho = findViewById(R.id.pecho);
        hombro = findViewById(R.id.hombro);
        triceps = findViewById(R.id.triceps);
        pierna = findViewById(R.id.pierna);
        descanso = findViewById(R.id.descanso);
        espalda2 = findViewById(R.id.espalda2);
        biceps2 = findViewById(R.id.biceps2);
        pecho2 = findViewById(R.id.pecho2);
        hombro2 = findViewById(R.id.hombro2);
        triceps2 = findViewById(R.id.triceps2);
        pierna2 = findViewById(R.id.pierna2);

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


        //Poner color al fondo y letras de los músculos
        cambiarColores(espalda, "#233284", "white");
        cambiarColores(biceps, "#00c143", "black");
        cambiarColores(pecho, "#db0900", "white");
        cambiarColores(hombro, "#0fa9c9", "black");
        cambiarColores(triceps, "#a9f858", "black");
        cambiarColores(pierna, "#d00155", "white");

        cambiarColores(descanso, "black", "white");

        cambiarColores(espalda2, "#233284", "white");
        cambiarColores(biceps2, "#00c143", "black");
        cambiarColores(pecho2, "#db0900", "white");
        cambiarColores(hombro2, "#0fa9c9", "black");
        cambiarColores(triceps2, "#a9f858", "black");
        cambiarColores(pierna2, "#d00155", "white");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.atras){
            finish();

        } else if (id == R.id.preguntas){
            cambiarActivity(PreguntasRegistro.class);

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