package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.google.android.material.imageview.ShapeableImageView;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    LinearLayout continuar_linearlayout, musculos_linearlayout, imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    TextView ejercicios, rutinas;
    ImageView lupa_ejercicios, mas_ejercicios_pequeno, lupa_rutinas, mas_rutinas_pequeno;
    FrameLayout mas_ejercicios_grande, mas_rutinas_grande;
    ShapeableImageView rutina1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Referencias de las vistas
        continuar_linearlayout = findViewById(R.id.continuar_linearlayout);
        musculos_linearlayout = findViewById(R.id.musculos_linearlayout);
        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);
        ejercicios = findViewById(R.id.ejercicios);
        rutinas = findViewById(R.id.rutinas);
        lupa_ejercicios = findViewById(R.id.lupa_ejercicios);
        mas_ejercicios_pequeno = findViewById(R.id.mas_ejercicios_pequeno);
        lupa_rutinas = findViewById(R.id.lupa_rutinas);
        mas_rutinas_pequeno = findViewById(R.id.mas_rutinas_pequeno);
        mas_ejercicios_grande = findViewById(R.id.mas_ejercicios_grande);
        mas_rutinas_grande = findViewById(R.id.mas_rutinas_grande);
        rutina1 = findViewById(R.id.rutina1);

        //Listeners
        continuar_linearlayout.setOnClickListener(this);
        musculos_linearlayout.setOnClickListener(this);
        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);
        ejercicios.setOnClickListener(this);
        rutinas.setOnClickListener(this);
        lupa_ejercicios.setOnClickListener(this);
        mas_ejercicios_pequeno.setOnClickListener(this);
        lupa_rutinas.setOnClickListener(this);
        mas_rutinas_pequeno.setOnClickListener(this);
        mas_ejercicios_grande.setOnClickListener(this);
        mas_rutinas_grande.setOnClickListener(this);
        rutina1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.continuar_linearlayout || id == R.id.rutina1 || id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.musculos_linearlayout) {
            cambiarActivity(Musculos.class);

        } else if (id == R.id.imagen_casa) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        } else if (id == R.id.ejercicios || id == R.id.lupa_ejercicios) {
            cambiarActivity(VerEjercicios.class);

        } else if (id == R.id.rutinas || id == R.id.lupa_rutinas) {
            cambiarActivity(VerRutinas.class);

        } else if (id == R.id.mas_ejercicios_pequeno || id == R.id.mas_ejercicios_grande) {
            cambiarActivity(CrearEjercicios.class);

        } else if (id == R.id.mas_rutinas_pequeno || id == R.id.mas_rutinas_grande) {
            cambiarActivity(CrearRutinas.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}
