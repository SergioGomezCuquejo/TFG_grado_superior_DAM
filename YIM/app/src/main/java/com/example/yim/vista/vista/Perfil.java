package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.CambiarVisibilidad;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private FirebaseAuth auth;
    ImageButton guardarIB, editarIB, cancelarIB;
    ImageView imagenPerfil, imagenPerfilMenu;
    TextView nombreTV, email, genero, pesoTV, alturaTV, edadTV, politica, cerrarSesion;
    EditText nombreET, pesoET, alturaET, edadET;
    LinearLayout logros;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();
        FirebaseUser user = auth.getCurrentUser();


        //Controlar que si se ha cerrado sesión lleve a la interfaz InicioSesion.
        if(user == null){
            cambiarActivity(InicioSesion.class);
        }


        //Referencias de las vistas.
        guardarIB = findViewById(R.id.guardar_ib);
        editarIB = findViewById(R.id.editar_ib);
        cancelarIB = findViewById(R.id.cancelar_ib);

        imagenPerfil = findViewById(R.id.imagen_perfil);

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

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);




        //Listeners.
        guardarIB.setOnClickListener(this);
        editarIB.setOnClickListener(this);
        cancelarIB.setOnClickListener(this);

        imagenPerfil.setOnClickListener(this);

        logros.setOnClickListener(this);

        politica.setOnClickListener(this);
        cerrarSesion.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);



        //Mostrar datos.
        try{
            mostrarDatos();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar el perfil.");
            ex.printStackTrace();
        }
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

            case "imagen_perfil":
                subirImagen();
                break;

            case "politica":
                cambiarActivity(PoliticasPrivacidad.class);
                break;
            case "cerrar_sesion":
                cambiarActivity();
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

    //Método para almacenar los cambios realizados
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

    //Método para mostrar los datos del usuario.
    public void mostrarDatos(){
        try{
            firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
                @Override
                public void onCallback(TablaPerfil perfil) {
                    if(perfil != null){
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

                        Imagenes.mostrarImagenPerfil(Perfil.this, imagenPerfil);
                        Imagenes.mostrarImagenPerfil(Perfil.this, imagenPerfilMenu);

                    }else {
                        mostrarToast("No hay datos del perfil");
                    }
                }
            });
        }catch (Exception ex){
            mostrarToast("Error al obtener los datos del usuario.");
            ex.printStackTrace();
        }

    }

    //Método para guardar los cambios del perfil.
    private void guardarDatos(TablaPerfil perfil) {
        firebaseManager.actualizarPerfil(this, perfil, new FirebaseCallbackBoolean() {
            @Override
            public void onCallback(boolean accionRealizada) {
                if (accionRealizada) {
                    mostrarToast("Datos actualizados correctamente");
                } else {
                    mostrarToast("Error al actualizar los datos");
                }
            }
        });
    }

    //Método para mostrar la galería del usuario.
    private void subirImagen(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        startActivityForResult(i, 300);
        progressDialog = new ProgressDialog(this);
    }

    //Al recibir la imagen la actualiza.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if (requestCode == 300){
                Uri image_url = data.getData();
                Imagenes.subirImagen(this, progressDialog, "Actualizando foto de perfil..", "perfil/" + auth.getUid(), image_url);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //Método para llamar a CambiarVisibilidad.java. (Clase que permite hacer visible o invisible una vista)
    private void cambiarVisibilidad(View view, int visibilidad) {
        CambiarVisibilidad.cambiarVisibilidad(view, visibilidad);
    }

    //Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        CambiarActivity.cambiar(this, activity);
    }
    private void cambiarActivity() {
        CambiarActivity.cambiar(this, "Cerrar sesión", "¿Desea cerrar sesión?", "cerrar_sesion");
    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }




}