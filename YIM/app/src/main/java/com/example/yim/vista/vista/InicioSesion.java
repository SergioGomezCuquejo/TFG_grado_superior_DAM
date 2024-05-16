package com.example.yim.vista.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.controlador.ValidarDatos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    EditText email, contrasena;
    TextView error;
    Button iniciarSesion, registrarse;
    String emailUsuario, contrasenaUsuario;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();

        //Referencias de las vistas
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);

        error = findViewById(R.id.error);

        iniciarSesion = findViewById(R.id.iniciar_sesion);
        registrarse = findViewById(R.id.registrarse);

        //Listeners
        iniciarSesion.setOnClickListener(this);
        registrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iniciar_sesion) {
            emailUsuario = email.getText().toString();
            contrasenaUsuario = contrasena.getText().toString();

            if(!camposVacios(emailUsuario, contrasenaUsuario)){
                iniciarSesion(emailUsuario, contrasenaUsuario);

            }

        } else if (id == R.id.registrarse) {
            cambiarActivity(RegistroSesion.class);

        }
    }

    //Comprueba que todos los campos estén rellenos.
    private boolean camposVacios(String emailUsuario, String contrasenaUsuario){
        boolean vacio;

        if(!emailUsuario.isEmpty() && !contrasenaUsuario.isEmpty() ){
            vacio = false;
        }else {
            vacio = true;
            mostrarError("Complete todos los campos");
        }

        return vacio;
    }

    //Iniciar sesión del usuario
    private void iniciarSesion(String emailUsuario, String contrasenaUsuario){
        try{
            boolean emailCorrecto = ValidarDatos.validarEmail(emailUsuario);
            boolean contrasenaCorrecta = ValidarDatos.validarContrasena(contrasenaUsuario);

            if ( emailCorrecto /*&& contrasenaCorrecta*/){
                auth.signInWithEmailAndPassword(emailUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            MostratToast.mostrarToast(InicioSesion.this, "Bienvenido.");
                            cambiarActivity(Inicio.class);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mostrarError("Correo o contraseña incorrectos.");
                    }
                });

            } else if(!emailCorrecto){
                mostrarError("Email no valido.");

            } else {
                if(contrasenaUsuario.length() < 6){
                    mostrarError("La contraseña debe tener al ménos 6 carácteres.");
                } else {
                    mostrarError("La contraseña debe incluir al menos una letra mayúscula, un número y un carácter especial.");
                }
            }
        }catch (Exception ex){
            MostratToast.mostrarToast(this, "Error al registrar sesión");
            ex.printStackTrace();
        }
    }

    //Mostrar el error por la interfaz.
    private void mostrarError(String mensaje){
        if(error.getVisibility() == View.GONE ){
            error.setVisibility(View.VISIBLE);
        }
        error.setText(mensaje);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){
            cambiarActivity(Inicio.class);
        }
    }


    //Cambiar a otra interfaz.
    private void cambiarActivity(Class<?> activity) {
        finish();
        CambiarActivity.cambiar(this, activity);
    }
}