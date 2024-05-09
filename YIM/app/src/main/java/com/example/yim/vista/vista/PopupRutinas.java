package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

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
import com.example.yim.modelo.tablas.TablaInfoRutinasUsuario;
import com.example.yim.modelo.tablas.TablaRutinasUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class PopupRutinas extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    TablaRutinasUsuario rutinaUsuario;
    ImageView cancelar, editar;
    ShapeableImageView imagen;
    LinearLayout activo_ll;
    SwitchCompat activo;
    TextView diasDescansados, diasDeDescanso, diasTotales, diasCompletados, musculosTotales, musculosActivos,
            ejerciciosSinRealizar, ejerciciosRealizados, vecesActivada, vecesCompletadas;
    Button borrar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_rutinas);

        //Cambiar el tamaño de la pantalla para que sea como un popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.85));

        intent = getIntent();
        rutinaUsuario = (TablaRutinasUsuario) intent.getSerializableExtra("rutinaUsuario");

        //Referencias de las vistas
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

        //Listeners
        cancelar.setOnClickListener(this);
        editar.setOnClickListener(this);
        borrar.setOnClickListener(this);

        activo_ll.setOnClickListener(this);
        activo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estaActivo) {
                cambiarActivo(estaActivo);
            }
        });

        mostrarInfo();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar) {
            cambiarActivity( "Descartar cambios no guardados.",
                    "¿Desea descartar los cambios no guardados?");

        } else if (id == R.id.editar) {
            CambiarActivity.cambiar(this, CrearRutinas.class, rutinaUsuario);

        } else if (id == R.id.activo_ll) {
            activo.setChecked(!activo.isChecked());
            cambiarActivo(activo.isChecked());

        } else if (id == R.id.borrar) {
            cambiarActivity("Borrar rutina.",
                    "¿Desea eliminar la rutina 'Nombre de la rutina'?");

        }
    }

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

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
    private void cambiarActivity(String titulo, String texto) {
        cambiarAlerta(this, titulo, texto, "ir_a_ver_rutinas");
    }

    private void mostrarInfo(){
        TablaInfoRutinasUsuario infoRutina = rutinaUsuario.getInformacion();

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
}