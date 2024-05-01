package com.example.yim.vista.controlador;

import android.content.Context;
import android.widget.Toast;

public class MostratToast {
    public static void mostrarToast(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }
}
