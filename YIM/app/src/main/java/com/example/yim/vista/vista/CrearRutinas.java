package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.CrearRutinasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaRutinasUsuario;

import java.util.ArrayList;
import java.util.HashMap;

public class CrearRutinas extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    Intent intent;
    TablaRutinasUsuario rutinaUsuario;
    ImageView atras, preguntas;
    EditText nombre;
    RecyclerView recyclerView;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    CrearRutinasAdaptador adaptador;
    HashMap<String, ColoresMusculoUsuario> musculosHM = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);

        firebaseManager = new FirebaseManager();

        intent = getIntent();
        rutinaUsuario = (TablaRutinasUsuario) intent.getSerializableExtra("rutinaUsuario");

        //Referencias de las vistas
        atras = findViewById(R.id.atras);
        preguntas = findViewById(R.id.preguntas);

        nombre = findViewById(R.id.nombre);

        recyclerView = findViewById(R.id.dias);

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

        mostrarSemana();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.atras){
            finish();

        } else if (id == R.id.preguntas){
            cambiarActivity(Cuestionario.class);

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

    private void mostrarSemana(){
        nombre.setText(rutinaUsuario.getInformacion().getNombre());
        firebaseManager.obtenerMusculosUsuario(CrearRutinas.this, new FirebaseCallbackMusculosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaMusculosUsuario> musculosUsuarios) {

                for (TablaMusculosUsuario musculo : musculosUsuarios){
                    musculosHM.put(musculo.getNombre(), new ColoresMusculoUsuario(musculo.getColor_fondo(), musculo.getColor_fuente()));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(CrearRutinas.this));
                adaptador = new CrearRutinasAdaptador(CrearRutinas.this, rutinaUsuario, musculosHM);
                recyclerView.setAdapter(adaptador);
            }
        });
    }

}