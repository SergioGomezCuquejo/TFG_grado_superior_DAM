package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.LogrosAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogrosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaLogroUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class Logros extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    RecyclerView logros;
    LogrosAdaptador adaptador;
    GridLayoutManager layoutManager;
    ProgressBar cargando;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    ImageView imagenPerfilMenu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logros);

        //Inicializar instancias.
        firebaseManager = new FirebaseManager();


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        logros = findViewById(R.id.logros);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners.
        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);


        //Mostrar datos.
        try{
            mostrarLogros();
            mostrarImagenPerfil();

        } catch (Exception ex) {
            mostrarToast("Error al obtener los logros de la rutina.");
            ex.printStackTrace();
        }

        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "imagen_casa_menu":
                cambiarActivity(Inicio.class);
                break;
            case "imagen_calendario_menu":
                cambiarActivity(Estadisticas.class);
                break;

            case "atras_iv":
            case "imagen_estadisticas_menu":
                cambiarActivity(Estadisticas.class);
                break;
            case "imagen_usuario_menu":
                cambiarActivity(Perfil.class);
                break;
        }
    }


    //Método para mostrar los logros y los progresos que lleva el usuario desde un adaptador.
    public void mostrarLogros(){
        try{
            firebaseManager.obtenerLogrosUsuario(this, new FirebaseCallbackLogrosUsuario() {
                @Override
                public void onCallback(ArrayList<TablaLogroUsuario> logrosUsuario) {
                    if(logrosUsuario.size() > 0){
                        int columnas = 4;

                        layoutManager = new GridLayoutManager(Logros.this, columnas);
                        logros.setLayoutManager(layoutManager);
                        adaptador = new LogrosAdaptador(Logros.this, logrosUsuario);
                        logros.setAdapter(adaptador);
                    }else{
                        mostrarToast("Logros no encontrados.");
                    }
                }
            });

        }catch (Exception ex){
            mostrarToast( "Error al obtener los logros del usuario.");
            ex.printStackTrace();
        }
    }



    //Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    private void mostrarImagenPerfil() {
        firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
            @Override
            public void onCallback(TablaPerfil perfil) {
                if (perfil.getImagen() != null && !perfil.getImagen().equals("")) {
                    Imagenes.urlImagenPerfil = perfil.getImagen();
                    Imagenes.mostrarImagenPerfil(Logros.this, imagenPerfilMenu);
                }

            }
        });
    }


    //Métodos para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }


    //Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }

}