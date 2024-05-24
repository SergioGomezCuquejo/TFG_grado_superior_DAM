package com.example.yim.vista.controlador;

import android.content.Context;
import android.content.Intent;

import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaActiva;
import com.example.yim.modelo.tablas.TablaEjercicioUsuario;
import com.example.yim.modelo.tablas.TablaLogroUsuario;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.vista.EjercicioActivo;
import com.example.yim.vista.vista.EjerciciosDiarios;
import com.example.yim.vista.vista.EjerciciosRutinas;
import com.example.yim.vista.vista.PopupAlerta;
import com.example.yim.vista.vista.PopupLogros;
import com.example.yim.vista.vista.PopupMusculos;

import java.util.HashMap;

public class CambiarActivity {

    //De un acivity a otro sin pasar datos.
    public static void cambiar(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    //De un activity a PopupAlerta.java.
    public static void cambiar(Context context, String titulo, String texto, String iraA) {
        Intent intent = new Intent(context, PopupAlerta.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("texto", texto);
        intent.putExtra("iraA", iraA);
        context.startActivity(intent);
    }

    //De un activity a PopupAlerta.java pasa pasando por parámetros Strings y un objeto TablaRutinaUsuario.
    public static void cambiar(Context context, String titulo, String texto, String iraA, TablaRutinaUsuario rutinaUsuario, String accion) {
        Intent intent = new Intent(context, PopupAlerta.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("texto", texto);
        intent.putExtra("iraA", iraA);
        intent.putExtra("rutinaUsuario", rutinaUsuario);
        intent.putExtra("accion", accion);
        context.startActivity(intent);

    }

    //De un activity a EjerciciosDiarios.java pasando por parámetro  un objeto TablaDiaRutinaActiva.
    public static void cambiar(Context context, TablaDiaRutinaActiva diaRutinaActiva) {
        Intent intent = new Intent(context, EjerciciosDiarios.class);
        intent.putExtra("diaRutinaActiva", diaRutinaActiva);
        context.startActivity(intent);
    }

    //De un activity a EjercicioActivo.java pasando por parámetros un objeto TablaDiaRutinaActiva y una posición.
    public static void cambiar(Context context, TablaDiaRutinaActiva diaRutinaActiva, int numEjercicio) {
        Intent intent = new Intent(context, EjercicioActivo.class);
        intent.putExtra("diaRutinaActiva", diaRutinaActiva);
        intent.putExtra("numEjercicio", numEjercicio);
        context.startActivity(intent);
    }

    //De un activity a otro pasando por parámetro un objeto TablaRutinaUsuario.
    public static void cambiar(Context context, Class<?> activity, TablaRutinaUsuario rutinaUsuario) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("rutinaUsuario", rutinaUsuario);
        context.startActivity(intent);
    }

    //De un activity a otro pasando por parámetros un objeto TablaRutinaUsuario, un día y un HashMap de String y objeto ColoresMusculoUsuario.
    public static void cambiar(Context context, Class<?> activity, TablaRutinaUsuario rutinaUsuario, int dia, HashMap<String, ColoresMusculoUsuario> musculosSemana) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("rutinaUsuario", rutinaUsuario);
        intent.putExtra("dia", dia);
        intent.putExtra("musculosSemana", musculosSemana);
        context.startActivity(intent);
    }



    /////////////////////////////////
    public static void cambiar(Context context, TablaMusculoUsuario musculoUsuario) {
        Intent intent = new Intent(context, PopupMusculos.class);
        intent.putExtra("musculoUsuario", musculoUsuario);
        context.startActivity(intent);
    }

    public static void cambiar(Context context, TablaLogroUsuario logroUsuario) {
        Intent intent = new Intent(context, PopupLogros.class);
        intent.putExtra("logroUsuario", logroUsuario);
        context.startActivity(intent);
    }

    public static void cambiar(Context context, Class<?> activity, TablaEjercicioUsuario ejercicioUsuario) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("ejercicioUsuario", ejercicioUsuario);
        context.startActivity(intent);
    }







}
