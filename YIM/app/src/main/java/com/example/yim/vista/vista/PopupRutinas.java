package com.example.yim.vista.vista;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaInfoRutinaUsuario;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.google.android.material.imageview.ShapeableImageView;

public class PopupRutinas extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private TablaRutinaUsuario rutinaUsuario;
    ImageView cancelar, editar;
    ShapeableImageView imagen;
    LinearLayout activo_ll;
    SwitchCompat activo;
    TextView diasDescansados, diasDeDescanso, diasTotales, diasCompletados, musculosTotales, musculosActivos,
            ejerciciosSinRealizar, ejerciciosRealizados, vecesActivada, vecesCompletadas;
    Button borrar;
    boolean primeraVez;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_rutinas);

        //Cambiar el tamaño de la pantalla.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.85));


        //Inicializar instancias.
        Intent intent = getIntent();
        primeraVez = true;


        //Obtener la rutina que se ha seleccionado.
        if(intent.hasExtra("diaRutinaActiva")) {
            rutinaUsuario = (TablaRutinaUsuario) intent.getSerializableExtra("rutinaUsuario");
        }else {
            mostrarToast("Errror al obtener la rutina");
            finish();
        }


        //Referencias de las vistas.
        cancelar = findViewById(R.id.cancelar);
        editar = findViewById(R.id.editar);

        imagen = findViewById(R.id.imagen);

        activo_ll = findViewById(R.id.activo_ll);
        activo = findViewById(R.id.activo);

        diasDescansados = findViewById(R.id.dias_descansados);
        diasDeDescanso = findViewById(R.id.dias_de_descanso);
        diasTotales = findViewById(R.id.dias_totales);
        diasCompletados = findViewById(R.id.dias_completados);
        musculosTotales = findViewById(R.id.musculos_totales);
        musculosActivos = findViewById(R.id.musculos_activos);
        ejerciciosSinRealizar = findViewById(R.id.ejercicios_sin_realizar);
        ejerciciosRealizados = findViewById(R.id.ejercicios_realizados);
        vecesActivada = findViewById(R.id.veces_activada);
        vecesCompletadas = findViewById(R.id.veces_completadas);

        borrar = findViewById(R.id.borrar);


        //Listeners.
        cancelar.setOnClickListener(this);
        editar.setOnClickListener(this);
        borrar.setOnClickListener(this);

        activo_ll.setOnClickListener(this);

        activo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estaActivo) {
                cambiarActivo(estaActivo);
                if(!primeraVez){

                    //TODO
                    if(activo.isChecked() && !rutinaUsuario.getInformacion().isActivo()){
                        cambiarActivity( "¿Activar rutina?", "Al activar la rutina se desactivará la que ya esté activa y se reiniciarán los días de la rutina semanal.", "activar");

                    } else if (!activo.isChecked() && rutinaUsuario.getInformacion().isActivo()){
                        cambiarActivity("¿Desactivar rutina?", "Al desactivar la rutina se reiniciarán los días de la rutina semanal.", "desactivar");
                    }
                }else {
                    primeraVez = false;
                }
            }
        });

        //Mostrar datos
        try{
            mostrarInfo();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar los datos de la rutina.");
            ex.printStackTrace();
        }
        mostrarInfo();
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "cancelar":
                finish();
                break;

            case "editar":
                cambiarActivity(CrearRutinas.class, rutinaUsuario);
                break;

            case "borrar"://todo
                CambiarActivity.cambiar(this, "Borrar rutina.",
                        "¿Desea eliminar la rutina '" + rutinaUsuario.getInformacion().getNombre() + "'?", "ID" + rutinaUsuario.getID() + "RU");
                break;
        }
    }

    //Método para cambiar el botón en marcado o desmarcado.
    @SuppressLint("UseCompatLoadingForColorStateLists")
    public void cambiarActivo(boolean estaActivo){
        if(estaActivo){
            activo.setBackgroundResource(R.drawable._style_rectangulo_verde__borde_blanco);
            activo.setThumbTintList(getResources().getColorStateList(R.color.fondo_clarito));

        }else{
            activo.setBackgroundResource(R.drawable._style_rectangulo_fondo_clarito__borde_blanco_50);
            activo.setThumbTintList(getResources().getColorStateList(R.color.gris));
        }
    }


    //Método para mostrar los datos de la rutina.
    private void mostrarInfo(){
        TablaInfoRutinaUsuario infoRutina = rutinaUsuario.getInformacion();

        activo.setChecked(infoRutina.isActivo());
        cambiarActivo(activo.isChecked());

        imagen.setImageResource(R.drawable.pierna);

        diasDescansados.setText(String.valueOf(infoRutina.getDias_descansados()));
        diasDeDescanso.setText(String.valueOf(infoRutina.getDias_descanso()));
        diasTotales.setText(String.valueOf(infoRutina.getDias_totales()));
        diasCompletados.setText(String.valueOf(infoRutina.getDias_completados()));
        musculosTotales.setText(String.valueOf(infoRutina.getMusculos_totales()));
        musculosActivos.setText(String.valueOf(infoRutina.getMusculos_activos()));
        ejerciciosSinRealizar.setText(String.valueOf(infoRutina.getEjercicios_sin_realizar()));
        ejerciciosRealizados.setText(String.valueOf(infoRutina.getEjercicios_realizados()));
        vecesActivada.setText(String.valueOf(infoRutina.getVeces_activada()));
        vecesCompletadas.setText(String.valueOf(infoRutina.getVeces_completada()));
    }


    //Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity, TablaRutinaUsuario rutinaUsuario) {
        CambiarActivity.cambiar(this, activity, rutinaUsuario);
    }
    private void cambiarActivity(String titulo, String texto, String accion) {
        CambiarActivity.cambiar(this, titulo, texto, "ir_a_ver_rutinas", rutinaUsuario, accion);
    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }

}