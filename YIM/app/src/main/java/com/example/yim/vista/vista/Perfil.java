package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.CambiarVisibilidad;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseManager firebaseManager;
    FirebaseUser user;
    ImageButton guardarIB, editarIB, cancelarIB;
    TextView nombreTV, email, genero, pesoTV, alturaTV, edadTV, politica, cerrarSesion;
    EditText nombreET, pesoET, alturaET, edadET;
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
        guardarIB = findViewById(R.id.guardar_ib);
        editarIB = findViewById(R.id.editar_ib);
        cancelarIB = findViewById(R.id.cancelar_ib);

        nombreTV = findViewById(R.id.nombre_tv);
        nombreET = findViewById(R.id.nombre_et);
        email = findViewById(R.id.email);
        genero = findViewById(R.id.genero);
        pesoTV = findViewById(R.id.peso_tv);
        pesoET = findViewById(R.id.peso_et);
        alturaTV = findViewById(R.id.altura_tv);
        alturaET = findViewById(R.id.altura_et);
        edadTV = findViewById(R.id.edad_tv);
        edadET = findViewById(R.id.edad_et);

        politica = findViewById(R.id.politica);
        cerrarSesion = findViewById(R.id.cerrar_sesion);

        logros = findViewById(R.id.logros);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);


        //Listeners.
        guardarIB.setOnClickListener(this);
        editarIB.setOnClickListener(this);
        cancelarIB.setOnClickListener(this);

        logros.setOnClickListener(this);

        cerrarSesion.setOnClickListener(this);

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
        String id = getResources().getResourceEntryName(view.getId());

        switch (id) {
            case "logros":
                cambiarActivity(Logros.class);
                break;

            case "guardar_ib":
                guardarDatos(obtenerPerfil());
            case "cancelar_ib":
                cambiarVisibilidad(guardarIB, View.GONE);
                cambiarVisibilidad(cancelarIB, View.GONE);
                cambiarVisibilidad(nombreET, View.GONE);
                cambiarVisibilidad(pesoET, View.GONE);
                cambiarVisibilidad(alturaET, View.GONE);
                cambiarVisibilidad(edadET, View.GONE);

                cambiarVisibilidad(editarIB, View.VISIBLE);
                cambiarVisibilidad(nombreTV, View.VISIBLE);
                cambiarVisibilidad(pesoTV, View.VISIBLE);
                cambiarVisibilidad(alturaTV, View.VISIBLE);
                cambiarVisibilidad(edadTV, View.VISIBLE);
                break;

            case "editar_ib":
                cambiarVisibilidad(editarIB, View.GONE);
                cambiarVisibilidad(nombreTV, View.GONE);
                cambiarVisibilidad(pesoTV, View.GONE);
                cambiarVisibilidad(alturaTV, View.GONE);
                cambiarVisibilidad(edadTV, View.GONE);

                cambiarVisibilidad(guardarIB, View.VISIBLE);
                cambiarVisibilidad(cancelarIB, View.VISIBLE);
                cambiarVisibilidad(nombreET, View.VISIBLE);
                cambiarVisibilidad(pesoET, View.VISIBLE);
                cambiarVisibilidad(alturaET, View.VISIBLE);
                cambiarVisibilidad(edadET, View.VISIBLE);
                break;

            case "cerrar_sesion":
                CambiarActivity.cambiarAlerta(this, "Cerrar sesión", "¿Desea cerrar sesión?", "cerrar_sesion");
                break;
            case "imagen_casa":
                cambiarActivity(Inicio.class);
                break;
            case "imagen_calendario":
                cambiarActivity(RutinaSemanal.class);
                break;
            case "imagen_estadisticas":
                cambiarActivity(Estadisticas.class);
                break;
            case "imagen_usuario":
                cambiarActivity(this.getClass());
                break;
        }
    }

    private TablaPerfil obtenerPerfil(){
        TablaPerfil perfil = new TablaPerfil();
        perfil.setNombre(nombreET.getText().toString());
        perfil.setEmail(email.getText().toString());
        perfil.setGenero(genero.getText().toString());
        perfil.setPeso(Double.parseDouble(pesoET.getText().toString()));
        perfil.setAltura(Double.parseDouble(alturaET.getText().toString()));
        perfil.setEdad(Integer.parseInt(edadET.getText().toString()));
        return perfil;
    }

    //Mostrar los datos del usuario.
    public void mostrarDatos(){
        try{
            firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
                @Override
                public void onCallback(TablaPerfil perfil) {
                    nombreTV.setText(perfil.getNombre());
                    nombreET.setText(perfil.getNombre());
                    email.setText(perfil.getEmail());
                    genero.setText(perfil.getGenero());
                    pesoTV.setText(String.valueOf(perfil.getPeso()));
                    pesoET.setText(String.valueOf(perfil.getPeso()));
                    alturaTV.setText(String.valueOf(perfil.getAltura()));
                    alturaET.setText(String.valueOf(perfil.getAltura()));
                    edadTV.setText(String.valueOf(perfil.getEdad()));
                    edadET.setText(String.valueOf(perfil.getEdad()));
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

    private void cambiarVisibilidad(View view, int visibilidad) {
        CambiarVisibilidad.cambiarVisibilidad(view, visibilidad);
    }

    private void guardarDatos(TablaPerfil perfil) {
        if(firebaseManager.modificarPerfil(this, perfil)){
            MostratToast.mostrarToast(this, "Datos actualizados correctamente");
        }else{
            MostratToast.mostrarToast(this, "Error al actualizar los datos");
        }
    }
}