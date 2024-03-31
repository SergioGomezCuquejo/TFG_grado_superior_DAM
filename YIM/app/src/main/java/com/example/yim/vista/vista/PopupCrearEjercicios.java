package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yim.R;

public class PopupCrearEjercicios extends AppCompatActivity implements View.OnClickListener {
    ImageView cancelar, guardar;
    FrameLayout imagen_rutina;
    TextView imagen_ejercicio, musculos_tv, musculos_et;
    EditText nombre_et;
    String inicialesNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_crear_ejercicios);

        //Cambiar el tamaño de la pantalla para que sea como un popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        Window ventana = getWindow();
        ventana.setLayout((int)(ancho), (int) (alto * 0.90));
        ventana.setGravity(Gravity.BOTTOM);


        //Referencias de las vistas
        cancelar = findViewById(R.id.cancelar);
        guardar = findViewById(R.id.guardar);

        imagen_rutina = findViewById(R.id.imagen_rutina);
        imagen_ejercicio = findViewById(R.id.imagen_ejercicio);

        nombre_et = findViewById(R.id.nombre_et);

        musculos_tv = findViewById(R.id.musculos_tv);
        musculos_et = findViewById(R.id.musculos_et);


        //Listeners
        cancelar.setOnClickListener(this);
        guardar.setOnClickListener(this);

        imagen_rutina.setOnClickListener(this);

        musculos_tv.setOnClickListener(this);
        musculos_et.setOnClickListener(this);

        nombre_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            //Actualizar las 2 primeras iniciales de la referencia del ejercicio.
            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() <= 0){
                    inicialesNombre = "NOMBRE";

                } else if (s.toString().length() == 1) {
                    inicialesNombre = s.toString();

                } else {
                    inicialesNombre = s.toString().substring(0,2);

                }
                imagen_ejercicio.setText( inicialesNombre.toUpperCase() );

            }
        });
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar){
            cambiarActivity( "Descartar cambios no guardados.",
                    "¿Desea descartar los cambios no guardados?");

        } else if (id == R.id.guardar) {
            Toast.makeText(getApplicationContext(), "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.musculos_tv || id == R.id.musculos_et) {
            Toast.makeText(getApplicationContext(), "Músculos.", Toast.LENGTH_SHORT).show();

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    private void cambiarActivity(String titulo, String texto) {
        cambiarAlerta(this, titulo, texto, "ir_a_ejercicios");
    }
}