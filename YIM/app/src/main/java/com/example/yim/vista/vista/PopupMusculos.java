package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PopupMusculos extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    Intent intent;
    TablaMusculoUsuario musculoUsuario;
    ImageView cancelar, guardar;
    EditText musculo;
    LinearLayout color_fondo, color_letras;
    TextView color_fondo_tv, color_letras_tv;
    //Button borrar;
    int colorFondo, colorLetras;
    TextView ejercicosEnRutinaActiva, ejerciciosRealizados, ejerciciosSinRealizar, ejerciciosTotales, ejercicosEnRutinas;
    boolean datosCambiados;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_musculos);

        //Cambiar el tamaño de la pantalla para que sea como un popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.85));

        firebaseManager = new FirebaseManager();

        intent = getIntent();
        musculoUsuario = (TablaMusculoUsuario) intent.getSerializableExtra("musculoUsuario");


        //Referencias de las vistas
        cancelar = findViewById(R.id.cancelar);
        guardar = findViewById(R.id.guardar_iv);

        ejercicosEnRutinaActiva = findViewById(R.id.ejercicos_en_rutina_activa);
        ejerciciosRealizados = findViewById(R.id.ejercicios_realizados);
        ejerciciosSinRealizar = findViewById(R.id.ejercicios_sin_realizar);
        ejerciciosTotales = findViewById(R.id.ejercicios_totales);
        ejercicosEnRutinas = findViewById(R.id.ejercicos_en_rutinas);

        musculo = findViewById(R.id.musculo);
        color_fondo = findViewById(R.id.color_fondo);
        color_fondo_tv = findViewById(R.id.color_fondo_tv);
        //borrar = findViewById(R.id.borrar);
        color_letras = findViewById(R.id.color_letras);
        color_letras_tv = findViewById(R.id.color_letras_tv);


        //Listeners
        cancelar.setOnClickListener(this);
        guardar.setOnClickListener(this);
        color_fondo.setOnClickListener(this);
        color_letras.setOnClickListener(this);
        //borrar.setOnClickListener(this);


        mostrarDatos();
        datosCambiados = false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar){
            if(datosCambiados){
                cambiarActivity( "Descartar cambios.",
                        "¿Desea descartar los cambios no guardados?");
            }else{
                finish();
            }

        } else if (id == R.id.guardar_iv) {

            if(datosCambiados){
                musculoUsuario.setColor_fondo("#" + Integer.toHexString(colorFondo).toUpperCase());
                musculoUsuario.setColor_fuente("#" + Integer.toHexString(colorLetras).toUpperCase());

                if(firebaseManager.actualizarColoresMusculosUsuario(this, musculoUsuario.getID(),
                        musculoUsuario.getColor_fondo(), musculoUsuario.getColor_fuente())){
                    MostratToast.mostrarToast(this, "Datos guardados correctamente.");
                    finish();
                }else{
                    MostratToast.mostrarToast(this, "error");
                }
            }else{
                MostratToast.mostrarToast(this, "No hay cambios que guardar");
            }

        } else if (id == R.id.color_fondo) {
            cambiarColor("fondo", colorFondo);

        } else if (id == R.id.color_letras) {
            cambiarColor("letras", colorLetras);

        } /*else if (id == R.id.borrar) {
            cambiarActivity("Borrar músculo.",
                    "¿Desea eliminar el músculo 'Biceps'?");

        }*/
    }

    private void cambiarActivity(String titulo, String texto) {
        CambiarActivity.cambiar(this, titulo, texto, "ir_a_musculos");
    }


    @SuppressLint("ResourceType")
    public void cambiarColor(String cambiar, int defaultColor){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                if(cambiar.equals("fondo")){
                    musculo.setBackgroundTintList(ColorStateList.valueOf(color));
                    colorFondo = color;
                    color_fondo_tv.setText("#" + Integer.toHexString(color).toUpperCase());
                } else if (cambiar.equals("letras")) {
                    musculo.setTextColor(color);
                    colorLetras = color;
                    color_letras_tv.setText("#" + Integer.toHexString(color).toUpperCase());
                }
                datosCambiados = true;
            }
        });
        ambilWarnaDialog.show();
    }

    private void mostrarDatos(){
        musculo.setText(musculoUsuario.getNombre());
        musculo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(musculoUsuario.getColor_fondo())));
        musculo.setTextColor(Color.parseColor(musculoUsuario.getColor_fuente()));

        color_fondo_tv.setText(musculoUsuario.getColor_fondo());
        colorFondo = Color.parseColor(musculoUsuario.getColor_fondo());
        color_letras_tv.setText(musculoUsuario.getColor_fuente());
        colorLetras = Color.parseColor(musculoUsuario.getColor_fuente());

        ejercicosEnRutinaActiva.setText(String.valueOf(musculoUsuario.getEjercicios_en_rutinaActual()));
        ejerciciosRealizados.setText(String.valueOf(musculoUsuario.getEjercicios_realizados()));
        ejerciciosSinRealizar.setText(String.valueOf(musculoUsuario.getEjercicios_sin_realizar()));
        ejerciciosTotales.setText(String.valueOf(musculoUsuario.getEjercicios_totales()));
        ejercicosEnRutinas.setText(String.valueOf(musculoUsuario.getEjercicios_en_rutinas()));

    }
}