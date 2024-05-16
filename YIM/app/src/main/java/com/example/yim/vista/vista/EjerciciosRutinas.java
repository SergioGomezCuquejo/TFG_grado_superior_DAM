package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.CambiarActivity.cambiarAlerta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.EjerciciosRutinasAdaptador;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaRutinasUsuario;
import com.example.yim.vista.controlador.CambiarActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class EjerciciosRutinas extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    TablaRutinasUsuario rutinaUsuario;
    TablaDiaRutinaUsuario diaRutinaUsuario;
    int dia;
    HashMap<String, ColoresMusculoUsuario> musculosSemana;
    ImageView atras, cancelar;
    LinearLayout musculos, cambiarMusculos, musculoDerechaLL;
    ViewFlipper viewFlipper;
    RelativeLayout cambiarEjercicios;
    Space space1, space2;
    RecyclerView recyclerView;
    FrameLayout imagen_casa, imagen_calendario, imagen_estadisticas, imagen_usuario;
    TextView musculoIzquierda, musculoCentro, musculoDerecha, descanso, musculoElegidoIzquierda, musculoElegidoCentro, musculoElegidoDerecha;
    ImageView agregar_ejercicio;
    EjerciciosRutinasAdaptador adaptador;
    CheckBox todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps;
    boolean izquierda, centro, derecha;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_rutinas);

        intent = getIntent();
        rutinaUsuario = (TablaRutinasUsuario) intent.getSerializableExtra("rutinaUsuario");
        dia = intent.getIntExtra("dia", 0);
        musculosSemana = (HashMap<String, ColoresMusculoUsuario>) intent.getSerializableExtra("musculosSemana");

        diaRutinaUsuario = rutinaUsuario.getSemana().get(dia);
        izquierda = false;
        centro = false;
        derecha = false;


        //Referencias de las vistas
        atras = findViewById(R.id.atras);

        viewFlipper = findViewById(R.id.viewFlipper);
        musculos = findViewById(R.id.musculos);
        musculoIzquierda = findViewById(R.id.musculo_izquierda);
        musculoCentro = findViewById(R.id.musculo_centro);
        musculoDerecha = findViewById(R.id.musculo_derecha);

        todoElCuerpo = findViewById(R.id.todo_el_cuerpo);
        trenSuperior = findViewById(R.id.tren_superior);
        espalda = findViewById(R.id.espalda);
        biceps = findViewById(R.id.biceps);
        cuadriceps = findViewById(R.id.cuadriceps);

        cancelar = findViewById(R.id.cancelar);
        musculoDerechaLL = findViewById(R.id.musculo_derecha_ll);
        cambiarEjercicios = findViewById(R.id.cambiar_ejercicios);
        cambiarMusculos = findViewById(R.id.cambiar_musculos);

        space1 = findViewById(R.id.space1);
        space2 = findViewById(R.id.space2);
        musculoElegidoIzquierda = findViewById(R.id.musculo_elegido_izquierda);
        musculoElegidoCentro = findViewById(R.id.musculo_elegido_centro);
        musculoElegidoDerecha = findViewById(R.id.musculo_elegido_derecha);

        descanso = findViewById(R.id.descanso);
        recyclerView = findViewById(R.id.ejericicos);

        imagen_casa = findViewById(R.id.imagen_casa);
        imagen_calendario = findViewById(R.id.imagen_calendario);
        imagen_estadisticas = findViewById(R.id.imagen_estadisticas);
        imagen_usuario = findViewById(R.id.imagen_usuario);

        agregar_ejercicio = findViewById(R.id.agregar_ejercicio);

        //Listeners
        atras.setOnClickListener(this);

        musculos.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        musculoElegidoIzquierda.setOnClickListener(this);
        musculoElegidoCentro.setOnClickListener(this);
        musculoElegidoDerecha.setOnClickListener(this);

        imagen_casa.setOnClickListener(this);
        imagen_calendario.setOnClickListener(this);
        imagen_estadisticas.setOnClickListener(this);
        imagen_usuario.setOnClickListener(this);

        agregar_ejercicio.setOnClickListener(this);

        //Cambiar los checkBox al marcarse
        CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                if (isChecked) {

                    cambiarColores((TextView) buttonView, getResources().getColor(R.color.blanco));
                    if (id == R.id.todo_el_cuerpo) {
                        trenSuperior.setChecked(false);
                        espalda.setChecked(false);
                        biceps.setChecked(false);
                        cuadriceps.setChecked(false);

                    } else{
                        todoElCuerpo.setChecked(false);

                        if (id == R.id.tren_superior) {
                            espalda.setChecked(false);
                            biceps.setChecked(false);

                        } else if (id == R.id.biceps || id == R.id.espalda) {
                            trenSuperior.setChecked(false);
                        }
                    }

                } else {
                    cambiarColores((TextView) buttonView, getResources().getColor(R.color.negro_oscuro));
                }
                musculoElegido(id, buttonView);
                viewFlipper.showPrevious();

            }
        };

        todoElCuerpo.setOnCheckedChangeListener(checkBoxListener);
        trenSuperior.setOnCheckedChangeListener(checkBoxListener);
        espalda.setOnCheckedChangeListener(checkBoxListener);
        biceps.setOnCheckedChangeListener(checkBoxListener);
        cuadriceps.setOnCheckedChangeListener(checkBoxListener);


        mostrarMusculos();
        mostrarEjercicios();
    }

    private void ponerCheckBoxFalse(){
        CheckBox[] checkBoxes = {todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps};

        for (CheckBox checkBox : checkBoxes) {
            checkBox.setChecked(false);
        }
    }
    private void musculoElegido(int id, CompoundButton buttonView){
        String musculoElegido = getResources().getResourceEntryName(id).replace("_", " ");
        musculoElegido = musculoElegido.substring(0, 1).toUpperCase() + musculoElegido.substring(1);
        switch (musculoElegido){
            case  "Biceps":
                musculoElegido = "Bíceps";
                break;
            case "Cuadriceps":
                musculoElegido = "Cuádriceps";
                break;
        }
        if(izquierda){
            musculoElegidoIzquierda.setText(musculoElegido);
            musculoIzquierda.setText(musculoElegido);
        } else if(centro){
            musculoElegidoCentro.setText(musculoElegido);
            if(musculoCentro.getVisibility() == View.GONE){
                musculoIzquierda.setBackgroundResource(R.drawable._style2_borde_blanco_izquierda);
                musculoCentro.setVisibility(View.VISIBLE);
                musculoDerechaLL.setVisibility(View.VISIBLE);
            }
            musculoCentro.setText(musculoElegido);
        } else if (derecha){
            musculoElegidoDerecha.setText(musculoElegido);
            if(musculoDerecha.getVisibility() == View.GONE){
                musculoCentro.setBackgroundResource(R.drawable._style2_borde_blanco_0);
                musculoDerecha.setVisibility(View.VISIBLE);
            }
            musculoDerecha.setText(musculoElegido);
        }
    }

    public void cambiarColores(View view, int color){
        ((TextView) view).setTextColor(color);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.atras) {
            ArrayList<String> arrayMusculos = new ArrayList<String>();
            arrayMusculos.add(musculoIzquierda.getText().toString());
            if(musculoCentro.getVisibility() == View.VISIBLE){
                arrayMusculos.add(musculoCentro.getText().toString());
            }
            if(musculoDerecha.getVisibility() == View.VISIBLE){
                arrayMusculos.add(musculoDerecha.getText().toString());
            }
            rutinaUsuario.getSemana().get(dia).setMusculos(arrayMusculos);
            rutinaUsuario.getSemana().get(dia).setEjercicios(adaptador.obtenerEjerciciosActualizados());
            CambiarActivity.cambiar(this, CrearRutinas.class, rutinaUsuario);

        } else if (id == R.id.musculos || id == R.id.cancelar) {
            cambiarVisibilidad(cambiarEjercicios);
            cambiarVisibilidad(viewFlipper);

        } else if (id == R.id.agregar_ejercicio) {
            cambiarActivity(PopupAgregarEjercicio.class);

        } else if (id == R.id.imagen_casa){
            cambiarActivity("ir_a_inicio");

        } else if (id == R.id.musculo_elegido_izquierda || id == R.id.musculo_elegido_centro || id == R.id.musculo_elegido_derecha){
            viewFlipper.showNext();
            if(id == R.id.musculo_elegido_izquierda) {
                izquierda = true;
                centro = false;
                derecha = false;

            } else if(id == R.id.musculo_elegido_centro) {
                centro = true;
                izquierda = false;
                derecha = false;
            } else {
                derecha = true;
                centro = false;
                izquierda = false;
            }

        } else if (id == R.id.imagen_calendario) {
            cambiarActivity("ir_a_rutina_semanal");

        } else if (id == R.id.imagen_estadisticas) {
            cambiarActivity("ir_a_estadisticas");

        } else if (id == R.id.imagen_usuario) {
            cambiarActivity("ir_a_perfil");

        }
    }
    private void cambiarVisibilidad(View view){
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    private void cambiarActivity(String ira) {
        cambiarAlerta(this, "Descartar cambios", "¿Desea descartar los cambios no guradados?", ira);
    }

    private void mostrarMusculos(){
        ArrayList<String> musculosUsuario = diaRutinaUsuario.getMusculos();
        if (musculosUsuario.size() >= 2){
            musculoIzquierda.setBackgroundResource(R.drawable._style2_borde_blanco_izquierda);
            cambiarVisibilidad(musculoCentro);

            cambiarVisibilidad(musculoElegidoCentro);
            cambiarVisibilidad(musculoDerechaLL);
            if(musculosUsuario.size() == 3){
                musculoCentro.setBackgroundResource(R.drawable._style2_borde_blanco_0);
                cambiarVisibilidad(musculoDerecha);
            }
        } else if (musculosUsuario.get(0).equals("Descanso")){
            space1.setVisibility(View.VISIBLE);
            space2.setVisibility(View.VISIBLE);
        } else{
            cambiarVisibilidad(musculoElegidoCentro);
        }
        String musculo;
        String fondo = "#FFFFFFFF";
        String fuente = "#FF000000";
        for (int i = 0; i < musculosUsuario.size(); i++){
            musculo = musculosUsuario.get(i);
            if(musculosSemana.containsKey(musculo)){
                fondo = musculosSemana.get(musculo).getColor_fondo();
                fuente = musculosSemana.get(musculo).getColor_fuente();
                musculosSemana.put(musculo, musculosSemana.get(musculo));
            }
            if(i == 0){
                musculoIzquierda.setText(musculo);
                musculoIzquierda.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoIzquierda.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));

                musculoElegidoIzquierda.setText(musculo);
                musculoElegidoIzquierda.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoElegidoIzquierda.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
            } else if (i == 1) {
                musculoCentro.setText(musculo);
                musculoCentro.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoCentro.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));

                musculoElegidoCentro.setText(musculo);
                musculoElegidoCentro.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoElegidoCentro.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
            }else {
                musculoDerecha.setText(musculo);
                musculoDerecha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoDerecha.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));

                musculoElegidoDerecha.setText(musculo);
                musculoElegidoDerecha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoElegidoDerecha.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
            }
        }
    }
    private void mostrarEjercicios(){
        if(diaRutinaUsuario.getEjercicios() != null){
            recyclerView.setLayoutManager(new LinearLayoutManager(EjerciciosRutinas.this));
            adaptador = new EjerciciosRutinasAdaptador(EjerciciosRutinas.this, diaRutinaUsuario, recyclerView);
            recyclerView.setAdapter(adaptador);
        }else {
            descanso.setVisibility(View.VISIBLE);
        }
    }
}