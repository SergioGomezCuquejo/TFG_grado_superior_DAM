package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.HistorialAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicioUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaEjercicioActivo;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.modelo.tablas.TablaHistorial;
import com.example.yim.modelo.tablas.TablaSeries;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class EjercicioActivo extends AppCompatActivity implements View.OnClickListener {
    private FirebaseManager firebaseManager;
    private TablaDiaRutinaActiva diaRutinaActiva;
    private TablaEjercicioActivo ejercicioActivo;
    private int numEjercicio, numEjercicios, dia, serieActual;
    private TextView ejerciciosTotalesTV, titulo, serieTV, costeTV, seriesTV, historialTV;
    private ShapeableImageView imagen;
    private ImageButton costeIB;
    private Button repeticionesNecesariasBT, descansoBT;
    private LinearLayout costeLL;
    private boolean descansando;
    private CountDownTimer countDownTimer;
    private EditText pesoET, repeticionesET;
    private RecyclerView historialRV;
    private String costeHoy;
    //TODO intentar cambiar de ejerccio al terminar las series
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_activo);

        Intent intent;
        ImageView atras, agregarRepeticion;
        ImageButton botonInfo;
        RadioGroup costeRG;
        FrameLayout imagenCasa, imagenCalendario, imagenEstadisticas, imagenUsuario;

        firebaseManager = new FirebaseManager();
        intent = getIntent();
        diaRutinaActiva = (TablaDiaRutinaActiva) intent.getSerializableExtra("diaRutinaActiva");
        numEjercicio = intent.getIntExtra("numEjercicio", 0);

        numEjercicios = diaRutinaActiva.getEjercicios().size();
        dia = diaRutinaActiva.getDia();
        ejercicioActivo = diaRutinaActiva.getEjercicios().get(numEjercicio-1);
        descansando = false;
        serieActual = 0;

        if(ejercicioActivo.getHistorial() != null){
            Collections.sort(ejercicioActivo.getHistorial());
        }

        //Referencias de las vistas
        atras = findViewById(R.id.atras);
        ejerciciosTotalesTV = findViewById(R.id.ejercios_totales_tv);

        imagen = findViewById(R.id.imagen);
        botonInfo = findViewById(R.id.boton_info);
        titulo = findViewById(R.id.titulo);

        repeticionesNecesariasBT = findViewById(R.id.repeticiones_necesarias_bt);
        descansoBT = findViewById(R.id.descanso_bt);
        seriesTV = findViewById(R.id.series_tv);
        costeTV = findViewById(R.id.coste_tv);

        serieTV = findViewById(R.id.serie_tv);

        pesoET = findViewById(R.id.peso_et);
        repeticionesET = findViewById(R.id.repeticiones_et);
        costeIB = findViewById(R.id.coste_ib);
        costeRG = findViewById(R.id.coste_rg);
        costeLL = findViewById(R.id.coste_ll);
        agregarRepeticion = findViewById(R.id.agregar_repeticion);

        historialRV = findViewById(R.id.historial_rv);
        historialTV = findViewById(R.id.historial_tv);

        imagenCasa = findViewById(R.id.imagen_casa);
        imagenCalendario = findViewById(R.id.imagen_calendario);
        imagenEstadisticas = findViewById(R.id.imagen_estadisticas);
        imagenUsuario = findViewById(R.id.imagen_usuario);

        //Listeners
        atras.setOnClickListener(this);
        botonInfo.setOnClickListener(this);

        repeticionesNecesariasBT.setOnClickListener(this);
        descansoBT.setOnClickListener(this);

        costeIB.setOnClickListener(this);
        agregarRepeticion.setOnClickListener(this);

        imagenCasa.setOnClickListener(this);
        imagenCalendario.setOnClickListener(this);
        imagenEstadisticas.setOnClickListener(this);
        imagenUsuario.setOnClickListener(this);


        costeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                costeHoy = selectedRadioButton.getText().toString();
                costeLL.setVisibility(View.GONE);

                if(costeHoy.equals("Subir")){
                    costeIB.setImageResource(R.drawable.flecha_verde);
                    costeIB.setRotation(0);
                }else if (costeHoy.equals("Bajar")){
                    costeIB.setImageResource(R.drawable.flecha_roja);
                    costeIB.setRotation(180);
                }else{
                    costeIB.setImageResource(R.drawable.mantener);
                    costeIB.setRotation(0);
                }
            }
        });

        mostrarDatos();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        String id, pesoString, repeticionesString;

        id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "atras":
                CambiarActivity.cambiar(this, diaRutinaActiva);
                finish();
                break;

            case "boton_info":
                mostrarInfoEjercicio();
                break;

            case "repeticiones_necesarias_bt":
                repeticionesET.setText(repeticionesNecesariasBT.getText());
                break;

            case "descanso_bt":
                if(!descansando){
                    descansar();
                }else {
                    noDescansar();
                }
                break;

            case "coste_ib":
                costeLL.setVisibility(View.VISIBLE);
                break;

            case "agregar_repeticion":
                pesoString = pesoET.getText().toString();
                repeticionesString = repeticionesET.getText().toString();

                if(!camposVacios(pesoString, repeticionesString)){
                    guardarSerie(pesoString, repeticionesString);

                    if(guardarHistorial() && subirSerie()){
                        if(descansando){
                            noDescansar();
                        }
                        ejercicioActivo.setSeries_realizadas(ejercicioActivo.getSeries_realizadas()+1);
                        vaciarCampos();
                        mostrarDatos();
                        historialTV.setVisibility(View.GONE);
                    }

                } else {
                    MostratToast.mostrarToast(this, "Ingrese el peso y las repeticiones");
                }
                break;

            case "imagen_casa":
                cambiarActivity(Inicio.class);
                break;
            case "imagen_calendario":
                cambiarActivity(RutinaSemanal.class);
                break;
            case "imagen_estadisticas":
                cambiarActivity(Estadisticas.class);
                break;
            case "imagen_usuario":
                cambiarActivity(Perfil.class);
                break;

        }
    }
    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void mostrarDatos(){
        ArrayList<TablaSeries> series = new ArrayList<TablaSeries>();
        ArrayList<String> dias = new ArrayList<String>();
        String costeString;
        int serieHistorial, colorCoste;
        TablaSeries serieAnteriorHistorial;
        HistorialAdaptador adaptador;

        ejerciciosTotalesTV.setText("Ejercicio " + numEjercicio + " de " + numEjercicios);

        imagen.setImageResource(R.drawable.curl_de_biceps_hombre);
        titulo.setText(ejercicioActivo.getNombre());

        repeticionesNecesariasBT.setText(String.valueOf(ejercicioActivo.getRepeticiones()));


        descansoBT.setText(obtenerTiempoDescanso(ejercicioActivo.getTiempo_descanso() / 60, ejercicioActivo.getTiempo_descanso() % 60));

        seriesTV.setText(ejercicioActivo.getSeries_realizadas() + "/" + ejercicioActivo.getSeries_necesarias());

        serieActual = ejercicioActivo.getSeries_realizadas() + 1;
        serieTV.setText("Serie " + serieActual);


        if(ejercicioActivo.getHistorial() != null) {
            Collections.sort(ejercicioActivo.getHistorial());
            if(ejercicioActivo.getHistorial().size() > 1 || (ejercicioActivo.getHistorial().size() == 1 && !ejercicioActivo.getHistorial().get(0).getDia().equals(obtenerDia()))){

                if(serieActual == 1 && ejercicioActivo.getHistorial().size() == 1){
                    serieHistorial = 0;
                }else{
                    serieHistorial = 1;
                }

                if(ejercicioActivo.getHistorial().get(serieHistorial).getSeries().size() >= serieActual){
                    serieAnteriorHistorial = ejercicioActivo.getHistorial().get(serieHistorial).getSeries().get(serieActual-1);
                    switch (serieAnteriorHistorial.getCoste()) {
                        case "Subir":
                            costeString = " (Subir de ";
                            colorCoste = R.color.verde_clarito;
                            break;
                        case "Mantener":
                            costeString = " (Mantener en ";
                            colorCoste = R.color.naranja;
                            break;
                        case "Bajar":
                            costeString = " (Bajar de ";
                            colorCoste = R.color.rojo_clarito;
                            break;
                        default:
                            costeTV.setVisibility(View.GONE);
                            costeString = "";
                            colorCoste = R.color.fondo_clarito;
                    }

                    costeString += serieAnteriorHistorial.getPeso() + " x " + serieAnteriorHistorial.getRepeticiones() + ")";
                    costeTV.setText(costeString);
                    costeTV.setTextColor(ContextCompat.getColor(this, colorCoste));
                }
            }


            for (TablaHistorial historial : ejercicioActivo.getHistorial()) {
                dias.add(historial.getDia());

                series.addAll(historial.getSeries());
            }
            historialRV.setLayoutManager(new LinearLayoutManager(this));
            adaptador = new HistorialAdaptador(this, dias, series);
            historialRV.setAdapter(adaptador);
        }else{
            historialTV.setVisibility(View.VISIBLE);
        }
    }

    private void mostrarInfoEjercicio(){
        firebaseManager.obtenerEjercicioUsuario(this, ejercicioActivo.getId_ejercicio(), new FirebaseCallbackEjercicioUsuario() {
            @Override
            public void onCallback(TablaEjerciciosUsuario ejercicio) {
                CambiarActivity.cambiar(EjercicioActivo.this, PopupVerEjercicios.class, ejercicio);
            }
        });
    }

    private void descansar(){
        descansando = true;
        long milisegundos = ejercicioActivo.getTiempo_descanso() * 1000L;
        countDownTimer = new CountDownTimer(milisegundos, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                long minutos = (millisUntilFinished / 1000) / 60;
                long segundos = (millisUntilFinished / 1000) % 60;

                descansoBT.setText(obtenerTiempoDescanso(minutos, segundos));
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                descansoBT.setText("00:00");
            }
        }.start();
    }
    private void noDescansar(){
        countDownTimer.cancel();
        descansoBT.setText(obtenerTiempoDescanso(ejercicioActivo.getTiempo_descanso() / 60, ejercicioActivo.getTiempo_descanso() % 60));
        descansando = false;
    }

    private String obtenerTiempoDescanso(long minutos, long segundos){
        String minutosString;
        String segundosString;

        if (minutos>=10){
            minutosString = String.valueOf(minutos);
        }else if(minutos == 0){
            minutosString = "00";
        } else {
            minutosString = "0" + minutos;
        }

        if (segundos>=10){
            segundosString = String.valueOf(segundos);
        }else if(segundos == 0){
            segundosString = "00";
        } else {
            segundosString = "0" + segundos;
        }

        return minutosString + ":" + segundosString;

    }
    private boolean camposVacios(String peso, String repeticiones){
        return peso.isEmpty() || repeticiones.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void guardarSerie(String peso, String repeticiones){
        int posicion = 0;
        if(costeHoy == null) {
            costeHoy = "Mantener";
        }

        if(ejercicioActivo.getHistorial() == null){
            ejercicioActivo.setHistorial(new ArrayList<>());
            ejercicioActivo.getHistorial().add(new TablaHistorial(obtenerDia(), new ArrayList<>()));

        } else if(!ejercicioActivo.getHistorial().get(0).getDia().equals(obtenerDia())) {
            ejercicioActivo.getHistorial().add(new TablaHistorial(obtenerDia(), new ArrayList<>()));
            posicion = (ejercicioActivo.getHistorial().size() - 1);
        }

        ejercicioActivo.getHistorial().get(posicion).getSeries().add(new TablaSeries(costeHoy, Integer.parseInt(peso), Integer.parseInt(repeticiones), serieActual));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String obtenerDia(){
        LocalDate currentDate = LocalDate.now();

        return currentDate.getDayOfMonth() + "/" + currentDate.getMonthValue() + "/" +  currentDate.getYear();
    }

    private boolean guardarHistorial(){
        return firebaseManager.modificarEjercicioRutinaActiva(this, dia, numEjercicio, ejercicioActivo.getHistorial());
    }

    private boolean subirSerie(){
        return firebaseManager.modificarEjercicioRutinaActiva(this, dia, numEjercicio, ejercicioActivo.getSeries_realizadas());
    }
    private void vaciarCampos(){
        pesoET.setText("");
        repeticionesET.setText("");
    }

}