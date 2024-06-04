package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.VerRutinasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinasUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class VerRutinas extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    RecyclerView recyclerView;
    TextView sinRutinas;
    ScrollView scrollView;
    ImageView agregarRutina, imagenPerfilMenu;
    ProgressBar cargando;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_rutinas);

        //Inicializar instancias.
        firebaseManager = new FirebaseManager();


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        recyclerView = findViewById(R.id.rutinas);

        sinRutinas = findViewById(R.id.sin_rutinas);
        scrollView = findViewById(R.id.scrollView);
        agregarRutina = findViewById(R.id.agregar_rutina);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners.
        agregarRutina.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);


        //Mostrar datos.
        try{
            mostrarImagenPerfil();
            mostrarRutinas();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar las rutinas.");
            ex.printStackTrace();
        }

        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "agregar_rutina":
                cambiarActivity(CrearRutinas.class);
                break;

            case "imagen_casa_menu":
                cambiarActivity(Inicio.class);
                break;
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

    // Método para mostrar las rutinas creadas por el usuario desde un adaptador.
    private void mostrarRutinas(){
        try {
            firebaseManager.obtenerRutinasUsuario(this, new FirebaseCallbackRutinasUsuario() {
                @Override
                public void onCallback(ArrayList<TablaRutinaUsuario> rutinas) {
                    if(!rutinas.isEmpty()){
                        recyclerView.setLayoutManager(new LinearLayoutManager(VerRutinas.this));
                        VerRutinasAdaptador adaptador = new VerRutinasAdaptador(VerRutinas.this, rutinas);
                        recyclerView.setAdapter(adaptador);
                    }else{
                        sinRutinas.setVisibility(View.VISIBLE);
                    }
                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al mostrar las rutinas del usuario.");
            ex.printStackTrace();
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
                        Imagenes.mostrarImagenPerfil(VerRutinas.this, imagenPerfilMenu);
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