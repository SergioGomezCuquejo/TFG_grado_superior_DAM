package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yim.R;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PopupMusculos extends AppCompatActivity implements View.OnClickListener {
    ImageView cancelar, guardar;
    EditText musculo;
    LinearLayout color_fondo, color_letras;
    TextView color_fondo_tv, color_letras_tv;
    Button borrar;
    Drawable shape;
    int colorFondo, colorLetras;
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


        //Referencias de las vistas
        cancelar = findViewById(R.id.cancelar);
        guardar = findViewById(R.id.guardar);
        musculo = findViewById(R.id.musculo);
        color_fondo = findViewById(R.id.color_fondo);
        color_fondo_tv = findViewById(R.id.color_fondo_tv);
        borrar = findViewById(R.id.borrar);
        color_letras = findViewById(R.id.color_letras);
        color_letras_tv = findViewById(R.id.color_letras_tv);

        //Listeners
        cancelar.setOnClickListener(this);
        guardar.setOnClickListener(this);
        color_fondo.setOnClickListener(this);
        color_letras.setOnClickListener(this);
        borrar.setOnClickListener(this);


        //Poner fondo verde
        shape = (Drawable) musculo.getBackground();
        shape.setColorFilter(Color.parseColor( "#00c143"), android.graphics.PorterDuff.Mode.SRC);

        colorFondo = Color.parseColor( "#00c143");
        color_fondo_tv.setText("#" + Integer.toHexString(colorFondo).toUpperCase());
        colorLetras = ContextCompat.getColor(PopupMusculos.this, R.color.negro_oscuro);
        color_letras_tv.setText("#" + Integer.toHexString(colorLetras).toUpperCase());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar){
            cambiarActivity( "Descartar cambios no guardados.",
                    "¿Desea descartar los cambios no guardados?");

        } else if (id == R.id.guardar) {
            Toast.makeText(getApplicationContext(), "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.color_fondo) {
            cambiarColor("fondo", colorFondo);

        } else if (id == R.id.color_letras) {
            cambiarColor("letras", colorLetras);

        } else if (id == R.id.borrar) {
            cambiarActivity("Borrar músculo.",
                    "¿Desea eliminar el músculo 'Biceps'?");

        }
    }

    private void cambiarActivity(String titulo, String texto) {
        cambiarAlerta(this, titulo, texto, "ir_a_musculos");
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
                    shape.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC);
                    colorFondo = color;
                    color_fondo_tv.setText("#" + Integer.toHexString(color).toUpperCase());
                } else if (cambiar.equals("letras")) {
                    musculo.setTextColor(color);
                    colorLetras = color;
                    color_letras_tv.setText("#" + Integer.toHexString(color).toUpperCase());
                }
            }
        });
        ambilWarnaDialog.show();
    }
}