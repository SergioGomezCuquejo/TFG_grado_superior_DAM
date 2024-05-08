package com.example.yim.modelo;

import android.content.Context;

import com.example.yim.modelo.Callbacks.FirebaseCallbackLogrosUsuario;
import com.example.yim.modelo.tablas.TablaLogrosUsuario;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class ObtenerLogro {
    private FirebaseManager firebaseManager;

    private ArrayList<String> titulos;
    public void obtenerLogro(Context context, String logro){
        firebaseManager = new FirebaseManager();
        titulos = new ArrayList<String>();
        switch (logro){
            case "Crear ejercicio":
                titulos.add("Creador novel");
                titulos.add("Creador avanzado");
                titulos.add("Creador supremo");
                crearEjercicio(context);
            default:

        }
    }

    private void crearEjercicio(Context context){
        firebaseManager.obtenerLogrosUsuario(context, titulos, new FirebaseCallbackLogrosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaLogrosUsuario> logrosUsuario) {
                ArrayList<TablaLogrosUsuario> logrosActualizados  = new ArrayList<TablaLogrosUsuario>();
                for (TablaLogrosUsuario logro : logrosUsuario){


                    if(logro.getProgreso() < logro.getTotal()){
                        logro.setProgreso(logro.getProgreso() + 1);
                        logrosActualizados.add(logro);

                        if (logro.getProgreso() == logro.getTotal()){
                            MostratToast.mostrarToast(context, "Logro '"+ logro.getTitulo() +"' obtenido.");
                        }
                    }
                }
                if (logrosUsuario.size() > 0){
                    firebaseManager.actualizarLogros(context, logrosUsuario);
                }
            }
        });
    }

}
