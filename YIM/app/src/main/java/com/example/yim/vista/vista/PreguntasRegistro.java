package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yim.R;

public class PreguntasRegistro extends AppCompatActivity implements View.OnClickListener {
    //TODO solucionar el que no se pueda marcar opciones al darle a omitir

    TextView omitir_comencemos, omitir_preguntas, siguiente_comencemos, siguiente_preguntas, siguiente_empezar;
    LinearLayout linearlayout_comencemos, linearlayout_preguntas, linearlayout_empezar;
    CardView cardview_omitir;
    Button cancelar, omitir_fondo, continuar;
    RadioGroup respuestas;
    View view;
    RadioButton radioButtonSeleccionado, radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_registro);

        //Referencias de las vistas
        cardview_omitir = findViewById(R.id.cardview_omitir);

        omitir_comencemos = findViewById(R.id.omitir_comencemos);
        omitir_preguntas = findViewById(R.id.omitir_preguntas);

        cancelar = findViewById(R.id.cancelar);
        omitir_fondo = findViewById(R.id.omitir_fondo);

        linearlayout_comencemos = findViewById(R.id.linearlayout_comencemos);
        linearlayout_preguntas = findViewById(R.id.linearlayout_preguntas);
        linearlayout_empezar = findViewById(R.id.linearlayout_empezar);

        siguiente_comencemos = findViewById(R.id.siguiente_comencemos);
        siguiente_preguntas = findViewById(R.id.siguiente_preguntas);

        continuar = findViewById(R.id.continuar);
        siguiente_empezar = findViewById(R.id.siguiente_empezar);

        respuestas = findViewById(R.id.respuestas);


        //Listeners
        omitir_comencemos.setOnClickListener(this);
        omitir_preguntas.setOnClickListener(this);

        cancelar.setOnClickListener(this);
        omitir_fondo.setOnClickListener(this);

        siguiente_comencemos.setOnClickListener(this);
        siguiente_preguntas.setOnClickListener(this);

        continuar.setOnClickListener(this);
        siguiente_empezar.setOnClickListener(this);

        respuestas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                cambiarEstiloRadioButton(group, checkedId);
            }
        });
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.omitir_comencemos || id == R.id.omitir_preguntas){
            visibilidad(cardview_omitir, true);

        }else if (id == R.id.cancelar || id == R.id.omitir_fondo) {
            visibilidad(cardview_omitir, false);

        }else if (id == R.id.siguiente_comencemos) {
            visibilidad(linearlayout_comencemos, false);
            visibilidad(linearlayout_preguntas, true);

        }else if (id == R.id.siguiente_preguntas) {
            visibilidad(linearlayout_preguntas, false);
            visibilidad(linearlayout_empezar, true);

        }else if (id == R.id.continuar || id == R.id.siguiente_empezar) {
            cambiarActivity(Inicio.class);
        }
    }

    private void visibilidad(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    //Estilos radioButtons al ser seleccionados (se ponen en negrita si se selecciona, o normal si no esta seleccionado)
    private void cambiarEstiloRadioButton(RadioGroup group, int checkedId) {
        radioButtonSeleccionado = findViewById(checkedId);

        for (int i = 0; i < group.getChildCount(); i++) {
            view = group.getChildAt(i);

            if (view instanceof RadioButton) {
                radioButton = (RadioButton) view;

                if (radioButton == radioButtonSeleccionado) { //RadioButton seleccionado
                    radioButton.setTypeface(null, Typeface.BOLD);

                } else { //RadioButton no seleccionados
                    radioButton.setTypeface(null, Typeface.NORMAL);
                }
            }
        }
    }
}