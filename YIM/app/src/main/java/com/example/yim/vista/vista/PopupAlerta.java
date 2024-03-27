package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yim.R;

public class PopupAlerta extends AppCompatActivity implements View.OnClickListener {
    TextView titulo_tv, texto_tv;
    Button cancelar_btn, aceptar_btn;
    String titulo, texto, aceptar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_alerta);

        //Creación de variables.
        DisplayMetrics medidasVentana;
        int ancho, alto;
        Intent intent;


        //Cambiar el tamaño de la pantalla para que sea como un popup.
        medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        ancho = medidasVentana.widthPixels;
        alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.80), (int) (alto * 0.30));


        //Obtener datos de la anterior activity.
        intent = getIntent();
        if(intent != null) {
            titulo = intent.getStringExtra("titulo");
            texto = intent.getStringExtra("texto");
            aceptar = intent.getStringExtra("aceptar");

        }else{
            titulo = "OOPS.";
            texto = "Ha habido un problema, intentelo de nuevo más tarde.";
            aceptar = "";
        }


        //Referencias de las vistas.
        cancelar_btn = findViewById(R.id.cancelar);
        aceptar_btn = findViewById(R.id.aceptar);
        titulo_tv = findViewById(R.id.titulo);
        texto_tv = findViewById(R.id.texto);

        //Listeners.
        cancelar_btn.setOnClickListener(this);
        aceptar_btn.setOnClickListener(this);


        //Cambiar los textos por los obtenidos.
        titulo_tv.setText(titulo);
        texto_tv.setText(texto);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar){
            finish();

        } else if (id == R.id.aceptar) {
            if(titulo.equals("OOPS.")){
                finish();
            }else{
                switch (aceptar){
                    case "ir_a_musculos":
                        cambiarActivity(Musculos.class);
                        break;
                    default:
                        finish();
                        break;
                }
            }

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}