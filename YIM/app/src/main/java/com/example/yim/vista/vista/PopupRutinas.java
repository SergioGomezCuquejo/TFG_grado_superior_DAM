package com.example.yim.vista.vista;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackUri;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaInfoRutinaUsuario;
import com.example.yim.modelo.tablas.TablaRutinaActiva;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class PopupRutinas extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaRutinaUsuario rutinaUsuario;
    private static final int REQUEST_CODE_PR = 6;
    ImageView cancelar, editar;
    RelativeLayout imagenRL;
    ImageView imagenIV;
    LinearLayout activo_ll;
    SwitchCompat activo;
    TextView nombreRutinaTV, nombreTextoRutinaTV;
    FrameLayout nombreRutinaFL;
    Button borrar;
    boolean primeraVez;
    String accion;
    ProgressDialog progressDialog;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_rutinas);

        //Cambiar el tamaño de la pantalla.
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.75));


        //Inicializar instancias.
        firebaseManager = new FirebaseManager();
        Intent intent = getIntent();
        primeraVez = true;


        //Obtener la rutina que se ha seleccionado.
        if(intent.hasExtra("rutinaUsuario")) {
            rutinaUsuario = (TablaRutinaUsuario) intent.getSerializableExtra("rutinaUsuario");
        }else {
            mostrarToast("Errror al obtener la rutina");
            finish();
        }


        //Referencias de las vistas.
        cancelar = findViewById(R.id.cancelar);
        editar = findViewById(R.id.editar);

        nombreRutinaFL = findViewById(R.id.nombre_rutina_fl);
        nombreRutinaTV = findViewById(R.id.nombre_rutina_tv);
        imagenRL = findViewById(R.id.imagen_rl);
        imagenIV = findViewById(R.id.imagen_iv);
        nombreTextoRutinaTV = findViewById(R.id.nombre_texto_rutina_tv);

        activo_ll = findViewById(R.id.activo_ll);
        activo = findViewById(R.id.activo);

        borrar = findViewById(R.id.borrar);


        //Listeners.
        imagenRL.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        editar.setOnClickListener(this);
        borrar.setOnClickListener(this);

        activo_ll.setOnClickListener(this);

        activo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estaActivo) {
                cambiarActivo(estaActivo);
                if(activo.isChecked() && !rutinaUsuario.getInformacion().isActivo()){
                    accion = "Activar";
                    cambiarActivity( "¿Activar rutina?", "Al activar la rutina se desactivará la que ya esté activa y se reiniciarán los días de la rutina semanal");

                } else if (!activo.isChecked() && rutinaUsuario.getInformacion().isActivo()){
                    accion = "Desactivar";
                    cambiarActivity("¿Desactivar rutina?", "Al desactivar la rutina se reiniciarán los días de la rutina semanal");
                }
            }
        });

        //Mostrar datos
        try{
            mostrarInfo();

        } catch (Exception ex) {
            mostrarToast("Error al mostrar los datos de la rutina.");
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "cancelar":
                finish();
                break;

            case "editar":
                cambiarActivity(rutinaUsuario);
                break;

            case "imagen_rl":
                subirImagen();
                break;

            case "borrar":
                accion = "Eliminar";
                if(rutinaUsuario.getInformacion().isActivo()){
                    cambiarActivity("Al borrar la rutina también se eliminará la Rutina Semanal", "¿Desea continuar?");
                }else{
                    cambiarActivity("Borrar rutina", "¿Desea eliminar la rutina?");
                }
                break;
        }
    }

    //Método para cambiar el botón en marcado o desmarcado.
    @SuppressLint("UseCompatLoadingForColorStateLists")
    public void cambiarActivo(boolean estaActivo){
        if(estaActivo){
            activo.setBackgroundResource(R.drawable._style_rectangulo_verde__borde_blanco);
            activo.setThumbTintList(getResources().getColorStateList(R.color.fondo_clarito));

        }else{
            activo.setBackgroundResource(R.drawable._style_rectangulo_fondo_clarito__borde_blanco_50);
            activo.setThumbTintList(getResources().getColorStateList(R.color.gris));
        }
    }


    //Método para mostrar los datos de la rutina.
    private void mostrarInfo(){
        TablaInfoRutinaUsuario infoRutina = rutinaUsuario.getInformacion();
        String nombre;

        activo.setChecked(infoRutina.isActivo());
        cambiarActivo(activo.isChecked());

        if(infoRutina.getImagen() != null){
            Imagenes.mostrarImagen(this, infoRutina.getImagen(), imagenIV);
        }else{
            nombreRutinaFL.setVisibility(View.VISIBLE);
            imagenIV.setVisibility(View.GONE);
            nombre = rutinaUsuario.getInformacion().getNombre().toUpperCase();
            if (nombre.length() >= 3) {
                nombre = nombre.substring(0, 3);
            }
            nombreRutinaTV.setText(nombre);
        }
        nombreTextoRutinaTV.setText(infoRutina.getNombre());
    }

    // Método para eliminar la rutina.
    private void eliminarRutina(){
        try{
            firebaseManager.eliminarRutina(this, rutinaUsuario.getID(), new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if(accionRealizada){
                        finish();
                        mostrarToast("Rutina eliminada correctamente.");
                    }
                }
            });

        } catch (Exception ex) {
            mostrarToast("Error al eliminar la rutina del usuario.");
            ex.printStackTrace();
        }

    }

    // Método para eliminar la rutina activa.
    private void eliminarRutinaActiva(){
        try{
            firebaseManager.eliminarRutinaActiva(this);

        } catch (Exception ex) {
            mostrarToast("Error al eliminar la rutina del usuario.");
            ex.printStackTrace();
        }
    }

    // Método para activar/desactivar la rutina.
    private void activarRutina(boolean activar){
        try {
            firebaseManager.actualizarRutina(this, rutinaUsuario.getID(), activar);
        } catch (Exception ex) {
            mostrarToast("Error al actualizar la rutina del usuario.");
            ex.printStackTrace();
        }

    }
    private  void desactivarOtraRutina(String IDRutina){
        try {
            firebaseManager.actualizarRutina(this, IDRutina, false);
        } catch (Exception ex) {
            mostrarToast("Error al desactivar la rutina del usuario.");
            ex.printStackTrace();
        }
    }

    private  void desactivarRutinas(){
        try {
            firebaseManager.desactivarRutinas(this, new FirebaseCallbackRutinaUsuario() {
                @Override
                public void onCallback(TablaRutinaUsuario rutina) {
                    if(rutina != null){
                        desactivarOtraRutina(rutina.getID());
                        eliminarRutinaActiva();
                    }
                }
            });
        } catch (Exception ex) {
            mostrarToast("Error al desactivar las rutinas del usuario.");
            ex.printStackTrace();
        }
    }
    private void crearRutinaActiva(){
        TablaRutinaActiva rutinaActiva = new TablaRutinaActiva();
        rutinaActiva.setIdRutina(rutinaUsuario.getID());

        ArrayList<TablaDiaRutinaActiva> semana = new ArrayList<>();
        for(TablaDiaRutinaUsuario diaRutinaUsuario : rutinaUsuario.getSemana()){
            semana.add(new TablaDiaRutinaActiva(diaRutinaUsuario));
        }
        rutinaActiva.setSemana(semana);
        firebaseManager.agregarRutinaActiva(this, rutinaActiva);
    }

    //Método para mostrar la galería del usuario.
    private void subirImagen(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        startActivityForResult(i, 300);
        progressDialog = new ProgressDialog(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            // Al recibir la imagen se actualizará.
            if (requestCode == 300){
                Uri image_url = data.getData();
                Imagenes.subirImagen(this, progressDialog, "Actualizando la imagen de la rutina..", "rutina/" + rutinaUsuario.getID(), image_url, new FirebaseCallbackUri() {
                    @Override
                    public void onCallback(Uri uri) {
                        finish();
                        Imagenes.mostrarImagen(PopupRutinas.this, uri.toString(), imagenIV);
                    }
                });


            // Si la respuesta es del PopupAlerta.java y es true se cerrará sesión.
            }else if (requestCode == REQUEST_CODE_PR) {
                boolean resultado = data.getBooleanExtra("resultado", false);
                if(resultado){
                    if (accion.equals("Eliminar")){
                        if(rutinaUsuario.getInformacion().isActivo()){
                            eliminarRutinaActiva();
                        }
                        eliminarRutina();

                    }else {
                        if(accion.equals("Activar")){
                            desactivarRutinas();
                            activarRutina(true);
                            crearRutinaActiva();
                        }else{
                            activarRutina(false);
                            eliminarRutinaActiva();
                        }
                        finish();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //Método para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(TablaRutinaUsuario rutinaUsuario) {
        CambiarActivity.cambiar(this, CrearRutinas.class, rutinaUsuario);
    }
    private void cambiarActivity(String titulo, String texto) {
        Intent intent = new Intent(this, PopupAlerta.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("texto", texto);
        startActivityForResult(intent, REQUEST_CODE_PR);
    }


    //Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }

}