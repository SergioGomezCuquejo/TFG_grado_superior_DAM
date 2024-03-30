package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yim.R;

public class PopupCrearEjercicios extends AppCompatActivity {
    TextView imagen_ejercicio;
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
        ventana.setLayout((int)(ancho), (int) (alto * 0.85));
        ventana.setGravity(Gravity.BOTTOM);


        //Referencias de las vistas
        nombre_et = findViewById(R.id.nombre_et);
        imagen_ejercicio = findViewById(R.id.imagen_ejercicio);

        //Listeners

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
}