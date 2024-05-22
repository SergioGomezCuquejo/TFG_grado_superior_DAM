package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.RutinaActivaAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaActiva;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaRutinaActiva;

import java.util.ArrayList;
import java.util.HashMap;

public class RutinaSemanal extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    HashMap<String, ColoresMusculoUsuario> musculosHM;
    RecyclerView recyclerView;
    TextView noRutinaTV;
    RelativeLayout relativeLayout;
    ImageView agregarSemana;
    LinearLayout  imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    RutinaActivaAdaptador adaptador;
    String idRutina;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_semanal);

        firebaseManager = new FirebaseManager();
        musculosHM = new HashMap<>();

        //Referencias de las vistas
        recyclerView = findViewById(R.id.dias);

        relativeLayout = findViewById(R.id.relativeLayout);
        noRutinaTV = findViewById(R.id.no_rutina_tv);
        agregarSemana = findViewById(R.id.agregar_semana);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        agregarSemana.setOnClickListener(this);
        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);
        obtenerSemana ();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa) {
            cambiarActivity(Inicio.class);

        } else if (id == R.id.agregar_semana) {
            agregarSemana();

        }  else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(Estadisticas.class);

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    private void obtenerSemana () {
        firebaseManager.obtenerRutinaActiva(this, new FirebaseCallbackRutinaActiva() {
            @Override
            public void onCallback(TablaRutinaActiva rutinaActiva) {
                if(rutinaActiva != null){
                    idRutina = rutinaActiva.getIdRutina();
                    mostrarSemana(rutinaActiva.getSemana());
                }else{
                    relativeLayout.setVisibility(View.GONE);
                    noRutinaTV.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void mostrarSemana(ArrayList<TablaDiaRutinaActiva> rutinaActiva){
        firebaseManager.obtenerMusculosUsuario(RutinaSemanal.this, new FirebaseCallbackMusculosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaMusculosUsuario> musculosUsuarios) {

                for (TablaMusculosUsuario musculo : musculosUsuarios){
                    musculosHM.put(musculo.getNombre(), new ColoresMusculoUsuario(musculo.getColor_fondo(), musculo.getColor_fuente()));
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(RutinaSemanal.this));
                adaptador = new RutinaActivaAdaptador(RutinaSemanal.this, rutinaActiva, musculosHM);
                recyclerView.setAdapter(adaptador);
            }
        });
    }

    private void agregarSemana(){
        firebaseManager.modificarRutinaActiva(this, idRutina);
    }
}