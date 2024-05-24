package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaLogroUsuario;
import com.example.yim.vista.controlador.MostratToast;
import com.google.android.material.imageview.ShapeableImageView;

public class PopupLogros extends AppCompatActivity {

    //Variables de instancias.
    TablaLogroUsuario logroUsuario;
    TextView titulo, progresoTV, descripcion;
    ShapeableImageView imagen;
    View progresoView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_logros);

        //Cambiar el tamaño de la pantalla.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.67));


        //Inicializar instancias.
        Intent intent = getIntent();

        //Obtener la rutina que se ha seleccionado.
        if(intent.hasExtra("logroUsuario")) {
            logroUsuario = (TablaLogroUsuario) intent.getSerializableExtra("logroUsuario");
        }else{
            mostrarToast("Error al obtener el logro.");
            finish();
        }

        //Referencias de las vistas
        progresoView = findViewById(R.id.progreso_view);
        titulo = findViewById(R.id.titulo);
        imagen = findViewById(R.id.imagen);
        progresoTV = findViewById(R.id.progreso_tv);
        descripcion = findViewById(R.id.descripcion);


        //Mostrar datos.
        try{
            mostrarLogro();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar el logro.");
            ex.printStackTrace();
        }
    }


    //Método para mostrar el logro seleccionado.
    @SuppressLint("SetTextI18n")
    public void mostrarLogro(){
        titulo.setText(logroUsuario.getTitulo());

        imagen.setImageResource(R.drawable.logro_50_flexiones);

        if(logroUsuario.getProgreso() < logroUsuario.getTotal()) {
            progresoView.setVisibility(View.VISIBLE);

            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)  progresoView.getLayoutParams();
            int progreso = (int) (( ((double) logroUsuario.getProgreso()) / logroUsuario.getTotal()) * 200);
            layoutParams.width = convertirDpAPixeles(200 - progreso);
            layoutParams.setMarginStart( convertirDpAPixeles(progreso));

            progresoView.setLayoutParams(layoutParams);
        }

        progresoTV.setText(logroUsuario.getProgreso() + "/" + logroUsuario.getTotal());
        descripcion.setText(logroUsuario.getDescripcion());
    }


    //Método para convertir de DP a pixeles.
    public int convertirDpAPixeles(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}