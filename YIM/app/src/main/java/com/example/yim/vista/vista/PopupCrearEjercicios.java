package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.ViewFlipper;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.ObtenerLogro;
import com.example.yim.modelo.tablas.TablaEjercicioUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.controlador.ValidarDatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class PopupCrearEjercicios extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    ViewFlipper viewFlipper;
    ImageView cancelar, guardar, atras;
    FrameLayout imagenFL;
    TextView imagen, musculosTV;
    EditText nombreET, descansoET, seriesET, repeticionesET, notasET;
    String inicialesNombre;
    CheckBox todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps;
    StringBuilder musculosString;
    TablaEjercicioUsuario ejercicioUsuario;
    String imagenEjercicio, nombreEjercicio, notasEjercicio, musculosEjercicio;
    int descansoEjercicio, seriesEjercicio, repeticionesEjercicio;
    HashSet<String> musculos = new HashSet<>();
    boolean nombreVacio, musculosVacios, descansoVacio, seriesVacias, repeticionesVacias, datosGuardados;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_crear_ejercicios);

        //Cambiar el tamaño del activity.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        Window ventana = getWindow();
        getWindow().setLayout((ancho), (int) (alto * 0.90));
        ventana.setGravity(Gravity.BOTTOM);


        //Inicializar instancias.
        firebaseManager = new FirebaseManager();
        datosGuardados = false;


        //Referencias de las vistas
        viewFlipper = findViewById(R.id.viewFlipper);

        cancelar = findViewById(R.id.cancelar);
        guardar = findViewById(R.id.guardar_iv);

        imagenFL = findViewById(R.id.imagen_fl);
        imagen = findViewById(R.id.imagen_ejercicio);

        nombreET = findViewById(R.id.nombre_et);
        descansoET = findViewById(R.id.descanso_et);
        seriesET = findViewById(R.id.series_et);
        repeticionesET = findViewById(R.id.repeticiones_et);
        notasET = findViewById(R.id.notas_et);

        musculosTV = findViewById(R.id.musculos_tv);

        atras = findViewById(R.id.atras_iv);


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


        //Cambiar los checkBox al marcarse.
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
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "cancelar":
                if(!datosGuardados){
                    cambiarActivity();
                }else{
                    finish();
                }
                break;

            case "guardar_iv":
                guardarEjercicio();
                break;

            case "musculos_tv":
                viewFlipper.showNext();
                break;

            case "atras_iv":
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
                break;
        }
    }


    //Método para identificar el músculo marcado.
    private String obtenerMusculo(int id) {
        String musculo = "";
        String idString = getResources().getResourceEntryName(id);

        switch (idString) {
            case "todo_el_cuerpo":
                musculo = "Todo el cuerpo";
                break;
            case "tren_superior":
                musculo = "Tren superior";
                break;
            case "espalda":
                musculo = "Espalda";
                break;
            case "biceps":
                musculo = "Bíceps";
                break;
            case "cuadriceps":
                musculo = "Cuádriceps";
                break;
        }
        return musculo;
    }


    //Método para el guardado de los datos del ejercicio.
    public void guardarEjercicio(){
        ArrayList<String> musculosArray;

        imagenEjercicio = imagen.getText().toString();

        nombreEjercicio = nombreET.getText().toString();
        nombreVacio = ValidarDatos.campoVacio(nombreEjercicio);

        notasEjercicio = notasET.getText().toString();

        musculosEjercicio = musculosTV.getText().toString();
        musculosVacios = musculosEjercicio.equals("Selecciona los músculos");

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


        if(!nombreVacio && !musculosVacios && !descansoVacio && !seriesVacias && !repeticionesVacias){
            musculosEjercicio = musculosEjercicio.substring(0, musculosString.length() - 2);
            musculosArray = new ArrayList<String>(Arrays.asList(musculosEjercicio.split(", ")));

            ejercicioUsuario = new TablaEjercicioUsuario(imagenEjercicio, musculosArray, "-" + nombreEjercicio, notasEjercicio,
                    repeticionesEjercicio, seriesEjercicio, descansoEjercicio);


            agregarEjercicio(ejercicioUsuario);
        }else{
            mostrarToast("Completa todos los campos");
        }

    }


    //Método para crear el ejercicio.
    private void agregarEjercicio(TablaEjercicioUsuario ejercicio){
        try{
            firebaseManager.agregarEjercicio(this, ejercicio, new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada) {
                        mostrarToast("Ejercicio creado correctamente");
                        ObtenerLogro obtenerLogro = new ObtenerLogro();
                        obtenerLogro.obtenerLogro(PopupCrearEjercicios.this, "Crear ejercicio");
                        finish();
                        datosGuardados = true;
                    } else {
                        datosGuardados = false;
                        mostrarToast("Error al crear la rutina");
                    }
                }
            });
        } catch (Exception ex) {
            mostrarToast("Error al mostrar los ejercicios de la rutina.");
            ex.printStackTrace();
        }
    }



    //Método que permite el cambiado de colores de una vista.
    private void cambiarColores(View view, int color){
        ((TextView) view).setTextColor(color);
    }


    //Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity() {
        CambiarActivity.cambiar(this, "Descartar cambios.", "¿Desea descartar los cambios no guardados?", "ir_a_ejercicios");
    }



    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}