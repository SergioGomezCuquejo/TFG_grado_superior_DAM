package com.example.yim.vista.vista;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
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
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class PopupVerEjerciciosCreados extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaEjercicioCreado ejercicioUsuario;
    private static final int REQUEST_CODE_PVEC = 7;
    ViewFlipper viewFlipper, instrucionesVF;
    TextView instrucionesTV, informacionTV, musculosTV, pesoMaxTV, repeticionesMaxTV, serieNumTV,
            vecesRealizadoTV, vecesNoRealizadoTV, vecesEnRutinasTV, vecesEnRutinaActivaTV, nombreRutinaTV;
    FrameLayout nombreRutinaFL;
    ImageView cerrar, atras, guardar, imagenIV;
    RelativeLayout imagenRL;
    EditText nombreET, descansoET, seriesET, repeticionesET, notasET;
    CheckBox todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps;
    Button borrar;
    StringBuilder musculosString;
    HashSet<String> musculos = new HashSet<>();
    String nombreEjercicio, notasEjercicio, musculosEjercicio, accion;
    int flipperActivo, descansoEjercicio, seriesEjercicio, repeticionesEjercicio;
    ArrayList<String> musculosArray;
    boolean nombreVacio, musculosVacios, descansoVacio, seriesVacias, repeticionesVacias, datosGuardados;
    ProgressDialog progressDialog;
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
            ejercicioUsuario = (TablaEjercicioCreado) intent.getSerializableExtra("ejercicioUsuario");
        }else{
            mostrarToast("Error al obtener el ejercicio");
            finish();
        }


        //Referencias de las vistas.
        viewFlipper = findViewById(R.id.viewFlipper);

        instrucionesTV = findViewById(R.id.instruciones_tv);
        informacionTV = findViewById(R.id.informacion_tv);
        cerrar = findViewById(R.id.cerrar);


        nombreRutinaFL = findViewById(R.id.nombre_rutina_fl);
        nombreRutinaTV = findViewById(R.id.nombre_rutina_tv);
        imagenRL = findViewById(R.id.imagen_rl);
        imagenIV = findViewById(R.id.imagen_iv);

        guardar = findViewById(R.id.guardar_iv);
        instrucionesVF = findViewById(R.id.instruciones_vf);
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
        imagenRL.setOnClickListener(this);
        guardar.setOnClickListener(this);
        instrucionesTV.setOnClickListener(this);
        informacionTV.setOnClickListener(this);
        cerrar.setOnClickListener(this);
        musculosTV.setOnClickListener(this);
        atras.setOnClickListener(this);
        borrar.setOnClickListener(this);


        //Poner por defecto la opción de instrucciones
        cambiarColores(instrucionesTV, R.color.fondo_oscuro, R.color.blanco);

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
            mostrarToast("Error al mostrar los datos del ejercicio2.");
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

            case "imagen_rl":
                subirImagen();
                break;

            case "cerrar":
                if(datosGuardados){
                    finish();
                }else{
                    accion = "";
                    cambiarActivity("Descartar cambios", "¿Desea descartar los cambios no guardados?");
                }
                break;

            case "guardar_iv":
                guardarEjercicio();
                finish();
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

            case "borrar":
                accion = "Eliminar";
                cambiarActivity("Eliminar ejercicio", "¿Desea eliminar el ejercicio?");
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

        String nombre;
        if(ejercicioUsuario.getImagen() != null){
            Imagenes.mostrarImagen(this, ejercicioUsuario.getImagen(), imagenIV);
        }else{
            nombreRutinaFL.setVisibility(View.VISIBLE);
            imagenIV.setVisibility(View.GONE);
            nombre = ejercicioUsuario.getNombre().toUpperCase();
            nombre = nombre.substring(1);
            if (nombre.length() >= 3) {
                nombre = nombre.substring(0, 3);
            }
            nombreRutinaTV.setText(nombre);
        }




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
        nombreEjercicio = nombreET.getText().toString();
        nombreVacio = nombreEjercicio.isEmpty() || nombreEjercicio.equals(" ");

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

        if(!musculosVacios){
            musculosEjercicio = musculosEjercicio.substring(0, musculosString.length() - 2);
            musculosArray = new ArrayList<>(Arrays.asList(musculosEjercicio.split(", ")));
        }


        if(!nombreVacio && !musculosVacios && !descansoVacio && !seriesVacias && !repeticionesVacias){

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
    private void actualizarEjercicio( TablaEjercicioCreado ejercicio){
        try{
            firebaseManager.actualizarEjercicioCreado(this, ejercicio, new FirebaseCallbackBoolean() {
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

    // Método para eliminar el ejercicio.
    private void eliminarEjercicio(){
        try{
            firebaseManager.eliminarEjercicio(this, ejercicioUsuario.getID(), new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if(accionRealizada){
                        mostrarToast("Ejercicio eliminado correctamente");
                        finish();
                    }else{
                        mostrarToast("No se han podido eliminar el ejercicio");
                    }
                }
            });
        } catch (Exception ex) {
            mostrarToast("Error al eliminar el ejercicio.");
            ex.printStackTrace();
        }
    }

    //Método para mostrar la galería del usuario.
    private void subirImagen(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        startActivityForResult(i, 300);
        progressDialog = new ProgressDialog(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Al recibir la imagen se actualizará.
            if (requestCode == 300){
                Uri image_url = data.getData();
                Imagenes.subirImagen(this, progressDialog, "Actualizando la imagen del ejercicio..", "ejercicio/" + ejercicioUsuario.getID(), image_url, new FirebaseCallbackUri() {
                    @Override
                    public void onCallback(Uri uri) {
                        Imagenes.mostrarImagen(PopupVerEjerciciosCreados.this, uri.toString(), imagenIV);
                        ejercicioUsuario.setImagen(uri.toString());
                    }
                });


                // Si la respuesta es del PopupAlerta.java y es true se cerrará sesión.
            }else if (requestCode == REQUEST_CODE_PVEC) {
                boolean resultado = data.getBooleanExtra("resultado", false);
                if(resultado){
                    if(accion.equals("Eliminar")){
                        eliminarEjercicio();
                    }else{
                        finish();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    //Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(String titulo, String texto) {
        Intent intent = new Intent(this, PopupAlerta.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("texto", texto);
        startActivityForResult(intent, REQUEST_CODE_PVEC);
    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}