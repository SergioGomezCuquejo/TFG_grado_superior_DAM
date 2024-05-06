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
import android.widget.Spinner;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.EstadisticasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.HashMap;

public class Estadisticas extends AppCompatActivity implements View.OnClickListener {
    FirebaseManager firebaseManager;
    Spinner tipo;
    RecyclerView recyclerView;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    EstadisticasAdaptador adaptador;
    HashMap<String, String> musculosHM = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        firebaseManager = new FirebaseManager();

        //Referencias de las vistas
        tipo = findViewById(R.id.tipo);

        recyclerView = findViewById(R.id.recyclerView);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        //Listeners
        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Estadisticas.this, R.layout.spinner_items, getResources().getStringArray(R.array.tipos_busqueda));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tipo.setAdapter(adapter);

        mostrarEjercicios();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imagen_casa){
            cambiarActivity(Inicio.class);

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity(RutinaSemanal.class);

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity(this.getClass());

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity(Perfil.class);

        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    public void mostrarEjercicios(){
        try{
            firebaseManager.obtenerEjerciciosUsuario(this, new FirebaseCallbackEjerciciosUsuario() {
                @Override
                public void onCallback(ArrayList<TablaEjerciciosUsuario> ejerciciosUsuarios) {
                    firebaseManager.obtenerMusculosUsuario(Estadisticas.this, new FirebaseCallbackMusculosUsuario() {
                        @Override
                        public void onCallback(ArrayList<TablaMusculosUsuario> musculosUsuarios) {

                            for (TablaMusculosUsuario musculo : musculosUsuarios){
                                musculosHM.put(musculo.getNombre(), musculo.getColor_fondo());
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(Estadisticas.this));
                            adaptador = new EstadisticasAdaptador(Estadisticas.this, ejerciciosUsuarios, musculosHM);
                            recyclerView.setAdapter(adaptador);
                        }
                    });
                }
            });

        }catch (Exception ex){
            MostratToast.mostrarToast(this, "Error al obtener los musculos del usuario.");
            ex.printStackTrace();
        }
    }
}