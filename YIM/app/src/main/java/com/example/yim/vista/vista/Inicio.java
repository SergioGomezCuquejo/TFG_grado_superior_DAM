package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaActiva;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaRutinaActiva;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    LinearLayout continuarLinearlayout, rutinasLL;
    TextView continuarTV, semanaTV, diaTV;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    ImageView imagenPerfilMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Inicializar instancias.
        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();


        //Controlar que no se ha cerrado sesión.
        FirebaseUser user = auth.getCurrentUser();
        if(user == null){
            cambiarActivity(InicioSesion.class);
        }

        //Referencias de las vistas.
        continuarLinearlayout = findViewById(R.id.continuar_linearlayout);
        continuarTV = findViewById(R.id.continuar_tv);
        semanaTV = findViewById(R.id.semana_tv);
        diaTV = findViewById(R.id.dia_tv);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners
        continuarLinearlayout.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);

        //Mostrar datos.
        try{
            mostrarImagenPerfil();
            continuar();

        } catch (Exception ex) {
            mostrarToast("Error al obtener el inicio.");
            ex.printStackTrace();
        }



    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "continuar_linearlayout":
                if(continuarTV.getText().equals("Continuar")){
                    cambiarActivity(RutinaSemanal.class);
                }else{
                    cambiarActivity(VerRutinas.class);
                }
                break;

            case "imagen_casa_menu":
                cambiarActivity(Inicio.class);
                break;
            case "imagen_calendario_menu":
                cambiarActivity(RutinaSemanal.class);
                break;
            case "imagen_estadisticas_menu":
                cambiarActivity(Estadisticas.class);
                break;
            case "imagen_usuario_menu":
                cambiarActivity(Perfil.class);
                break;
        }
    }

    private void continuar(){
        try {
            firebaseManager.obtenerRutinaActiva(this, new FirebaseCallbackRutinaActiva() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onCallback(TablaRutinaActiva rutinaActiva) {
                    if(rutinaActiva != null){
                        semanaTV.setVisibility(View.VISIBLE);

                        continuarTV.setText("Continuar");
                        semanaTV.setText("Semana " + (rutinaActiva.getSemana().size()/7));
                    }else{
                        continuarTV.setText("Empezar a crear una rutina");
                    }
                }
            });
        } catch (Exception ex) {
            mostrarToast("Error al obtener la rutina semanal.");
            ex.printStackTrace();
        }
    }



    //Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int REQUEST_CODE = 1;
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                boolean resultado = data.getBooleanExtra("resultado", false);
                MostratToast.mostrarToast(this, "res - " + resultado);
            }
        }
    }

    private void mostrarImagenPerfil(){
        firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
            @Override
            public void onCallback(TablaPerfil perfil) {
                if(perfil.getImagen() != null && !perfil.getImagen().equals("")){
                    Imagenes.urlImagenPerfil = perfil.getImagen();
                    Imagenes.mostrarImagenPerfil(Inicio.this, imagenPerfilMenu);
                }

            }
        });

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
