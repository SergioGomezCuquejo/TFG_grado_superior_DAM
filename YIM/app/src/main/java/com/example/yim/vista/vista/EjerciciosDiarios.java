package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.EjerciciosDiariosAdaptador;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;

public class EjerciciosDiarios extends AppCompatActivity  implements View.OnClickListener {
    Intent intent;
    TablaDiaRutinaActiva diaRutinaActiva;
    RecyclerView recyclerView;
    ImageView atras;
    TextView diaTV;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    EjerciciosDiariosAdaptador adaptador;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_diarios);

        intent = getIntent();
        diaRutinaActiva = (TablaDiaRutinaActiva) intent.getSerializableExtra("diaRutinaActiva");

        //Referencias de las vistas
        //TOdo intentar hacer
        //agregar_ejercicio = findViewById(R.id.agregar_ejercicio);
        atras = findViewById(R.id.atras);
        diaTV = findViewById(R.id.dia_tv);
        recyclerView = findViewById(R.id.ejercicios);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        atras.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        diaTV.setText("Ejercicios del día " + diaRutinaActiva.getDia());
        mostrarEjercicios();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

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

    private void mostrarEjercicios(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new EjerciciosDiariosAdaptador(this, diaRutinaActiva);
        recyclerView.setAdapter(adaptador);
    }
}