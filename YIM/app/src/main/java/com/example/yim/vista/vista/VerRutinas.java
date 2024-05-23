package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.VerRutinasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinasUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;

import java.util.ArrayList;

public class VerRutinas extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    RecyclerView recyclerView;
    ImageView agregarRutina;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    VerRutinasAdaptador adaptador;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_rutinas);

        firebaseManager = new FirebaseManager();

        //Referencias de las vistas
        recyclerView = findViewById(R.id.rutinas);

        agregarRutina = findViewById(R.id.agregar_rutina);

        imagen_casa = findViewById(R.id.imagen_casa_menu);
        imagen_calendario = findViewById(R.id.imagen_calendario_menu);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas_menu);
        imagen_usuario = findViewById(R.id.imagen_usuario_menu);

        //Listeners
        agregarRutina.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        mostrarRutinas();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.agregar_rutina){
            cambiarActivity(CrearRutinas.class);

        } else if (id == R.id.imagen_casa_menu) {
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario_menu) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas_menu) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario_menu) {
            cambiarActivity(Perfil.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    private void mostrarRutinas(){
        firebaseManager.obtenerRutinasUsuario(this, new FirebaseCallbackRutinasUsuario() {
            @Override
            public void onCallback(ArrayList<TablaRutinaUsuario> rutinas) {
                recyclerView.setLayoutManager(new LinearLayoutManager(VerRutinas.this));
                adaptador = new VerRutinasAdaptador(VerRutinas.this, rutinas);
                recyclerView.setAdapter(adaptador);
            }
        });
    }
}