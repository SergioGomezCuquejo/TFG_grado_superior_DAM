package com.example.yim.vista.vista;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.CrearRutinasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.ObtenerLogro;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaInfoRutinaUsuario;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.HashMap;

public class CrearRutinas extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaRutinaUsuario rutinaUsuario;
    private Class<?> irA;
    private static final int REQUEST_CODE_CR = 1;
    ImageView atrasIV, guardarIV, imagenPerfilMenu;
    EditText nombreET;
    RecyclerView diasRV;
    ProgressBar cargando;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);

        //Inicializar instancias.
        Intent intent = getIntent();
        firebaseManager = new FirebaseManager();


        //Obtener la rutina que se ha seleccionado.
        if(intent.hasExtra("rutinaUsuario")) {
            rutinaUsuario = (TablaRutinaUsuario) intent.getSerializableExtra("rutinaUsuario");


        //Si no existe se creará una nueva rutina con 7 días.
        } else {
            rutinaUsuario = new TablaRutinaUsuario();
            ArrayList<TablaDiaRutinaUsuario> semana = new ArrayList<TablaDiaRutinaUsuario>();
            for(int i = 0; i < 7; i++){
                semana.add(new TablaDiaRutinaUsuario(i+1));
            }
            rutinaUsuario.setSemana(semana);
        }


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        atrasIV = findViewById(R.id.atras_iv);
        guardarIV = findViewById(R.id.guardar_iv);

        nombreET = findViewById(R.id.nombre_et);

        diasRV = findViewById(R.id.dias_rv);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners.
        atrasIV.setOnClickListener(this);
        guardarIV.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);


        //Mostrar datos.
        try{
            mostrarImagenPerfil();
            mostrarRutina(rutinaUsuario);

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
            case "atras_iv":
                irA = VerRutinas.class;
                cambiarActivity();
                break;

            case "guardar_iv":
                guardarRutina();
                break;

            case "imagen_casa_menu":
                irA = Inicio.class;
                cambiarActivity();
                break;
            case "imagen_calendario_menu":
                irA = RutinaSemanal.class;
                cambiarActivity();
                break;
            case "imagen_estadisticas_menu":
                irA = Logros.class;
                cambiarActivity();
                break;
            case "imagen_usuario_menu":
                irA = Perfil.class;
                cambiarActivity();
                break;
        }
    }


    // Métodos para mostrar los datos de la rutina.
    private void mostrarRutina(TablaRutinaUsuario rutina){

        if(rutina.getInformacion() != null){
            nombreET.setText(rutina.getInformacion().getNombre());
        }

        mostrarSemana(rutina);
    }


    // Obtenemos los colores de los músculos del usuario.
    private void mostrarSemana(TablaRutinaUsuario rutina){
        try{
            firebaseManager.obtenerMusculosUsuario(CrearRutinas.this, new FirebaseCallbackMusculosUsuario() {
                @Override
                public void onCallback(ArrayList<TablaMusculoUsuario> musculosUsuarios) {
                    HashMap<String, ColoresMusculoUsuario> musculosHM = new HashMap<>();

                    for (TablaMusculoUsuario musculo : musculosUsuarios){
                        musculosHM.put(musculo.getNombre(), new ColoresMusculoUsuario(musculo.getColor_fondo(), musculo.getColor_fuente()));
                    }

                    //Mostramos la semana desde un adaptador.
                    CrearRutinasAdaptador adaptador = new CrearRutinasAdaptador(CrearRutinas.this, rutina, musculosHM);
                    diasRV.setLayoutManager(new LinearLayoutManager(CrearRutinas.this));
                    diasRV.setAdapter(adaptador);
                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al obtener los músculos.");
            ex.printStackTrace();
        }
    }


    //Método para guardar la rutina.
    private void guardarRutina(){
        String nombreRutina = nombreET.getText().toString();
        boolean nueva = false;
        TablaInfoRutinaUsuario infoRutinaUsuario;

        if(nombreRutina.equals("")){
            mostrarToast("Escribe un nombre de rutina");

        }else{
            if (rutinaUsuario.getInformacion() == null){
                nueva = true;
                infoRutinaUsuario = new TablaInfoRutinaUsuario(nombreRutina);
            }else{
                infoRutinaUsuario = rutinaUsuario.getInformacion();
                infoRutinaUsuario.setNombre(nombreRutina);
            }
            rutinaUsuario.setInformacion(infoRutinaUsuario);

            // Si no existe se creará.
            if(nueva){
                crearRutina();

            // Si ya existe se actualizarán sus datos.
            }else{
                actualizarRutina();
            }
        }
    }


    //Método para crear la rutina.
    private void crearRutina(){
        try{
            firebaseManager.agregarRutina(this, rutinaUsuario, new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada) {
                        mostrarToast("Rutina creada correctamente");
                        ObtenerLogro obtenerLogro = new ObtenerLogro();
                        obtenerLogro.obtenerLogro(CrearRutinas.this, "Crear rutina", 1);
                        cambiarActivity(VerRutinas.class);
                    } else {
                        mostrarToast("Error al crear la rutina");
                    }
                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al crear la rutina.");
            ex.printStackTrace();
        }
    }


    //Método para actualizar la rutina.
    private void actualizarRutina(){
        try{
            firebaseManager.actualizarRutina(this, rutinaUsuario, new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada) {
                        mostrarToast("Rutina actualizada correctamente");
                        cambiarActivity(VerRutinas.class);
                    } else {
                        mostrarToast("Error al actualizar la rutina");
                    }
                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al actualizar la rutina.");
            ex.printStackTrace();
        }
    }


    //Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    private void mostrarImagenPerfil(){
        try{
            firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
                @Override
                public void onCallback(TablaPerfil perfil) {
                    if(perfil.getImagen() != null && !perfil.getImagen().equals("")){
                        Imagenes.urlImagenPerfil = perfil.getImagen();
                        Imagenes.mostrarImagenPerfil(CrearRutinas.this, imagenPerfilMenu);
                    }

                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al mostrar la imagen de perfil.");
            ex.printStackTrace();
        }

    }


    // Método para obtener la respuesta del botón que se pulsa en PopupAlerta.java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CR) {
                boolean resultado = data.getBooleanExtra("resultado", false);
                if(resultado){
                    cambiarActivity(irA);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    //Métodos para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        CambiarActivity.cambiar(this, activity);
        finish();
    }
    private void cambiarActivity() {
        Intent intent = new Intent(this, PopupAlerta.class);
        intent.putExtra("titulo", "Descartar cambios");
        intent.putExtra("texto", "¿Desea descartar los cambios no guardados?");
        startActivityForResult(intent, REQUEST_CODE_CR);
    }


    //Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }


}