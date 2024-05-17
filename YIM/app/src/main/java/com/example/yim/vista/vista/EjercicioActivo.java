package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicioUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjercicioActivo;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class EjercicioActivo extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    Intent intent;
    TablaEjercicioActivo ejercicioActivo;
    String ejerciciosTotales;
    ImageView atras;
    TextView ejerciciosTotalesTV, titulo, serieTV;
    ShapeableImageView imagen;
    ImageButton boton_info;
    Button repeticionesNecesariasBT, descansoBT, seriesBT;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_activo);

        firebaseManager = new FirebaseManager();
        intent = getIntent();
        ejercicioActivo = (TablaEjercicioActivo) intent.getSerializableExtra("ejercicioActivo");
        ejerciciosTotales = intent.getStringExtra("ejerciciosTotales");

        //Referencias de las vistas
        atras = findViewById(R.id.atras);
        ejerciciosTotalesTV = findViewById(R.id.ejercios_totales_tv);

        imagen = findViewById(R.id.imagen);
        boton_info = findViewById(R.id.boton_info);
        titulo = findViewById(R.id.titulo);

        repeticionesNecesariasBT = findViewById(R.id.repeticiones_necesarias_bt);
        descansoBT = findViewById(R.id.descanso_bt);
        seriesBT = findViewById(R.id.series_bt);

        serieTV = findViewById(R.id.serie_tv);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        atras.setOnClickListener(this);
        boton_info.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);


        mostrarDatos();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.boton_info){
            mostrarInfoEjercicio();

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

    @SuppressLint("SetTextI18n")
    private void mostrarDatos(){
        int minutos = ejercicioActivo.getTiempo_descanso() / 60;
        int segundos = ejercicioActivo.getTiempo_descanso() % 60;
        String minutosString;
        String segundosString;

        ejerciciosTotalesTV.setText("Ejercicio " + ejerciciosTotales);

        imagen.setImageResource(R.drawable.curl_de_biceps_hombre);
        titulo.setText(ejercicioActivo.getNombre());

        repeticionesNecesariasBT.setText(String.valueOf(ejercicioActivo.getRepeticiones()));

        if (minutos>=10){
            minutosString = String.valueOf(minutos);
        }else if(minutos == 0){
            minutosString = "00";
        } else {
            minutosString = "0" + minutos;
        }

        if (segundos>=10){
            segundosString = String.valueOf(segundos);
        }else if(segundos == 0){
            segundosString = "00";
        } else {
            segundosString = "0" + segundos;
        }
        descansoBT.setText(minutosString + ":" + segundosString);

        seriesBT.setText(ejercicioActivo.getSeries_realizadas() + "/" + ejercicioActivo.getSeries_necesarias());

        serieTV.setText("Serie " + (ejercicioActivo.getSeries_realizadas() + 1));
    }

    private void mostrarInfoEjercicio(){
        firebaseManager.obtenerEjercicioUsuario(this, ejercicioActivo.getId_ejercicio(), new FirebaseCallbackEjercicioUsuario() {
            @Override
            public void onCallback(TablaEjerciciosUsuario ejercicio) {
                CambiarActivity.cambiar(EjercicioActivo.this, PopupVerEjercicios.class, ejercicio);
            }
        });
    }


}