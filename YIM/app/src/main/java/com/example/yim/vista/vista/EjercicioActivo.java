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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.controlador.Adaptadores.HistorialAdaptador;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicioCreado;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicioPorDefecto;
import com.example.yim.modelo.Callbacks.FirebaseCallbackPerfil;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaEjercicioActivo;
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;
import com.example.yim.modelo.tablas.TablaHistorial;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaSerie;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class EjercicioActivo extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseManager firebaseManager;
    private TablaDiaRutinaActiva diaRutinaActiva;
    private TablaEjercicioActivo ejercicioActivo;
    ImageView atras, imagenSH, agregarSerie, costeIB, imagenPerfilMenu;
    TextView ejerciciosTotalesTV, tituloTV, serieTV, costeTV, seriesTV, historialTV, nombreRutinaTV;
    ImageButton botonInfo;
    Button repeticionesNecesariasBT, descansoBT;
    LinearLayout costeLL;
    RadioGroup costeRG;
    EditText pesoET, repeticionesET;
    RecyclerView historialRV;
    ProgressBar cargando;
    FrameLayout imagenCasaMenu, imagenCalendarioMenu, imagenEstadisticasMenu, imagenUsuarioMenu, nombreRutinaFL;

    private TablaEjercicioCreado ejercicioCreado;
    private TablaEjercicioPorDefecto ejercicioPorDefecto;
    private boolean descansando;
    private int numEjercicio, numEjercicios, dia, serieActual;
    private CountDownTimer countDownTimer;
    private String costeHoy;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_activo);

        //Inicializar instancias.
        Intent intent = getIntent();
        firebaseManager = new FirebaseManager();
        descansando = false;
        serieActual = 0;


        //Obtener la rutina que se ha seleccionado.
        if(intent.hasExtra("diaRutinaActiva")) {
            diaRutinaActiva = (TablaDiaRutinaActiva) intent.getSerializableExtra("diaRutinaActiva");
            numEjercicio = intent.getIntExtra("numEjercicio", 0);

            numEjercicios = diaRutinaActiva.getEjercicios().size();
            dia = diaRutinaActiva.getDia();
            ejercicioActivo = diaRutinaActiva.getEjercicios().get(numEjercicio);

            if(ejercicioActivo.getHistorial() != null){
                Collections.sort(ejercicioActivo.getHistorial());
            }
        }


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        atras = findViewById(R.id.atras_iv);
        nombreRutinaFL = findViewById(R.id.nombre_rutina_fl);
        nombreRutinaTV = findViewById(R.id.nombre_rutina_tv);
        ejerciciosTotalesTV = findViewById(R.id.ejercios_totales_tv);

        imagenSH = findViewById(R.id.imagen_sh);
        botonInfo = findViewById(R.id.boton_info);
        tituloTV = findViewById(R.id.titulo_tv);

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
        agregarSerie = findViewById(R.id.agregar_serie);

        historialRV = findViewById(R.id.historial_rv);
        historialTV = findViewById(R.id.historial_tv);

        imagenCasaMenu = findViewById(R.id.imagen_casa_menu);
        imagenCalendarioMenu = findViewById(R.id.imagen_calendario_menu);
        imagenEstadisticasMenu = findViewById(R.id.imagen_estadisticas_menu);
        imagenUsuarioMenu = findViewById(R.id.imagen_usuario_menu);
        imagenPerfilMenu = findViewById(R.id.imagen_perfil_menu);


        //Listeners.
        atras.setOnClickListener(this);
        botonInfo.setOnClickListener(this);

        repeticionesNecesariasBT.setOnClickListener(this);
        descansoBT.setOnClickListener(this);

        costeIB.setOnClickListener(this);
        agregarSerie.setOnClickListener(this);

        imagenCasaMenu.setOnClickListener(this);
        imagenCalendarioMenu.setOnClickListener(this);
        imagenEstadisticasMenu.setOnClickListener(this);
        imagenUsuarioMenu.setOnClickListener(this);


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



        //Mostrar datos.
        try{
            mostrarImagenPerfil();
            mostrarEjercicio();


        } catch (Exception ex) {
            mostrarToast("Error al obtener los datos del ejercicio.1");
            ex.printStackTrace();
        }

        //Ocultar barra de progreso.
        cargando.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        String id, pesoString, repeticionesString;

        id = getResources().getResourceEntryName(view.getId());

        switch (id){
            case "atras_iv":
                cambiarActivity(diaRutinaActiva);
                finish();
                break;

            case "boton_info":
                if(ejercicioActivo.getNombre().startsWith("-")){
                    mostrarInfoCreado();
                }else {
                    mostrarInfoPorDefecto();
                }
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

            case "agregar_serie":
                pesoString = pesoET.getText().toString();
                repeticionesString = repeticionesET.getText().toString();

                if(!camposVacios(pesoString, repeticionesString)){

                    agregarSerie(pesoString, repeticionesString);
                    vaciarCampos();
                    mostrarEjercicio();
                    historialTV.setVisibility(View.GONE);


                    if(ejercicioActivo.getSeries_realizadas() == ejercicioActivo.getSeries_necesarias()){
                        if(numEjercicio+1 == numEjercicios){
                            cambiarActivity(diaRutinaActiva);
                            mostrarToast("Día completado");
                            finish();
                        }else{
                            cambiarActivity();
                        }
                    }

                } else {
                    mostrarToast("Ingrese el peso y las repeticiones");
                }
                break;

            case "imagen_casa_menu":
                cambiarActivity(Inicio.class);
                break;
            case "imagen_calendario_menu":
                cambiarActivity(RutinaSemanal.class);
                break;
            case "imagen_estadisticas_menu":
                cambiarActivity(Estadisticas.class);
                break;
            case "imagen_usuario_menu":
                cambiarActivity(Perfil.class);
                break;

        }
    }


    //Método para mostrar los datos del ejercicio seleccionado.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void mostrarEjercicio(){
        ArrayList<TablaSerie> series = new ArrayList<TablaSerie>();
        ArrayList<String> dias = new ArrayList<String>();
        String costeString, nombre;
        int serieHistorial, colorCoste;
        TablaSerie serieAnteriorHistorial;
        HistorialAdaptador adaptador;

        ejerciciosTotalesTV.setText("Ejercicio " + (numEjercicio+1) + " de " + numEjercicios);

        if(ejercicioActivo.getNombre().startsWith("-")){
            ejercicioCreado = new TablaEjercicioCreado();
            if(ejercicioActivo.getImagen() != null){
                Imagenes.mostrarImagen(this, ejercicioActivo.getImagen(),  imagenSH);
            }else{
                nombreRutinaFL.setVisibility(View.VISIBLE);
                imagenSH.setVisibility(View.INVISIBLE);
                nombre = ejercicioActivo.getNombre().toUpperCase();
                nombre = nombre.substring(1);
                if (nombre.length() >= 3) {
                    nombre = nombre.substring(0, 3);
                }
                nombreRutinaTV.setText(nombre);
            }
            tituloTV.setText(ejercicioActivo.getNombre().substring(1));
        }else {
            ejercicioPorDefecto = new TablaEjercicioPorDefecto();
            if(ejercicioActivo.getImagen() != null){
                imagenSH.setImageResource(getResources().getIdentifier(ejercicioActivo.getImagen(), "drawable", getPackageName()));
            }
            tituloTV.setText(ejercicioActivo.getNombre());

        }

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

                if(serieHistorial == 1 && ejercicioActivo.getHistorial().size() == 1){
                    serieHistorial = 0;
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

        if(ejercicioActivo.getNombre().startsWith("-")){
            obtenerEjercicioCreado(new FirebaseCallbackEjercicioCreado() {
                @Override
                public void onCallback(TablaEjercicioCreado ejercicio) {
                }
            });
        }else {
            obtenerEjercicioPorDefecto(new FirebaseCallbackEjercicioPorDefecto() {
                @Override
                public void onCallback(TablaEjercicioPorDefecto ejercicio) {
                }
            });
        }
    }


    //Método para mostrar la información del ejercicio.
    private void obtenerEjercicioCreado(FirebaseCallbackEjercicioCreado callback){
        firebaseManager.obtenerEjercicioCreado(this, ejercicioActivo.getId_ejercicio(), new FirebaseCallbackEjercicioCreado() {
            @Override
            public void onCallback(TablaEjercicioCreado ejercicio) {
                if (ejercicio == null) {
                    mostrarToast("Error al obtener el ejercicio. 2 ");
                }
                callback.onCallback(ejercicio);
            }
        });
    }
    private void obtenerEjercicioPorDefecto(FirebaseCallbackEjercicioPorDefecto callback){
        firebaseManager.obtenerEjercicioPorDefecto(this, ejercicioActivo.getId_ejercicio(), new FirebaseCallbackEjercicioPorDefecto() {
            @Override
            public void onCallback(TablaEjercicioPorDefecto ejercicio) {
                if (ejercicio == null) {
                    mostrarToast("Error al obtener el ejercicio. 3");
                }
                callback.onCallback(ejercicio);
            }
        });
    }

    private void mostrarInfoCreado(){
        obtenerEjercicioCreado(new FirebaseCallbackEjercicioCreado() {
            @Override
            public void onCallback(TablaEjercicioCreado ejercicio) {
                cambiarActivity(ejercicio);
            }
        });
    }

    private void mostrarInfoPorDefecto(){
        obtenerEjercicioPorDefecto(new FirebaseCallbackEjercicioPorDefecto() {
            @Override
            public void onCallback(TablaEjercicioPorDefecto ejercicio) {
                cambiarActivity(ejercicio);
            }
        });
    }

    //Método para iniciar el contador regresivo.
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

    //Método para reiniciar y parar el contador regresivo.
    private void noDescansar(){
        countDownTimer.cancel();
        descansoBT.setText(obtenerTiempoDescanso(ejercicioActivo.getTiempo_descanso() / 60, ejercicioActivo.getTiempo_descanso() % 60));
        descansando = false;
    }

    //Método para pasar de minutos y segundos a String con el formato '00:00'.
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

    //Método para comprobar si los campos están o no vacios.
    private boolean camposVacios(String peso, String repeticiones){
        return peso.isEmpty() || repeticiones.isEmpty();
    }


    //Método para actualizar la serie del ejercicio.
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void agregarSerie(String pesoString, String repeticionesString){
        try {
            cargando.setVisibility(View.VISIBLE);
            guardarSerie(pesoString, repeticionesString);
            guardarHistorial(new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if(ejercicioCreado != null){

                        guardarHistorialCreado(new FirebaseCallbackBoolean() {
                            @Override
                            public void onCallback(boolean accionRealizada) {
                                series();
                            }
                        });
                    }else{

                        guardarHistorialPorDefecto(new FirebaseCallbackBoolean() {
                            @Override
                            public void onCallback(boolean accionRealizada) {
                                series();
                            }
                        });
                    }
                }
            });

        } catch (Exception ex) {
            cargando.setVisibility(View.GONE);
            mostrarToast("Error al agregar la serie del ejercicio.");
            ex.printStackTrace();
        }
    }

    //Método para actualizar la serie en el historial del objeto.
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

        ejercicioActivo.getHistorial().get(posicion).getSeries().add(new TablaSerie(costeHoy, Integer.parseInt(peso), Integer.parseInt(repeticiones), serieActual));
    }

    //Método para obtener el día de hoy en formato 'dia/mes/año'.
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String obtenerDia(){
        LocalDate currentDate = LocalDate.now();

        return currentDate.getDayOfMonth() + "/" + currentDate.getMonthValue() + "/" +  currentDate.getYear();
    }


    //Método para actualizar el historial en Firebase Database.
    private void guardarHistorial(FirebaseCallbackBoolean callback){
        firebaseManager.actualizarRutinaActiva(this, dia, numEjercicio, ejercicioActivo.getHistorial(), new FirebaseCallbackBoolean() {
            @Override
            public void onCallback(boolean accionRealizada) {
                if(!accionRealizada){
                    mostrarToast("Error al actualizar el ejercicio.");
                }
                callback.onCallback(accionRealizada);
            }
        });
    }
    private void guardarHistorialCreado(FirebaseCallbackBoolean callback){
        firebaseManager.actualizarEjercicioCreado(this, ejercicioActivo.getId_ejercicio(), ejercicioActivo.getHistorial(), new FirebaseCallbackBoolean() {
            @Override
            public void onCallback(boolean accionRealizada) {
                if(!accionRealizada){
                    mostrarToast("Error al actualizar el ejercicio creado.");
                }
                callback.onCallback(accionRealizada);
            }
        });
    }
    private void guardarHistorialPorDefecto(FirebaseCallbackBoolean callback){
        firebaseManager.actualizarEjercicioPorDefecto(this, ejercicioActivo.getId_ejercicio(), ejercicioActivo.getHistorial(), new FirebaseCallbackBoolean() {
            @Override
            public void onCallback(boolean accionRealizada) {
                if(!accionRealizada){
                    mostrarToast("Error al actualizar el ejercicio creado.");
                }
                callback.onCallback(accionRealizada);
            }
        });
    }
    private void series(){
        subirSerie(new FirebaseCallbackBoolean() {
            @Override
            public void onCallback(boolean accionRealizada) {

            }
        });
        if(descansando){
            noDescansar();
        }

        ejercicioActivo.setSeries_realizadas(ejercicioActivo.getSeries_realizadas()+1);
        cargando.setVisibility(View.GONE);
    }


    //Método para actualizar la serie en Firebase Database.
    private void subirSerie(FirebaseCallbackBoolean callback){
        firebaseManager.actualizarRutinaActiva(this, dia, numEjercicio,ejercicioActivo.getSeries_realizadas(), new FirebaseCallbackBoolean() {
            @Override
            public void onCallback(boolean accionRealizada) {
                if(!accionRealizada){
                    mostrarToast("Error al actualizar el ejercicio.");
                }
                callback.onCallback(accionRealizada);
            }
        });
    }


    //Método para vaciar los campos.
    private void vaciarCampos(){
        pesoET.setText("");
        repeticionesET.setText("");
    }


    //Método que obtiene la imagen de perfil, si tiene llama a Imagenes.java. (Clase que permite la visualización de imagenes de Firebase Storage)
    private void mostrarImagenPerfil(){
        firebaseManager.obtenerPerfil(this, new FirebaseCallbackPerfil() {
            @Override
            public void onCallback(TablaPerfil perfil) {
                if(perfil.getImagen() != null && !perfil.getImagen().equals("")){
                    Imagenes.urlImagenPerfil = perfil.getImagen();
                    Imagenes.mostrarImagenPerfil(EjercicioActivo.this, imagenPerfilMenu);
                }

            }
        });

    }


    //Métodos para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }
    private void cambiarActivity(TablaDiaRutinaActiva diaRutinaActiva) {
        CambiarActivity.cambiar(this, diaRutinaActiva);
    }
    private void cambiarActivity(TablaEjercicioCreado ejercicio){
        CambiarActivity.cambiar(this, ejercicio);
    }
    private void cambiarActivity(TablaEjercicioPorDefecto ejercicio){
        CambiarActivity.cambiar(this, ejercicio);
    }
    private void cambiarActivity(){
        CambiarActivity.cambiar(this, diaRutinaActiva, (ejercicioActivo.getPosicion()-1));
    }


    //Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }
}