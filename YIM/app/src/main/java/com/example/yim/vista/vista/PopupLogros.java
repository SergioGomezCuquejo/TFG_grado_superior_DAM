package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.yim.R;

public class PopupLogros extends AppCompatActivity {
    View mascara;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_logros);

        //Cambiar el tamaño de la pantalla para que sea como un popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.67));

        //Referencias de las vistas
        mascara = findViewById(R.id.mascara);


        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mascara.getLayoutParams();


        int progreso = (int) ((8.0/50) * 200);
        layoutParams.width = convertirDpAPixeles(200 - progreso);
        layoutParams.setMarginStart( convertirDpAPixeles(progreso) );

        mascara.setLayoutParams(layoutParams);
    }

    public int convertirDpAPixeles(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}