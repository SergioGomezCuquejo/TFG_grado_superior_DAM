package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.CrearRutinasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaInfoRutinasUsuario;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaRutinasUsuario;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.HashMap;

public class CrearRutinas extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    Intent intent;
    TablaRutinasUsuario rutinaUsuario;
    ImageView atras, guardar;
    EditText nombre;
    RecyclerView recyclerView;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    CrearRutinasAdaptador adaptador;
    HashMap<String, ColoresMusculoUsuario> musculosHM;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);

        firebaseManager = new FirebaseManager();
        musculosHM = new HashMap<>();
        intent = getIntent();

        if(intent.hasExtra("rutinaUsuario")) {
            rutinaUsuario = (TablaRutinasUsuario) intent.getSerializableExtra("rutinaUsuario");
        } else {
            rutinaUsuario = new TablaRutinasUsuario();
            ArrayList<TablaDiaRutinaUsuario> semana = new ArrayList<TablaDiaRutinaUsuario>();
            for(int i = 0; i < 7; i++){
                semana.add(new TablaDiaRutinaUsuario(i+1));
            }
            rutinaUsuario.setSemana(semana);
        }

        //Referencias de las vistas
        atras = findViewById(R.id.atras);
        guardar = findViewById(R.id.guardar);

        nombre = findViewById(R.id.nombre);

        recyclerView = findViewById(R.id.dias);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        atras.setOnClickListener(this);
        guardar.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        mostrarSemana(rutinaUsuario);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.atras){
            finish();

        } else if (id == R.id.guardar){
            guardar();

        } else if (id == R.id.imagen_casa){
            cambiarActivity("ir_a_inicio");

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity("ir_a_rutina_semanal");

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity("ir_a_estadisticas");

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity("ir_a_perfil");

        }
    }
    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    private void cambiarActivity(String ira) {
        cambiarAlerta(this, "Descartar cambios", "¿Desea descartar los cambios no guradados?", ira);
    }


    private void mostrarSemana(TablaRutinasUsuario rutina){
        if(rutina.getInformacion() != null){
            nombre.setText(rutina.getInformacion().getNombre());
        }
        firebaseManager.obtenerMusculosUsuario(CrearRutinas.this, new FirebaseCallbackMusculosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaMusculosUsuario> musculosUsuarios) {

                for (TablaMusculosUsuario musculo : musculosUsuarios){
                    musculosHM.put(musculo.getNombre(), new ColoresMusculoUsuario(musculo.getColor_fondo(), musculo.getColor_fuente()));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(CrearRutinas.this));
                adaptador = new CrearRutinasAdaptador(CrearRutinas.this, rutina, musculosHM);
                recyclerView.setAdapter(adaptador);
            }
        });
    }

    private void guardar(){
        boolean nueva = false;
        if(nombre.getText().toString().equals("")){
            MostratToast.mostrarToast(this, "Escribe un nombre de rutina");
        }else{
            if (rutinaUsuario.getInformacion() == null){
                nueva = true;
            }
            rutinaUsuario.setInformacion(new TablaInfoRutinasUsuario(nombre.getText().toString()));
            if(nueva){
                if(firebaseManager.agregarRutina(this, rutinaUsuario)){
                    MostratToast.mostrarToast(this, "Rutina creada correctamente");
                    finish();
                    cambiarActivity(VerRutinas.class);

                }else {
                    MostratToast.mostrarToast(this, "Error al crear la rutina");
                }
            }else{
                if(firebaseManager.actualizarRutina(this, rutinaUsuario)){
                    MostratToast.mostrarToast(this, "Rutina actualizada correctamente");
                    finish();
                    cambiarActivity(VerRutinas.class);

                }else {
                    MostratToast.mostrarToast(this, "Error al actualizar la rutina");
                }
            }
        }
    }


}