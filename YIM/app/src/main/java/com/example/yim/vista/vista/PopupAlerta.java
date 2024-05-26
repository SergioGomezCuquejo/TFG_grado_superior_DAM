package com.example.yim.vista.vista;

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

    // Variables de instancias.
    TextView tituloTV, textoTV;
    Button cancelar, aceptar;
    String titulo, texto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_alerta);


        // Cambiar el tamaño de la pantalla.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.80), (int) (alto * 0.30));


        // Inicializar instancias.
        Intent intent = getIntent();


        // Obtener los datos del anterior acitivity.
        if(intent.hasExtra("titulo")) {
            titulo = intent.getStringExtra("titulo");
            texto = intent.getStringExtra("texto");
        }


        // Referencias de las vistas.
        tituloTV = findViewById(R.id.titulo_tv);
        textoTV= findViewById(R.id.texto_tv);

        cancelar = findViewById(R.id.cancelar);
        aceptar = findViewById(R.id.aceptar);


        // Listeners.
        cancelar.setOnClickListener(this);
        aceptar.setOnClickListener(this);


        // Mostrar datos.
        if(titulo != null){
            mostrarTextos();
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "cancelar":
                devolverResultado(false);
                break;

            case "aceptar":
                devolverResultado(true);
                break;
        }
    }


    // Método para el mostrado de datos.
    private void mostrarTextos(){
        tituloTV.setText(titulo);
        textoTV.setText(texto);
    }


    // Método para la devolución del botón seleccionado.
    private void devolverResultado(boolean resultado){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("resultado", resultado);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}