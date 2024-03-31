package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.yim.R;

import java.util.ArrayList;
import java.util.HashSet;

public class PopupCrearEjercicios extends AppCompatActivity implements View.OnClickListener {
    ViewFlipper viewFlipper;
    ImageView cancelar, guardar, atras;
    FrameLayout imagen_rutina;
    TextView imagen_ejercicio, musculos_tv, musculos_et;
    EditText nombre_et;
    String inicialesNombre;
    CheckBox todo_el_cuerpo, tren_superior, espalda, biceps, cuadriceps;
    HashSet<String> musculos = new HashSet<>();
    StringBuilder musculosString;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_crear_ejercicios);

        //Creación de variables.
        DisplayMetrics medidasVentana;
        int ancho, alto;
        Intent intent;
        ArrayList<String> musculosArray;
        StringBuilder musculosString;

        //Cambiar el tamaño de la pantalla para que sea como un popup
        medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        ancho = medidasVentana.widthPixels;
        alto = medidasVentana.heightPixels;

        Window ventana = getWindow();
        ventana.setLayout((int)(ancho), (int) (alto * 0.90));
        ventana.setGravity(Gravity.BOTTOM);


        //Referencias de las vistas
        viewFlipper = findViewById(R.id.viewFlipper);

        cancelar = findViewById(R.id.cancelar);
        guardar = findViewById(R.id.guardar);

        imagen_rutina = findViewById(R.id.imagen_rutina);
        imagen_ejercicio = findViewById(R.id.imagen_ejercicio);

        nombre_et = findViewById(R.id.nombre_et);

        musculos_tv = findViewById(R.id.musculos_tv);
        musculos_et = findViewById(R.id.musculos_et);

        atras = findViewById(R.id.atras);


        //Listeners
        cancelar.setOnClickListener(this);
        guardar.setOnClickListener(this);

        imagen_rutina.setOnClickListener(this);

        musculos_tv.setOnClickListener(this);
        musculos_et.setOnClickListener(this);

        atras.setOnClickListener(this);

        todo_el_cuerpo = findViewById(R.id.todo_el_cuerpo);
        tren_superior = findViewById(R.id.tren_superior);
        espalda = findViewById(R.id.espalda);
        biceps = findViewById(R.id.biceps);
        cuadriceps = findViewById(R.id.cuadriceps);



        //Actualizar las 2 primeras iniciales de la referencia del ejercicio.
        nombre_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

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


        //Cambiar los checkBox al marcarse
        CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                String musculo = obtenerMusculo(id);

                if (isChecked) {
                    musculos.add(musculo);

                    cambiarColores((TextView) buttonView, getResources().getColor(R.color.blanco));
                    if (id == R.id.todo_el_cuerpo) {
                        tren_superior.setChecked(false);
                        espalda.setChecked(false);
                        biceps.setChecked(false);
                        cuadriceps.setChecked(false);

                    } else{
                        todo_el_cuerpo.setChecked(false);

                        if (id == R.id.tren_superior) {
                            espalda.setChecked(false);
                            biceps.setChecked(false);

                        } else if (id == R.id.biceps || id == R.id.espalda) {
                            tren_superior.setChecked(false);
                        }
                    }
                } else {
                    musculos.remove(musculo);

                    cambiarColores((TextView) buttonView, getResources().getColor(R.color.negro_oscuro));
                }

            }
        };

        todo_el_cuerpo.setOnCheckedChangeListener(checkBoxListener);
        tren_superior.setOnCheckedChangeListener(checkBoxListener);
        espalda.setOnCheckedChangeListener(checkBoxListener);
        biceps.setOnCheckedChangeListener(checkBoxListener);
        cuadriceps.setOnCheckedChangeListener(checkBoxListener);


    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar){
            cambiarActivity( "Descartar cambios no guardados.",
                    "¿Desea descartar los cambios no guardados?");

        } else if (id == R.id.guardar) {
            Toast.makeText(getApplicationContext(), "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.musculos_tv || id == R.id.musculos_et) {
            viewFlipper.showNext();

        } else if (id == R.id.atras) {
            if (musculos != null && !musculos.isEmpty()) {
                musculosString = new StringBuilder();
                for (String musculo : musculos) {
                    musculosString.append(musculo).append(", ");
                }
                musculos_et.setText(musculosString.substring(0, musculosString.length() - 2) + ".");
            } else {
                musculos_et.setText("Selecciona los músculos");
            }
            viewFlipper.showPrevious();

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    private void cambiarActivity(String titulo, String texto) {
        cambiarAlerta(this, titulo, texto, "ir_a_ejercicios");
    }

    public void cambiarColores(View view, int color){
        ((TextView) view).setTextColor(color);
    }

    private String obtenerMusculo(int id) {
        String musculo = "";
        if(id == R.id.todo_el_cuerpo){
            musculo = "Todo el cuerpo";
        } else if (id == R.id.tren_superior){
            musculo = "Tren superior";
        } else if (id == R.id.espalda) {
            musculo = "Espalda";
        } else if (id == R.id.biceps) {
            musculo = "Biceps";
        } else if (id == R.id.cuadriceps) {
            musculo = "Cuadriceps";
        }

        return musculo;
    }
}