package com.example.yim.vista.controlador;

import android.content.Context;
import android.content.Intent;

public class CambiarActivity {
    public static void cambiar(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

}
