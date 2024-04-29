package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yim.R;

public class Logros extends AppCompatActivity implements View.OnClickListener {
    FrameLayout logro_50_flexiones, logro_100_flexiones, logro_150_flexiones, logro_250_flexiones, imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    ImageView imagen_logro_50_flexiones, imagen_logro_100_flexiones, imagen_logro_150_flexiones;
    View progreso_logro_50_flexiones, progreso_logro_100_flexiones, progreso_logro_150_flexiones, progreso_logro_250_flexiones;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logros);


        //Referencias de las vistas
        logro_50_flexiones = findViewById(R.id.logro_50_flexiones);
        imagen_logro_50_flexiones = findViewById(R.id.imagen_logro_50_flexiones);
        progreso_logro_50_flexiones = findViewById(R.id.progreso_logro_50_flexiones);

        logro_100_flexiones = findViewById(R.id.logro_100_flexiones);
        imagen_logro_100_flexiones = findViewById(R.id.imagen_logro_100_flexiones);
        progreso_logro_100_flexiones = findViewById(R.id.progreso_logro_100_flexiones);

        logro_150_flexiones = findViewById(R.id.logro_150_flexiones);
        imagen_logro_150_flexiones = findViewById(R.id.imagen_logro_150_flexiones);
        progreso_logro_150_flexiones = findViewById(R.id.progreso_logro_150_flexiones);

        logro_250_flexiones = findViewById(R.id.logro_250_flexiones);
        progreso_logro_250_flexiones = findViewById(R.id.progreso_logro_250_flexiones);


        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        logro_50_flexiones.setOnClickListener(this);
        logro_100_flexiones.setOnClickListener(this);
        logro_150_flexiones.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);


        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) progreso_logro_50_flexiones.getLayoutParams();
        int progreso = (int) ((50.0/50) * 80);
        layoutParams.width = convertirDpAPixeles(80 - progreso);
        layoutParams.setMarginStart( convertirDpAPixeles(progreso) );

        progreso_logro_50_flexiones.setLayoutParams(layoutParams);


        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) progreso_logro_100_flexiones.getLayoutParams();
        int progreso2 = (int) ((50.0/100) * 80);
        layoutParams2.width = convertirDpAPixeles(80 - progreso2);
        layoutParams2.setMarginStart( convertirDpAPixeles(progreso2) );

        progreso_logro_100_flexiones.setLayoutParams(layoutParams2);


        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) progreso_logro_150_flexiones.getLayoutParams();
        int progreso3 = (int) ((50.0/150) * 80);
        layoutParams3.width = convertirDpAPixeles(80 - progreso3);
        layoutParams3.setMarginStart( convertirDpAPixeles(progreso3) );

        progreso_logro_150_flexiones.setLayoutParams(layoutParams3);
        progreso_logro_250_flexiones.setLayoutParams(layoutParams3);
    }

    public int convertirDpAPixeles(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.logro_50_flexiones){
            cambiarActivity(PopupLogros.class);

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}