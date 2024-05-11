package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import com.example.yim.controlador.Adaptadores.EjerciciosRutinasAdaptador;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.HashMap;

public class EjerciciosRutinas extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    TablaDiaRutinaUsuario diaRutinaUsuario;
    HashMap<String, ColoresMusculoUsuario> musculosSemana;
    ImageView atras;
    LinearLayout musculos;
    RecyclerView recyclerView;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    TextView musculoIzquierda, musculoCentro, musculoDerecha;
    ImageView agregar_ejercicio;
    EjerciciosRutinasAdaptador adaptador;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_rutinas);

        intent = getIntent();
        diaRutinaUsuario = (TablaDiaRutinaUsuario) intent.getSerializableExtra("diaRutinaUsuario");
        musculosSemana = (HashMap<String, ColoresMusculoUsuario>) intent.getSerializableExtra("musculosSemana");

        //Referencias de las vistas
        atras = findViewById(R.id.atras);

        musculos = findViewById(R.id.musculos);
        musculoIzquierda = findViewById(R.id.musculo_izquierda);
        musculoCentro = findViewById(R.id.musculo_centro);
        musculoDerecha = findViewById(R.id.musculo_derecha);

        recyclerView = findViewById(R.id.ejericicos);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        agregar_ejercicio = findViewById(R.id.agregar_ejercicio);

        //Listeners
        atras.setOnClickListener(this);

        musculos.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        agregar_ejercicio.setOnClickListener(this);

        mostrarMusculos();
        mostrarEjercicios();
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

        } else if (id == R.id.agregar_ejercicio) {
            cambiarActivity(PopupAgregarEjercicio.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    private void mostrarMusculos(){
        ArrayList<String> musculosUsuario = diaRutinaUsuario.getMusculos();
        if (musculosUsuario.size() >= 2){
            musculoIzquierda.setBackgroundResource(R.drawable._style2_borde_blanco_izquierda);
            musculoCentro.setVisibility(View.VISIBLE);
            if(musculosUsuario.size() == 3){
                musculoCentro.setBackgroundResource(R.drawable._style2_borde_blanco_0);
                musculoDerecha.setVisibility(View.VISIBLE);
            }
        }
        String musculo;
        String fondo = "#FFFFFFFF";
        String fuente = "#FF000000";
        for (int i = 0; i < musculosUsuario.size(); i++){
            musculo = musculosUsuario.get(i);
            if(musculosSemana.containsKey(musculo)){
                fondo = musculosSemana.get(musculo).getColor_fondo();
                fuente = musculosSemana.get(musculo).getColor_fuente();
                musculosSemana.put(musculo, musculosSemana.get(musculo));
            }
            musculo = musculo.toUpperCase();
            if(i == 0){
                musculoIzquierda.setText(musculo);
                musculoIzquierda.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoIzquierda.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
            } else if (i == 1) {
                musculoCentro.setText(musculo);
                musculoCentro.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoCentro.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
            }else {
                musculoDerecha.setText(musculo);
                musculoDerecha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoDerecha.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
            }
        }
    }
    private void mostrarEjercicios(){
        recyclerView.setLayoutManager(new LinearLayoutManager(EjerciciosRutinas.this));
        adaptador = new EjerciciosRutinasAdaptador(EjerciciosRutinas.this, diaRutinaUsuario);
        recyclerView.setAdapter(adaptador);
    }
}