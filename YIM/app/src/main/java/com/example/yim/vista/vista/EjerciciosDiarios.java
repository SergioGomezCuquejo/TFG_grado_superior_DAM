package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.EjerciciosDiariosAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

public class EjerciciosDiarios extends AppCompatActivity  implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaDiaRutinaActiva diaRutinaActiva;
    ImageView atras, imagenPerfilMenu;
    RecyclerView recyclerView;
    ProgressBar cargando;
    TextView diaTV, sinEjercicios;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_diarios);

        //Inicializar instancias.
        firebaseManager = new FirebaseManager();
        Intent intent = getIntent();


        //Obtener la rutina que se ha seleccionado.
        if(intent.hasExtra("diaRutinaActiva")) {
            diaRutinaActiva = (TablaDiaRutinaActiva) intent.getSerializableExtra("diaRutinaActiva");
        }


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        sinEjercicios = findViewById(R.id.sin_ejercicios);

        atras = findViewById(R.id.atras_iv);
        diaTV = findViewById(R.id.dia_tv);
        recyclerView = findViewById(R.id.ejercicios);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);

        //Listeners.
        atras.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);


        //Mostrar datos.
        diaTV.setText("Ejercicios del día " + diaRutinaActiva.getDia());
        try{
            mostrarImagenPerfil();
            mostrarEjercicios();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar los ejercicios de la rutina.");
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
            case "atras_iv":
            case "imagen_calendario_menu":
                cambiarActivity(RutinaSemanal.class);
                break;
            case "imagen_estadisticas_menu":
                cambiarActivity(Logros.class);
                break;
            case "imagen_usuario_menu":
                cambiarActivity(Perfil.class);
                break;
        }
    }


    //Método para mostrar los ejercicios desde un adaptador.
    private void mostrarEjercicios(){
        if(diaRutinaActiva.getEjercicios() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            EjerciciosDiariosAdaptador adaptador = new EjerciciosDiariosAdaptador(this, diaRutinaActiva);
            recyclerView.setAdapter(adaptador);
        }else{
            sinEjercicios.setVisibility(View.VISIBLE);
        }
    }


    // Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    private void mostrarImagenPerfil(){
        try{
            firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
                @Override
                public void onCallback(TablaPerfil perfil) {
                    if(perfil.getImagen() != null && !perfil.getImagen().equals("")){
                        Imagenes.urlImagenPerfil = perfil.getImagen();
                        Imagenes.mostrarImagenPerfil(EjerciciosDiarios.this, imagenPerfilMenu);
                    }

                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al mostrar la imagen de perfil.");
            ex.printStackTrace();
        }

    }


    // Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        CambiarActivity.cambiar(this, activity);
    }


    // Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}