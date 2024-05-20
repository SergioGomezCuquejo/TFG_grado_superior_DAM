package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.RutinaActivaAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackRutinaActiva;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;

import java.util.ArrayList;
import java.util.HashMap;

public class RutinaSemanal extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    HashMap<String, ColoresMusculoUsuario> musculosHM;
    RecyclerView recyclerView;
    TextView semanaTV, noRutinaTV;
    ScrollView scrollView;
    LinearLayout  imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    RutinaActivaAdaptador adaptador;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_semanal);

        firebaseManager = new FirebaseManager();
        musculosHM = new HashMap<>();

        //Referencias de las vistas
        semanaTV = findViewById(R.id.semana_tv);
        recyclerView = findViewById(R.id.dias);

        scrollView = findViewById(R.id.scrollView);
        noRutinaTV = findViewById(R.id.no_rutina_tv);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
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

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.imagen_estadisticas) {
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
            public void onCallback(ArrayList<TablaDiaRutinaActiva> rutinaActiva) {
                if(!rutinaActiva.isEmpty()){
                    mostrarSemana(rutinaActiva);
                }else{
                    scrollView.setVisibility(View.GONE);
                    noRutinaTV.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void mostrarSemana(ArrayList<TablaDiaRutinaActiva> rutinaActiva){
        semanaTV.setText("Semana " + ((rutinaActiva.get(0).getDia() - 1) / 7 + 1));

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
}