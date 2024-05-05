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
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.controlador.ValidarDatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class PopupCrearEjercicios extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    ViewFlipper viewFlipper;
    ImageView cancelar, guardar, atras;
    FrameLayout imagenFL;
    TextView imagen, musculosTV;
    EditText nombreET, descansoET, seriesET, repeticionesET, notasET;
    String inicialesNombre;
    CheckBox todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps;
    HashSet<String> musculos = new HashSet<>();
    StringBuilder musculosString;
    TablaEjerciciosUsuario ejercicioUsuario;
    String imagenEjercicio, nombreEjercicio, notasEjercicio, musculosEjercicio;
    int descansoEjercicio, seriesEjercicio, repeticionesEjercicio;
    ArrayList<String> musculosArray;
    boolean nombreVacio, musculosVacios, descansoVacio, seriesVacias, repeticionesVacias;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_crear_ejercicios);

        firebaseManager = new FirebaseManager();

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

        imagenFL = findViewById(R.id.imagen_fl);
        imagen = findViewById(R.id.imagen_ejercicio);

        nombreET = findViewById(R.id.nombre_et);
        descansoET = findViewById(R.id.descanso_et);
        seriesET = findViewById(R.id.series_et);
        repeticionesET = findViewById(R.id.repeticiones_et);
        notasET = findViewById(R.id.notas_et);

        musculosTV = findViewById(R.id.musculos_tv);

        atras = findViewById(R.id.atras);


        //Listeners
        cancelar.setOnClickListener(this);
        guardar.setOnClickListener(this);

        imagenFL.setOnClickListener(this);

        musculosTV.setOnClickListener(this);

        atras.setOnClickListener(this);

        todoElCuerpo = findViewById(R.id.todo_el_cuerpo);
        trenSuperior = findViewById(R.id.tren_superior);
        espalda = findViewById(R.id.espalda);
        biceps = findViewById(R.id.biceps);
        cuadriceps = findViewById(R.id.cuadriceps);



        //Actualizar las 2 primeras iniciales de la referencia del ejercicio.
        nombreET.addTextChangedListener(new TextWatcher() {
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
                imagen.setText( inicialesNombre.toUpperCase() );

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
                        trenSuperior.setChecked(false);
                        espalda.setChecked(false);
                        biceps.setChecked(false);
                        cuadriceps.setChecked(false);

                    } else{
                        todoElCuerpo.setChecked(false);

                        if (id == R.id.tren_superior) {
                            espalda.setChecked(false);
                            biceps.setChecked(false);

                        } else if (id == R.id.biceps || id == R.id.espalda) {
                            trenSuperior.setChecked(false);
                        }
                    }
                } else {
                    musculos.remove(musculo);

                    cambiarColores((TextView) buttonView, getResources().getColor(R.color.negro_oscuro));
                }

            }
        };

        todoElCuerpo.setOnCheckedChangeListener(checkBoxListener);
        trenSuperior.setOnCheckedChangeListener(checkBoxListener);
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
            guardarEjercicio();

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.musculos_tv) {
            viewFlipper.showNext();

        } else if (id == R.id.atras) {
            if (musculos != null && !musculos.isEmpty()) {
                musculosString = new StringBuilder();
                for (String musculo : musculos) {
                    musculosString.append(musculo).append(", ");
                }
                musculosTV.setText(musculosString.substring(0, musculosString.length() - 2) + ".");
            } else {
                musculosTV.setText("Selecciona los músculos");
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

    public void guardarEjercicio(){
        imagenEjercicio = imagen.getText().toString();

        nombreEjercicio = nombreET.getText().toString();
        nombreVacio = ValidarDatos.campoVacio(nombreEjercicio);

        notasEjercicio = notasET.getText().toString();

        musculosEjercicio = musculosTV.getText().toString();
        if(musculosEjercicio.equals("Selecciona los músculos")){
            musculosVacios = true;
        }else{
            musculosVacios = false;
        }

        if(descansoET.getText().toString().isEmpty()){
            descansoEjercicio = 0;
            descansoVacio = true;
        }else{
            descansoEjercicio = Integer.parseInt(descansoET.getText().toString());
            descansoVacio = false;
        }

        if(seriesET.getText().toString().isEmpty()){
            seriesEjercicio = 0;
            seriesVacias = true;
        }else{
            seriesEjercicio = Integer.parseInt(seriesET.getText().toString());
            seriesVacias = false;

        }

        if(repeticionesET.getText().toString().isEmpty()){
            repeticionesEjercicio = 0;
            repeticionesVacias = true;
        }else{
            repeticionesEjercicio = Integer.parseInt(repeticionesET.getText().toString());
            repeticionesVacias = false;
        }

        if(!musculosVacios){
            musculosEjercicio = musculosEjercicio.substring(0, musculosString.length() - 2);
            musculosArray = new ArrayList<String>(Arrays.asList(musculosEjercicio.split(",")));
        }


        if(!nombreVacio && !musculosVacios && !descansoVacio && !seriesVacias && !repeticionesVacias){

            ejercicioUsuario = new TablaEjerciciosUsuario(imagenEjercicio, musculosArray, nombreEjercicio, notasEjercicio,
                    repeticionesEjercicio, seriesEjercicio, descansoEjercicio);
            firebaseManager.agregarEjercicio(this, ejercicioUsuario);
            Toast.makeText(getApplicationContext(), "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();
        }else{
            MostratToast.mostrarToast(this, "Completa todos los campos");
        }

    }
}