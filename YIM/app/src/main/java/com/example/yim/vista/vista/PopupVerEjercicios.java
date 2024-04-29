package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.yim.R;

public class PopupVerEjercicios extends AppCompatActivity implements View.OnClickListener {
    TextView instruciones, informacion;
    ImageView cerrar;
    ViewFlipper viewFlipper;
    int flipperActivo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_ver_ejercicios);

        //Cambiar el tamaño de la pantalla para que sea como un popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.95), (int) (alto * 0.85));


        //Referencias de las vistas
        instruciones = findViewById(R.id.instruciones);
        informacion = findViewById(R.id.informacion);
        cerrar = findViewById(R.id.cerrar);
        viewFlipper = findViewById(R.id.viewFlipper);

        //Listeners
        instruciones.setOnClickListener(this);
        cerrar.setOnClickListener(this);
        informacion.setOnClickListener(this);
        viewFlipper.setOnClickListener(this);

        
        //Poner por defecto la opción de instrucciones
        cambiarColores(instruciones, R.color.fondo_oscuro, R.color.blanco);
        flipperActivo  = 1;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.instruciones && flipperActivo != 1) {
            flipperActivo = 1;
            viewFlipper.showNext();

            cambiarColores(instruciones, R.color.fondo_oscuro, R.color.blanco);
            cambiarColores(informacion, R.color.fondo_clarito, R.color.negro_clarito);

        } else if (id == R.id.informacion && flipperActivo != 2) {
            flipperActivo = 2;
            viewFlipper.showPrevious();

            cambiarColores(informacion, R.color.fondo_oscuro, R.color.blanco);
            cambiarColores(instruciones, R.color.fondo_clarito, R.color.negro_clarito);

        } else if (id == R.id.cerrar) {
            finish();

        }
    }

    public void cambiarColores(View view, @ColorRes int colorFondoRes, @ColorRes int colorLetrasRes){
        Drawable shape;
        TextView textView = (TextView) view;

        // Obtener colores de los recursos
        int colorFondo = ContextCompat.getColor(this, colorFondoRes);
        int colorLetras = ContextCompat.getColor(this, colorLetrasRes);

        if(view.getId() == R.id.instruciones){
            shape = textView.getBackground();
            shape.setColorFilter(colorFondo, android.graphics.PorterDuff.Mode.SRC);
        } else if (view.getId() == R.id.informacion) {
            textView.setBackgroundColor(colorFondo);
        }
        textView.setTextColor(colorLetras);
    }
}