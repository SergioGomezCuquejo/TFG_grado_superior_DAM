package com.example.yim.vista.vista;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.PopupVerEjerciciosAdaptador;
import com.example.yim.modelo.tablas.TablaEjercicioUsuario;
import com.example.yim.vista.controlador.MostratToast;

public class PopupVerEjercicios extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private TablaEjercicioUsuario ejercicioUsuario;
    TextView instruciones, informacion;
    ImageView cerrar;
    ViewFlipper viewFlipper;
    ImageView imagen;
    TextView nombre, musculosTV, peso, repeticiones, serieNum, vecesRealizado, vecesNoRealizado, vecesEnRutinas, vecesEnRutinaActiva;
    RecyclerView ejecucion, consejos;
    int flipperActivo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_ejercicios);

        //Cambiar el tamaño de la pantalla.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.95), (int) (alto * 0.85));


        //Inicializar instancias.
        Intent intent = getIntent();
        flipperActivo  = 1;

        //Obtener la rutina que se ha seleccionado.
        if(intent.hasExtra("ejercicioUsuario")) {
            ejercicioUsuario = (TablaEjercicioUsuario) intent.getSerializableExtra("ejercicioUsuario");

        }else {
            mostrarToast("Error al obtener el ejercicio");
            finish();
        }


        //Referencias de las vistas.
        instruciones = findViewById(R.id.instruciones);
        informacion = findViewById(R.id.informacion);
        cerrar = findViewById(R.id.cerrar);
        viewFlipper = findViewById(R.id.viewFlipper);

        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombreTV);
        musculosTV = findViewById(R.id.musculos_tv);

        ejecucion = findViewById(R.id.ejecucion);
        consejos = findViewById(R.id.consejos);

        peso = findViewById(R.id.peso);
        repeticiones = findViewById(R.id.repeticiones);
        serieNum = findViewById(R.id.serie_num);
        vecesRealizado = findViewById(R.id.veces_realizado);
        vecesNoRealizado = findViewById(R.id.veces_no_realizado);
        vecesEnRutinas = findViewById(R.id.veces_en_rutinas);
        vecesEnRutinaActiva = findViewById(R.id.veces_en_rutina_activa);


        //Listeners.
        instruciones.setOnClickListener(this);
        cerrar.setOnClickListener(this);
        informacion.setOnClickListener(this);


        //Poner por defecto la opción de instrucciones
        cambiarColores(instruciones, R.color.fondo_oscuro, R.color.blanco);


        //Mostrar datos.
        try{
            mostrarDatos();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar los datos del ejercicio.");
            ex.printStackTrace();
        }

    }

    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "instruciones":
                if(flipperActivo != 1){
                    flipperActivo = 1;
                    viewFlipper.showNext();

                    cambiarColores(instruciones, R.color.fondo_oscuro, R.color.blanco);
                    cambiarColores(informacion, R.color.fondo_clarito, R.color.negro_clarito);
                }
                break;

            case "informacion":
                if(flipperActivo != 2){
                    flipperActivo = 2;
                    viewFlipper.showPrevious();

                    cambiarColores(informacion, R.color.fondo_oscuro, R.color.blanco);
                    cambiarColores(instruciones, R.color.fondo_clarito, R.color.negro_clarito);
                }
                break;

            case "cerrar":
                finish();
                break;
        }
    }


    //Método para cambiar los colores de la vista.
    public void cambiarColores(View view, @ColorRes int colorFondoRes, @ColorRes int colorLetrasRes){
        Drawable shape;
        TextView textView = (TextView) view;

        // Obtener colores de los recursos.
        int colorFondo = ContextCompat.getColor(this, colorFondoRes);
        int colorLetras = ContextCompat.getColor(this, colorLetrasRes);

        if(view.getId() == R.id.instruciones){
            shape = textView.getBackground();
            shape.setColorFilter(colorFondo, android.graphics.PorterDuff.Mode.SRC);
        } else if (view.getId() == R.id.informacion) {
            textView.setBackgroundColor(colorFondo);
        }
        textView.setTextColor(colorLetras);
    }


    //Método para mostrar la información del ejercicio.
    public void mostrarDatos(){
        String musculos = "";
        PopupVerEjerciciosAdaptador adaptadorEjecucion, adaptadorConsejos;

        imagen.setImageResource(R.drawable.curl_realizar);

        nombre.setText(ejercicioUsuario.getNombre());

        for(String musculo : ejercicioUsuario.getMusculos()){
            musculos += musculo + ", ";
        }
        musculos = musculos.substring(0, musculos.length() - 2);
        musculosTV.setText(musculos);


        ejecucion.setLayoutManager(new LinearLayoutManager(this));
        adaptadorEjecucion = new PopupVerEjerciciosAdaptador(this, ejercicioUsuario.getEjecucion());
        ejecucion.setAdapter(adaptadorEjecucion);

        consejos.setLayoutManager(new LinearLayoutManager(this));
        adaptadorConsejos = new PopupVerEjerciciosAdaptador(this, ejercicioUsuario.getConsejos_clave());
        consejos.setAdapter(adaptadorConsejos);

        peso.setText(String.valueOf(ejercicioUsuario.getPeso_maximo()));
        repeticiones.setText(String.valueOf(ejercicioUsuario.getRepeticiones_maximas()));
        serieNum.setText(String.valueOf(ejercicioUsuario.getSeries_maximas()));
        vecesRealizado.setText(String.valueOf(ejercicioUsuario.getVeces_realizado()));
        vecesRealizado.setText(String.valueOf(ejercicioUsuario.getVeces_no_realizado()));
        vecesEnRutinas.setText(String.valueOf(ejercicioUsuario.getVeces_usado_en_rutinas()));
        vecesEnRutinaActiva.setText(String.valueOf(ejercicioUsuario.getVeces_usado_en_rutina_activa()));

    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }

}