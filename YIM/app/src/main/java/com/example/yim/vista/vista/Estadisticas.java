package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.EstadisticasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjercicioUsuario;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.HashMap;

public class Estadisticas extends AppCompatActivity implements View.OnClickListener {
    //todo hacer este, este petaba acuerdate
    FirebaseManager firebaseManager;
    ImageButton buscar;
    Spinner tipo;
    RecyclerView recyclerView;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    ScrollView scrollView;
    TextView noEstadisticasTV;
    EstadisticasAdaptador adaptador;
    HashMap<String, String> musculosHM = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        firebaseManager = new FirebaseManager();

        //Referencias de las vistas
        buscar = findViewById(R.id.buscar);
        tipo = findViewById(R.id.tipo);

        recyclerView = findViewById(R.id.recyclerView);

        scrollView = findViewById(R.id.scrollView);
        noEstadisticasTV = findViewById(R.id.no_estadisticas_tv);

        imagen_casa = findViewById(R.id.imagen_casa_menu);
        imagen_calendario = findViewById(R.id.imagen_calendario_menu);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas_menu);
        imagen_usuario = findViewById(R.id.imagen_usuario_menu);

        //Listeners
        buscar.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Estadisticas.this, R.layout.spinner_items, getResources().getStringArray(R.array.tipos_busqueda));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tipo.setAdapter(adapter);


        try{
            obtenerEjercicios();
        }catch (Exception ex) {
            MostratToast.mostrarToast(this, "Error al obtener los ejercicios del usuario.");
            ex.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa_menu){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario_menu) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas_menu) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.imagen_usuario_menu) {
            cambiarActivity(Perfil.class);

        } else if (id == R.id.buscar) {
            MostratToast.mostrarToast(this, String.valueOf(tipo.getSelectedItemPosition()));

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    public void obtenerEjercicios(){
        firebaseManager.obtenerEjerciciosUsuarioConEstadisticas(this, new FirebaseCallbackEjerciciosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaEjercicioUsuario> ejerciciosUsuarios) {
                if(ejerciciosUsuarios != null && ejerciciosUsuarios.size() > 0){
                    obtenerMusculos(ejerciciosUsuarios);
                }else{
                    scrollView.setVisibility(View.GONE);
                    noEstadisticasTV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void obtenerMusculos(ArrayList<TablaEjercicioUsuario> ejercicios){
        firebaseManager.obtenerMusculosUsuario(Estadisticas.this, new FirebaseCallbackMusculosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaMusculoUsuario> musculosUsuarios) {

                for (TablaMusculoUsuario musculo : musculosUsuarios){
                    musculosHM.put(musculo.getNombre(), musculo.getColor_fondo());
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(Estadisticas.this));
                adaptador = new EstadisticasAdaptador(Estadisticas.this, ejercicios, musculosHM);
                recyclerView.setAdapter(adaptador);
            }
        });
    }
}