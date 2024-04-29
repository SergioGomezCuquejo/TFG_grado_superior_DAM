package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;

public class RutinaSemanal extends AppCompatActivity implements View.OnClickListener {
    LinearLayout dia1, imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    TextView descanso, espalda, biceps, pecho, hombro, triceps, pierna, espalda2, biceps2, pecho2, hombro2, triceps2, pierna2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_semanal);

        //Referencias de las vistas
        dia1 = findViewById(R.id.dia1);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        dia1.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);


        //Poner color al fondo de los musculos
        String colorToApply = "#233284";
        String blanco = "#FFFFFFFF";

        descanso = findViewById(R.id.descanso);
        Drawable shapeD = (Drawable) descanso.getBackground();
        shapeD.setColorFilter(Color.parseColor("#FF000000"), android.graphics.PorterDuff.Mode.SRC);
        descanso.setTextColor(Color.parseColor(blanco)); //cambiar letras a blanco


        espalda = findViewById(R.id.espalda);
        Drawable shape = (Drawable) espalda.getBackground();
        shape.setColorFilter(Color.parseColor(colorToApply), android.graphics.PorterDuff.Mode.SRC);
        espalda.setTextColor(Color.parseColor(blanco)); //cambiar letras a blanco

        espalda2 = findViewById(R.id.espalda2);
        Drawable shape12 = (Drawable) espalda2.getBackground();
        shape12.setColorFilter(Color.parseColor(colorToApply), android.graphics.PorterDuff.Mode.SRC);
        espalda2.setTextColor(Color.parseColor(blanco)); //cambiar letras a blanco


        String colorToApply4 = "#00c143";
        biceps = findViewById(R.id.biceps);
        Drawable shape4 = (Drawable) biceps.getBackground();
        shape4.setColorFilter(Color.parseColor(colorToApply4), android.graphics.PorterDuff.Mode.SRC);

        biceps2 = findViewById(R.id.biceps2);
        Drawable shape42 = (Drawable) biceps2.getBackground();
        shape42.setColorFilter(Color.parseColor(colorToApply4), android.graphics.PorterDuff.Mode.SRC);


        String colorToApply6 = "#db0900";
        pecho = findViewById(R.id.pecho);
        Drawable shape6 = (Drawable) pecho.getBackground();
        shape6.setColorFilter(Color.parseColor(colorToApply6), android.graphics.PorterDuff.Mode.SRC);
        pecho.setTextColor(Color.parseColor(blanco));

        pecho2 = findViewById(R.id.pecho2);
        Drawable shape62 = (Drawable) pecho2.getBackground();
        shape62.setColorFilter(Color.parseColor(colorToApply6), android.graphics.PorterDuff.Mode.SRC);
        pecho2.setTextColor(Color.parseColor(blanco));


        String colorToApply7 = "#0fa9c9";
        hombro = findViewById(R.id.hombro);
        Drawable shape7 = (Drawable) hombro.getBackground();
        shape7.setColorFilter(Color.parseColor(colorToApply7), android.graphics.PorterDuff.Mode.SRC);

        hombro2 = findViewById(R.id.hombro2);
        Drawable shape72 = (Drawable) hombro2.getBackground();
        shape72.setColorFilter(Color.parseColor(colorToApply7), android.graphics.PorterDuff.Mode.SRC);


        String colorToApply8 = "#a9f858";
        triceps = findViewById(R.id.triceps);
        Drawable shape8 = (Drawable) triceps.getBackground();
        shape8.setColorFilter(Color.parseColor(colorToApply8), android.graphics.PorterDuff.Mode.SRC);

        triceps2 = findViewById(R.id.triceps2);
        Drawable shape82 = (Drawable) triceps2.getBackground();
        shape82.setColorFilter(Color.parseColor(colorToApply8), android.graphics.PorterDuff.Mode.SRC);


        String colorToApply10 = "#d00155";
        pierna = findViewById(R.id.pierna);
        Drawable shape10 = (Drawable) pierna.getBackground();
        shape10.setColorFilter(Color.parseColor(colorToApply10), android.graphics.PorterDuff.Mode.SRC);
        pierna.setTextColor(Color.parseColor(blanco));

        pierna2 = findViewById(R.id.pierna2);
        Drawable shape102 = (Drawable) pierna2.getBackground();
        shape102.setColorFilter(Color.parseColor(colorToApply10), android.graphics.PorterDuff.Mode.SRC);
        pierna2.setTextColor(Color.parseColor(blanco));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dia1){
            cambiarActivity(EjerciciosDiarios.class);

        } else if (id == R.id.imagen_casa) {
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(this.getClass());

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