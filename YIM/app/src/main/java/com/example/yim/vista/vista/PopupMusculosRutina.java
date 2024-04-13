package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yim.R;

public class PopupMusculosRutina extends AppCompatActivity implements View.OnClickListener {
    ImageView cancelar;
    TextView musculo1, musculo2, musculo_elegido_1, musculo_elegido_2;
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
        musculo1 = findViewById(R.id.musculo1);
        musculo2 = findViewById(R.id.musculo2);
        musculo_elegido_1 = findViewById(R.id.musculo_elegido_1);
        musculo_elegido_2 = findViewById(R.id.musculo_elegido_2);

        //Listeners
        cancelar.setOnClickListener(this);

        //Poner color al fondo y letras de los músculos
        cambiarColores(musculo1, "#233284", "white");
        cambiarColores(musculo_elegido_1, "#233284", "white");
        cambiarColores(musculo2, "#00c143", "black");
        cambiarColores(musculo_elegido_2, "#00c143", "black");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar) {
            finish();

        }
    }

    public void cambiarColores(View view, String colorFondo, String colorLetras){
        TextView textView = (TextView) view;
        Drawable shape = (Drawable) textView.getBackground();

        shape.setColorFilter(Color.parseColor(colorFondo), android.graphics.PorterDuff.Mode.SRC);
        textView.setTextColor(Color.parseColor(colorLetras));

        if (textView.getText().equals("_DESCANSO_")) {
            textView.setTypeface(null, Typeface.ITALIC);
        }
    }
}