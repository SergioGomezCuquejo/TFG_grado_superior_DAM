package com.example.yim.vista.vista;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjercicioUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class PopupVerEjerciciosCreados extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaEjercicioUsuario ejercicioUsuario;
    ViewFlipper viewFlipper, instrucionesVF;
    TextView instrucionesTV, informacionTV, imagenTV, musculosTV, pesoMaxTV, repeticionesMaxTV, serieNumTV,
            vecesRealizadoTV, vecesNoRealizadoTV, vecesEnRutinasTV, vecesEnRutinaActivaTV;
    ImageView cerrar, atras, guardar;
    EditText nombreET, descansoET, seriesET, repeticionesET, notasET;
    CheckBox todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps;
    Button borrar;
    StringBuilder musculosString;
    HashSet<String> musculos = new HashSet<>();
    String inicialesNombre, imagenEjercicio, nombreEjercicio, notasEjercicio, musculosEjercicio;
    int flipperActivo, descansoEjercicio, seriesEjercicio, repeticionesEjercicio;
    ArrayList<String> musculosArray;
    boolean nombreVacio, musculosVacios, descansoVacio, seriesVacias, repeticionesVacias, datosGuardados;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_ver_ejercicios_creados);

        //Cambiar el tamaño de la pantalla.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.95), (int) (alto * 0.85));


        //Inicializar instancias.
        firebaseManager = new FirebaseManager();
        Intent intent = getIntent();
        flipperActivo  = 1;


        //Obtener la rutina que se ha seleccionado.
        if(intent.hasExtra("ejercicioUsuario")) {
            ejercicioUsuario = (TablaEjercicioUsuario) intent.getSerializableExtra("ejercicioUsuario");
        }else{
            mostrarToast("Error al obtener el ejercicio");
            finish();
        }


        //Referencias de las vistas.
        viewFlipper = findViewById(R.id.viewFlipper);

        instrucionesTV = findViewById(R.id.instruciones_tv);
        informacionTV = findViewById(R.id.informacion_tv);
        cerrar = findViewById(R.id.cerrar);

        guardar = findViewById(R.id.guardar_iv);
        instrucionesVF = findViewById(R.id.instruciones_vf);
        imagenTV = findViewById(R.id.imagen_ejercicio);
        nombreET = findViewById(R.id.nombre_et);
        descansoET = findViewById(R.id.descanso_et);
        seriesET = findViewById(R.id.series_et);
        repeticionesET = findViewById(R.id.repeticiones_et);
        notasET = findViewById(R.id.notas_et);
        musculosTV = findViewById(R.id.musculos_tv);
        borrar = findViewById(R.id.borrar);

        atras = findViewById(R.id.atras_iv);
        todoElCuerpo = findViewById(R.id.todo_el_cuerpo);
        trenSuperior = findViewById(R.id.tren_superior);
        espalda = findViewById(R.id.espalda);
        biceps = findViewById(R.id.biceps);
        cuadriceps = findViewById(R.id.cuadriceps);

        pesoMaxTV = findViewById(R.id.peso_max_tv);
        repeticionesMaxTV = findViewById(R.id.repeticiones_max_tv);
        serieNumTV = findViewById(R.id.serie_num_tv);
        vecesRealizadoTV = findViewById(R.id.veces_realizado_tv);
        vecesNoRealizadoTV = findViewById(R.id.veces_no_realizado_tv);
        vecesEnRutinasTV = findViewById(R.id.veces_en_rutinas_tv);
        vecesEnRutinaActivaTV = findViewById(R.id.veces_en_rutina_activa_tv);


        //Listeners.
        guardar.setOnClickListener(this);
        instrucionesTV.setOnClickListener(this);
        informacionTV.setOnClickListener(this);
        cerrar.setOnClickListener(this);
        musculosTV.setOnClickListener(this);
        atras.setOnClickListener(this);
        borrar.setOnClickListener(this);


        //Poner por defecto la opción de instrucciones
        cambiarColores(instrucionesTV, R.color.fondo_oscuro, R.color.blanco);


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
                imagenTV.setText( inicialesNombre.toUpperCase() );

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

        //Mostrar datos.
        try{
            mostrarDatos();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar los datos del ejercicio.");
            ex.printStackTrace();
        }

    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "instruciones_tv":
                if(flipperActivo != 1) {
                    flipperActivo = 1;
                    viewFlipper.showNext();

                    cambiarColores(instrucionesTV, R.color.fondo_oscuro, R.color.blanco);
                    cambiarColores(informacionTV, R.color.fondo_clarito, R.color.negro_clarito);
                }
                break;

            case "informacion_tv":
                if(flipperActivo != 2) {
                    flipperActivo = 2;
                    viewFlipper.showPrevious();

                    cambiarColores(informacionTV, R.color.fondo_oscuro, R.color.blanco);
                    cambiarColores(instrucionesTV, R.color.fondo_clarito, R.color.negro_clarito);
                }
                break;

            case "cerrar":
                if(datosGuardados){
                    finish();
                }else{
                    cambiarActivity();
                }
                break;

            case "guardar_iv":
                guardarEjercicio();
                break;

            case "musculos_tv":
                instrucionesVF.showNext();
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
                instrucionesVF.showPrevious();
                break;

            case "borrar": //todo
                CambiarActivity.cambiar(this, "¿Eliminar ejercicio?", "", "ID" + ejercicioUsuario.getID() + "EJ");
                break;
        }
    }

    //Método para obtener el nombre del músculo marcado.
    private String obtenerMusculo(int id) {
        String musculo = "";

        String idString = getResources().getResourceEntryName(id);

        switch (idString){
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

    //Método para marcar los músculos seleccionados.
    private void marcarMusculo(String musculoMarcado) {
        switch (musculoMarcado) {
            case "Todo el cuerpo":
                todoElCuerpo.setChecked(true);
                break;
            case "Tren superior":
                trenSuperior.setChecked(true);
                break;
            case "Espalda":
                espalda.setChecked(true);
                break;
            case "Bíceps":
                biceps.setChecked(true);
                break;
            case "Cuádriceps":
                cuadriceps.setChecked(true);
                break;
        }
    }


    //Métodos para cambiar los colores de la vista.
    public void cambiarColores(View view, @ColorRes int colorFondoRes, @ColorRes int colorLetrasRes){
        Drawable shape;
        TextView textView = (TextView) view;

        int colorFondo = ContextCompat.getColor(this, colorFondoRes);
        int colorLetras = ContextCompat.getColor(this, colorLetrasRes);

        if(view.getId() == R.id.instruciones_tv){
            shape = textView.getBackground();
            shape.setColorFilter(colorFondo, android.graphics.PorterDuff.Mode.SRC);
        } else if (view.getId() == R.id.informacion_tv) {
            textView.setBackgroundColor(colorFondo);
        }
        textView.setTextColor(colorLetras);
    }
    public void cambiarColores(View view, int color){
        ((TextView) view).setTextColor(color);
    }


    //Método para mostrar la información el ejercicio.
    @SuppressLint("SetTextI18n")
    public void mostrarDatos(){
        imagenTV.setText(ejercicioUsuario.getImagen());
        nombreET.setText(ejercicioUsuario.getNombre().substring(1));
        descansoET.setText(String.valueOf(ejercicioUsuario.getTiempo_descanso()));
        seriesET.setText(String.valueOf(ejercicioUsuario.getSeries_recomendadas()));
        repeticionesET.setText(String.valueOf(ejercicioUsuario.getRepeticiones_recomendadas()));
        notasET.setText(ejercicioUsuario.getNotas());

        musculosString = new StringBuilder();
        for (String musculo : ejercicioUsuario.getMusculos()) {
            musculosString.append(musculo).append(", ");
            marcarMusculo(musculo);
        }

        musculosTV.setText(musculosString.substring(0, musculosString.length() - 2) + ".");

        pesoMaxTV.setText(String.valueOf(ejercicioUsuario.getPeso_maximo()));
        repeticionesMaxTV.setText(String.valueOf(ejercicioUsuario.getRepeticiones_maximas()));
        serieNumTV.setText(String.valueOf(ejercicioUsuario.getSeries_maximas()));
        vecesRealizadoTV.setText(String.valueOf(ejercicioUsuario.getVeces_realizado()));
        vecesNoRealizadoTV.setText(String.valueOf(ejercicioUsuario.getVeces_no_realizado()));
        vecesEnRutinasTV.setText(String.valueOf(ejercicioUsuario.getVeces_usado_en_rutinas()));
        vecesEnRutinaActivaTV.setText(String.valueOf(ejercicioUsuario.getVeces_usado_en_rutina_activa()));
    }

    //Método para guardar los datos del ejercicio.
    public void guardarEjercicio(){
        imagenEjercicio = imagenTV.getText().toString();

        nombreEjercicio = nombreET.getText().toString();
        nombreVacio = nombreEjercicio.isEmpty() || nombreEjercicio.equals(" ");

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
            musculosArray = new ArrayList<String>(Arrays.asList(musculosEjercicio.split(", ")));
        }


        if(!nombreVacio && !musculosVacios && !descansoVacio && !seriesVacias && !repeticionesVacias){

            ejercicioUsuario.setImagen(imagenEjercicio);
            ejercicioUsuario.setMusculos(musculosArray);
            ejercicioUsuario.setNombre("-" + nombreEjercicio);
            ejercicioUsuario.setNotas(notasEjercicio);
            ejercicioUsuario.setRepeticiones_recomendadas(repeticionesEjercicio);
            ejercicioUsuario.setSeries_recomendadas(seriesEjercicio);
            ejercicioUsuario.setTiempo_descanso(descansoEjercicio);

            actualizarEjercicio(ejercicioUsuario);
        }else{
            mostrarToast("Completa todos los campos");
        }

    }
    private void actualizarEjercicio( TablaEjercicioUsuario ejercicio){
        try{
            firebaseManager.actualizarEjercicioUsuario(this, ejercicio, new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada){
                        mostrarToast("Datos guardados correctamente");
                        datosGuardados = true;
                    }else{
                        mostrarToast("No se han podido guardar los datos correctamente");
                        datosGuardados = false;
                    }
                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al obtener los músculos.");
            ex.printStackTrace();
        }
    }



    //Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity() {
        CambiarActivity.cambiar(this, "Cerrar sin guardar", "¿Desea descartar los cambios?", "ir_a_ver_ejercicios");
    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}