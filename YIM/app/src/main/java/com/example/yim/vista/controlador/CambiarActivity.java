package com.example.yim.vista.controlador;

import android.content.Context;
import android.content.Intent;

import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.vista.vista.PopupAlerta;
import com.example.yim.vista.vista.PopupMusculos;
import com.example.yim.vista.vista.PopupVerEjercicios;

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

    public static void cambiar(Context context, Class<?> activity, TablaEjerciciosUsuario ejercicioUsuario) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("ejercicioUsuario", ejercicioUsuario);
        context.startActivity(intent);
    }

    public static void cambiarAlerta(Context context, String titulo, String texto, String iraA) {
        Intent intent = new Intent(context, PopupAlerta.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("texto", texto);
        intent.putExtra("iraA", iraA);
        context.startActivity(intent);

    }

}
