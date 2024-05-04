package com.example.yim.vista.controlador;

import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidarDatos {


    //Comprobar si la estructura del email es correcta.
    public static boolean validarEmail(String emailUsuario) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        return pattern.matcher(emailUsuario).matches();
    }

    //Comprobar que la contraseña tenga mínimo 6 carácteres.
    public static boolean validarContrasena(String contrasenaUsuario){
        boolean correcta;

        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+");

        if( contrasenaUsuario.length() >= 6 ){
            correcta = pattern.matcher(contrasenaUsuario).matches();
        } else{
            correcta = false;
        }
        return correcta;
    }
}
