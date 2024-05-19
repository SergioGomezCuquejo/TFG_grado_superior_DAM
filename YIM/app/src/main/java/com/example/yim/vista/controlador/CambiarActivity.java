package com.example.yim.vista.controlador;

import android.content.Context;
import android.content.Intent;

import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaEjercicioActivo;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.modelo.tablas.TablaLogrosUsuario;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.modelo.tablas.TablaRutinasUsuario;
import com.example.yim.vista.vista.EjercicioActivo;
import com.example.yim.vista.vista.EjerciciosDiarios;
import com.example.yim.vista.vista.EjerciciosRutinas;
import com.example.yim.vista.vista.PopupAlerta;
import com.example.yim.vista.vista.PopupLogros;
import com.example.yim.vista.vista.PopupMusculos;
import com.example.yim.vista.vista.PopupRutinas;

import java.util.HashMap;

public class CambiarActivity {
    public static void cambiar(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public static void cambiar(Context context, TablaMusculosUsuario musculoUsuario) {
        Intent intent = new Intent(context, PopupMusculos.class);
        intent.putExtra("musculoUsuario", musculoUsuario);
        context.startActivity(intent);
    }

    public static void cambiar(Context context, TablaLogrosUsuario logroUsuario) {
        Intent intent = new Intent(context, PopupLogros.class);
        intent.putExtra("logroUsuario", logroUsuario);
        context.startActivity(intent);
    }

    public static void cambiar(Context context, Class<?> activity, TablaEjerciciosUsuario ejercicioUsuario) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("ejercicioUsuario", ejercicioUsuario);
        context.startActivity(intent);
    }

    public static void cambiar(Context context, Class<?> activity, TablaRutinasUsuario rutinaUsuario) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("rutinaUsuario", rutinaUsuario);
        context.startActivity(intent);
    }

    public static void cambiar(Context context, TablaRutinasUsuario rutinaUsuario, int dia, HashMap<String, ColoresMusculoUsuario> musculosSemana) {
        Intent intent = new Intent(context, EjerciciosRutinas.class);
        intent.putExtra("rutinaUsuario", rutinaUsuario);
        intent.putExtra("dia", dia);
        intent.putExtra("musculosSemana", musculosSemana);
        context.startActivity(intent);
    }
    public static void cambiar(Context context, TablaDiaRutinaActiva diaRutinaActiva) {
        Intent intent = new Intent(context, EjerciciosDiarios.class);
        intent.putExtra("diaRutinaActiva", diaRutinaActiva);
        context.startActivity(intent);
    }


    public static void cambiar(Context context, TablaDiaRutinaActiva diaRutinaActiva, int numEjercicio) {

        Intent intent = new Intent(context, EjercicioActivo.class);
        intent.putExtra("diaRutinaActiva", diaRutinaActiva);
        intent.putExtra("numEjercicio", numEjercicio);
        context.startActivity(intent);
    }

    public static void cambiarAlerta(Context context, String titulo, String texto, String iraA) {
        Intent intent = new Intent(context, PopupAlerta.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("texto", texto);
        intent.putExtra("iraA", iraA);
        context.startActivity(intent);

    }

    public static void cambiarAlerta(Context context, String titulo, String texto, String iraA, TablaRutinasUsuario rutinaUsuario, String accion) {
        Intent intent = new Intent(context, PopupAlerta.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("texto", texto);
        intent.putExtra("iraA", iraA);
        intent.putExtra("rutinaUsuario", rutinaUsuario);
        intent.putExtra("accion", accion);
        context.startActivity(intent);

    }
}
