package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackUri;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.ObtenerLogro;
import com.example.yim.modelo.tablas.TablaEjercicio;
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.Arrays;

public class PopupCrearEjercicios extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private static final int REQUEST_CODE_PCE = 4;
    ViewFlipper viewFlipper;
    ImageView cancelar, guardar, atras, imagenIV;
    RelativeLayout imagenRL;
    TextView musculosTV, nombreRutinaTV;
    FrameLayout nombreRutinaFL;
    EditText nombreET, descansoMinutosET, descansoSegundosET, seriesET, repeticionesET, notasET;
    CheckBox todoElCuerpo, trenSuperior, trenInferior, pecho, espalda, hombro, biceps, antebrazo, triceps, trapecio, gluteo, gemelo, femoral, cuadriceps;
    StringBuilder musculosString;
    TablaEjercicioCreado ejercicioUsuario;
    String nombreEjercicio, notasEjercicio, musculosEjercicio;
    int descansoEjercicio, seriesEjercicio, repeticionesEjercicio;
    ArrayList<String> musculos = new ArrayList<>();
    boolean nombreVacio, musculosVacios, descansoVacio, seriesVacias, repeticionesVacias, datosGuardados;
    ProgressDialog progressDialog;

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

        nombreRutinaFL = findViewById(R.id.nombre_rutina_fl);
        nombreRutinaTV = findViewById(R.id.nombre_rutina_tv);
        imagenRL = findViewById(R.id.imagen_rl);
        imagenIV = findViewById(R.id.imagen_iv);


        nombreET = findViewById(R.id.nombre_et);
        descansoMinutosET = findViewById(R.id.descanso_minutos_et);
        descansoSegundosET = findViewById(R.id.descanso_segundos_et);
        seriesET = findViewById(R.id.series_et);
        repeticionesET = findViewById(R.id.repeticiones_et);
        notasET = findViewById(R.id.notas_et);

        musculosTV = findViewById(R.id.musculos_tv);

        atras = findViewById(R.id.atras_iv);


        //Listeners
        cancelar.setOnClickListener(this);
        guardar.setOnClickListener(this);
        imagenRL.setOnClickListener(this);

        musculosTV.setOnClickListener(this);

        atras.setOnClickListener(this);

        todoElCuerpo = findViewById(R.id.todo_el_cuerpo);
        trenSuperior = findViewById(R.id.tren_superior);
        trenInferior = findViewById(R.id.tren_inferior);
        pecho = findViewById(R.id.pecho);
        espalda = findViewById(R.id.espalda);
        hombro = findViewById(R.id.hombro);
        biceps = findViewById(R.id.biceps);
        antebrazo = findViewById(R.id.antebrazo);
        triceps = findViewById(R.id.triceps);
        trapecio = findViewById(R.id.trapecio);
        gluteo = findViewById(R.id.gluteo);
        gemelo = findViewById(R.id.gemelo);
        femoral = findViewById(R.id.femoral);
        cuadriceps = findViewById(R.id.cuadriceps);


        //Cambiar los checkBox al marcarse.
        CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                String musculo = obtenerMusculo(id);

                if (isChecked) {
                    if(musculos.size() < 3){
                        musculos.add(musculo);

                        cambiarColores((TextView) buttonView, getResources().getColor(R.color.blanco));
                        if (id == R.id.todo_el_cuerpo) {
                            trenSuperior.setChecked(false);
                            trenInferior.setChecked(false);
                            pecho.setChecked(false);
                            espalda.setChecked(false);
                            hombro.setChecked(false);
                            biceps.setChecked(false);
                            antebrazo.setChecked(false);
                            triceps.setChecked(false);
                            trapecio.setChecked(false);
                            gluteo.setChecked(false);
                            gemelo.setChecked(false);
                            femoral.setChecked(false);
                            cuadriceps.setChecked(false);


                        } else{
                            todoElCuerpo.setChecked(false);

                            if (id == R.id.tren_superior) {
                                pecho.setChecked(false);
                                espalda.setChecked(false);
                                hombro.setChecked(false);
                                biceps.setChecked(false);
                                antebrazo.setChecked(false);
                                triceps.setChecked(false);
                                trapecio.setChecked(false);

                            } else if (id == R.id.pecho || id == R.id.espalda ||id == R.id.hombro || id == R.id.biceps ||id == R.id.antebrazo || id == R.id.triceps || id == R.id.trapecio) {
                                trenSuperior.setChecked(false);
                            }

                            if (id == R.id.tren_inferior) {
                                gluteo.setChecked(false);
                                gemelo.setChecked(false);
                                femoral.setChecked(false);
                                cuadriceps.setChecked(false);

                            } else if (id == R.id.gluteo || id == R.id.gemelo || id == R.id.femoral || id == R.id.cuadriceps) {
                                trenInferior.setChecked(false);
                            }
                    }

                    }
                    else{
                        buttonView.setChecked(false);
                    }
                } else {
                    musculos.remove(musculo);

                    cambiarColores((TextView) buttonView, getResources().getColor(R.color.negro_oscuro));
                }

            }
        };

        todoElCuerpo.setOnCheckedChangeListener(checkBoxListener);
        trenSuperior.setOnCheckedChangeListener(checkBoxListener);
        trenInferior.setOnCheckedChangeListener(checkBoxListener);
        pecho.setOnCheckedChangeListener(checkBoxListener);
        espalda.setOnCheckedChangeListener(checkBoxListener);
        hombro.setOnCheckedChangeListener(checkBoxListener);
        biceps.setOnCheckedChangeListener(checkBoxListener);
        antebrazo.setOnCheckedChangeListener(checkBoxListener);
        triceps.setOnCheckedChangeListener(checkBoxListener);
        trapecio.setOnCheckedChangeListener(checkBoxListener);
        gluteo.setOnCheckedChangeListener(checkBoxListener);
        gemelo.setOnCheckedChangeListener(checkBoxListener);
        femoral.setOnCheckedChangeListener(checkBoxListener);
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
        String idString = getResources().getResourceEntryName(id);
        String musculo = idString.replace('_', ' ');

        switch (musculo) {
            case "biceps":
                musculo = "bíceps";
                break;
            case "cuadriceps":
                musculo = "cuádriceps";
                break;
            case "triceps":
                musculo = "tríceps";
                break;
            case "gluteo":
                musculo = "glúteo";
                break;
        }
        musculo = musculo.substring(0,1).toUpperCase() + musculo.substring(1);

        return musculo;
    }

    //Método para el guardado de los datos del ejercicio.
    public void guardarEjercicio(){
        ArrayList<String> musculosArray;
        nombreEjercicio = nombreET.getText().toString();
        nombreVacio = nombreEjercicio.isEmpty() || nombreEjercicio.equals(" ");

        notasEjercicio = notasET.getText().toString();

        musculosEjercicio = musculosTV.getText().toString();
        musculosVacios = musculosEjercicio.equals("Selecciona los músculos");

        if(descansoMinutosET.getText().toString().isEmpty() && descansoSegundosET.getText().toString().isEmpty()){
            descansoEjercicio = 0;
            descansoVacio = true;
        }else{
            descansoEjercicio = Integer.parseInt(descansoMinutosET.getText().toString()) * 60 + Integer.parseInt(descansoSegundosET.getText().toString());
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

            mostrarToast(musculosArray.toString());
            ejercicioUsuario = new TablaEjercicioCreado(musculosArray, "-" + nombreEjercicio,
                    notasEjercicio, repeticionesEjercicio, seriesEjercicio, descansoEjercicio);

            agregarEjercicio();
        }else{
            mostrarToast("Completa todos los campos");
        }

    }


    //Método para crear el ejercicio.
    private void agregarEjercicio(){
        try{
            firebaseManager.agregarEjercicio(this, ejercicioUsuario, new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada) {
                        mostrarToast("Ejercicio creado correctamente");
                        ObtenerLogro obtenerLogro = new ObtenerLogro();
                        obtenerLogro.obtenerLogro(PopupCrearEjercicios.this, "Crear ejercicio", 1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PCE) {
                boolean resultado = data.getBooleanExtra("resultado", false);
                if(resultado){
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //Método que permite el cambiado de colores de una vista.
    private void cambiarColores(View view, int color){
        ((TextView) view).setTextColor(color);
    }


    //Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity() {
        Intent intent = new Intent(this, PopupAlerta.class);
        intent.putExtra("titulo", "Descartar cambios");
        intent.putExtra("texto", "¿Desea descartar los cambios no guardados?");
        startActivityForResult(intent, REQUEST_CODE_PCE);
    }



    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}