package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yim.R;

public class PopupRutinas extends AppCompatActivity implements View.OnClickListener {
    ImageView cancelar, editar;
    LinearLayout activo_ll;
    SwitchCompat activo;
    Button borrar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_rutinas);

        //Cambiar el tamaño de la pantalla para que sea como un popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.85));


        //Referencias de las vistas
        cancelar = findViewById(R.id.cancelar);
        editar = findViewById(R.id.editar);
        activo_ll = findViewById(R.id.activo_ll);
        activo = findViewById(R.id.activo);
        borrar = findViewById(R.id.borrar);

        //Listeners
        cancelar.setOnClickListener(this);
        editar.setOnClickListener(this);
        borrar.setOnClickListener(this);

        activo_ll.setOnClickListener(this);
        activo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estaActivo) {
                cambiarActivo(estaActivo);
            }
        });

        activo.setChecked(true); //Poner en activo
        cambiarActivo(activo.isChecked());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar) {
            cambiarActivity( "Descartar cambios no guardados.",
                    "¿Desea descartar los cambios no guardados?");

        } else if (id == R.id.editar) {
            cambiarActivity(CrearRutinas.class);

        } else if (id == R.id.activo_ll) {
            activo.setChecked(!activo.isChecked());
            cambiarActivo(activo.isChecked());

        } else if (id == R.id.borrar) {
            cambiarActivity("Borrar rutina.",
                    "¿Desea eliminar la rutina 'Nombre de la rutina'?");

        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    public void cambiarActivo(boolean estaActivo){
        if(estaActivo){
            activo.setBackgroundResource(R.drawable._style_rectangulo_verde__borde_blanco);
            activo.setThumbTintList(getResources().getColorStateList(R.color.fondo_clarito));

        }else{
            activo.setBackgroundResource(R.drawable._style_rectangulo_fondo_clarito__borde_blanco_50);
            activo.setThumbTintList(getResources().getColorStateList(R.color.gris));
        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
    private void cambiarActivity(String titulo, String texto) {
        cambiarAlerta(this, titulo, texto, "ir_a_ver_rutinas");
    }
}