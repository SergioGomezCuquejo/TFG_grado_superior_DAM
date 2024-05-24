package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PopupMusculos extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaMusculoUsuario musculoUsuario;
    ImageView cancelar, guardar;
    LinearLayout color_fondo, color_letras;
    TextView musculoTV, color_fondo_tv, color_letras_tv;
    TextView ejercicosEnRutinaActiva, ejerciciosRealizados, ejerciciosSinRealizar, ejerciciosTotales, ejercicosEnRutinas;
    int colorFondo, colorLetras;
    boolean datosCambiados;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_musculos);

        //Cambiar el tamaño de la pantalla.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.85));


        //Inicializar instancias.
        firebaseManager = new FirebaseManager();
        Intent intent = getIntent();
        musculoUsuario = (TablaMusculoUsuario) intent.getSerializableExtra("musculoUsuario");


        //Referencias de las vistas.
        cancelar = findViewById(R.id.cancelar);
        guardar = findViewById(R.id.guardar_iv);

        ejercicosEnRutinaActiva = findViewById(R.id.ejercicos_en_rutina_activa);
        ejerciciosRealizados = findViewById(R.id.ejercicios_realizados);
        ejerciciosSinRealizar = findViewById(R.id.ejercicios_sin_realizar);
        ejerciciosTotales = findViewById(R.id.ejercicios_totales);
        ejercicosEnRutinas = findViewById(R.id.ejercicos_en_rutinas);

        musculoTV = findViewById(R.id.musculo_tv);
        color_fondo = findViewById(R.id.color_fondo);
        color_fondo_tv = findViewById(R.id.color_fondo_tv);
        color_letras = findViewById(R.id.color_letras);
        color_letras_tv = findViewById(R.id.color_letras_tv);


        //Listeners.
        cancelar.setOnClickListener(this);
        guardar.setOnClickListener(this);
        color_fondo.setOnClickListener(this);
        color_letras.setOnClickListener(this);


        //Mostrar datos.
        try{
            mostrarDatos();
            datosCambiados = false;

        } catch (Exception ex) {
            mostrarToast("Error al mostrar los músculos.");
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {String id = getResources().getResourceEntryName(view.getId());

        switch (id) {
            case "cancelar":
                if(datosCambiados){
                    cambiarActivity();
                }else{
                    finish();
                }
                break;
            case "guardar_iv":

                if(datosCambiados){
                    musculoUsuario.setColor_fondo("#" + Integer.toHexString(colorFondo).toUpperCase());
                    musculoUsuario.setColor_fuente("#" + Integer.toHexString(colorLetras).toUpperCase());

                    actualizarMusculo(musculoUsuario);
                }else{
                    mostrarToast("No hay cambios que guardar");
                }
                break;

            case "color_fondo":
                cambiarColor("fondo", colorFondo);
                break;

            case "color_letras":
                cambiarColor("letras", colorLetras);
                break;
        }
    }


    //Método para mostrar toda la información del músculo.
    private void mostrarDatos(){
        musculoTV.setText(musculoUsuario.getNombre());
        musculoTV.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(musculoUsuario.getColor_fondo())));
        musculoTV.setTextColor(Color.parseColor(musculoUsuario.getColor_fuente()));

        color_fondo_tv.setText(musculoUsuario.getColor_fondo());
        colorFondo = Color.parseColor(musculoUsuario.getColor_fondo());
        color_fondo_tv.setTextColor(colorFondo);

        color_letras_tv.setText(musculoUsuario.getColor_fuente());
        colorLetras = Color.parseColor(musculoUsuario.getColor_fuente());
        color_letras_tv.setTextColor(colorLetras);

        ejercicosEnRutinaActiva.setText(String.valueOf(musculoUsuario.getEjercicios_en_rutinaActual()));
        ejerciciosRealizados.setText(String.valueOf(musculoUsuario.getEjercicios_realizados()));
        ejerciciosSinRealizar.setText(String.valueOf(musculoUsuario.getEjercicios_sin_realizar()));
        ejerciciosTotales.setText(String.valueOf(musculoUsuario.getEjercicios_totales()));
        ejercicosEnRutinas.setText(String.valueOf(musculoUsuario.getEjercicios_en_rutinas()));

    }


    //Método para actualizar el músculo
    private void actualizarMusculo(TablaMusculoUsuario musculo){
        try{
            firebaseManager.actualizarMusculoUsuario(this, musculo, new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if(accionRealizada){
                        mostrarToast("Datos guardados correctamente");
                        finish();
                    }else{
                        mostrarToast("No se ha podido actualizar el músculo correctamente");
                    }

                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al actualizar el músculo.");
            ex.printStackTrace();
        }
    }



    //Método para cambiar el color de fondo y el de las letras.
    @SuppressLint("ResourceType")
    private void cambiarColor(String cambiar, int defaultColor){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                if(cambiar.equals("fondo")){
                    musculoTV.setBackgroundTintList(ColorStateList.valueOf(color));
                    colorFondo = color;
                    color_fondo_tv.setText("#" + Integer.toHexString(color).toUpperCase());
                    color_fondo_tv.setTextColor(color);
                } else if (cambiar.equals("letras")) {
                    musculoTV.setTextColor(color);
                    colorLetras = color;
                    color_letras_tv.setText("#" + Integer.toHexString(color).toUpperCase());
                    color_letras_tv.setTextColor(color);
                }
                datosCambiados = true;
            }
        });
        ambilWarnaDialog.show();
    }



    //Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity() {
        CambiarActivity.cambiar(this, "Descartar cambios.", "¿Desea descartar los cambios no guardados?", "ir_a_musculos");
    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}