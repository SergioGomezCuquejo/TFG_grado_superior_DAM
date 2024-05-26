package com.example.yim.vista.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.EjerciciosRutinasAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EjerciciosRutinas extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaRutinaUsuario rutinaUsuario;
    private TablaDiaRutinaUsuario diaRutinaUsuario;
    private HashMap<String, ColoresMusculoUsuario> musculosSemana;
    private Class<?> irA;
    private static final int REQUEST_CODE_ER = 2;
    ImageView atras, cancelar, agregarEjercicio, imagenPerfilMenu;
    LinearLayout musculos, cambiarMusculos, musculoDerechaLL;
    ViewFlipper viewFlipper;
    RelativeLayout cambiarEjercicios;
    RecyclerView recyclerView;
    ProgressBar cargando;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    TextView musculoIzquierda, musculoCentro, musculoDerecha, descanso, musculoElegidoIzquierda, musculoElegidoCentro, musculoElegidoDerecha;
    CheckBox todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps;
    boolean cambiar;
    int dia;
    ArrayList<String> musculosArray = new ArrayList<>();

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_rutinas);


        //Inicializar instancias.
        firebaseManager = new FirebaseManager();
        Intent intent = getIntent();
        cambiar = false;

        //Obtener la rutina que se ha seleccionado.
        if (intent.hasExtra("rutinaUsuario")) {
            rutinaUsuario = (TablaRutinaUsuario) intent.getSerializableExtra("rutinaUsuario");
            dia = intent.getIntExtra("dia", 0);
            musculosSemana = (HashMap<String, ColoresMusculoUsuario>) intent.getSerializableExtra("musculosSemana");
            diaRutinaUsuario = rutinaUsuario.getSemana().get(dia);
        } else {
            rutinaUsuario = null;
        }


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        atras = findViewById(R.id.atras_iv);

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

        musculoElegidoIzquierda = findViewById(R.id.musculo_elegido_izquierda);
        musculoElegidoCentro = findViewById(R.id.musculo_elegido_centro);
        musculoElegidoDerecha = findViewById(R.id.musculo_elegido_derecha);

        descanso = findViewById(R.id.descanso_bt);
        recyclerView = findViewById(R.id.ejericicos);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);

        agregarEjercicio = findViewById(R.id.agregar_ejercicio);


        //Listeners.
        atras.setOnClickListener(this);

        musculos.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        musculoElegidoIzquierda.setOnClickListener(this);
        musculoElegidoCentro.setOnClickListener(this);
        musculoElegidoDerecha.setOnClickListener(this);

        agregarEjercicio.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);



        //Cambiar los checkBox al marcarse.
        CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                String musculo = obtenerMusculo(id);
                ArrayList<String> musculosEliminar;

                if (isChecked) {
                    if(musculosArray.size() >= 3){
                        musculosArray.remove(musculosArray.size()-1);
                    }
                    musculosArray.add(musculo);

                    cambiarColores((TextView) buttonView, getResources().getColor(R.color.blanco));
                    if (id == R.id.todo_el_cuerpo) {
                        trenSuperior.setChecked(false);
                        espalda.setChecked(false);
                        biceps.setChecked(false);
                        cuadriceps.setChecked(false);

                        musculosEliminar = new ArrayList<>(Arrays.asList("Tren superior", "Cuádriceps", "Bíceps", "Espalda"));
                        musculosArray.removeAll(musculosEliminar);

                    } else{
                        todoElCuerpo.setChecked(false);

                        if (id == R.id.tren_superior) {
                            espalda.setChecked(false);
                            biceps.setChecked(false);

                            musculosArray.remove("Bíceps");
                            musculosArray.remove("Espalda");

                        } else if (id == R.id.biceps || id == R.id.espalda) {
                            trenSuperior.setChecked(false);
                        }
                    }
                } else {
                    musculosArray.remove(musculo);

                    cambiarColores((TextView) buttonView, getResources().getColor(R.color.negro_oscuro));
                }
                if(cambiar){
                    viewFlipper.showPrevious();
                    cambiar = false;
                }
                actualizarMusculos(musculosArray);

            }
        };

        todoElCuerpo.setOnCheckedChangeListener(checkBoxListener);
        trenSuperior.setOnCheckedChangeListener(checkBoxListener);
        espalda.setOnCheckedChangeListener(checkBoxListener);
        biceps.setOnCheckedChangeListener(checkBoxListener);
        cuadriceps.setOnCheckedChangeListener(checkBoxListener);


        //Mostrar datos.
        try {
            mostrarImagenPerfil();
            actualizarMusculos(rutinaUsuario.getSemana().get(dia).getMusculos());
            marcarMusculos();
            mostrarEjercicios();
        } catch (Exception ex) {
            mostrarToast("Error al mostrar los ejercicios de la rutina");
            ex.printStackTrace();
        }


        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id) {
            case "atras_iv":
                guardarMusculos();
                if (diaRutinaUsuario.getEjercicios() != null) {
                    rutinaUsuario.getSemana().get(dia).setEjercicios(((EjerciciosRutinasAdaptador) recyclerView.getAdapter()).obtenerEjerciciosActualizados());
                }
                cambiarActivity(rutinaUsuario);
                break;

            case "musculos":
            case "cancelar":
                cambiarVisibilidad(cambiarEjercicios);
                cambiarVisibilidad(viewFlipper);
                break;

            case "agregar_ejercicio":
                guardarMusculos();
                cambiarActivity(musculosSemana);
                break;

            case "musculo_elegido_izquierda":
            case "musculo_elegido_centro":
            case "musculo_elegido_derecha":
                viewFlipper.showNext();
                cambiar = true;
                break;

            case "imagen_casa_menu":
                irA = Inicio.class;
                cambiarActivity();
                break;
            case "imagen_calendario_menu":
                irA = RutinaSemanal.class;
                cambiarActivity();
                break;
            case "imagen_estadisticas_menu":
                irA = Estadisticas.class;
                cambiarActivity();
                break;
            case "imagen_usuario_menu":
                irA = Perfil.class;
                cambiarActivity();
                break;
        }
    }

    private String obtenerMusculo(int id) {
        String idString = getResources().getResourceEntryName(id);
        String musculo = idString.replace('_', ' ');
        musculo = musculo.substring(0, 1).toUpperCase() + musculo.substring(1);

        switch (musculo) {
            case "Biceps":
                musculo = "Bíceps";
                break;
            case "Cuadriceps":
                musculo = "Cuádriceps";
                break;
        }
        return musculo;
    }

    private void marcarMusculos(){
        CheckBox checkBox;
        for(String musculo : rutinaUsuario.getSemana().get(dia).getMusculos()){
            musculo = musculo.replace(' ', '_');
            switch (musculo) {
                case "Bíceps":
                    musculo = "biceps";
                    break;
                case "Cuádriceps":
                    musculo = "cuadriceps";
                    break;
            }
            checkBox = findViewById(getResources().getIdentifier(musculo.toLowerCase(), "id", getPackageName()));

            if (checkBox != null) {
                checkBox.setChecked(true);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void actualizarMusculos(ArrayList<String> musculosArray){
        String musculo;

        musculoDerechaLL.setVisibility(View.GONE);
        musculoDerecha.setVisibility(View.GONE);
        musculoCentro.setVisibility(View.GONE);
        musculoIzquierda.setBackgroundResource(R.drawable._style2_borde_blanco_10);

        if(musculosArray.size() >= 1){
            musculo = musculosArray.get(0);
            musculoElegidoIzquierda.setText(musculo);
            musculoIzquierda.setText(musculo);
            colorMusculo(musculo, musculoElegidoIzquierda, musculoIzquierda);


            if(musculosArray.size() >= 2){
                musculoIzquierda.setBackgroundResource(R.drawable._style2_borde_blanco_izquierda);
                musculo = musculosArray.get(1);
                musculoElegidoCentro.setText(musculo);
                musculoDerecha.setVisibility(View.VISIBLE);
                musculoDerecha.setText(musculo);

                colorMusculo(musculo, musculoElegidoCentro, musculoDerecha);

                musculoDerechaLL.setVisibility(View.VISIBLE);

                if(musculosArray.size() == 3){
                    musculoCentro.setVisibility(View.VISIBLE);
                    musculoCentro.setText(musculo);
                    colorMusculo(musculo, musculoElegidoCentro, musculoCentro);

                    musculo = musculosArray.get(2);
                    musculoElegidoDerecha.setText(musculo);
                    musculoDerecha.setText(musculo);

                    colorMusculo(musculo, musculoElegidoDerecha, musculoDerecha);
                }
            }
        }else{
            musculoElegidoIzquierda.setText("DESCANSO");
            musculoIzquierda.setText("DESCANSO");
        }
    }

    private void colorMusculo(String musculo, TextView textViewElegido, TextView textViewArriba) {
        int colorFuenteInt;
        int colorFondoInt;

        if(musculosSemana.containsKey(musculo)){
            colorFuenteInt = Color.parseColor(musculosSemana.get(musculo).getColor_fuente());
            colorFondoInt = Color.parseColor(musculosSemana.get(musculo).getColor_fondo());

        }else {
            colorFuenteInt = Color.parseColor("#C5C4C4");
            colorFondoInt = Color.parseColor("#FF000000");

        }


        textViewElegido.setTextColor(ColorStateList.valueOf(colorFuenteInt));
        textViewElegido.setBackgroundTintList(ColorStateList.valueOf(colorFondoInt));

        textViewArriba.setTextColor(ColorStateList.valueOf(colorFuenteInt));
        textViewArriba.setBackgroundTintList(ColorStateList.valueOf(colorFondoInt));
    }
    private void guardarMusculos(){
        ArrayList<String> musculos = new ArrayList<>();
        if(musculosArray.isEmpty()){
            musculos.add("Descanso");
        }else  {
            musculos = musculosArray;
        }
        rutinaUsuario.getSemana().get(dia).setMusculos(musculos);
    }

    // Método para mostrar los ejercicios desde un adaptador.
    private void mostrarEjercicios() {
        if (diaRutinaUsuario.getEjercicios() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(EjerciciosRutinas.this));
            EjerciciosRutinasAdaptador adaptador = new EjerciciosRutinasAdaptador(EjerciciosRutinas.this, diaRutinaUsuario, recyclerView);
            recyclerView.setAdapter(adaptador);
        } else {
            descanso.setVisibility(View.VISIBLE);
        }
    }


    // Método para hacer visible o invisible.
    private void cambiarVisibilidad(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }


    // Método para el cambio de color del texto.
    public void cambiarColores(View view, int color) {
        ((TextView) view).setTextColor(color);
    }


    // Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    private void mostrarImagenPerfil() {
        try{

            firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
                @Override
                public void onCallback(TablaPerfil perfil) {
                    if (perfil.getImagen() != null && !perfil.getImagen().equals("")) {
                        Imagenes.urlImagenPerfil = perfil.getImagen();
                        Imagenes.mostrarImagenPerfil(EjerciciosRutinas.this, imagenPerfilMenu);
                    }

                }
            });
        } catch (Exception ex) {
            mostrarToast("Error al mostrar la foto de perfil");
            ex.printStackTrace();
        }

    }


    // Método para obtener la respuesta del botón que se pulsa en PopupAlerta.java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ER) {
                boolean resultado = data.getBooleanExtra("resultado", false);
                if(resultado){
                    cambiarActivity(irA);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Métodos para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity() {
        Intent intent = new Intent(this, PopupAlerta.class);
        intent.putExtra("titulo", "Descartar cambios");
        intent.putExtra("texto", "¿Desea descartar los cambios no guardados?");
        startActivityForResult(intent, REQUEST_CODE_ER);
    }
    private void cambiarActivity(Class<?> activity) {
        CambiarActivity.cambiar(this, activity);
    }
    private void cambiarActivity(TablaRutinaUsuario rutina) {
        CambiarActivity.cambiar(this, CrearRutinas.class, rutina);
    }

    private void cambiarActivity(HashMap<String, ColoresMusculoUsuario> musculosSemana) {
        CambiarActivity.cambiar(this, PopupAgregarEjercicio.class, rutinaUsuario, dia, musculosSemana);
    }


    // Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje) {
        MostratToast.mostrarToast(this, mensaje);
    }


}
