package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.MusculosAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Musculos extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseManager firebaseManager;
    RecyclerView recyclerView;
    FirebaseUser user;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    private MusculosAdaptador adaptador;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musculos);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();


        //Controlar que no se ha cerrado sesión.
        user = auth.getCurrentUser();
        if(user == null){
            cambiarActivity(InicioSesion.class);
        }


        //Referencias de las vistas.
        recyclerView = findViewById(R.id.musculos);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);


        //Listeners.
        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);


        //Mostrar los musculos del usuario.
        mostrarMusculos();

        /*
        String colorToApply10 = "#d00155";
        pierna = findViewById(R.id.pierna);
        Drawable shape10 = (Drawable) pierna.getBackground();
        shape10.setColorFilter(Color.parseColor(colorToApply10), android.graphics.PorterDuff.Mode.SRC);
        pierna.setTextColor(Color.parseColor(blanco));
         */
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        }
    }



    public void mostrarMusculos(){
        try{
            firebaseManager.obtenerMusculosUsuario(this, new FirebaseCallbackMusculosUsuario() {
                @Override
                public void onCallback(ArrayList<TablaMusculosUsuario> musculosUsuarios) {

                    recyclerView.setLayoutManager(new LinearLayoutManager(Musculos.this));
                    adaptador = new MusculosAdaptador(Musculos.this, musculosUsuarios);
                    recyclerView.setAdapter(adaptador);
                }
            });

        }catch (Exception ex){
            MostratToast.mostrarToast(this, "Error al obtener los musculos del usuario.");
            ex.printStackTrace();
        }
    }





    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
}