package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseManager firebaseManager;
    FirebaseUser user;
    TextView nombre, email, genero, peso, altura, edad, politica, cerrarSesion;
    LinearLayout logros, imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();


        //Controlar que no se ha cerrado sesión.
        user = auth.getCurrentUser();
        if(user == null){
            cambiarActivity(InicioSesion.class);
        }


        //Referencias de las vistas.
        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        genero = findViewById(R.id.genero);
        peso = findViewById(R.id.peso);
        altura = findViewById(R.id.altura);
        edad = findViewById(R.id.edad);
        politica = findViewById(R.id.politica);
        cerrarSesion = findViewById(R.id.cerrar_sesion);

        logros = findViewById(R.id.logros);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);


        //Listeners.
        cerrarSesion.setOnClickListener(this);

        logros.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);


        //Mostrar los datos del usuario.
        mostrarDatos();
    }

    //Listeners.
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.logros){
            cambiarActivity(Logros.class);

        } else if (id == R.id.cerrar_sesion){
            CambiarActivity.cambiarAlerta(this, "Cerrar sesión", "¿Desea cerrar sesión?", "cerrar_sesion");

        } else if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(this.getClass());

        }
    }

    //Mostrar los datos del usuario.
    public void mostrarDatos(){
        try{
            firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
                @Override
                public void onCallback(TablaPerfil perfil) {
                    nombre.setText(perfil.getNombre());
                    email.setText(perfil.getEmail());
                    genero.setText(perfil.getGenero());
                    peso.setText(String.valueOf(perfil.getPeso()));
                    altura.setText(String.valueOf(perfil.getAltura()));
                    edad.setText(String.valueOf(perfil.getEdad()));
                }
            });

        }catch (Exception ex){
            MostratToast.mostrarToast(this, "Error al obtener los datos del usuario.");
            ex.printStackTrace();
        }

    }

    //Cambiar a otra interfaz.
    private void cambiarActivity(Class<?> activity) {
        CambiarActivity.cambiar(this, activity);
    }
}