package com.example.yim.modelo;

import android.content.Context;

import com.example.yim.modelo.Callbacks.FirebaseCallbackLogrosUsuario;
import com.example.yim.modelo.tablas.TablaLogroUsuario;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;

public class ObtenerLogro {
    private FirebaseManager firebaseManager;
    private ArrayList<String> titulos;
    public void obtenerLogro(Context context, String logro, int suma){
        firebaseManager = new FirebaseManager();
        titulos = new ArrayList<String>();
        switch (logro){
            case "Crear ejercicio":
                titulos.add("Creador novel");
                titulos.add("Creador avanzado");
                titulos.add("Creador supremo");
                crearLogro(context, suma);
                break;
            case "Hacer flexiones":
                titulos.add("El niño de las flexiones");
                titulos.add("El señor de las flexiones");
                titulos.add("La máquina de las flexiones");
                crearLogro(context, suma);
                break;
            case "Cambiar colores":
                titulos.add("Color primario");
                titulos.add("Color ido");
                titulos.add("Multicolor");
                crearLogro(context, suma);
                break;
            case "Inicio sesion":
                titulos.add("Iniciador");
                crearLogro(context, suma);
                break;
            case "Hacer dia":
                titulos.add("Por algo se empieza");
                titulos.add("La semana más larga");
                titulos.add("¿Un mes ya?");
                crearLogro(context, suma);
                break;
            case "Crear rutina":
                titulos.add("Las rutinas son lo mio");
                crearLogro(context, suma);
                break;
            case "Toma de medidas":
                titulos.add("Así soy yo");
                crearLogro(context, suma);
                break;
            default:

        }
    }

    private void crearLogro(Context context, int suma){
        firebaseManager.obtenerLogrosUsuario(context, titulos, new FirebaseCallbackLogrosUsuario() {
            @Override
            public void onCallback(ArrayList<TablaLogroUsuario> logrosUsuario) {
                ArrayList<TablaLogroUsuario> logrosActualizados  = new ArrayList<TablaLogroUsuario>();
                for (TablaLogroUsuario logro : logrosUsuario){


                    if(logro.getProgreso() < logro.getTotal()){
                        logro.setProgreso(logro.getProgreso() + suma);
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
