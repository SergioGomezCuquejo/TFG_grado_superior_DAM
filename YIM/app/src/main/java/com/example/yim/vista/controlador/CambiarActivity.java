package com.example.yim.vista.controlador;

import android.content.Context;
import android.content.Intent;

import com.example.yim.vista.vista.PopupAlerta;

public class CambiarActivity {
    public static void cambiar(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
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
