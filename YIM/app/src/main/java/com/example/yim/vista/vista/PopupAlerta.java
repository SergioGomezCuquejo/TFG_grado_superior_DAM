package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaRutinaActiva;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PopupAlerta extends AppCompatActivity implements View.OnClickListener {

    private FirebaseManager firebaseManager;
    TextView titulo_tv, texto_tv;
    Button cancelar_btn, aceptar;
    String titulo, texto, iraA, accion;
    TablaRutinaUsuario rutinaUsuario;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_alerta);

        //Cambiar el tamaño de la pantalla.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.80), (int) (alto * 0.30));


        //Inicializar instancias.
        Intent intent = getIntent();
        firebaseManager = new FirebaseManager();


        //Obtener datos del anterior activity.
        if(intent != null) {
            titulo = intent.getStringExtra("titulo");
            texto = intent.getStringExtra("texto");
            iraA = intent.getStringExtra("iraA");
            if(intent.hasExtra("rutinaUsuario")) {
                rutinaUsuario = (TablaRutinaUsuario) intent.getSerializableExtra("rutinaUsuario");
                accion = intent.getStringExtra("accion");
            }

        }else{
            titulo = "OOPS.";
            texto = "Ha habido un problema, intentelo de nuevo más tarde.";
            iraA = "";
        }


        //Referencias de las vistas.
        cancelar_btn = findViewById(R.id.cancelar);
        aceptar = findViewById(R.id.aceptar);
        titulo_tv = findViewById(R.id.titulo);
        texto_tv = findViewById(R.id.texto);


        //Listeners.
        cancelar_btn.setOnClickListener(this);
        aceptar.setOnClickListener(this);


        //Mostrar los mensajes del acivity anterior.
        titulo_tv.setText(titulo);
        texto_tv.setText(texto);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancelar){
            finish();

        } else if (id == R.id.aceptar) {
            if(titulo.equals("OOPS.")){
                finish();
            }else{
                aceptar();
            }

        }
    }

    //Método que se acciona al dar al boton de aceptar.
    private void aceptar(){
        if(iraA.startsWith("ID")){//TOdo haer otra variable mejor
            crudID();

        }else{
            irA();
        }
        finish();

    }

    //Método para redireccionar a otro acivity.
    private void irA(){
        switch (iraA){
            case "ir_a_inicio":
                cambiarActivity(Inicio.class);
                break;
            case "ir_a_rutina_semanal":
                cambiarActivity(RutinaSemanal.class);
                break;
            case "ir_a_estadisticas":
                cambiarActivity(Estadisticas.class);
            case "ir_a_perfil":
                cambiarActivity(Perfil.class);
                break;
            case "ir_a_ver_rutinas":

                if(rutinaUsuario != null) { //TOdo haer otra variable mejor
                    activarRutina();
                }

                cambiarActivity(VerRutinas.class);
                break;


            //////
            case "ir_a_musculos":
                cambiarActivity(Musculos.class);
                break;
            case "ir_a_ejercicios":
                cambiarActivity(VerEjercicios.class);
                break;
            case "ir_a_ejercicios_rutina":
                cambiarActivity(EjerciciosRutinas.class);
                break;
            case "ir_a_ver_ejercicios":
                cambiarActivity(VerEjercicios.class);
                break;
            case "cerrar_sesion":
                //Variables de instancias.
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                finish();
                cambiarActivity(InicioSesion.class);
                break;
            default:
                finish();
                break;
        }
    }


    private void activarRutina(){
        firebaseManager.desactivarRutinas(this);
        firebaseManager.modificarActivoRutina(this, rutinaUsuario.getID(), false);
        firebaseManager.eliminarRutinaActiva(this);

        if(accion.equals("activar")){
            firebaseManager.modificarActivoRutina(this, rutinaUsuario.getID(), true);

            TablaRutinaActiva rutinaActiva = new TablaRutinaActiva();
            rutinaActiva.setIdRutina(rutinaUsuario.getID());

            ArrayList<TablaDiaRutinaActiva> semana = new ArrayList<>();
            for(TablaDiaRutinaUsuario diaRutinaUsuario : rutinaUsuario.getSemana()){
                semana.add(new TablaDiaRutinaActiva(diaRutinaUsuario));
            }
            rutinaActiva.setSemana(semana);
            firebaseManager.agregarRutinaActiva(this, rutinaActiva);

        }
    }


    private void crudID(){
        String ID = iraA.substring(2, iraA.length()-2);
        String mensaje = "";

        if (iraA.endsWith("EJ")){
            if(firebaseManager.eliminarEjercicio(this, ID)){
                mensaje = "Ejercicio eliminado correctamente";
            }else{
                mensaje = "Error al eliminar el ejercicio";
            }
        }else if (iraA.endsWith("RU")) {
            if (firebaseManager.eliminarRutina(this, ID)) {
                mensaje = "Rutina eliminada correctamente";
                CambiarActivity.cambiar(this, VerRutinas.class);
            } else {
                mensaje = "Error al eliminar la rutina";
            }
        }

        mostrarToast(mensaje);
    }



    //Métodos para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }


    //Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }

}