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
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.controlador.ValidarDatos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegistroSesion extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseManager firebaseManager;
    EditText nombre, email, contrasena;
    TextView error;
    Button registrarse, iniciarSesion;

    String nombreUsuario, emailUsuario, contrasenaUsuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sesion);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();

        //Referencias de las vistas.
        nombre = findViewById(R.id.nombreTV);
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);

        error = findViewById(R.id.error);

        registrarse = findViewById(R.id.registrarse);
        iniciarSesion = findViewById(R.id.iniciar_sesion);

        //Listeners.
        registrarse.setOnClickListener(this);
        iniciarSesion.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.registrarse) {
            nombreUsuario = nombre.getText().toString();
            emailUsuario = email.getText().toString();
            contrasenaUsuario = contrasena.getText().toString();

            if(!camposVacios(nombreUsuario, emailUsuario, contrasenaUsuario)){
                registro(nombreUsuario, emailUsuario, contrasenaUsuario);

            }

        } else if (id == R.id.iniciar_sesion) {
            cambiarActivity(InicioSesion.class);

        }
    }

    //Comprueba que todos los campos estén rellenos.
    private boolean camposVacios(String nombreUsuario, String emailUsuario, String contrasenaUsuario){
        boolean vacio;

        if(!nombreUsuario.isEmpty() && !emailUsuario.isEmpty() && !contrasenaUsuario.isEmpty() ){
            vacio = false;
        }else {
            vacio = true;
            mostrarError("Complete todos los campos");
        }

        return vacio;
    }

    //Registrar al usuario.
    private void registro(String nombreUsuario, String emailUsuario, String contrasenaUsuario){
        try{
            boolean emailCorrecto = ValidarDatos.validarEmail(emailUsuario);
            boolean contrasenaCorrecta = ValidarDatos.validarContrasena(contrasenaUsuario);

            if ( emailCorrecto /*&& contrasenaCorrecta*/){
                auth.createUserWithEmailAndPassword(emailUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                            firebaseManager.agregarUsuario(RegistroSesion.this, id, contrasenaUsuario, emailUsuario, nombreUsuario);

                            MostratToast.mostrarToast(RegistroSesion.this, "Usuario registrado correctamente");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        MostratToast.mostrarToast(RegistroSesion.this, "Error al registrar sesión");
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

    //Cambiar a otra interfaz.
    private void cambiarActivity(Class<?> activity) {
        finish();
        CambiarActivity.cambiar(this, activity);
    }
}