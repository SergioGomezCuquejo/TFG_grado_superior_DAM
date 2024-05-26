package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.EstadisticasEjercicioCreadoAdaptador;
import com.example.yim.controlador.Adaptadores.EstadisticasEjercicioPorDefectoAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosCreados;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosPorDefecto;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.HashMap;

public class Estadisticas extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    ImageButton buscar;
    Spinner tipo;
    RecyclerView recyclerView;
    ScrollView scrollView;
    TextView noEstadisticasTV;
    ProgressBar cargando;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    ImageView imagenPerfilMenu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        //Inicializar instancias.
        firebaseManager = new FirebaseManager();


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        buscar = findViewById(R.id.buscar);
        tipo = findViewById(R.id.tipo);

        recyclerView = findViewById(R.id.recyclerView);

        scrollView = findViewById(R.id.scrollView);
        noEstadisticasTV = findViewById(R.id.no_estadisticas_tv);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners
        buscar.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Estadisticas.this, R.layout.spinner_items, getResources().getStringArray(R.array.tipos_busqueda));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tipo.setAdapter(adapter);


        //Mostrar datos.
        try{
            mostrarImagenPerfil();
            obtenerEjerciciosCreados();
            obtenerEjerciciosPorDefecto();

        } catch (Exception ex) {
            mostrarToast("Error al obtener las estadísticas.");
            ex.printStackTrace();
        }

        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "buscar"://todo
                mostrarToast(String.valueOf(tipo.getSelectedItemPosition()));
                break;

            case "imagen_casa_menu":
                cambiarActivity(Inicio.class);
                break;
            case "imagen_calendario_menu":
                cambiarActivity(RutinaSemanal.class);
                break;
            case "imagen_estadisticas_menu":
                cambiarActivity(Estadisticas.class);
                break;
            case "imagen_usuario_menu":
                cambiarActivity(Perfil.class);
                break;
        }
    }

    //Método para obtener los ejercicos que tengan estadísticas.
    private void obtenerEjerciciosCreados(){
        firebaseManager.obtenerEjerciciosCreadosConEstadisticas(this, new FirebaseCallbackEjerciciosCreados() {
            @Override
            public void onCallback(ArrayList<TablaEjercicioCreado> ejerciciosUsuarios) {
                if(ejerciciosUsuarios != null && ejerciciosUsuarios.size() > 0){
                    obtenerMusculosEjercicioCreados(ejerciciosUsuarios);
                }else{
                    scrollView.setVisibility(View.GONE);
                    noEstadisticasTV.setVisibility(View.VISIBLE);
                }

            }
        });
    }
    private void obtenerEjerciciosPorDefecto(){
        firebaseManager.obtenerEjerciciosPorDefectoConEstadisticas(this, new FirebaseCallbackEjerciciosPorDefecto() {
            @Override
            public void onCallback(ArrayList<TablaEjercicioPorDefecto> ejerciciosUsuarios) {
                if(ejerciciosUsuarios != null && ejerciciosUsuarios.size() > 0){
                    obtenerMusculosEjercicioPorDefecto(ejerciciosUsuarios);
                }else{
                    scrollView.setVisibility(View.GONE);
                    noEstadisticasTV.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    //Método para obtener los colores de los músculos del usuario.
    private void obtenerMusculosEjercicioCreados(ArrayList<TablaEjercicioCreado> ejercicios){

        firebaseManager.obtenerMusculosUsuario(this, new FirebaseCallbackMusculosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaMusculoUsuario> musculosUsuarios) {
                if(musculosUsuarios != null){
                    HashMap<String, String> musculosHM = new HashMap<>();
                    for (TablaMusculoUsuario musculo : musculosUsuarios){
                        musculosHM.put(musculo.getNombre(), musculo.getColor_fondo());
                    }
                    mostrarEjerciciosCreados(ejercicios, musculosHM);
                }

            }
        });
    }
    private void obtenerMusculosEjercicioPorDefecto(ArrayList<TablaEjercicioPorDefecto> ejercicios){

        firebaseManager.obtenerMusculosUsuario(this, new FirebaseCallbackMusculosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaMusculoUsuario> musculosUsuarios) {
                if(musculosUsuarios != null){
                    HashMap<String, String> musculosHM = new HashMap<>();
                    for (TablaMusculoUsuario musculo : musculosUsuarios){
                        musculosHM.put(musculo.getNombre(), musculo.getColor_fondo());
                    }
                    mostrarEjerciciosPorDefecto(ejercicios, musculosHM);
                }

            }
        });
    }


    //Método para mostrar los ejercicicios desde un adaptador.
    private void  mostrarEjerciciosCreados(ArrayList<TablaEjercicioCreado> ejercicios, HashMap<String, String> musculosHM){
        recyclerView.setLayoutManager(new LinearLayoutManager(Estadisticas.this));
        EstadisticasEjercicioCreadoAdaptador adaptador = new EstadisticasEjercicioCreadoAdaptador(Estadisticas.this, ejercicios, musculosHM);
        recyclerView.setAdapter(adaptador);
    }
    private void  mostrarEjerciciosPorDefecto(ArrayList<TablaEjercicioPorDefecto> ejercicios, HashMap<String, String> musculosHM){
        recyclerView.setLayoutManager(new LinearLayoutManager(Estadisticas.this));
        EstadisticasEjercicioPorDefectoAdaptador adaptador = new EstadisticasEjercicioPorDefectoAdaptador(Estadisticas.this, ejercicios, musculosHM);
        recyclerView.setAdapter(adaptador);
    }


    //Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    private void mostrarImagenPerfil(){
        firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
            @Override
            public void onCallback(TablaPerfil perfil) {
                if(perfil.getImagen() != null && !perfil.getImagen().equals("")){
                    Imagenes.urlImagenPerfil = perfil.getImagen();
                    Imagenes.mostrarImagenPerfil(Estadisticas.this, imagenPerfilMenu);
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