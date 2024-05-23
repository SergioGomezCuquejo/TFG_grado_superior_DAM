package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.LogrosAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogrosUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaLogroUsuario;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class Logros extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    RecyclerView logros;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    LogrosAdaptador adaptador;
    int columnas;
    GridLayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logros);

        firebaseManager = new FirebaseManager();

        //Referencias de las vistas
        logros = findViewById(R.id.logros);

        imagen_casa = findViewById(R.id.imagen_casa_menu);
        imagen_calendario = findViewById(R.id.imagen_calendario_menu);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas_menu);
        imagen_usuario = findViewById(R.id.imagen_usuario_menu);

        //Listeners
        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);


        //Mostrar los logros del usuario.
        mostrarLogros();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa_menu){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario_menu) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas_menu) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.imagen_usuario_menu) {
            cambiarActivity(Perfil.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    public void mostrarLogros(){
        try{
            firebaseManager.obtenerLogrosUsuario(this, new FirebaseCallbackLogrosUsuario() {
                @Override
                public void onCallback(ArrayList<TablaLogroUsuario> logrosUsuario) {

                    columnas = 4;

                    layoutManager = new GridLayoutManager(Logros.this, columnas);
                    logros.setLayoutManager(layoutManager);
                    adaptador = new LogrosAdaptador(Logros.this, logrosUsuario);
                    logros.setAdapter(adaptador);

                }
            });

        }catch (Exception ex){
            MostratToast.mostrarToast(this, "Error al obtener los logros del usuario.");
            ex.printStackTrace();
        }
    }

}