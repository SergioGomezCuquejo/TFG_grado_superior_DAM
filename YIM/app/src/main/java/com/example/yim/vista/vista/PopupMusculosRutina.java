package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.example.yim.R;

public class PopupMusculosRutina extends AppCompatActivity implements View.OnClickListener {
    ImageView cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_musculos_rutina);

        //Cambiar el tamaño de la pantalla para que sea como un popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.95), (int) (alto * 0.65));

        //Referencias de las vistas
        cancelar = findViewById(R.id.cancelar);

        //Listeners
        cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar) {
            finish();

        }
    }
}