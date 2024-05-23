package com.example.yim.vista.vista;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
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
import java.util.HashMap;

public class EjerciciosRutinas extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaRutinaUsuario rutinaUsuario;
    private TablaDiaRutinaUsuario diaRutinaUsuario;
    private HashMap<String, ColoresMusculoUsuario> musculosSemana;
    ImageView atras, cancelar, agregarEjercicio, imagenPerfilMenu;
    LinearLayout musculos, cambiarMusculos, musculoDerechaLL;
    ViewFlipper viewFlipper;
    RelativeLayout cambiarEjercicios;
    Space space1, space2;
    RecyclerView recyclerView;
    ProgressBar cargando;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu;
    TextView musculoIzquierda, musculoCentro, musculoDerecha, descanso, musculoElegidoIzquierda, musculoElegidoCentro, musculoElegidoDerecha;
    CheckBox todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps;
    EjerciciosRutinasAdaptador adaptador;
    boolean izquierda, centro, derecha;
    int dia;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_rutinas);


        //Inicializar instancias.
        firebaseManager = new FirebaseManager();
        Intent intent = getIntent();
        izquierda = false;
        centro = false;
        derecha = false;

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

        space1 = findViewById(R.id.space1);
        space2 = findViewById(R.id.space2);
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

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);

        agregarEjercicio.setOnClickListener(this);


        //Cambiar los checkBox al marcarse.
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

                    } else {
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
                musculoElegido(id);
                viewFlipper.showPrevious();

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
            mostrarMusculos();
            mostrarEjercicios();
        } catch (Exception ex) {
            mostrarToast("Error al mostrar los ejercicios de la rutina2.");
            ex.printStackTrace();
        }


        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);
    }


    //Método para mostrar la elección del usuario.
    private void musculoElegido(int id) {
        String musculoElegido = getResources().getResourceEntryName(id).replace("_", " ");
        musculoElegido = musculoElegido.substring(0, 1).toUpperCase() + musculoElegido.substring(1);
        switch (musculoElegido) {
            case "Biceps":
                musculoElegido = "Bíceps";
                break;
            case "Cuadriceps":
                musculoElegido = "Cuádriceps";
                break;
        }
        if (izquierda) {
            musculoElegidoIzquierda.setText(musculoElegido);
            musculoIzquierda.setText(musculoElegido);
        } else if (centro) {
            musculoElegidoCentro.setText(musculoElegido);
            if (musculoCentro.getVisibility() == View.GONE) {
                musculoIzquierda.setBackgroundResource(R.drawable._style2_borde_blanco_izquierda);
                musculoCentro.setVisibility(View.VISIBLE);
                musculoDerechaLL.setVisibility(View.VISIBLE);
            }
            musculoCentro.setText(musculoElegido);
        } else if (derecha) {
            musculoElegidoDerecha.setText(musculoElegido);
            if (musculoDerecha.getVisibility() == View.GONE) {
                musculoCentro.setBackgroundResource(R.drawable._style2_borde_blanco_0);
                musculoDerecha.setVisibility(View.VISIBLE);
            }
            musculoDerecha.setText(musculoElegido);
        }
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id) {
            case "atras_iv":
                actualizarRutina();
                cambiarActivity(CrearRutinas.class, rutinaUsuario);
                break;

            case "musculos":
            case "cancelar":
                cambiarVisibilidad(cambiarEjercicios);
                cambiarVisibilidad(viewFlipper);
                break;

            case "agregar_ejercicio":
                actualizarRutina();

                cambiarActivity(PopupAgregarEjercicio.class, rutinaUsuario);
                break;

            case "musculo_elegido_izquierda":
                viewFlipper.showNext();
                izquierda = true;
                centro = false;
                derecha = false;
                break;

            case "musculo_elegido_centro":
                viewFlipper.showNext();
                centro = true;
                izquierda = false;
                derecha = false;
                break;

            case "musculo_elegido_derecha":
                viewFlipper.showNext();
                derecha = true;
                centro = false;
                izquierda = false;
                break;


            case "imagen_casa_menu":
                cambiarActivity("ir_a_inicio");
                break;
            case "imagen_calendario_menu":
                cambiarActivity("ir_a_rutina_semanal");
                break;
            case "imagen_estadisticas_menu":
                cambiarActivity("ir_a_estadisticas");
                break;
            case "imagen_usuario_menu":
                cambiarActivity("ir_a_perfil");
                break;
        }
    }


    //Método para la actualización del objeto.
    private void actualizarRutina() {
        ArrayList<String> arrayMusculos = new ArrayList<String>();
        arrayMusculos.add(musculoIzquierda.getText().toString());
        if (musculoCentro.getVisibility() == View.VISIBLE) {
            arrayMusculos.add(musculoCentro.getText().toString());
        }
        if (musculoDerecha.getVisibility() == View.VISIBLE) {
            arrayMusculos.add(musculoDerecha.getText().toString());
        }
        rutinaUsuario.getSemana().get(dia).setMusculos(arrayMusculos);
        rutinaUsuario.getSemana().get(dia).setEjercicios(adaptador.obtenerEjerciciosActualizados());
    }


    //Método para el mostrado de músculos con sus respectivos colores.
    private void mostrarMusculos() {
        ArrayList<String> musculosUsuario = diaRutinaUsuario.getMusculos();
        if (musculosUsuario.size() >= 2) {
            musculoIzquierda.setBackgroundResource(R.drawable._style2_borde_blanco_izquierda);
            cambiarVisibilidad(musculoCentro);

            cambiarVisibilidad(musculoElegidoCentro);
            cambiarVisibilidad(musculoDerechaLL);
            if (musculosUsuario.size() == 3) {
                musculoCentro.setBackgroundResource(R.drawable._style2_borde_blanco_0);
                cambiarVisibilidad(musculoDerecha);
            }
        } else if (musculosUsuario.get(0).equals("Descanso")) {
            space1.setVisibility(View.VISIBLE);
            space2.setVisibility(View.VISIBLE);
        } else {
            cambiarVisibilidad(musculoElegidoCentro);
        }
        String musculo;
        String fondo = "#FFFFFFFF";
        String fuente = "#FF000000";
        for (int i = 0; i < musculosUsuario.size(); i++) {
            musculo = musculosUsuario.get(i);
            if (musculosSemana.containsKey(musculo)) {
                fondo = musculosSemana.get(musculo).getColor_fondo();
                fuente = musculosSemana.get(musculo).getColor_fuente();
                musculosSemana.put(musculo, musculosSemana.get(musculo));
            }
            if (i == 0) {
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
            } else {
                musculoDerecha.setText(musculo);
                musculoDerecha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoDerecha.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));

                musculoElegidoDerecha.setText(musculo);
                musculoElegidoDerecha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                musculoElegidoDerecha.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
            }
        }
    }


    //Método para mostrar los ejercicios desde un adaptador.
    private void mostrarEjercicios() {
        if (diaRutinaUsuario.getEjercicios() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(EjerciciosRutinas.this));
            adaptador = new EjerciciosRutinasAdaptador(EjerciciosRutinas.this, diaRutinaUsuario, recyclerView);
            recyclerView.setAdapter(adaptador);
        } else {
            descanso.setVisibility(View.VISIBLE);
        }
    }


    //Método para hacer visible o invisible.
    private void cambiarVisibilidad(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }


    //Método para el cambio de color del texto.
    public void cambiarColores(View view, int color) {
        ((TextView) view).setTextColor(color);
    }


    //Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    private void mostrarImagenPerfil() {
        firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
            @Override
            public void onCallback(TablaPerfil perfil) {
                if (perfil.getImagen() != null && !perfil.getImagen().equals("")) {
                    Imagenes.urlImagenPerfil = perfil.getImagen();
                    Imagenes.mostrarImagenPerfil(EjerciciosRutinas.this, imagenPerfilMenu);
                }

            }
        });

    }


    //Métodos para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        CambiarActivity.cambiar(this, activity);
    }

    private void cambiarActivity(Class<?> activity, TablaRutinaUsuario rutina) {
        CambiarActivity.cambiar(this, activity, rutina);
    }

    private void cambiarActivity(String ira) {
        CambiarActivity.cambiar(this, "Descartar cambios", "¿Desea descartar los cambios no guardados?", ira);
    }


    //Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje) {
        MostratToast.mostrarToast(this, mensaje);
    }


}
    /*
    private void ponerCheckBoxFalse(){
        CheckBox[] checkBoxes = {todoElCuerpo, trenSuperior, espalda, biceps, cuadriceps};

        for (CheckBox checkBox : checkBoxes) {
            checkBox.setChecked(false);
        }
    }
     */
