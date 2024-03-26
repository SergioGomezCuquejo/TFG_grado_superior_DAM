package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PopupMusculos extends AppCompatActivity {
    EditText musculo;
    LinearLayout colorFondo, colorLetras;
    int defaultColor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_musculos);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.85));


        musculo = findViewById(R.id.musculo);
        colorFondo = findViewById(R.id.colorFondo);
        colorLetras = findViewById(R.id.colorLetras);

        defaultColor = ContextCompat.getColor(PopupMusculos.this, R.color.azul);

        colorFondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPaleta();
            }
        });
        colorLetras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPaleta();
            }
        });
    }

    public void abrirPaleta(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                musculo.setBackgroundColor(defaultColor);
                musculo.setTextColor(defaultColor);
            }
        });
        ambilWarnaDialog.show();
    }
}