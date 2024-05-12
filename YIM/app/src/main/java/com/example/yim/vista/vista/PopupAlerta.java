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
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;

public class PopupAlerta extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseManager firebaseManager;
    Intent intent;
    TextView titulo_tv, texto_tv;
    Button cancelar_btn, aceptar;
    String titulo, texto, iraA;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_alerta);

        //Cambiar el tamaño de la pantalla para que sea como un popup.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.80), (int) (alto * 0.30));


        firebaseManager = new FirebaseManager();


        //Obtener datos de la anterior activity.
        intent = getIntent();
        if(intent != null) {
            titulo = intent.getStringExtra("titulo");
            texto = intent.getStringExtra("texto");
            iraA = intent.getStringExtra("iraA");

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


        //Cambiar los textos por los obtenidos.
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

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    private void aceptar(){
        if(iraA.startsWith("ID")){
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

            MostratToast.mostrarToast(this, mensaje);
            finish();

        }else{
            switch (iraA){
                case "ir_a_musculos":
                    cambiarActivity(Musculos.class);
                    break;
                case "ir_a_ver_rutinas":
                    cambiarActivity(VerRutinas.class);
                    break;
                case "ir_a_ejercicios":
                    cambiarActivity(VerEjercicios.class);
                    break;
                case "ir_a_inicio":
                    cambiarActivity(Inicio.class);
                    break;
                case "ir_a_ejercicios_rutina":
                    cambiarActivity(EjerciciosRutinas.class);
                    break;
                case "ir_a_ver_ejercicios":
                    cambiarActivity(VerEjercicios.class);
                    break;
                case "cerrar_sesion":
                    auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    finish();
                    cambiarActivity(InicioSesion.class);
                    break;
                default:
                    finish();
                    break;
            }
        }

    }
}