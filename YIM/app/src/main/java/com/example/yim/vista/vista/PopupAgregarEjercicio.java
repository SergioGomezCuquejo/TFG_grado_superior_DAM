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
import android.widget.ProgressBar;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.EjerciciosDiariosAdaptador;
import com.example.yim.controlador.Adaptadores.PopupAgregarEjercicioAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaEjercicioUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class PopupAgregarEjercicio extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    ImageView cancelar;
    ProgressBar cargando;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_agregar_ejercicio);

        //Cambiar el tamaño del activity.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.95), (int) (alto * 0.90));


        //Inicializar instancias.
        firebaseManager = new FirebaseManager();


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        cancelar = findViewById(R.id.cancelar);
        recyclerView = findViewById(R.id.ejercicios);


        //Listeners.
        cancelar.setOnClickListener(this);


        //Mostrar datos.
        try{
            mostrarEjercicios();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar los ejercicios de la rutina.");
            ex.printStackTrace();
        }

        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar) {
            finish();
        }
    }

    //Todo recibir la rutina para si se le da a un ejercicio se agreue a los ejercicios y luego llevar a otra actividad para mostrar todo ;) <3
    //Método parar mostrar los ejercicios disponibles.
    private void mostrarEjercicios(){
        firebaseManager.obtenerEjerciciosUsuario(this, new FirebaseCallbackEjerciciosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaEjercicioUsuario> ejerciciosUsuarios) {
                if(ejerciciosUsuarios.size() > 0) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(PopupAgregarEjercicio.this));
                    PopupAgregarEjercicioAdaptador adaptador = new PopupAgregarEjercicioAdaptador(PopupAgregarEjercicio.this, ejerciciosUsuarios);
                    recyclerView.setAdapter(adaptador);

                }else{
                    mostrarToast("Ejercicios no encontrados.");
                }
            }
        });
    }



    //Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}