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
import com.example.yim.controlador.Adaptadores.VerEjerciciosAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class VerEjercicios extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseManager firebaseManager;
    FirebaseUser user;
    RecyclerView recyclerView;
    ImageView agregar_ejercicio;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    VerEjerciciosAdaptador adaptador;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicios);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();


        //Controlar que no se ha cerrado sesión.
        user = auth.getCurrentUser();
        if(user == null){
            cambiarActivity(InicioSesion.class);
        }

        //Referencias de las vistas.
        recyclerView = findViewById(R.id.ejercicios);

        agregar_ejercicio = findViewById(R.id.agregar_ejercicio);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        agregar_ejercicio.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        mostrarEjercicios();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.agregar_ejercicio) {
            cambiarActivity(PopupCrearEjercicios.class);

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        }
    }

    public void mostrarEjercicios(){
        try{
            firebaseManager.obtenerEjerciciosUsuario(this, new FirebaseCallbackEjerciciosUsuario() {
                @Override
                public void onCallback(ArrayList<TablaEjerciciosUsuario> ejerciciosUsuarios) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(VerEjercicios.this));
                    adaptador = new VerEjerciciosAdaptador(VerEjercicios.this, ejerciciosUsuarios);
                    recyclerView.setAdapter(adaptador);
                }
            });

        }catch (Exception ex){
            MostratToast.mostrarToast(this, "Error al obtener los musculos del usuario.");
            ex.printStackTrace();
        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}