package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;

public class CrearRutinas extends AppCompatActivity implements View.OnClickListener {
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    TextView espalda, biceps, pecho, hombro, triceps, pierna, descanso, espalda2, biceps2, pecho2, hombro2, triceps2, pierna2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);

        //Referencias de las vistas
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

    //Poner color al fondo de los musculos
    public void cambiarColores(){
        String colorToApply = "#233284";
        String blanco = "#FFFFFFFF";

        Drawable shape = (Drawable) espalda.getBackground();
        shape.setColorFilter(Color.parseColor(colorToApply), android.graphics.PorterDuff.Mode.SRC);
        espalda.setTextColor(Color.parseColor(blanco));
    }

}