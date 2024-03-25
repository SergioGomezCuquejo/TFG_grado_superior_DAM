package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;

public class RutinaSemanal extends AppCompatActivity implements View.OnClickListener {
    LinearLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    TextView espalda, biceps, pecho, hombro, triceps, pierna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_semanal);

        //Referencias de las vistas
        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);


        //Poner color al fondo de los musculos
        String colorToApply = "#233284";
        String blanco = "#FFFFFFFF";

        espalda = findViewById(R.id.espalda);
        Drawable shape = (Drawable) espalda.getBackground();
        shape.setColorFilter(Color.parseColor(colorToApply), android.graphics.PorterDuff.Mode.SRC);
        espalda.setTextColor(Color.parseColor(blanco)); //cambiar letras a blanco


        String colorToApply4 = "#00c143";
        biceps = findViewById(R.id.biceps);
        Drawable shape4 = (Drawable) biceps.getBackground();
        shape4.setColorFilter(Color.parseColor(colorToApply4), android.graphics.PorterDuff.Mode.SRC);

        String colorToApply6 = "#db0900";
        pecho = findViewById(R.id.pecho);
        Drawable shape6 = (Drawable) pecho.getBackground();
        shape6.setColorFilter(Color.parseColor(colorToApply6), android.graphics.PorterDuff.Mode.SRC);
        pecho.setTextColor(Color.parseColor(blanco));

        String colorToApply7 = "#0fa9c9";
        hombro = findViewById(R.id.hombro);
        Drawable shape7 = (Drawable) hombro.getBackground();
        shape7.setColorFilter(Color.parseColor(colorToApply7), android.graphics.PorterDuff.Mode.SRC);

        String colorToApply8 = "#a9f858";
        triceps = findViewById(R.id.triceps);
        Drawable shape8 = (Drawable) triceps.getBackground();
        shape8.setColorFilter(Color.parseColor(colorToApply8), android.graphics.PorterDuff.Mode.SRC);

        String colorToApply10 = "#d00155";
        pierna = findViewById(R.id.pierna);
        Drawable shape10 = (Drawable) pierna.getBackground();
        shape10.setColorFilter(Color.parseColor(colorToApply10), android.graphics.PorterDuff.Mode.SRC);
        pierna.setTextColor(Color.parseColor(blanco));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa){
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