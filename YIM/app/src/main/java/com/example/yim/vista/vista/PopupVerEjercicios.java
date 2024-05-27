package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.PopupVerEjerciciosAdaptador;
import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;
import com.example.yim.vista.controlador.MostratToast;

public class PopupVerEjercicios extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private TablaEjercicioPorDefecto ejercicioUsuario;
    ImageView cerrar;
    ViewFlipper viewFlipper;
    ImageView imagenIV;
    TextView nombre, musculosTV;
    RecyclerView ejecucion, consejos;
    int flipperActivo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_ver_ejercicios);

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
            ejercicioUsuario = (TablaEjercicioPorDefecto) intent.getSerializableExtra("ejercicioUsuario");

        }


        //Referencias de las vistas.
        cerrar = findViewById(R.id.cerrar);
        viewFlipper = findViewById(R.id.viewFlipper);

        imagenIV = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombreTV);
        musculosTV = findViewById(R.id.musculos_tv);

        ejecucion = findViewById(R.id.ejecucion);
        consejos = findViewById(R.id.consejos);


        //Listeners.
        cerrar.setOnClickListener(this);



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

        if (id.equals("cerrar")) {
            finish();
        }
    }


    //Método para mostrar la información del ejercicio.
    public void mostrarDatos(){
        String musculos = "";
        PopupVerEjerciciosAdaptador adaptadorEjecucion, adaptadorConsejos;

        if(ejercicioUsuario.getImagen_realizar() != null){
            imagenIV.setImageResource(getResources().getIdentifier(ejercicioUsuario.getImagen_realizar(), "drawable", getPackageName()));
        }

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

    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }

}