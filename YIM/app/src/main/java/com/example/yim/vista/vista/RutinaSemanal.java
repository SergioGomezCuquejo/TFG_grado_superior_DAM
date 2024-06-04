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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.RutinaSemanalAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaActiva;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaRutinaActiva;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.HashMap;

public class RutinaSemanal extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    RecyclerView recyclerView;
    TextView noRutinaTV;
    RelativeLayout relativeLayout;
    ProgressBar cargando;
    ImageView agregarSemana, imagenPerfilMenu;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    RutinaSemanalAdaptador adaptador;
    String IDRutina;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_semanal);

        //Inicializar instancias.
        firebaseManager = new FirebaseManager();

        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        recyclerView = findViewById(R.id.dias);

        relativeLayout = findViewById(R.id.relativeLayout);
        noRutinaTV = findViewById(R.id.no_rutina_tv);
        agregarSemana = findViewById(R.id.agregar_semana);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners.
        agregarSemana.setOnClickListener(this);
        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);


        //Mostrar datos.
        try{
            mostrarImagenPerfil();
            obtenerSemana ();

        } catch (Exception ex) {
            mostrarToast("Error al obtener los datos de la rutina.");
            ex.printStackTrace();
        }

        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "agregar_semana":
                agregarSemana();
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


    // Método para obtener la rutina activa.
    private void obtenerSemana () {
        firebaseManager.obtenerRutinaActiva(this, new FirebaseCallbackRutinaActiva() {
            @Override
            public void onCallback(TablaRutinaActiva rutinaActiva) {
                if(rutinaActiva != null){
                    IDRutina = rutinaActiva.getIdRutina();
                    mostrarSemana(rutinaActiva.getSemana());
                }else{
                    relativeLayout.setVisibility(View.GONE);
                    noRutinaTV.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    // Método para mostar la semana de la rutian activa.
    @SuppressLint("SetTextI18n")
    private void mostrarSemana(ArrayList<TablaDiaRutinaActiva> rutinaActiva){
        firebaseManager.obtenerMusculosUsuario(RutinaSemanal.this, new FirebaseCallbackMusculosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaMusculoUsuario> musculosUsuarios) {

                HashMap<String, ColoresMusculoUsuario> musculosHM = new HashMap<>();
                for (TablaMusculoUsuario musculo : musculosUsuarios){
                    musculosHM.put(musculo.getNombre(), new ColoresMusculoUsuario(musculo.getColor_fondo(), musculo.getColor_fuente()));
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(RutinaSemanal.this));
                adaptador = new RutinaSemanalAdaptador(RutinaSemanal.this, rutinaActiva, musculosHM);
                recyclerView.setAdapter(adaptador);
            }
        });
    }


    // Método para agregar una nueva semana a la rutina activa de la rutina activada.
    private void agregarSemana(){
        try{
            firebaseManager.obtenerRutinaUsuario(this, IDRutina, new FirebaseCallbackRutinaUsuario() {
                @Override
                public void onCallback(TablaRutinaUsuario rutinaUsuario) {
                    if(rutinaUsuario!= null){
                        firebaseManager.actualizarRutinaActiva(RutinaSemanal.this, rutinaUsuario);
                    }else {
                        mostrarToast("Ha ocurrido un error al agregar una nueva semana");
                    }

                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al mostrar la rutina activa.");
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
                        Imagenes.mostrarImagenPerfil(RutinaSemanal.this, imagenPerfilMenu);
                    }

                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al mostrar la imagen de perfil.");
            ex.printStackTrace();
        }

    }


    // Métodos para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        CambiarActivity.cambiar(this, activity);
    }


    // Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }

}