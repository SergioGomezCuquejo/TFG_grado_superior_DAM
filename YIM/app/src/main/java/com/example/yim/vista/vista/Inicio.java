package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.InicioEjerciciosAdaptador;
import com.example.yim.controlador.Adaptadores.InicioRutinasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosCreados;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaActiva;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinasUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaRutinaActiva;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    LinearLayout continuarLinearlayout, rutinasLL, musculosLL, ejerciciosLL;
    RecyclerView rutinasRV, ejercicioRV;
    TextView continuarTV, semanaTV, diaTV;
    FrameLayout agregarRutinaFL, agregarEjercicioFL, imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    ImageView imagenPerfilMenu;
    GridLayoutManager layoutManager;
    ProgressBar cargando;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Inicializar instancias.
        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();


        //Controlar que no se ha cerrado sesión.
        FirebaseUser user = auth.getCurrentUser();
        if(user == null){
            cambiarActivity(InicioSesion.class);
        }

        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        continuarLinearlayout = findViewById(R.id.continuar_linearlayout);
        continuarTV = findViewById(R.id.continuar_tv);
        semanaTV = findViewById(R.id.semana_tv);
        diaTV = findViewById(R.id.dia_tv);

        rutinasLL = findViewById(R.id.rutinas_ll);
        rutinasRV = findViewById(R.id.rutinas_rv);
        agregarRutinaFL = findViewById(R.id.agregar_rutina_fl);

        musculosLL = findViewById(R.id.musculos_ll);

        ejerciciosLL = findViewById(R.id.ejercicios_ll);
        ejercicioRV = findViewById(R.id.ejercicio_rv);
        agregarEjercicioFL = findViewById(R.id.agregar_ejercicio_fl);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners
        continuarLinearlayout.setOnClickListener(this);

        rutinasLL.setOnClickListener(this);
        agregarRutinaFL.setOnClickListener(this);
        musculosLL.setOnClickListener(this);

        ejerciciosLL.setOnClickListener(this);
        ejercicioRV.setOnClickListener(this);
        agregarEjercicioFL.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);

        //Mostrar datos.
        try{
            mostrarImagenPerfil(new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {

                    continuar(new FirebaseCallbackBoolean() {
                        @Override
                        public void onCallback(boolean accionRealizada) {

                            mostrarRutinas(new FirebaseCallbackBoolean() {
                                @Override
                                public void onCallback(boolean accionRealizada) {
                                    mostrarEjercicios(new FirebaseCallbackBoolean() {
                                        @Override
                                        public void onCallback(boolean accionRealizada) {
                                            //Ocultar barra de progreso.
                                            cargando.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            });

                        }
                    });

                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al obtener el inicio.");
            ex.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "continuar_linearlayout":
                if(continuarTV.getText().equals("Continuar")){
                    cambiarActivity(RutinaSemanal.class);
                }else{
                    cambiarActivity(VerRutinas.class);
                }
                break;

            case "rutinas_ll":
                cambiarActivity(VerRutinas.class);
                break;
            case "agregar_rutina_fl":
                cambiarActivity(CrearRutinas.class);
                break;

            case "musculos_ll":
                cambiarActivity(Musculos.class);
                break;

            case "ejercicios_ll":
                cambiarActivity(VerEjercicios.class);
                break;
            case "agregar_ejercicio_fl":
                cambiarActivity(PopupCrearEjercicios.class);
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

    private void continuar(FirebaseCallbackBoolean callback){
        try {
            firebaseManager.obtenerRutinaActiva(this, new FirebaseCallbackRutinaActiva() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onCallback(TablaRutinaActiva rutinaActiva) {
                    if(rutinaActiva != null){
                        semanaTV.setVisibility(View.VISIBLE);

                        continuarTV.setText("Continuar");
                        semanaTV.setText("Semana " + (rutinaActiva.getSemana().size()/7));
                    }else{
                        continuarTV.setText("Empezar a crear una rutina");
                    }
                    callback.onCallback(true);
                }
            });
        } catch (Exception ex) {
            mostrarToast("Error al obtener la rutina semanal.");
            ex.printStackTrace();
        }
    }

    //Método para mostrar las rutinas del usuario desde un adaptador.
    public void mostrarRutinas(FirebaseCallbackBoolean callback){
        try{
            firebaseManager.obtenerRutinasUsuario(this, new FirebaseCallbackRutinasUsuario() {
                @Override
                public void onCallback(ArrayList<TablaRutinaUsuario> rutinasUsuario) {
                    if(rutinasUsuario.size() > 0){
                        InicioRutinasAdaptador adaptador;
                        int columnas = rutinasUsuario.size();

                        layoutManager = new GridLayoutManager(Inicio.this, columnas);
                        rutinasRV.setLayoutManager(layoutManager);
                        adaptador = new InicioRutinasAdaptador(Inicio.this, rutinasUsuario);
                        rutinasRV.setAdapter(adaptador);
                    }
                    callback.onCallback(true);
                }
            });

        }catch (Exception ex){
            mostrarToast( "Error al obtener los logros del usuario.");
            ex.printStackTrace();
        }
    }

    //Método para mostrar los ejercicios creados por el usuario desde un adaptador.
    public void mostrarEjercicios(FirebaseCallbackBoolean callback){
        try{
            firebaseManager.obtenerEjerciciosCreados(this, new FirebaseCallbackEjerciciosCreados() {
                @Override
                public void onCallback(ArrayList<TablaEjercicioCreado> ejerciciosCreados) {
                    if(ejerciciosCreados.size() > 0){
                        InicioEjerciciosAdaptador adaptador;

                        int columnas = ejerciciosCreados.size();

                        layoutManager = new GridLayoutManager(Inicio.this, columnas);
                        ejercicioRV.setLayoutManager(layoutManager);
                        adaptador = new InicioEjerciciosAdaptador(Inicio.this, ejerciciosCreados);
                        ejercicioRV.setAdapter(adaptador);
                    }
                    callback.onCallback(true);
                }
            });

        }catch (Exception ex){
            mostrarToast( "Error al obtener los logros del usuario.");
            ex.printStackTrace();
        }
    }



    //Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int REQUEST_CODE = 1;
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                boolean resultado = data.getBooleanExtra("resultado", false);
                MostratToast.mostrarToast(this, "res - " + resultado);
            }
        }
    }

    private void mostrarImagenPerfil(FirebaseCallbackBoolean callback){
        firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
            @Override
            public void onCallback(TablaPerfil perfil) {
                if(perfil.getImagen() != null && !perfil.getImagen().equals("")){
                    Imagenes.urlImagenPerfil = perfil.getImagen();
                    Imagenes.mostrarImagenPerfil(Inicio.this, imagenPerfilMenu);
                }
                callback.onCallback(true);

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
